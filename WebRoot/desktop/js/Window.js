nui.ux.Window = function () {
    nui.ux.Window.superclass.constructor.call(this);    
}
nui.extend(nui.ux.Window, nui.Window, {
    title: "New Window",
    width: 500,
    height: 280,
    minWidth: 300,
    minHeight: 200,

    showMaxButton: true,
    showMinButton: true,
    showModal: false,
    allowResize: true,

    uiCls: "nui-ux-window"
});
nui.regClass(nui.ux.Window, "ux.window");