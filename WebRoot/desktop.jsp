<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>学生管理系统(桌面版)</title>
<%@include file="/common/common.jsp" %>

<style type="text/css">
    a {
	display: inline;
	color:#3399CC;
	text-decoration: none;
    }
    .logoutText{
      cursor:pointer 
    }
</style>
<link href="<%=basePath%>desktop/js/desktop.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>desktop/js/DeskTop.js" type="text/javascript"></script>
<script src="<%=basePath%>desktop/js/Window.js" type="text/javascript"></script>

<script src="<%=basePath%>desktop/js/windows/IFrameWindow.js" type="text/javascript"></script>
<script src="<%=basePath%>desktop/js/windows/GridWindow.js" type="text/javascript"></script>
<script src="<%=basePath%>desktop/js/windows/TabsWindow.js" type="text/javascript"></script>
<script src="<%=basePath%>desktop/js/windows/SplitterWindow.js" type="text/javascript"></script>
<script src="<%=basePath%>desktop/js/windows/ChartWindow.js" type="text/javascript"></script>
</head>
<body>
<input id="userType" type="hidden" value="<c:user fieldName='userType'/>" />
<div class="bar_top">
<table width="100%"><tr><td width="700px" valign="top">
&nbsp;&nbsp;<span>欢迎您，<strong><c:user fieldName="userName"/></strong></span> &nbsp;&nbsp;
[<a href="#"><span id="userTypeSpan"></span></a>，<a href="#" onclick="logout()">退出</a>]&nbsp;&nbsp;&nbsp;&nbsp;
<span><script src="<%=basePath%>desktop/js/Clock.js" type="text/javascript"></script></span>
</td>
<td align="right">
[<a href="#" onclick="changeIndex('index.html')">切换到标准版</a>]&nbsp;&nbsp;[<a href="#" onclick="changeIndex('dwz.html')">切换到dwz版</a>]&nbsp;&nbsp;[<span class="logoutText" onclick="logout()"><font color="#8A8A8A">退出]</font></span>&nbsp;&nbsp;
</td></tr></table>
</div>
<script type="text/javascript">
    var modules = [
        { name: "userManager", text: "用户管理", iconCls: "nui-desktop-module-icon-user", title: "用户管理", type: "ux.iframewindow", url: "<%=basePath%>jsp/user/listUser.jsp" },
        { name: "classManager", text: "班级管理", iconCls: "nui-desktop-module-icon-classManager", title: "班级管理", type: "ux.iframewindow", url: "<%=basePath%>jsp/classes/listClass.jsp" },
        { name: "studentManager", text: "学生管理", iconCls: "nui-desktop-module-icon-student", title: "学生管理", type: "ux.iframewindow", url: "<%=basePath%>jsp/student/listStudent.jsp" },
        { name: "grid", text: "角色管理", iconCls: "nui-desktop-module-icon-role", title: "角色管理", type: "ux.iframewindow", url: "<%=basePath%>jsp/role/listRole.jsp" },
        { name: "grid", text: "系统设置", iconCls: "nui-desktop-module-icon-setup", title: "系统设置", type: "ux.iframewindow", url: "<%=basePath%>jsp/role/listRole.jsp" }
    ];
    /////////////////////////////////////////////////

    //desktop
    var desk = new nui.ux.DeskTop();
    desk.render(document.body);

    //modules
    desk.setModules(modules);

    //module click
    desk.on("moduleclick", function (e) {
        var module = e.module;

        var win = module.single;
        if (!win) {
            win = module.single = desk.createWindow(module.type);
            if (win) {
                if (win.type == "ux.iframewindow") {
                    win.setUrl(module.url);
                    win.setTitle(module.title);
                    win.setWidth(module.width);
                    win.setHeight(module.height);
                }
            }
        }
        if (win) {
            desk.showWindow(win);

        }
    });
    
</script>
<script type="text/javascript">
    function logout(){
                nui.confirm("确认注销用户？", "确定？",
                    function (action) {            
                        if (action == "ok") {
                        $.ajax({
                            url: "<%=basePath%>usermgr/logout",
                            success: function (text) {
    			            	location.href = "<%=basePath%>login.jsp";                               
                            },
                            error: function () {
                        	    nui.alert("注销失败");
                            }
                        });
                    }
                }
            );    
    }
    
  function changeIndex(src){
       window.top.location.href = "<%=basePath%>" + src;
  }
  
    function lock(){
        $.ajax({
            url: "<%=basePath%>usermgr/logout",
            success: function (text) {
                nui.open({
                    url: "<%=basePath%>relogin.jsp?userName='lailaiwcl'",
                    title: "用户锁定", width: 300, height: 200,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {userName:"lailaiwcl"};
                        iframe.contentWindow.SetData(data);
                     },
                ondestroy: function (action) {
                nui.confirm("窗口关闭将跳转到登录页面，确认关闭？", "确定？",
                    function (action) {            
                        if (action == "ok") {
                            window.location.href="login.jsp";
                        }
                }
            );    
                }
            });                              
            },
            error: function () {
               nui.alert("注销失败");
           }
       });
       
        
    }
    
var userType = document.getElementById("userType").value;
var userTypeSpan = document.getElementById("userTypeSpan");
var userTypeText = "";
if(userType == 0){
   userTypeText = "系统用户";
}else if(userType == 1){
   userTypeText = "老师";
}else if(userType == 2){
   userTypeText = "学生";
}else if(userType == 3){
   userTypeText = "访客";
}
userTypeSpan.innerHTML = userTypeText;


    window.setTimeout(lock,1000*60*60);
</script>
</body>
</html>