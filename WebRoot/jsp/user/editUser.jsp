<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>编辑用户</title>
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
		<form id="saveUserForm" action="<%=basePath%>studentmgr/edit" method="post">
		  <input name="obj.userID" class="nui-hidden" />
			<fieldset style="border: solid 1px #aaa; padding: 3px;">
				<legend>
					必填信息
				</legend>
				<table style="width: 100%;">
					<tr>
						<td style="width: 80px;">用户名：</td>
						<td style="width: 300px;">
							<input id="userName" name="obj.userName" allowInput="false" emptyText="用户名称" required="true" requiredErrorText="用户名不能为空" class="nui-textbox" />字符串长度（4~30）
						</td>
						<td id="usr.userName_error" class="errorText"></td>
					</tr>
					<tr>
						<td style="width: 80px;">密码：</td>
						<td style="width: 300px;">
							<input name="obj.passWords" vtype="rangeLength:6,20" emptyText="空则不修改" class="nui-textbox" />字符串长度（6~20）
						</td>
						<td id="usr.passWords_error" class="errorText"></td>
					</tr>
					<tr>
						<td style="width: 80px;">
							用户类型：
						</td>
						<td style="width: 300px;">
                            <div id="obj.userType" name="obj.userType" class="nui-radiobuttonlist" repeatDirection="vertical"
                                textField="dictEntryName" valueField="dictEntryValue" url="<%=basePath%>dictentrymgr/getModelByType?type=usertype"></div>  
						</td>
						<td id="usr.passWords_error" class="errorText"></td>
					</tr>
					<tr>
						<td style="width: 80px;">
							用户状态：
						</td>
						<td style="width: 300px;">
							<select name="obj.userState" class="nui-radiobuttonlist">
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
							<input name="obj.userEmail" selectOnFocus="true" class="nui-textbox" vtype="email" emptyText="用户邮箱"/>
						</td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><textarea name="obj.notes" class="nui-textarea" emptyText="请输入备注"></textarea></td>
					</tr>
				</table>
			</fieldset>
			<table>
				<tr>
					<td style="text-align: right; padding-top: 5px; padding-right: 20px;" align="right">
						<a class="nui-button" iconCls="icon-save" style="width: 60px;" onclick="saveUser()">保存</a>
						<a class="nui-button" iconCls="icon-cancel" style="width: 60px;" onclick="cancel()">取消</a>
					</td>
				</tr>
			</table>
		</form>
		 <script type="text/javascript">
		 
		 nui.parse();
		 
		 var form = new nui.Form("saveUserForm");
		 var b = true;
		 
		 function saveUser(){
       	    var editUserForm =$("#saveUserForm");
       	    form.validate();
            if (form.isValid() == false || b == false) return;
       	    editUserForm.submit();
       	    nui.loading("保存中，请稍后......");
       	    cancel();
		 }		 
		 
        function updateError(e) {
            var id = e.sender.name + "_error";
            var el = document.getElementById(id);
            if (el) {
                el.innerHTML = e.errorText;
            }
        }	 
          
        function SetData(data) {
              data = nui.clone(data);
              $.ajax({
                  url: "<%=basePath%>usermgr/getUser?id=" + data.id,
                  cache: false,
                  success: function (text) {
                      var o = nui.decode(text);
                      form.setData(o);
                      form.setChanged(false);
                    }
                });
        }

        function GetData() {
            var o = form.getData();
            return o;
        }
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            //nui.confirm("数据被修改了，是否先保存？", "确定？",
                //function (action) {            
                   // if (action == "ok") {
                     //   return false;
                    //}
               /// }
           // );
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        function onOk(e) {
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
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