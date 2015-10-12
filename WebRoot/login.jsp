<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>管理员用户登录</title>
    <%@include file="/common/common.jsp" %>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <style type="text/css">
    body
    {
        width:100%;height:100%;margin:0;overflow:hidden;
    }
    .login-error{margin-bottom:15px; padding-left:62px; color:#ff4107;}
    </style>
</head>
<body>
<div id="loginWindow" class="nui-window" title="管理员用户登录" style="width:350px;height:200px;" 
   showModal="true" showCloseButton="false">
    <div id="loginForm" style="padding:15px;padding-top:10px;">
        <table >
            <tr>
                <td style="width:60px;"><label for="username$text">帐号：</label></td>
                <td>
                    <input id="userName" name="userName" class="nui-textbox" vtype="rangeLength:3,20" required="true" requiredErrorText="用户名不能为空" style="width:150px;"/>
                    <span id="error" class="login-error" style="display: inline-block; height: 20px; color: red;"></span>
                </td>    
            </tr>
            <tr>
                <td style="width:60px;"><label for="pwd$text">密码：</label></td>
                <td>
                    <input id="passwords" name="passwords" class="nui-password" vtype="rangeLength:6,20" requiredErrorText="密码不能为空" required="true" style="width:150px;" onenter="onLoginClick"/>
                    &nbsp;&nbsp;<a href="#" >忘记密码?</a>
                </td>
            </tr>
            <tr>
                <td></td>
                <td style="">
					<select id="indexType" name="indexType" value="0" class="nui-radiobuttonlist">
						<option value="0">标准版</option>
						<option value="1">dwz版</option>
						<option value="2">桌面版</option>
				   </select>                
                </td>
            </tr>       
            <tr>
                <td></td>
                <td style="padding-top:5px;">
                    <a onclick="onLoginClick" class="nui-button" style="width:60px;">登录</a>
                    <a onclick="onResetClick" class="nui-button" style="width:60px;">重置</a>
                </td>
            </tr>
        </table>
    </div>
</div>
    <script type="text/javascript">
        nui.parse();
        var form = new nui.Form("#loginForm");
        nui.get("userName").focus();

        var loginWindow = nui.get("loginWindow");
        loginWindow.show();

        function onLoginClick(e) {
            var form = new nui.Form("#loginWindow");

            form.validate();
            if (form.isValid() == false) return;

            loginWindow.hide();
            nui.loading("正在验证用户名和密码...", "验证");
            var data = form.getData();
            $.ajax({
                url:"<%=basePath%>usermgr/login",
                type:'POST',
                data:data,
                success:function(text){
  		        	if(text == 0){
  			            nui.loading("登录成功，马上转到系统...", "登录成功");
  			            var indexType = nui.get("indexType").value;
  			            if(indexType == 0){
  				            location.href = "<%=basePath%>index.html"; 
  				        }else if(indexType == 1){
  				            location.href = "<%=basePath%>dwz.html"; 
  				        }else{
  				            location.href = "<%=basePath%>desktop.jsp"; 
  				        }
  			        }else if(text == 3){
  			            loginWindow.show();
  				        $("#error").html("用户被锁定，请联系管理员");
  				        return;
  			        }else if(text == 1 || text == 2){
  			            loginWindow.show();
  				        $("#error").html("用户名密码错误");
  				        return;
  			        }else if(text.indexOf("sql")>=0){
  			            loginWindow.show();
  				        nui.alert("<font color='red'>存在非法sql注入信息，如属正常登录，请联系管理员</font>","非法登录"); 			        
  			        }else if(text.indexOf("script")>=0){
  			            loginWindow.show();
  				        nui.alert("<font color='red'>存在非法script注入信息，如属正常登录，请联系管理员</font>","非法登录"); 			        
  			        }else{
  			            loginWindow.show();
  				        nui.alert("<font color='red'>登录系统出错,请联系管理员</font>","系统提示");
  			        }
              },
  		      error:function(){
  		          loginWindow.show();
  		       	  nui.alert("<font color='red'>登录系统出错，请联系管理员</font>","系统提示");
  		        }
             });
        }
        function onResetClick(e) {
            $("#error").html("");
            var form = new nui.Form("#loginWindow");
            form.clear();
        }
    </script>
</body>
</html>
<%
    if(session != null && session.getAttribute(com.wucl.stdmis.module.UserModule.HAS_LOGIN) != null){
        response.sendRedirect("index.html");
	}
%>