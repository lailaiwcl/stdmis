<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/common.css" type="text/css" />
<title>注销用户</title>
 <script type="text/javascript">
     function logout(){
        var cf =window.top.confirm("确认注销用户？");
        if (cf==true){
            $.ajax({
                url: "usermgr/logout",
                success: function (text) {
    			    window.top.location.href = "login.jsp";                               
                },
                error: function () {
                    alert("注销失败");
                }
           });
        }else{
           return;
        }
  }
  logout();
 </script>
</head>

<body>
</body>
</html>
