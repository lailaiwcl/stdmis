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
		<form id="applyLeaveForm" action="<%=basePath%>leavemgr/edit" method="post">
		<fieldset style="border: solid 1px #aaa; padding: 3px;">
				<legend>
					申请信息
				</legend>
		<input id="model.senderUserName" name="model.senderUserName" type="hidden" value ="<c:user fieldName='userName'/>"/>
		<input id="model.leaveID" name="model.leaveID" class="nui-hidden"/>
				<table style="width: 100%;">
					<tr>
						<td style="width: 80px;" align="right">申请标题：</td>
						<td style="width: 300px;">
							<input name="model.leaveName" width="200px" required="true" allowInput="false" class="nui-textbox" />
						</td>
					</tr>
					<tr>
						<td align="right">开始时间：</td>
						<td style="width: 300px;">
							<input id="model.startTime" name="model.startTime" required="true" readOnly="true" allowInput="false" vtype="date:yyyy-MM-dd" class="nui-datepicker" ondrawdate="onStartDrawDate"/>
						</td>
					</tr>
					<tr>
						<td align="right">结束时间：</td>
						<td style="width: 300px;">
							<input id="model.expiredTime" name="model.expiredTime" required="true" readOnly="true" allowInput="false" vtype="date:yyyy-MM-dd" class="nui-datepicker" ondrawdate="onEndDrawDate"/>
						</td>
					</tr>
					<tr>
						<td align="right">事由描述：</td>
						<td style="width: 300px;">
							<input name="model.leaveText" allowInput="false" readOnly="true" width="200px" class="nui-textarea" />
						</td>
					</tr>
			</table>
		</fieldset>
			
		<fieldset style="border: solid 1px #aaa; padding: 3px;">
				<legend>
					审批结果
				</legend>
				<table style="width: 100%;">
					<tr>
						<td align="right" style="width: 80px;">审批人：</td>
						<td style="width: 300px;">
						<input id="model.receiverUserName" allowInput="false" readOnly="true" required="true" name="model.receiverUserName" class="nui-autocomplete" valueField="teacherNo" textField="teacherName"/>
						</td>
					</tr>
					<tr>
						<td align="right">审批结果：</td>
						<td>
							<select allowInput="false" name="model.leaveState" readOnly="true" class="nui-radiobuttonlist">
							    <option value="0">审批中</option>
								<option value="1">同意</option>
								<option value="2">拒绝</option>
							</select>
						</td>
					</tr>					
					<tr>
						<td align="right">审批意见：</td>
						<td>
							<input name="model.replyText" allowInput="false" width="200px" class="nui-textarea" />
						</td>
					</tr>
			</table>				
		</fieldset>
		</form>
		 <script type="text/javascript">
		 
		 var form = new nui.Form("applyLeaveForm");

        
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
        
        function SetData(data) {
              data = nui.clone(data);
              $.ajax({
                  url: "<%=basePath%>leavemgr/getModel?id=" + data.id,
                  cache: false,
                  success: function (text) {
                      var o = nui.decode(text);
                      form.setData(o);
                      var json = getTeacherByID(data.receiverUserName);
                      //alert(data.receiverUserName);
                      if(json != null){
                          var t = nui.get("model.receiverUserName");
                          t.setValue(json.teacherNo);
                          t.setText(json.teacherName); 
                      }
                      form.setChanged(false);
                    }
                });
        }
        function GetData() {
            var o = form.getData();
            return o;
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