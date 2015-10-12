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
                <input id="key2" class="nui-textbox" emptyText="请输入老师姓名" style="width:150px;" onenter="searchTeacher()"/>   
                <a class="nui-button" onclick="searchTeacher()">查询</a>
              </td>
            </tr>
        </table>   
    </div>
    <div id="datagrid1" class="nui-datagrid" style="width:100%;height:92%;" 
        url="<%=basePath%>teachermgr/listWithPage" idField="teacherNo"
        allowResize="true" pageSize="20" dataField="data" sizeList="[10,20,30]"
        allowCellSelect="true" multiSelect="true" onrowdblclick="onRowDblClick">
        <div property="columns">
            <div type="indexcolumn" width="5%">序号</div>
            <div type="checkcolumn" width="5%"></div>
            <div type="expandcolumn" width="5%">&nbsp;</div>
            <div field="teacherNo" width="10%" allowSort="true">工号</div>
            <div field="teacherName" width="7%" allowSort="true">姓名</div>
            <div field="teacherSex" width="5%" allowSort="true" renderer="onGenderRenderer">性别</div>
            <div field="teacherMobile" width="10%" allowSort="true">手机</div>
            <div field="teacherEmail" width="12%" allowSort="false">邮箱</div>
            <div field="teacherProfessional" width="8%" renderer="onProfessionalRenderer">职称</div>
            <div field="deptNo" width="15%" renderer="onDeptRenderer" allowSort="true">部门</div>
            <div field="teacherPoltics" width="8%" renderer="onPoliticsRenderer" allowSort="true">政治面貌</div>
            <div field="teacherOfficePhone" width="10%" allowSort="false">办公室号码</div>
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
        
        var Genders = [{ id: 0, text: '男' }, { id: 1, text: '女'}, { id: 2, text: '未知'}];
        var Politics = [{ id: 0, text: '群众' }, { id: 1, text: '团员'}, { id: 2, text: '中共预备党员'},{ id: 3, text: '中共党员' }, { id: 4, text: '其他党派'}];
         var Professionals = [{ id: 0, text: '讲师 '}, { id: 1, text: '副教授'}, { id: 2, text: '教授'},{ id: 3, text: '其他' }];
        nui.parse();
        
       function onGenderRenderer(e) {
            for (var i = 0, l = Genders.length; i < l; i++) {
                var g = Genders[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
        
        function onProfessionalRenderer(e){
            for (var i = 0, l = Professionals.length; i < l; i++) {
                var g = Professionals[i];
                if (g.id == e.value) return g.text;
            }
            return "";  
        }
        
        function onSchoolAreaRenderer(e){
            var value = e.value;
            if(value=="0"){
                return "东校区";
            }else if(value=="1"){
                 return "本部";
            }
            return "";
        }
        
        function onPoliticsRenderer(e){
            for (var i = 0, l = Politics.length; i < l; i++) {
                var g = Politics[i];
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
        
        function onDeptRenderer(e){
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