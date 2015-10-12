<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>申请请假</title>
		<%@include file="/common/common.jsp"%>
	</head>
	<body>
		<form id="applyLeaveForm" action="<%=basePath%>leavemgr/applyLeave" method="post">
		<input id="model.senderUserName" name="model.senderUserName" type="hidden" value ="<c:user fieldName='userName'/>"/>
				<table style="width: 100%;">
					<tr>
						<td style="width: 60px;" align="right">申请标题：</td>
						<td style="width: 300px;">
							<input name="model.leaveName" emptyText="简单描述请假原因" width="200px" required="true" requiredErrorText="申请事由不能为空" class="nui-textbox" />
						</td>
					</tr>
					<tr>
						<td align="right">开始时间：</td>
						<td style="width: 300px;">
							<input id="model.startTime" name="model.startTime" required="true" vtype="date:yyyy-MM-dd" class="nui-datepicker" ondrawdate="onStartDrawDate"/>
						</td>
					</tr>
					<tr>
						<td style="width: 80px;" align="right">结束时间：</td>
						<td style="width: 300px;">
							<input id="model.expiredTime" name="model.expiredTime" required="true" vtype="date:yyyy-MM-dd" class="nui-datepicker" ondrawdate="onEndDrawDate"/>(不能超过30天)
						</td>
					</tr>
					<tr>
						<td align="right">请假审批人：</td>
						<td style="width: 300px;">
						<input id="model.receiverUserName" required="true" name="model.receiverUserName" class="nui-autocomplete" valueField="teacherNo" textField="teacherName" url="<%=basePath%>teachermgr/autocomplete"/>
                        <a class="nui-button" iconCls="icon-user" onclick="selectTeacher()">选择</a>	
						</td>
					</tr>
					<tr>
						<td align="right">事由描述：</td>
						<td style="width: 300px;">
							<input name="model.leaveText"  emptyText="详细描述请假原因" width="200px" class="nui-textarea" />
						</td>
					</tr>
					<tr>
						<td>
						 					
						</td>
						<td style="width: 300px;">
						<br />
						 <a class="nui-button" iconCls="icon-upload" onclick="applyLeave()">申请</a>&nbsp;&nbsp;&nbsp;<a class="nui-button" iconCls="icon-cancel" onclick="cancel()">取消</a>	
 </td>
					</tr>
			</table>
		</form>
		 <script type="text/javascript">
		 
		 var form = new nui.Form("applyLeaveForm");
		 var b = true;
		 
		 function applyLeave(){
       	    var applyLeaveForm =$("#applyLeaveForm");
       	    form.validate();
            if (form.isValid() == false || b == false) return;
       	    applyLeaveForm.submit();
       	    nui.loading("保存中，请稍后......");
       	    cancel();
		 }
		 
		function onStartDrawDate(e) {
			var edate=nui.get("model.expiredTime");
			if(edate.getValue())
            if (e.date.getTime() > edate.value.getTime()||(edate.value.getTime()-e.date.getTime())>1000*3600*24*30) {
                e.allowSelect = false;
            }
        }
		
		function onEndDrawDate(e) {
			var sdate=nui.get("model.startTime");
			if(sdate.getValue())
            if (e.date.getTime() < sdate.value.getTime()||(e.date.getTime()-sdate.value.getTime())>1000*3600*24*30) {
                e.allowSelect = false;
            }
        }
		 
        function selectTeacher(){
             nui.open({
                url: "<%=basePath%>jsp/teacher/selectTeacher.jsp",
                showMaxButton: false,
                title: "选择审批人",
                width: 500,
                height: 380,
                ondestroy: function (action) {                    
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = nui.clone(data);
                        if (data) {
                            var teacherNoBtn = nui.get("model.receiverUserName");
                            teacherNoBtn.setValue(data.teacherNo);
                            teacherNoBtn.setText(data.teacherName);
                        }
                    }
                }
            });       
        }

          function cancel() {
              if (window.CloseOwnerWindow){
        		  return window.CloseOwnerWindow("close");
        	  }else{
        		  window.close();
        	  }
          }
		 </script>
		 
	</body>
</html>