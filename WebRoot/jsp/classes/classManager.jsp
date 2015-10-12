<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<%@include file="/common/common.jsp" %>
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style> 
</head>
<body>

<div class="nui-splitter" style="width:100%;height:100%;">
    <div size="240" showCollapseButton="true">
        <div class="nui-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            <span style="padding-left:5px;">名称：</span>
            <input id="key" class="nui-textbox"  onenter="search"/>
            <a class="nui-button" iconCls="icon-search" plain="true" onclick="search()">查找</a>                  
        </div>
        <div class="nui-fit">
        <ul id="tree1" class="nui-tree" url="<%=basePath%>userclassmgr/getPermissionUserByUserID?userID=<c:user fieldName='userID'/>" style="width:100%;padding:1px;"
            showTreeIcon="true" onnodecheck="onNodeCheck" checkRecursive="false"  expandOnLoad="0" textField="className" idField="classID" parentField="parentClassID" resultAsTree="false">        
        </ul> 
        </div>
    </div>
    <div showCollapseButton="true">
        <div class="nui-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            
            <a class="nui-button" iconCls="icon-add" plain="true" onclick="addRow()">新增</a>
            <a class="nui-button" iconCls="icon-remove" plain="true" onclick="removeRow()">删除</a>     
            <span class="separator"></span>             
            <a class="nui-button" iconCls="icon-save" plain="true" onclick="saveData()">保存</a>                  
        </div>
        <div class="nui-fit" >


        </div>
    </div>        
</div>

<script type="text/javascript">
        nui.parse();
        var tree = nui.get("tree1");
        tree.expandLevel(0);
        //tree.expandLevel(1);
        
    function search() {
        var key = nui.get("key").getValue();
        if(key == ""){
            tree.clearFilter();
        }else{
            key = key.toLowerCase();
            tree.filter(function (node) {
                var text = node.className ? node.className.toLowerCase() : "";
                if (text.indexOf(key) != -1) {
                    var childNode = tree.getParentNode(node);
                    tree.expandNode (childNode);
                    return true;
                }
            });
        }
    }        
</script>
</body>
</html>