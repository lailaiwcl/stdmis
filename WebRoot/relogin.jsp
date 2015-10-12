<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>管理员解锁</title>
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
<div id="loginWindow">
    <div id="loginForm" style="padding:15px;padding-top:10px;">
     <input id="userName" name="userName" class="nui-hidden"/>
        <table >
            <tr>
                <td style="width:60px;"><label for="pwd$text">登录密码：</label></td>
                <td>
                    <input id="passwords" name="passwords" class="nui-password" vtype="rangeLength:6,20" requiredErrorText="密码不能为空" required="true" style="width:150px;" onenter="onLoginClick"/>
                    <span id="error" class="login-error" style="display: inline-block; height: 20px; color: red;"></span>
                </td>
            </tr>         
            <tr>
                <td></td>
                <td style="padding-top:5px;">
                    <a onclick="onLoginClick" class="nui-button" style="width:60px;">解锁</a>
                    <a onclick="onResetClick" class="nui-button" style="width:60px;">退出</a>
                </td>
            </tr>
        </table>
    </div>
</div>
    <script type="text/javascript">
        nui.parse();
        var form = new nui.Form("#loginForm");
        nui.get("passwords").focus();

        function onLoginClick(e) {
            var form = new nui.Form("#loginWindow");

            form.validate();
            if (form.isValid() == false) return;

            //nui.loading("正在验证密码...", "验证");
            var data = form.getData();
            var json = nui.encode(data);
            var userName = nui.get("userName").value;
            var passwords =  nui.get("passwords").value;
            $.ajax({
           url:"<%=basePath%>usermgr/login",
           type:'POST',
           data:json,
  		   contentType:"application/json",
           success:function(text){
  			if(text == 0){
  			    //nui.loading("登录成功，马上转到系统...", "登录成功");
  				//location.href = "<%=basePath%>desktop.jsp"; 
              if (window.CloseOwnerWindow){
        		  return window.CloseOwnerWindow("close");
        	  }else{
        		  window.close();
        	  } 				
  			}else if(text == 3){
  				$("#error").html("用户被锁定，与管理员联系");
  				return;
  			}else{
  				$("#error").html("密码错误");
  				return;
  			}
         },
  		 error:function(){
  			nui.alert("登录系统出错","系统提示");
  		 }
         });
        }
        function onResetClick(e) {
            $("#error").html("");
            //var form = new nui.Form("#loginWindow");
            //form.clear();
        }
    </script>
</body>
</html>