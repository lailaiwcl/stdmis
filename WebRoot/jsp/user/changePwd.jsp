<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>修改密码</title>
<%@include file="/common/common.jsp" %>
    <style type="text/css">
    body
    {
        width:100%;height:100%;margin:0;overflow:hidden;
    }
    .login-error{margin-bottom:15px; padding-left:62px; color:#ff4107;}
    </style>
</head>
<body>
    <div id="changPwdForm" style="padding:15px;padding-top:10px;">
    <input id="userID" name="userID" class="nui-hidden" value ="<c:user fieldName='userID'/>"/>
        <table >
            <tr>
                <td style="width:80px;"><label for="username$text">旧密码：</label></td>
                <td>
                    <input id="oldPwd" name="oldPwd" class="nui-password" vtype="rangeLength:6,20" required="true" requiredErrorText="密码不能为空" minLengthErrorText="密码不能少于6个字符" style="width:150px;"/>
                </td>    
            </tr>
            <tr>
                <td style="width:80px;"><label for="pwd$text">新密码：</label></td>
                <td>
                    <input id="newPwd" name="newPwd" class="nui-password" vtype="rangeLength:6,20" requiredErrorText="密码不能为空" minLengthErrorText="密码不能少于6个字符" required="true" style="width:150px;" onenter="onLoginClick"/>
                </td>
            </tr>  
            <tr>
                <td style="width:80px;"><label for="pwd$text">确认新密码：</label></td>
                <td>
                    <input id="repeatPwd" name="repeatPwd" class="nui-password" vtype="rangeLength:6,20" requiredErrorText="密码不能为空" minLengthErrorText="密码不能少于6个字符" required="true" style="width:150px;" onenter="onLoginClick"/>
                </td>
            </tr>          
            <tr>
                <td></td>
                <td style="padding-top:5px;">
                    <a onclick="onOK" class="nui-button" style="width:60px;">修改</a>
                    <a onclick="onResetClick" class="nui-button" style="width:60px;">重置</a>
                </td>
            </tr>
        </table>
    </div>
<script type="text/javascript">
        nui.parse();
        var form = new nui.Form("changPwdForm");
        nui.get("oldPwd").focus();
        
        function onOK() {
            form.validate();
            if (form.isValid() == false) return;
            if(nui.get("newPwd").getValue() != nui.get("repeatPwd").getValue()){
            	nui.alert("新密码和确认密码不一致。");
            	return;
            }

            var userID = nui.get("userID").getValue();
            var oldPwd = nui.get("oldPwd").getValue();
            var newPwd = nui.get("newPwd").getValue();
            $.ajax({
                url: "<%=basePath%>usermgr/changePwd?userID=" + userID + "&oldPwd=" + oldPwd + "&newPwd=" + newPwd,
                //data: json,
                type:'POST',
                contentType:"application/json",
                success: function (text) {
                    if(text == "-1"){
					    nui.alert("系统错误。");
					}                
                    if(text == "0"){
					    nui.alert("旧密码不正确修改成功。");
					}
                    if(text == "1"){
					    nui.alert("密码修改成功。");
					}
					onResetClick();
                    
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    nui.alert(jqXHR.responseText, "异常", function(){
						//CloseWindow();
					});
                    
                }
            });
        }
        
        
        function onResetClick(){
            nui.get("oldPwd").setValue("");
            nui.get("newPwd").setValue("");
            nui.get("repeatPwd").setValue("");
        }
</script>
</body>
</html>