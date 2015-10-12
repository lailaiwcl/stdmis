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
            <input id="key" class="nui-textbox" emptyText="请输入部门名称" onenter="search"/>
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
       <table style="width:100%;">
           <tr>
               <td style="width:100%;">
                   <a class="nui-button" iconCls="icon-add" plain="true" onclick="addRow()">新增</a>
                   <span class="separator"></span>     
                   <a class="nui-button" iconCls="icon-edit" plain="true" onclick="saveData()">编辑</a>
                   <span class="separator"></span> 
                   <a class="nui-button" iconCls="icon-remove" plain="true" onclick="removeRow()">删除</a>  
              </td>
              <td style="white-space:nowrap;">
                <input id="key2" class="nui-textbox" emptyText="请输入部门名称" style="width:150px;" onenter="searchTeacher()"/>   
                <a class="nui-button" onclick="searchTeacher()">查询</a>
              </td>
            </tr>
        </table>   
    </div>
    <div id="datagrid1" class="nui-datagrid" style="width:100%;height:92%;" 
        url="<%=basePath%>deptmgr/listWithPage" idField="deptNo"
        allowResize="true" pageSize="20" dataField="data" sizeList="[10,20,30]"
        allowCellSelect="true" multiSelect="true">
        <div property="columns">
            <div type="indexcolumn" width="5%">序号</div>
            <div type="checkcolumn" width="5%"></div>
            <div type="expandcolumn" width="5%">&nbsp;</div>
            <div field="deptNo" width="10%" allowSort="true">部门编号</div>
            <div field="deptName" width="20%" allowSort="true">部门名称</div>
            <div field="deptShortName" width="15%" allowSort="true">部门简称</div>
            <div field="parentDeptNo" width="20%" allowSort="true" renderer="onParentDeptNoRenderer">父级部门</div>
            <div field="deptType" width="10%" renderer="onDeptTypeRenderer" allowSort="true">部门类型</div>
            <div field="deptTel" width="10%" allowSort="false" renderer="onProfessionalRenderer">部门电话</div>
        </div>
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
        
         var DeptTypes = [{ id: 0, text: '管理机构 '}, { id: 1, text: '教学机构'}, { id: 2, text: '直属机构'},{ id: 3, text: '独立学院' },{ id: 3, text: '研究机构' }];
        nui.parse();
        
       function onGenderRenderer(e) {
            for (var i = 0, l = Genders.length; i < l; i++) {
                var g = Genders[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
        
        function onDeptTypeRenderer(e){
            for (var i = 0, l = DeptTypes.length; i < l; i++) {
                var g = DeptTypes[i];
                if (g.id == e.value) return g.text;
            }
            return "";  
        }

        function getDeptByID(id){
            var classJson;
            $.ajax({
                url: "<%=basePath%>deptmgr/getModel?id=" + id,
                async : false,
                success: function (text) {
                    classJson = nui.decode(text);
                }
            }); 
            return classJson;        
        }
        
        function onParentDeptNoRenderer(e){
            var json = getDeptByID(e.value);
            if(json != null){
                return json.deptName; 
            }
            return "";  
        }
        
    function GetData() {
        var row = grid.getSelected();
        return row;
    }
    
    function treeNodeSelect(e){
        var node = tree.getSelectedNode();
        grid.setUrl("<%=basePath%>deptmgr/getChildModel?key=" + node.deptNo);
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