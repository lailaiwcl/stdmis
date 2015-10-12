<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>管理员用户管理</title>
<%@include file="/common/common.jsp" %>
</head>
<body>
 <c:auth sessionName="<%=user%>" resourcesID="userMrg">
    <div style="width:100%;">
        <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                    <c:auth sessionName="<%=user%>" resourcesID="userMrgAdd">
                        <a class="nui-button" iconCls="icon-add" onclick="add()">增加</a>
                    </c:auth>
                    <c:auth sessionName="<%=user%>" resourcesID="userMrgEdit">
                        <a class="nui-button" iconCls="icon-edit" onclick="edit()">编辑</a>
                    </c:auth>
                    <c:auth sessionName="<%=user%>" resourcesID="userMrgDel">
                        <a class="nui-button" iconCls="icon-remove" onclick="remove()">删除</a>
                    </c:auth>
                    <c:auth sessionName="<%=user%>" resourcesID="userMrgLock">
                        <a class="nui-button" iconCls="icon-lock" onclick="lock()">锁定</a> 
                    </c:auth>
                    <c:auth sessionName="<%=user%>" resourcesID="userMrgUnLock">
                        <a class="nui-button" iconCls="icon-unlock" onclick="unlock()">解锁</a>
                    </c:auth>
                    <c:auth sessionName="<%=user%>" resourcesID="userMrgLook">
                        <a class="nui-button" iconCls="icon-collapse" onclick="setPermissions(0)">查看权限</a>
                    </c:auth>
                    <c:auth sessionName="<%=user%>" resourcesID="userMrgAlloc">
                        <a class="nui-button" iconCls="icon-user" onclick="setPermissions(1)">分配权限</a>
                    </c:auth>
                    <c:auth sessionName="<%=user%>" resourcesID="userMrgExport">
                        <a class="nui-button" iconCls="icon-node" onclick="exportExcel()">导出excel</a>  
                    </c:auth>        
                    </td>
                    <td style="white-space:nowrap;">
                        <input id="key" class="nui-textbox" emptyText="请输入用户名" style="width:150px;" onenter="search"/>   
                        <a class="nui-button" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div id="datagrid1" class="nui-datagrid" style="width:100%;height:520px;" 
        url="<%=basePath%>usermgr/listUsers" idField="userID"
        allowResize="true" pageSize="20" dataField="data" sizeList="[20,30,40]"
        allowCellSelect="true" multiSelect="true" showColumnsMenu="true">
        <div property="columns">
            <div type="indexcolumn" align="center">编号</div>
            <div type="checkcolumn"></div>
            <div field="userID" visible="false">系统编号</div>
            <div field="userName" allowSort="true">用户名</div>
            <div field="userType" renderer="onUserTypeRenderer" allowSort="true">用户类型</div>
            <div field="userEmail">邮箱</div>
            <div field="loginTimes" allowSort="true">登录次数</div>
            <div field="recentLoginTime" allowSort="true">最近登录时间</div>
            <div field="registerDate"  allowSort="true">注册日期</div>
            <div field="userState" renderer="onUserStateRenderer" allowSort="true">用户状态</div>
    </div>
    </div>
 </c:auth>
    <script type="text/javascript">
        
        nui.parse();

        var grid = nui.get("datagrid1");
        grid.load();

        //////////////////////////////////////////////////////

        function search() {       
            var key = nui.get("key").getValue();
            grid.load({ key: key });
        }
        
        function add(){
            nui.open({
                url: "<%=basePath%>jsp/user/addUser.jsp",
                title: "新增管理员", width: 500, height: 350,
                ondestroy: function (action) {
                    grid.reload();
                }
            });        
        }
  
        function remove() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                nui.confirm("确定删除选中的 " + rows.length + " 条记录？", "确定？",
                    function (action) {            
                        if (action == "ok") {
                            var names = [];
                            for (var i = 0, l = rows.length; i < l; i++) {
                                var r = rows[i];
                                if("sysadmin" == (r.userName).toLowerCase()){
                                     nui.alert("超级管理员用户[ sysadmin ]不能被删除");
                                     continue;
                                }
                                names.push(r.userID);
                             }
                        var ids = names.join(',');
                        $.ajax({
                            url: "<%=basePath%>usermgr/delUser?ids=" +ids,
                            success: function (text) {
                                grid.reload();
                            },
                            error: function () {
                        	    nui.alert("删除失败");
                            }
                        });
                    }
                }
            );
            } else {
                nui.alert("至少选中一条记录");
            }
        }  

        function edit() {
            var rows = grid.getSelecteds();
            if (rows.length == 0){
               nui.alert("至少选中一条记录");
            } else if(rows.length >1){
               nui.alert("只能选中一条记录");
            }else{
               if("sysadmin" == (rows[0].userName).toLowerCase()){
                   nui.alert("超级管理员用户[ sysadmin ]不能编辑");
                   return;
               }
               var id = rows[0].userID;
                nui.open({
                    url: "<%=basePath%>jsp/user/editUser.jsp",
                    title: "编辑管理员信息", width: 500, height: 350,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {id: id};
                        iframe.contentWindow.SetData(data);
                     },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
            }
        } 
        function lock() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                nui.confirm("确定锁定选中的 " + rows.length + " 个管理员用户？", "确定？",
                    function (action) {            
                        if (action == "ok") {
                            var names = [];
                            for (var i = 0, l = rows.length; i < l; i++) {
                                var r = rows[i];
                                if("sysadmin" == (r.userName).toLowerCase()){
                                     nui.alert("超级管理员用户[ sysadmin ]不能被锁定");
                                     continue;
                                }                                
                                names.push(r.userID);
                            }
                            var ids = names.join(',');
                            $.ajax({
                                url: "<%=basePath%>usermgr/lockUser?ids=" +ids,
                                success: function (text) {
                                    grid.reload();
                                },
                                error: function () {
                        	        nui.alert("锁定失败");
                                }
                            });
                        }
                    }
                );
            } else {
                nui.alert("至少选中一条记录");
            }
        }
        
        function unlock() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                nui.confirm("确定解锁选中的 " + rows.length + " 个管理员用户？", "确定？",
                    function (action) {            
                        if (action == "ok") {
                            var names = [];
                            for (var i = 0, l = rows.length; i < l; i++) {
                                var r = rows[i];
                                names.push(r.userID);
                            }
                            var ids = names.join(',');
                            $.ajax({
                                url: "<%=basePath%>usermgr/unlockUser?ids=" +ids,
                                success: function (text) {
                                    grid.reload();
                                },
                                error: function () {
                        	        nui.alert("解锁失败");
                                }
                            });
                        }
                    }
                );
            } else {
                nui.alert("至少选中一条记录");
            }
        }
        
        function setPermissions(type){
            var rows = grid.getSelecteds();
            if (rows.length == 0){
               nui.alert("至少选中一条记录");
            } else if(rows.length >1){
               nui.alert("只能选中一条记录");
            }else{
               var id = rows[0].userID;
               var userName = rows[0].userName;
               var windowsName = "查看权限";
               if(type==1){
                   windowsName = "分配权限";
               }
              
                nui.open({
                    url: "<%=basePath%>jsp/permission/permission.jsp",
                    title: windowsName + "(" + userName +")", width: 400, height: 580,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {id: id, userName: userName, type: type};
                        iframe.contentWindow.SetData(data);
                    },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
            }
        
        }
        
        function exportExcel() {
            nui.confirm("确定导出 所有管理员信息", "确定？",
                function (action) {            
                    if (action == "ok") {
                       window.location="<%=basePath%>usermgr/exportExcel";
                    }
                }
            );
        } 
        
                
        function onUserTypeRenderer(e){
            var value = e.value;
            if(value==0){
              return "<font color='#ff0000'>系统用户</font>";
            }else if(value==1){
              return "<font color='#0000ff'>老师</font>";
            }else if(value==2){
              return "<font color='#0000ff'>学生</font>";
            }else if(value==3){
              return "<font color='#0000ff'>访客</font>";
            }
        }
        
        function onUserStateRenderer(e){
            var value = e.value;
            if(value==0){
              return "<font color='#0000ff'>正常</font>";
            }else if(value==1){
              return "<font color='#ff0000'>锁定</font>";
            }else{
              return "<font color='#ff0000'>未知</font>";
            }
        }
        
        function onRencentLoginTimeRenderer(e) {
            var value = e.value;
            if (value) return nui.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
            return "从未登录";
        }        
    </script>
</body>
</html>