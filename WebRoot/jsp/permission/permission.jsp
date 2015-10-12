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
        margin:0;
        border:0;
        height:100%;
        overflow:hidden;
    }
    </style>
    </head>
<body>
<div id="mainTabs" class="nui-tabs" activeIndex="0" style="width:100%;height:100%;">
    <div title="系统权限">
        <div align="right">
            <a class="nui-button" id="submitSystemPermission" iconCls="icon-goto" onclick="submitSystemPermission()">提交</a>
            <a class="nui-button" iconCls="icon-close" onclick="cancel()">关闭</a>
        </div>
        <br />
        <fieldset style="border: solid 1px #aaa; padding: 3px;">
		<legend>角色选择</legend>
        <div id="systempermission" class="nui-checkboxlist" repeatItems="3" repeatLayout="table"
            textField="roleName" valueField="roleID"
            url="<%=basePath%>rolemgr/listrole" >
        </div>
        </fieldset>
        <br />
        <br />
        <font color="red">说明：</font>
        <hr />
                     用户拥有了该角色，就拥有了该角色的所有功能。
    </div>
    <div title="部门权限">
        <div align="right">
            <a class="nui-button" id="submitClassPermission" iconCls="icon-goto" onclick="submitClassPermission()">提交</a>
            <a class="nui-button" iconCls="icon-close" onclick="cancel()">关闭</a>
        </div>
        <a href="#" onclick="expandAll()">展开树</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  href="#" onclick="collapseAll()">折叠树</a>
        <ul id="tree1" class="nui-tree" url="" style="width:100%;padding:1px;"
            showTreeIcon="true" onnodecheck="onNodeCheck" checkRecursive="false"  autoCheckParent="true" expandOnLoad="0" showCheckBox="true" textField="className" idField="classID" parentField="parentClassID" resultAsTree="false">        
        </ul>  
    </div>
</div> 
<script type="text/javascript">
    nui.parse();
    var tree = nui.get("tree1");
    var checkBBoxList = nui.get("systempermission");
	var userID;
	var userName;
	var type;
    function SetData(data) {
         data = nui.clone(data);
         userID = data.id;
         userName = data.userName;
         type = data.type;
         loadTree();
         loadCheckBoxList();
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
                url: "<%=basePath%>userclassmgr/getUserClassByUserID?userID=" + userID,
                success: function (text) {
                    var obj = eval('(' + text + ')');
                    if(type == 0){
                        tree.setShowCheckBox(false);
                        tree.load("<%=basePath%>userclassmgr/getPermissionUserByUserID?userID=" + userID);
                        checkBBoxList.disable();
                        var submitSystemPermission = nui.get("submitSystemPermission");
                        var submitClassPermission = nui.get("submitClassPermission");
                        submitSystemPermission.disable();
                        submitClassPermission.disable();
                        var selectedNodes=[];
                        for(var i = 0 ; i < obj.length ; i++){
                            var node = tree.getNode(obj[i].classID);
                            selectedNodes.push(node);
                        }
                        tree.expandAll();                  
                    }else if(type == 1){
                        tree.setShowCheckBox(true);
                        tree.load("<%=basePath%>classmgr/listClass");
                        for(var i = 0 ; i < obj.length ; i++){
                            var node = tree.getNode(obj[i].classID);
                            tree.checkNode(node);
                        }
                    }
                },
                error: function () {
                	nui.alert("班级权限数据加载失败");
                }
            });    
    }
    
    function loadCheckBoxList(){
        $.ajax({
            url: "<%=basePath%>userrolemgr/getUserRoleByUserID?userID=" + userID,
            success: function (text) {
                var obj = eval('(' + text + ')');
                var valueArray=[];
                for(var i = 0 ; i < obj.length ; i++){
                   valueArray.push(obj[i].roleID);
                }
                var value =  valueArray.join(",");
                checkBBoxList.setValue(value);
            },
            error: function () {
             nui.alert("系统权限数据加载失败");
            }
        });        
    }
    
    function getTreeData(){
        var nodes = tree.getCheckedNodes();
        var ids = [];
        for (var i = 0, l = nodes.length; i < l; i++) {
            var node = nodes[i];
            ids.push(node.classID);
        }
        var data =  ids.join(",");
        return data;
    }
    
    function submitClassPermission(){
        nui.confirm("确定修改用户["+userName+"]的班级权限", "确定？",
            function (action) {            
                if (action == "ok") {
                    $.ajax({
                        url: "<%=basePath%>userclassmgr/delUserClassByUserID?userID=" + userID,
                        success: function (text) {
                            addUserClass();
                        },
                        error: function () {
                        	nui.alert("提交失败");
                        }
                    });
                }
            }
        );   
    }
    
    
    function submitSystemPermission(){
        nui.confirm("确定修改 " + userName + " 用户的系统权限", "确定？",
            function (action) {            
                if (action == "ok") {
                    $.ajax({
                        url: "<%=basePath%>userrolemgr/delUserRoleByUserID?userID=" + userID,
                        success: function (text) {
                            addUserRole();
                        },
                        error: function () {
                        	nui.alert("提交失败");
                        }
                    });
                }
            }
        );   
    }
    
    
    function addUserClass(){
        var classIDs = getTreeData();
        $.ajax({
            url: "<%=basePath%>userclassmgr/addUserClass?userID=" + userID + "&classIDs=" + classIDs,
            success: function (text) {
                nui.alert("保存成功");
            },
            error: function () {
                nui.alert("保存失败");
            }
       });   
    }

    function addUserRole(){
        var roleIDs = checkBBoxList.getValue();
        $.ajax({
            url: "<%=basePath%>userrolemgr/addUserRole?userID=" + userID + "&roleIDs=" + roleIDs,
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