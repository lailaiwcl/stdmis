<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>审批假</title>
		<%@include file="/common/common.jsp"%>
	</head>
	<body>
		<form id="verifyLeaveForm" action="<%=basePath%>leavemgr/edit" method="post">
		<input id="model.leaveID" name="model.leaveID" class="nui-hidden"/>
				<table style="width: 100%;">
					<tr>
						<td style="width: 60px;" align="right">申请人：</td>
						<td style="width: 300px;">
							<input id="senderUserName" name="senderUserName" allowInput="false" required="true" width="200px" class="nui-textbox" />
						</td>
					</tr>
					<tr>
						<td align="right">申请标题：</td>
						<td>
							<input name="model.leaveName" allowInput="false" width="200px" required="true" requiredErrorText="申请事由不能为空" class="nui-textbox" />
						</td>
					</tr>
					<tr>
						<td align="right">开始时间：</td>
						<td style="width: 300px;">
							<input id="model.startTime" allowInput="false" readOnly="true" name="model.startTime" required="true" vtype="date:yyyy-MM-dd" class="nui-datepicker" ondrawdate="onStartDrawDate"/>
						</td>
					</tr>
					<tr>
						<td style="width: 80px;" align="right">结束时间：</td>
						<td style="width: 300px;">
							<input id="model.expiredTime" allowInput="false" readOnly="true" name="model.expiredTime" required="true" vtype="date:yyyy-MM-dd" class="nui-datepicker" ondrawdate="onEndDrawDate"/>
						</td>
					</tr>
					<tr>
						<td align="right">审批结果</td>
						<td>
							<select name="model.leaveState" required="true" class="nui-radiobuttonlist">
								<option value="1">同意</option>
								<option value="2">拒绝</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">审批意见：</td>
						<td style="width: 300px;">
							<input name="model.replyText"  width="200px" class="nui-textarea" />
						</td>
					</tr>
					<tr>
						<td>
						 					
						</td>
						<td style="width: 300px;">
						<br />
						 <a class="nui-button" iconCls="icon-upload" onclick="verifyLeave()">审核</a>&nbsp;&nbsp;&nbsp;<a class="nui-button" iconCls="icon-cancel" onclick="cancel()">取消</a>	
 </td>
					</tr>
			</table>
		</form>
		 <script type="text/javascript">
		 
		 var form = new nui.Form("verifyLeaveForm");
		 var b = true;
		 
		 function verifyLeave(){
       	    var applyLeaveForm =$("#verifyLeaveForm");
       	    form.validate();
            if (form.isValid() == false || b == false) return;
       	    applyLeaveForm.submit();
       	    nui.loading("保存中，请稍后......");
       	    cancel();
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
        
        function SetData(data) {
              data = nui.clone(data);
              $.ajax({
                  url: "<%=basePath%>leavemgr/getModel?id=" + data.id,
                  cache: false,
                  success: function (text) {
                      var o = nui.decode(text);
                      form.setData(o);
                      var json = getStudentByID(data.senderUserName);
                      if(json != null){
                          var t = nui.get("senderUserName");
                          t.setValue(json.model.studentName+"(" + json.model.studentNo + ")");
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