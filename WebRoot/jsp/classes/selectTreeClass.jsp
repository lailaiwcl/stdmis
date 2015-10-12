<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<%@include file="/common/common.jsp" %>
</head>
<body>
        <div class="nui-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            <span style="padding-left:5px;">名称：</span>
            <input id="key" class="nui-textbox"  onenter="search"/>
            <a class="nui-button" iconCls="icon-search" plain="true" onclick="search()">查找</a>                  
        </div>
        <ul id="tree1" class="nui-tree" url="<%=basePath%>classmgr/listClass" style="width:100%;padding:1px;"
            showTreeIcon="true" onnodedblclick="onNodeDblClick" checkRecursive="false"  textField="className" idField="classID" parentField="parentClassID" resultAsTree="false">        
        </ul> 

<script type="text/javascript">
        nui.parse();
        var tree = nui.get("tree1");
        //tree.expandLevel(0);
    function GetData() {
        var node = tree.getSelectedNode();
        return node;
    }
        
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
    
    function onKeyEnter(e) {
        search();
    }
    function onNodeDblClick(e) {
        onOk();
    }
    //////////////////////////////////
    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }

    function onOk() {
        var node = tree.getSelectedNode();
        if (node && tree.isLeaf(node) == false) {
            alert("不能选中父节点");
            return;
        }

        CloseWindow("ok");        
    }
    function onCancel() {
        CloseWindow("cancel");
    }
      
</script>
</body>
</html>