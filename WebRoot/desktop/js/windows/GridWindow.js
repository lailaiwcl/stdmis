nui.ux.GridWindow = function () {
    nui.ux.GridWindow.superclass.constructor.call(this);
    this.initControls();
}
nui.extend(nui.ux.GridWindow, nui.ux.Window, {
    title: "Grid Window",
    showToolbar: true,
    bodyStyle: "padding:0",

    width: 550,
    height: 300,
    minWidth: 450,
    minHeight: 200,

    dataUrl: "../data/AjaxService.jsp?method=SearchEmployees",

    initControls: function () {
        var toolbarEl = this.getToolbarEl();
        var footerEl = this.getFooterEl();
        var bodyEl = this.getBodyEl();

        //toolbar
        var topHtml =
            '<div style="padding:5px;">'
                + '<a name="add" class="nui-button">增加</a> '
                + '<a name="edit" class="nui-button">修改</a> '
                + '<a name="remove" class="nui-button">删除</a> '
            + '</div>';
        jQuery(toolbarEl).append(topHtml);

        nui.parse(this.el);

        //body
        this.grid = new nui.DataGrid();
        this.grid.set({
            multiSelect: this.multiSelect,
            style: "width: 100%;height: 100%;",
            borderStyle: "border:0",
            columns: [
                { type: "checkcolumn", header: "#" },
            //{ type: "indexcolumn", header: "#", headerAlign: "center" },
                {header: "帐号", field: "loginname" },
                { header: "姓名", field: "name" }
            ]
        });
        this.grid.setUrl(this.dataUrl);
        this.grid.render(bodyEl);

        this.grid.load();
    }
});
nui.regClass(nui.ux.GridWindow, "ux.gridwindow");