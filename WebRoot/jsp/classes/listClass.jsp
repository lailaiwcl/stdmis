<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>班级信息列表</title>
<%@include file="/common/common.jsp" %>
</head>
<body>
<c:auth sessionName="<%=user%>" resourcesID="classMrg">
    <div style="width:100%;">
        <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <c:auth sessionName="<%=user%>" resourcesID="classMrgAdd">
                             <a class="nui-button" iconCls="icon-add" onclick="add()">增加</a>
                        </c:auth>
                         <c:auth sessionName="<%=user%>" resourcesID="classMrgEdit">
                            <a class="nui-button" iconCls="icon-edit" onclick="edit()">编辑</a>
                        </c:auth>
                         <c:auth sessionName="<%=user%>" resourcesID="classMrgDel">
                            <a class="nui-button" iconCls="icon-remove" onclick="remove()">删除</a>
                        </c:auth>
                         <c:auth sessionName="<%=user%>" resourcesID="classMrgExport">
                            <a class="nui-button" iconCls="icon-node" onclick="exportExcel()">导出excel</a>
                        </c:auth>
                         <c:auth sessionName="<%=user%>" resourcesID="classMrgImport">
                            <a class="nui-button" iconCls="icon-node" onclick="importExcel()">导入信息</a>
                        </c:auth>           
                    </td>
                    <td style="white-space:nowrap;">
                        <input id="key" class="nui-textbox" emptyText="请输入班级名称" style="width:150px;" onenter="onKeyEnter"/>   
                        <a class="nui-button" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div id="datagrid1" class="nui-datagrid" style="width:100%;height:290px;" 
        url="<%=basePath%>classmgr/listClassWithPage" idField="classID"
        allowResize="true" pageSize="10" dataField="data" sizeList="[10,20,40]"
        allowCellSelect="true" multiSelect="true" >
        <div property="columns">
            <div type="indexcolumn" align="center">序号</div>
            <div type="checkcolumn"></div>
            <div field="classID" width="100" allowSort="true">班级编号</div>
            <div field="parentClassID" width="100" allowSort="true">父级编号</div>
            <div field="className" width="150" allowSort="true">班级名称</div>
            <div field="shortClassName" width="100" allowSort="true">班级简称</div>
            <div field="notes" width="100">备注</div>
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
                url: "",
                title: "新增班级", width: 500, height: 350,
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
                                names.push(r.userID);
                             }
                        var ids = names.join(',');
                        $.ajax({
                            url: "<%=basePath%>classmgr/delUser?ids=" +ids,
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
               var id = rows[0].userID;
            nui.open({
                url: "",
                title: "编辑班级信息", width: 500, height: 350,
                onload: function () {
                       var iframe = this.getIFrameEl();
                       var data = {id: rows[0].userID};
                       iframe.contentWindow.SetData(data);
                    },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
            }
        } 
        
        function exportExcel() {
            nui.confirm("确定导出 所有班级信息", "确定？",
                function (action) {            
                    if (action == "ok") {
                       window.location="<%=basePath%>classmgr/exportExcel";
                    }
                }
            );
        } 
        
        function importExcel(){
           nui.open({
                url: "<%=basePath%>jsp/classes/importClass.jsp",
                title: "从Excel中导入班级信息", width: 350, height: 200,
                ondestroy: function (action) {
                    grid.reload();
                }
            }); 
        }
        
    </script>
</body>
</html>