﻿nui.ux.DeskTop = function () {
    this.modules = [];
    this.windows = [];
    nui.ux.DeskTop.superclass.constructor.call(this);
}
nui.extend(nui.ux.DeskTop, nui.Control, {

    windows: [],
    active: null,

    modules: [],
    moduleHeight: 95,
    modulesOffset: 20,
    showTaskbar: true,

    _create: function () {
        this.el = document.createElement("div");
        this.el.className = "nui-desktop";

        this.el.innerHTML = '<div class="nui-desktop-viewport"><div class="nui-desktop-modules"></div></div><div class="nui-desktop-taskbar"></div>';
        this._viewportEl = this.el.firstChild;
        this.taskbarEl = this.el.lastChild;
        this.modulesEl = this._viewportEl.firstChild;

        this.taskbarEl.innerHTML = '<div class="nui-desktop-bars"></div>';
        this.barsEl = this.taskbarEl.firstChild;
    },
    _initEvents: function () {
        nui.on(window, "resize", function (e) {
            this.doLayout();
        }, this);
        nui.on(this.el, "click", this.__OnClick, this);
    },
    doLayout: function () {
        this._viewportEl.style.height = this.getViewportHeight() + "px";
        this._doUpdateModules();

        nui.layout(this._viewportEl);
    },
    getViewportHeight: function () {
        var viewBox = nui.getViewportBox();
        var taskbarHeight = this.getTaskbarHeight();
        var h = viewBox.height - taskbarHeight;
        if (h < 220) h = 220;
        return h;
    },
    getTaskbarHeight: function () {
        return this.showTaskbar ? this.taskbarEl.offsetHeight : 0;
    },

    /////////////////////////////////////////////////////////
    //Modules
    _doUpdateModules: function () {
        var sb = [];
        var columns = this._getModuleColumns();
        for (var j = 0, k = columns.length; j < k; j++) {
            var column = columns[j];
            sb.push('<div class="nui-desktop-modules-list">');
            for (var i = 0, l = column.length; i < l; i++) {
                var m = column[i];
                var text = m.text || "";
                var icon = m.iconCls;
                var s1 = '<div class="' + icon + '"></div>';
                var s2 = '<div class="nui-desktop-module-text">' + text + '</div>';
                sb.push('<div id="' + m._id + '" title="' + m.title + '" class="nui-desktop-module">' + s1 + s2 + '</div>');
            }
            sb.push('</div>');
        }

        var s = sb.join('');
        this.modulesEl.innerHTML = s;
    },
    _getModuleColumns: function () {
        var height = this.getViewportHeight();
        height -= this.modulesOffset;
        var columns = [];

        var index = 0;
        var top = 0;
        var mh = this.moduleHeight;
        for (var i = 0, l = this.modules.length; i < l; i++) {
            var m = this.modules[i];
            if (top + mh > height) {
                index++;
                top = mh;
            } else {
                top += mh;
            }
            var column = columns[index];
            if (!column) {
                column = columns[index] = [];
            }
            column.push(m);
        }
        return columns;
    },
    setModules: function (value) {
        if (!nui.isArray(value)) value = [];
        this.modules = value;
        for (var i = 0, l = this.modules.length; i < l; i++) {
            var m = this.modules[i];
            m._id = nui.ux.DeskTop.ModuleID++;
        }
    },
    getModule: function (name) {
        if (typeof name == "object") return name;
        for (var i = 0, l = this.modules.length; i < l; i++) {
            var m = this.modules[i];
            if (m.name == name) return m;
        }
        return null;
    },
    _getModuleByID: function (id) {
        for (var i = 0, l = this.modules.length; i < l; i++) {
            var m = this.modules[i];
            if (m._id == id) return m;
        }
        return null;
    },
    /////////////////////////////////
    //Window
    findWindow: function (id) {
        return nui.get(id);
    },
    setWindow: function (win, options) {
        /*
        title, iconCls, 
        showCloseButton, showMaxButton, showMinButton
        x, y, width, height
        showModal, allowResize, allowDrag
        */
        if (!win || !options || typeof options != "object") return;

        win.set(options);
        this._doUpdateBars();
    },
    createWindow: function (type) {
        if (typeof type == "string") {
            type = nui.getClass(type);
        }
        if (!type) return null;

        //create
        var win = new type();
        win._containerEl = this._viewportEl;
        win.render(this._viewportEl);

        this.windows.push(win);

        //
        win.on("beforebuttonclick", this.__OnWindowBeforeButtonClick, this);

        var me = this;
        nui.on(win.el, "mousedown", function (e) {
            me.activeWindow(win);
        });

        return win;
    },
    destroyWindow: function (win) {
        if (!win) return;
        win.destroy();
        this.windows.remove(win);
        this._doUpdateBars();
    },
    showWindow: function (win, x, y) {
        if (!win) return;
        win._minState = false;
        win.show(x, y);

        this.activeWindow(win);

        ///////////////        
        var box = nui.getBox(win.el);
        var barBox = this.getBarBox(win);

        win.el.style.display = "none";

        //amin
        var el = nui.append(document.body, '<div class="nui-desktop-proxy"></div>');
        nui.setBox(el, barBox);

        jQuery(el).animate(
            { width: box.width, height: box.height, left: box.x, top: box.y },
            160,
            function () {
                jQuery(el).remove();
                win.el.style.display = "block";
            }
        );
        //////////////////
    },
    hideWindow: function (win) {
        if (!win) return;
        win.hide();

        var active = this.getNextActiveWindow();
        this.activeWindow(active);
    },
    maxWindow: function (win) {
        if (!win) return;
        win._minState = false;
        win.max();

        this.activeWindow(win);
    },
    restoreWindow: function (win) {
        if (!win) return;
        win._minState = false;

        win.restore();
        this.activeWindow(win);


    },
    minWindow: function (win) {
        if (!win) return;
        win._minState = true;

        ///////////////
        var box = nui.getBox(win.el);
        var barBox = this.getBarBox(win);
        //amin
        var el = nui.append(document.body, '<div class="nui-desktop-proxy"></div>');
        nui.setBox(el, box);

        jQuery(el).animate(
            { width: 150, height: 100, left: barBox.x, top: this.getViewportHeight() - 100 },
            160,
            function () {
                jQuery(el).remove();
            }
        );
        //////////////////
        //win.setVisible(false);
        win.el.style.display = "none";
        var active = this.getNextActiveWindow();
        this.activeWindow(active);
    },
    getNextActiveWindow: function (win) {
        var active = null;
        var maxZindex = 0;
        for (var i = 0, l = this.windows.length; i < l; i++) {
            var win = this.windows[i];
            var zindex = win._ZIndex;
            if (win.visible == true
                    && this.getWindowState(win) != "min"
                    && win != this.active
            ) {
                if (zindex > maxZindex) {
                    maxZindex = zindex;
                    active = win;
                }
            }
        }
        return active;
    },
    activeWindow: function (win) {
        //if(win) win.setVisible(true);
        this.active = win;
        if (win) {
            win._ZIndex = win.el.style.zIndex = nui.ux.DeskTop.maxZIndex();
        }
        this._doUpdateBars();
    },
    getWindowState: function (win) {
        if (win._minState == true) return "min";
        return win.state;
    },
    _doUpdateBars: function () {
        var sb = [];

        for (var i = 0, l = this.windows.length; i < l; i++) {
            var win = this.windows[i];
            if (win.visible == true) {
                var text = win.title;
                var id = win.id + "$bar";
                var cls = "";
                if (this.active == win) {
                    cls = "nui-desktop-bar-active";
                }
                sb.push('<a id="' + id + '" href="javascript:;" hidefocus onclick="return false" class="nui-desktop-bar ' + cls + '"><span class="nui-desktop-bar-text">' + text + '</span></a>');
            }
        }
        var s = sb.join('');
        this.barsEl.innerHTML = s;
    },
    getBarBox: function (win) {
        var id = win.id + "$bar";
        var el = document.getElementById(id);
        return nui.getBox(el);
    },
    /////////////////////////////////
    __OnWindowBeforeButtonClick: function (e) {
        e.cancel = true;

        var win = e.sender;
        if (e.name == "close") {
            this.hideWindow(win);
        }
        if (e.name == "max") {
            var state = this.getWindowState(win);
            if (state == "max") {
                this.restoreWindow(win);
            } else {
                this.maxWindow(win);
            }
        }
        if (e.name == "min") {
            this.minWindow(win);
        }

    },
    __OnClick: function (e) {
        //module
        var t = nui.findParent(e.target, "nui-desktop-module");
        if (t) {
            var module = this._getModuleByID(t.id);
            if (module) {
                var ev = {
                    module: module,
                    name: module.name,
                    htmlEvent: e
                };
                this.fire("moduleclick", ev);
            }
        }
        //bar
        var t = nui.findParent(e.target, "nui-desktop-bar");
        if (t) {
            var id = t.id.substr(0, t.id.length - "$bar".length);
            var win = this.findWindow(id);
            var state = this.getWindowState(win);
            if (state != "min") {
                this.minWindow(win);
            } else {
                this.showWindow(win, win.x, win.y);
            }
        }
    }
});
nui.regClass(nui.ux.DeskTop, "ux.desktop");

nui.ux.DeskTop.ModuleID = 1;
nui.ux.DeskTop.WindowID = 1;

nui.ux.DeskTop.zIndex = 1000;
nui.ux.DeskTop.maxZIndex = function () {
    return nui.ux.DeskTop.zIndex++;
}