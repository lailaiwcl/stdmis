<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>添加用户</title>
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
					必填信息
				</legend>
				<table style="width: 100%;">
					<tr>
						<td style="width: 80px;">用户名：</td>
						<td style="width: 300px;">
							<input id="userName" name="usr.userName" vtype="rangeLength:4,30" onvalidation="validateUserName" emptyText="用户名称" required="true" requiredErrorText="用户名不能为空" class="nui-textbox" />字符串长度（4~30）
						</td>
						<td id="usr.userName_error" class="errorText"></td>
					</tr>
					<tr>
						<td style="width: 80px;">密码：</td>
						<td style="width: 300px;">
							<input name="usr.passWords" vtype="rangeLength:6,20" emptyText="默认密码" required="true" requiredErrorText="密码不能为空" class="nui-textbox" />字符串长度（6~20）
						</td>
						<td id="usr.passWords_error" class="errorText"></td>
					</tr>
					<tr>
						<td style="width: 80px;">
							用户类型：
						</td>
						<td style="width: 300px;">
                            <div id="usr.userType" name="usr.userType" class="nui-radiobuttonlist" repeatDirection="vertical"
                                textField="dictEntryName" required="true" valueField="dictEntryValue" url="<%=basePath%>dictentrymgr/getModelByType?type=usertype" value="0"></div>  
						</td>
						<td id="usr.passWords_error" class="errorText"></td>
					</tr>
					<tr>
						<td style="width: 80px;">
							用户状态：
						</td>
						<td style="width: 300px;">
							<select name="usr.userState" class="nui-radiobuttonlist">
								<option value="0">正常（可以登陆）</option>
								<option value="1">锁定（不能登录）</option>
							</select>

						</td>
						<td id="usr.passWords_error" class="errorText"></td>
					</tr>
				</table>
			</fieldset>
			<br />
			<fieldset style="border: solid 1px #aaa; padding: 3px;">
				<legend>基本信息</legend>
				<table>
					<tr><td>邮箱：</td>
						<td>
							<input name="usr.userEmail" class="nui-textbox" vtype="email" emptyText="用户邮箱"/>
						</td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><textarea name="usr.notes" class="nui-textarea" emptyText="请输入备注"></textarea></td>
					</tr>
				</table>
			</fieldset>
			<table>
				<tr>
					<td style="text-align: right; padding-top: 5px; padding-right: 20px;" align="right">
						<a class="nui-button" iconCls="icon-add" style="width: 60px;" onclick="addUser()">添加</a>
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