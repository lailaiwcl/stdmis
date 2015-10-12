<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>管理员用户登录</title>
    <%@include file="/common/common.jsp" %>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="css/common.css" type="text/css" />
    <script type="text/javascript">
    function logout(){
        var cf =window.top.confirm("确认注销用户？");
        if (cf==true){
            $.ajax({
                url: "<%=basePath%>usermgr/logout",
                success: function (text) {
    			    window.top.location.href = "<%=basePath%>login.jsp";                               
                },
                error: function () {
                    nui.alert("注销失败");
                }
           });
        }else{
        }
  }
    </script>
 </head>
<body>
<div id="man_zone">
  <table width="99%" style="height: 99%" border="0" align="center"  cellpadding="3" cellspacing="1" class="table_style">
    <tr>
      <td valign="top" class="" width="70%">
      <strong> 系统消息</strong>
      <hr />
                 您一共收到 <a href="#">10</a> 条系统消息。
      </td>
      <td width="30%" valign="top">
      <strong> 登录信息</strong>
      <hr />
      <div>欢迎用户:<c:user fieldName="userName"/></div>
      <div>上次登录时间：<c:user fieldName="lastLoginTime"/></div>
      <div>这是您第  <c:user fieldName="loginTimes"/>  次登录系统</div>
      <div><a href="#" onclick="logout()"><font color="red">[退出]</font></a></div>
      </td>
    </tr>
    <tr bgcolor="#FFFFFF">
      <td class="left_title_2">
      &nbsp;
      </td>
      <td>&nbsp;</td>
    </tr>
  </table>
</div>
</body>
</html>