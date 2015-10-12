<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>入侵返回日志</title>
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
        url="<%=basePath%>aggressionlogmgr/listWithPage" idField="logID"
        allowResize="true" pageSize="20" dataField="data" sizeList="[20,30,40]"
        allowCellSelect="true" multiSelect="true" showColumnsMenu="true">
        <div property="columns">
            <div type="indexcolumn" align="center" width="5%">编号</div>
            <div type="checkcolumn" width="5%"></div>
            <div field="logID" visible="false">系统编号</div>
            <div field="aggressionIP" width="10" allowSort="true">入侵者IP</div>
            <div field="requstUrl" allowSort="true" width="20%">请求url</div>
            <div field="aggressionType"  renderer="onTypeRenderer" allowSort="true" width="8%">入侵类型</div>
            <div field="aggressionTime" allowSort="true" width="7%">入侵时间</div>
            <div field="aggressionDetail" allowSort="true" width="45%">详细</div>
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