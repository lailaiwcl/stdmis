<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>角色管理</title>
<%@include file="/common/common.jsp" %>
</head>
<body>
    <div style="width:100%;">
        <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                     <a class="nui-button" iconCls="icon-add" onclick="add()">增加</a>
                        <a class="nui-button" iconCls="icon-edit" onclick="edit()">编辑</a>
                        <a class="nui-button" iconCls="icon-remove" onclick="remove()">删除</a>
                        <a class="nui-button" iconCls="icon-collapse" onclick="setPermissions(0)">查看权限</a>
                        <a class="nui-button" iconCls="icon-user" onclick="setPermissions(1)">分配权限</a>
                    </td>
                    <td style="white-space:nowrap;">
                        <input id="key" class="nui-textbox" emptyText="请输入角色名" style="width:150px;" onenter="search"/>   
                        <a class="nui-button" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div id="datagrid1" class="nui-datagrid" style="width:100%;height:290px;" 
        url="<%=basePath%>rolemgr/listRolesWithPage" idField="roleID"
        allowResize="true" pageSize="10" dataField="data" sizeList="[10,20]"
        allowCellSelect="true" multiSelect="true" >
        <div property="columns">
            <div type="indexcolumn" align="center" width="5">编号</div>
            <div type="checkcolumn" width="5"></div>
            <div field="roleID" visible="false"></div>
            <div field="roleName" allowSort="true" width="10">角色名称</div>
            <div field="notes">角色描述</div>
    </div>
    </div>
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
                url: "<%=basePath%>jsp/role/addRole.jsp",
                title: "新增角色", width: 400, height: 180,
                ondestroy: function (action) {
                    grid.reload();
                }
            });        
        }
  
        function remove() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                nui.confirm("确定删除选中的 " + rows.length + " 条记录？<br/>拥有该角色的用户角色权限<br/>都将被回收。", "确定？",
                    function (action) {            
                        if (action == "ok") {
                            var idArray = [];
                            for (var i = 0, l = rows.length; i < l; i++) {
                                var r = rows[i];
                                idArray.push(r.roleID);
                             }
                        var ids = idArray.join(',');
                        $.ajax({
                            url: "<%=basePath%>rolemgr/delRole?ids=" +ids,
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
               var id = rows[0].roleID;
                nui.open({
                    url: "<%=basePath%>jsp/role/editRole.jsp",
                    title: "编辑角色信息", width: 400, height: 180,
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
        
        function setPermissions(type){
            var rows = grid.getSelecteds();
            if (rows.length == 0){
               nui.alert("至少选中一条记录");
            } else if(rows.length >1){
               nui.alert("只能选中一条记录");
            }else{
               var id = rows[0].roleID;
               var roleName = rows[0].roleName;
               var windowsName = "查看权限";
               if(type==1){
                   windowsName = "分配权限";
               }
              
                nui.open({
                    url: "<%=basePath%>jsp/permission/role.jsp",
                    title: windowsName + "(" + roleName +")", width: 500, height: 650,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {id: id, roleName: roleName, type: type};
                        iframe.contentWindow.SetData(data);
                    },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
            }
        }
        
    </script>
</body>
</html>