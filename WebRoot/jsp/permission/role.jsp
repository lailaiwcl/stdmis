<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>权限管理</title>
		<%@include file="/common/common.jsp"%>
    <style type="text/css">
    html, body
    {
        font-size:14px;
        padding: 3px;
        margin:0;
        border:0;
        height:100%;
        overflow:hidden;
    }
    </style>
    </head>
<body>
        <div align="right">
            <a class="nui-button" id="submitRolePermission" iconCls="icon-goto" onclick="submitRolePermission()">提交</a>
            <a class="nui-button" iconCls="icon-close" onclick="cancel()">关闭</a>
        </div>
        <a href="#" onclick="expandAll()">展开树</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  href="#" onclick="collapseAll()">折叠树</a>
        <ul id="tree1" class="nui-tree" url="" style="width:100%;padding:1px;"
            showTreeIcon="true" onnodecheck="onNodeCheck" checkRecursive="false"  autoCheckParent="true" showCheckBox="true" value="autoID" textField="resourcesName" idField="resourcesID" parentField="parentResourcesID" resultAsTree="false">        
        </ul> 
<script type="text/javascript">
    nui.parse();
    var tree = nui.get("tree1");
    var checkBBoxList = nui.get("systempermission");
	var roleID;
	var roleName;
	var type;
    function SetData(data) {
         data = nui.clone(data);
         roleID = data.id;
         roleName = data.roleName;
         type = data.type;
         loadTree();
        }

    function GetData() {
        var o = form.getData();
        return o;
    }		 
    
    function expandAll(){
        tree.expandAll();
    }
    
    function collapseAll(){
        tree.collapseAll();
    }
    
    function onNodeCheck(e){
        var node = e.node;
        var nodes = tree.getAllChildNodes(node);
        if(tree.isCheckedNode(node)){
            tree.checkNodes(nodes);
        }else{
            tree.uncheckNodes(nodes);
        }
    }
    
    
    function loadTree(){
            $.ajax({
                url: "<%=basePath%>roleResourcesMgr/getResourcesByRoleID?roleID=" + roleID,
                success: function (text) {
                    var obj = eval('(' + text + ')');
                    if(type == 0){
                        tree.setShowCheckBox(false);
                        tree.load("<%=basePath%>roleResourcesMgr/getResourcesByRoleID?roleID=" + roleID);
                        var btn = nui.get("submitRolePermission");
                        btn.setEnabled(false);
                        tree.expandAll();                  
                    }else if(type == 1){
                        tree.setShowCheckBox(true);
                        tree.load("<%=basePath%>resourcesmgr/listAll");
                        var nodes = [];
                        var j = 1;
                        for(var i = 0 ; i < obj.length ; i++){
                            var node = tree.getNode(obj[i].resourcesID);
                            nodes.push(node);
                        }
                        tree.checkNodes(nodes);
                    }
                },
                error: function () {
                	nui.alert("角色["+roleName+"]资源权限数据加载失败");
                }
            });    
    }
    
    function getTreeData(){
        var nodes = tree.getCheckedNodes();
        var ids = [];
        for (var i = 0, l = nodes.length; i < l; i++) {
            var node = nodes[i];
            ids.push(node.autoID);
        }
        var data =  ids.join(",");
        return data;
    }
    
    function submitRolePermission(){
        nui.confirm("确定修改角色["+roleName+"]的资源权限", "确定？",
            function (action) {            
                if (action == "ok") {
                    $.ajax({
                        url: "<%=basePath%>roleResourcesMgr/delUserRoleByRoleID?roleID=" + roleID,
                        success: function (text) {
                            addRoleResources();
                        },
                        error: function () {
                        	nui.alert("提交失败");
                        }
                    });
                }
            }
        );   
    }
    
    
    
    function addRoleResources(){
        var autoIDs = getTreeData();
        $.ajax({
            url: "<%=basePath%>roleResourcesMgr/addRoleResourcesModel?roleID=" + roleID + "&autoIDs=" + autoIDs,
            success: function (text) {
                nui.alert("保存成功");
            },
            error: function () {
                nui.alert("保存失败");
            }
       });   
    }
    
    function cancel() {
         if (window.CloseOwnerWindow){
             return window.CloseOwnerWindow("close");
         }else{
             window.close();
         }
     }
		 </script>
	</body>
</html>