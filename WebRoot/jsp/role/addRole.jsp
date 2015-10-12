<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>添加角色</title>
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
		<form id="addForm" action="<%=basePath%>rolemgr/addRole" method="post">
				<table style="width: 100%;">
					<tr>
						<td style="width: 80px;">角色名称：</td>
						<td style="width: 300px;">
							<input id="roleName" name="role.roleName" width="280" emptyText="角色名称" required="true" requiredErrorText="角色名称不能为空" class="nui-textbox" />
						</td>
						<td id="role.roleName_error" class="errorText"></td>
					</tr>
					<tr>
						<td style="width: 80px;">角色描述：</td>
						<td style="width: 300px;">
							<input name="role.notes" width="280" emptyText="角色描述" class="nui-textarea" />
						</td>
						<td id="role.notes_error" class="errorText"></td>
					</tr>
				</table>
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
		 
		 var form = new nui.Form("addForm");
		 var b = true;
		 
		 function addUser(){
       	    var addUserForm =$("#addForm");
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