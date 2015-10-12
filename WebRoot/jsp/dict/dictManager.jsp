<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/common.css" type="text/css" />
<title>业务字典管理</title>
<%@include file="/common/common.jsp" %>
</head>
<body>
    <div id="dicttype_grid" class="nui-datagrid" style="width:500px;height:180px;" 
        url="<%=basePath%>dicttypemgr/listWithPage"  idField="dictTypeID"  pageSize="5" sizeList="[5,10,20]"
        onselectionchanged="onSelectionChanged" selectOnLoad="true" 
        editNextOnEnterKey="true" multiSelect="true" allowCellEdit="true">
        <div property="columns"> 
            <div type="indexcolumn" align="center" width="10%">编号</div>
            <div type="checkcolumn" width="10%"></div>          
            <div field="dictTypeID" width="20%" headerAlign="center" >ID</div>                                        
            <div field="dictTypeName" width="60%" headerAlign="left" >业务字典类型名称
                <input property="editor" class="nui-textbox" style="width:100%;" /></div>          
        </div>
    </div> 
    <br />
    
    <div style="width:700px;">
        <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="nui-button" iconCls="icon-add" onclick="addRow()" plain="true">增加</a>
                        <a class="nui-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
                        <span class="separator"></span>
                        <a class="nui-button" iconCls="icon-save" onclick="saveData()" plain="true">保存</a>            
                    </td>
                    <td style="white-space:nowrap;">
                        <input id="key" class="nui-textbox" emptyText="请输入名称" style="width:150px;" onenter="search"/>   
                        <a class="nui-button" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div id="dictentry_grid" class="nui-datagrid" style="width:700px;height:180px;" 
        url="<%=basePath%>dictentrymgr/getModelByType" idField="dictEntryID"
        allowResize="true"  pageSize="5" sizeList="[5,10,20]"
        allowCellEdit="true" allowCellSelect="true" multiSelect="true" 
        editNextOnEnterKey="true">
        <div property="columns">
            <div type="indexcolumn" width="5%">编号</div>
            <div type="checkcolumn" width="5%"></div>               
            <div field="dictEntryID" width="15%" allowSort="true">ID</div>
            <div field="dictTypeID">字典类型</div>                 
            <div field="dictEntryName" width="40%" allowSort="true">业务字典名称
                <input property="editor" class="nui-textbox" style="width:100%;" />
            </div>
            <div field="dictEntryValue" width="20%"  allowSort="true">字典值 
                <input property="editor" class="nui-textbox" style="width:100%;" />
            </div>
            <div field="sortField" width="15%"  allowSort="true">排序值
                <input property="editor" class="nui-spinner"  value="0" style="width:100%;"/>
            </div>              
        </div>
    </div>  
    <br/>
    <br />
     <a class="nui-button" iconCls="icon-reload" onclick="reload()">刷新业务字典缓存</a>
 <script type="text/javascript">
         nui.parse();

        var dicttype_grid = nui.get("dicttype_grid");
        var dictentry_grid = nui.get("dictentry_grid");
        dicttype_grid.load();
        
         function search() {       
            var key = nui.get("key").getValue();
            dictentry_grid.load({ key: key });
        }
        
        function reload(){
           $.ajax({
                url: "<%=basePath%>dicttypemgr/reload",
                type: "get",
                success: function (text) {
                    nui.alert("刷新业务字典缓存成功。");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    nui.alert("刷新业务字典缓存出错。");
                }
            });
        
        }
        
        function onSelectionChanged(e) {
            var grid = e.sender;
            var record = grid.getSelected();
            if (record) {
                dictentry_grid.load({ type: record.dictTypeID });
            }
        }
        function addRow() {          
            var newRow = { name: "New Row" };
            dictentry_grid.addRow(newRow, 0);
        } 
        
        function saveData() {
            var data = dictentry_grid.getChanges();
            alert(data[0].dictEntryName);
            var json = nui.encode(data);
            
            dictentry_grid.loading("保存中，请稍后......");
            $.ajax({
                url: "<%=basePath%>dictentrymgr/save",
                data: { data: json },
                type: "post",
                success: function (text) {
                    dictentry_grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
        }

 </script>
</body>
</html>
