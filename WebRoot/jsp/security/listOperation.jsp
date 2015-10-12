<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>系统操作日志</title>
<%@include file="/common/common.jsp" %>
</head>
<body>
    <div style="width:100%;">
        <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="nui-button" iconCls="icon-remove" onclick="remove()">清除日志</a>
                    </td>
                    <td style="white-space:nowrap;">
                        <input id="key" class="nui-textbox" emptyText="请输入描述" style="width:150px;" onenter="search"/>   
                        <a class="nui-button" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div id="datagrid1" class="nui-datagrid" style="width:100%;height:520px;" 
        url="<%=basePath%>operationmgr/listWithPage" idField="operationID"
        allowResize="true" pageSize="20" dataField="data" sizeList="[20,30,40]"
        allowCellSelect="true" multiSelect="true" showColumnsMenu="true">
        <div property="columns">
            <div type="indexcolumn" align="center" width="5%">编号</div>
            <div type="checkcolumn" width="5%"></div>
            <div field="operationID" visible="false">系统编号</div>
            <div field="userID" allowSort="true" width="10%">操作者编号</div>
            <div field="operationContent" width="10%" allowSort="true">操作模块</div>
            <div field="operationIP" allowSort="true" width="20%">操作者IP</div>
            <div field="requestUrl" allowSort="true" width="35%">请求URL</div>
            <div field="operationDateTime" allowSort="true" dateFormat="yyyy-MM-dd hh:mm:ss" width="15%">操作时间</div>
    </div>
    </div>
    <script type="text/javascript">
        
        nui.parse();

        var grid = nui.get("datagrid1");
        grid.load();
        
       function search() {       
            var key = nui.get("key").getValue();
            grid.load({ key: key });
        }

        //////////////////////////////////////////////////////
        function onTypeRenderer(e){
            var value = e.value;
            if(value==0){
              return "sql注入";
            }else{
              return "script脚本注入";
            }
        }
        
    </script>
</body>
</html>