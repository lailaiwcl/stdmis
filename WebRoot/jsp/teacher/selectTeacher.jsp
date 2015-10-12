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
    <div size="200" showCollapseButton="true">
        <div class="nui-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            <span style="padding-left:5px;">名称：</span>
            <input id="key" class="nui-textbox" emptyText="请输入学院名称" onenter="search"/>
            <a class="nui-button" iconCls="icon-search" plain="true" onclick="search()">查找</a>                  
        </div>
        <div class="nui-fit">
        <ul id="tree1" class="nui-tree" url="<%=basePath%>deptmgr/listAll" style="width:100%;padding:1px;"
            showTreeIcon="true" onnodecheck="onNodeCheck" onnodeselect="treeNodeSelect" checkRecursive="false"  expandOnLoad="0" textField="deptName" idField="deptNo" parentField="parentDeptNo" resultAsTree="false">        
        </ul> 
        </div>
    </div>
    <div showCollapseButton="true">
    <div class="nui-toolbar" style="border-bottom:0;padding:2px;">
        <input id="key2" class="nui-textbox" emptyText="请输入老师姓名" style="width:150px;" onenter="searchTeacher()"/>   
        <a class="nui-button" onclick="searchTeacher()">查询</a>
    </div>
    <div id="datagrid1" class="nui-datagrid" style="width:100%;height:82%;" 
        url="<%=basePath%>teachermgr/listWithPage" idField="teacherNo"
        allowResize="true" pageSize="10" dataField="data" sizeList="[10,20,30]"
        allowCellSelect="true" multiSelect="false" onrowdblclick="onRowDblClick">
        <div property="columns">
            <div type="checkcolumn" width="20%"></div>
            <div field="teacherNo" width="30%" allowSort="true">工号</div>
            <div field="teacherName" width="60%" allowSort="true">姓名</div>
        </div>
    </div>
      <div class="nui-toolbar" style="border-bottom:0;padding:2px;" align="right">
        <a class="nui-button" iconCls="icon-goto" style="width:60px;" onclick="onOk()">选择</a>
        <a class="nui-button" iconCls="icon-cancel" style="width:60px;" onclick="onCancel()">取消</a>
      </div>
    </div>      
</div>

<script type="text/javascript">
        nui.parse();
        var tree = nui.get("tree1");
        var grid = nui.get("datagrid1");
        tree.expandLevel(0);
        grid.load();
        //tree.expandLevel(1);
        
    function GetData() {
        var row = grid.getSelected();
        return row;
    }
    
    function treeNodeSelect(e){
        var node = tree.getSelectedNode();
        grid.setUrl("<%=basePath%>teachermgr/listWithPageAndDeptNo?key=" + node.deptNo);
        grid.reload();
        //alert(node.deptNo);
    }
        
    function search() {
        var key = nui.get("key").getValue();
        if(key == ""){
            tree.clearFilter();
        }else{
            key = key.toLowerCase();
            tree.filter(function (node) {
                var text = node.deptName ? node.deptName.toLowerCase() : "";
                if (text.indexOf(key) != -1) {
                    var childNode = tree.getParentNode(node);
                    tree.expandNode (childNode);
                    return true;
                }
            });
        }
    }
   function searchTeacher() {
        var key = nui.get("key2").getValue();
        grid.load({ key: key });
    }
    
    function onRowDblClick(e) {
        onOk();
    }
    
    function onRowDblClick(e) {
        onOk();
    }
    //////////////////////////////////
    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }

    function onOk() {
        CloseWindow("ok");
    }
    function onCancel() {
        CloseWindow("cancel");
    }
       
</script>
</body>
</html>