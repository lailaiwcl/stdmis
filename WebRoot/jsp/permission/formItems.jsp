<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>表单域权限</title>
		<%@include file="/common/common.jsp"%>
 <style type="text/css">
    html, body
    {
        font-size:12px;
        padding:3px 10px 10px 3px;
        margin:0;
        border:0;
        height:100%;
        overflow:hidden;
    }
        
    .errorText
    {
        color:red;
    }
    </style>
	</head>
	<body>
		<form id="addUserForm" action="<%=basePath%>usermgr/addUser" method="post">
			<fieldset style="border: solid 1px #aaa; padding: 3px;">
				<legend>
					表单项
				</legend>
				<table style="width: 100%;">
					<tr>
						<td style="width: 80px;">用户名：</td>
						<td style="width: 300px;">
							<select name="usr.userState" value="2" class="nui-radiobuttonlist">
								<option value="0">隐藏</option>
								<option value="1">只读</option>
								<option value="2">读写</option>
							</select>
						</td>
						<td id="usr.userName_error" class="errorText"></td>
					</tr>
					<tr>
						<td style="width: 80px;">密码：</td>
						<td style="width: 300px;">
							<select name="usr.userState" value="2" class="nui-radiobuttonlist">
								<option value="0">隐藏</option>
								<option value="1">只读</option>
								<option value="2">读写</option>
							</select>
						</td>
						<td id="usr.passWords_error" class="errorText"></td>
					</tr>
					<tr>
						<td style="width: 80px;">
							用户类型：
						</td>
						<td style="width: 300px;">
							<select name="usr.userState" value="2" class="nui-radiobuttonlist">
								<option value="0">隐藏</option>
								<option value="1">只读</option>
								<option value="2">读写</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="width: 80px;">
							用户状态：
						</td>
						<td style="width: 300px;">
							<select name="usr.userState" value="2" class="nui-radiobuttonlist">
								<option value="0">隐藏</option>
								<option value="1">只读</option>
								<option value="2">读写</option>
							</select>

						</td>
						<td id="usr.passWords_error" class="errorText"></td>
					</tr>
					<tr><td>邮箱：</td>
						<td>
							<select name="usr.userState" value="2" class="nui-radiobuttonlist">
								<option value="0">隐藏</option>
								<option value="1">只读</option>
								<option value="2">读写</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>备注：</td>
						<td>
							<select name="usr.userState" value="2" class="nui-radiobuttonlist">
								<option value="0">隐藏</option>
								<option value="1">只读</option>
								<option value="2">读写</option>
							</select>
</td>
					</tr>					
				</table>
			</fieldset>
			<br />
			<table>
				<tr>
					<td style="text-align: right; padding-top: 5px; padding-right: 20px;" align="right">
						<a class="nui-button" iconCls="icon-add" style="width: 60px;" onclick="addUser()">保存</a>
						<a class="nui-button" iconCls="icon-cancel" style="width: 60px;" onclick="cancel()">取消</a>
					</td>
				</tr>
			</table>
		</form>
		 <script type="text/javascript">
		 
		 var form = new nui.Form("addUserForm");
		 var b = true;
		 
		 function addUser(){
       	    var addUserForm =$("#addUserForm");
       	    form.validate();
            if (form.isValid() == false || b == false) return;
       	    addUserForm.submit();
       	    nui.loading("保存中，请稍后......");
       	    cancel();
		 }
		 
		 function validateUserName(e){
            $.ajax({
                url: "<%=basePath%>usermgr/validateUserName/?userName=" + nui.get('userName').getValue(),
                type: "post",
                success: function (text) {
                b = true;
                updateError(e);                
                if('false' == text){
                   e.errorText = '用户名已经存在';
                   b = false;
                   updateError(e);
                }
                }
            });
		    
		 }
		 
        function updateError(e) {
            var id = e.sender.name + "_error";
            var el = document.getElementById(id);
            if (el) {
                el.innerHTML = e.errorText;
            }
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