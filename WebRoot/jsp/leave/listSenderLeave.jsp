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
                     <a class="nui-button" iconCls="icon-node" onclick="applyLeave()">请假</a>
                        <a class="nui-button" iconCls="icon-edit" onclick="edit()">修改</a>
                        <a class="nui-button" iconCls="icon-remove" onclick="remove()">删除</a>
                        <a class="nui-button" iconCls="icon-goto" onclick="result()">审批结果</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <input id="senderUserID" type="hidden" value ="<c:user fieldName='userName'/>"/>
    <div id="datagrid1" class="nui-datagrid" style="width:100%;height:290px;" 
        url="<%=basePath%>leavemgr/getByUserID" idField="leaveID"
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
            <div field="receiverUserName" allowSort="true" renderer="onReceiverRenderer" width="10%">处理人</div>
            <div field="leaveState" allowSort="true" renderer="onLeaveStateRenderer" width="10%">请假状态</div>
    </div>
    </div>
    <script type="text/javascript">
        
        nui.parse();

        var grid = nui.get("datagrid1");
        var userName= document.getElementById("senderUserID").value;
        grid.load({ userName: userName });

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
        
        function onReceiverRenderer(e){
            var json = getTeacherByID(e.value);
            if(json != null){
                return json.teacherName;
            }
            return "";           
        }
        
        
        function result(){
            var rows = grid.getSelecteds();
            if (rows.length == 0){
               nui.alert("至少选中一条记录");
            } else if(rows.length >1){
               nui.alert("只能选中一条记录");
            }else{
               var id = rows[0].leaveID;
               var receiverUserName = rows[0].receiverUserName;
                nui.open({
                    url: "<%=basePath%>jsp/leave/resultLeave.jsp",
                    title: "查看审批结果", width: 400, height: 360,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {id:id ,receiverUserName:receiverUserName};
                        iframe.contentWindow.SetData(data);
                     },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
            }
        }

        function getTeacherByID(id){
            var jsonObject;
            $.ajax({
                url: "<%=basePath%>teachermgr/getModel?id=" + id,
                async : false,
                success: function (text) {
                    jsonObject = nui.decode(text);
                }
            }); 
            return jsonObject;        
        }
        
        function applyLeave(){
            nui.open({
                url: "<%=basePath%>jsp/leave/applyLeave.jsp",
                title: "请假申请", width: 400, height: 340,
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
                            var idArray = [];
                            for (var i = 0, l = rows.length; i < l; i++) {
                                var r = rows[i];
                                if(r.leaveState != 0){
                                    nui.alert("记录 [ "+ r.leaveID +" ] 删除失败<br/>只能删除审批中的请假记录！");
                                    continue;
                                }
                                idArray.push(r.leaveID);
                             }
                        var ids = idArray.join(',');
                        $.ajax({
                            url: "<%=basePath%>leavemgr/del?ids=" +ids,
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
               if(rows[0].leaveState != 0){
                   nui.alert("只能修改审批中的请假记录！");
                   return;
               }
               var id = rows[0].leaveID;
               var receiverUserName = rows[0].receiverUserName;
                nui.open({
                    url: "<%=basePath%>jsp/leave/editLeave.jsp",
                    title: "修改请假申请", width: 400, height: 340,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {id:id ,receiverUserName:receiverUserName};
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