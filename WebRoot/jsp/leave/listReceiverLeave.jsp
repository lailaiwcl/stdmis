<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>请假管理</title>
<%@include file="/common/common.jsp" %>
</head>
<body>
    <div style="width:100%;">
        <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                     <a class="nui-button" iconCls="icon-lock" onclick="edit()">审批</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <input id="receiverUserName" type="hidden" value ="<c:user fieldName='userName'/>"/>
    <div id="datagrid1" class="nui-datagrid" style="width:100%;height:290px;" 
        url="<%=basePath%>leavemgr/getByReceiverUserID" idField="leaveID"
        allowResize="true" pageSize="10" dataField="data" sizeList="[10,20]"
        allowCellSelect="true" multiSelect="true" showColumnsMenu="true">
        <div property="columns">
            <div type="indexcolumn" align="center" width="5%">编号</div>
            <div type="checkcolumn" width="5%"></div>
            <div field="leaveID" visible="false">系统编号</div>
            <div field="leaveName" width="25%">请假名称</div>
            <div field="applyTime" allowSort="true" width="15%">申请时间</div>
            <div field="startTime" allowSort="true" width="15%">开始时间</div>
            <div field="expiredTime" allowSort="true" width="15%">结束时间</div>
            <div field="senderUserName" allowSort="true" renderer="onSenderRenderer" width="10%">申请人</div>
            <div field="leaveState" allowSort="true" renderer="onLeaveStateRenderer" width="10%">请假状态</div>
    </div>
    </div>
    <script type="text/javascript">
        
        nui.parse();

        var grid = nui.get("datagrid1");
        var receiverUserName= document.getElementById("receiverUserName").value;
        grid.load({ receiverUserName: receiverUserName });

        //////////////////////////////////////////////////////
        function onLeaveStateRenderer(e){
            var value = e.value;
            if(value==0){
              return "<font color='#0000ff'>审批中</font>";
            }else if(value==1){
              return "<font color='#15ff00'>同意</font>";
            }else{
              return "<font color='#ff0000'>拒绝</font>";
            }
        }
        
        function onSenderRenderer(e){
            var json = getStudentByID(e.value);
            if(json != null){
                return json.model.studentName;
            }
            return "";           
        }

        function getStudentByID(id){
            var jsonObject;
            $.ajax({
                url: "<%=basePath%>studentmgr/getModel?id=" + id,
                async : false,
                success: function (text) {
                    jsonObject = nui.decode(text);
                }
            }); 
            return jsonObject;        
        }
  

        function edit() {
            var rows = grid.getSelecteds();
            if (rows.length == 0){
               nui.alert("至少选中一条记录");
            } else if(rows.length >1){
               nui.alert("只能选中一条记录");
            }else{
               if(rows[0].leaveState != 0){
                   nui.alert("已经审批过，不能再次审批！");
                   return;
               }
               var id = rows[0].leaveID;
               var senderUserName = rows[0].senderUserName;
                nui.open({
                    url: "<%=basePath%>jsp/leave/verifyLeave.jsp",
                    title: "审批请假申请", width: 400, height: 340,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {id:id ,senderUserName:senderUserName};
                        iframe.contentWindow.SetData(data);
                     },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
            }
        } 
        
        
    </script>
</body>
</html>