nui.ux.IFrameWindow = function () {
    nui.ux.IFrameWindow.superclass.constructor.call(this);    
}
nui.extend(nui.ux.IFrameWindow, nui.ux.Window, {
    title: "用户管理",
    url: "",
    width: 1000,
    height: 400
});
nui.regClass(nui.ux.IFrameWindow, "ux.iframewindow");