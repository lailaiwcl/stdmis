<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <%@include file="/common/common.jsp" %>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="<%=basePath %>css/common.css" type="text/css" />
    <script type="text/javascript">
    function logout(){
        var cf =confirm("确认注销用户？");
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
  
  function changeIndex(src){
       window.top.location.href = "<%=basePath%>" + src;
  }
    
    </script>
 </head>
<body>
<div class="header_content">
     <div class="logo"><img src="#" alt="教务信息管理系统" /></div>
	 <div class="right_nav">
	    <div class="text_left"><ul class="nav_list"><li><img src="images/direct.gif" width="8" height="21" /><span><script src="desktop/js/Clock.js" type="text/javascript"></script></span></li></ul>
	    </div>
		<div class="text_right"><ul class="nav_return"><li><img src="images/return.gif" width="13" height="21" />&nbsp;</li>
		<li> [<a href="#" onclick="changeIndex('desktop.jsp')">切换到桌面版</a>]&nbsp;&nbsp;</li><li> [<a href="#" onclick="changeIndex('dwz.html')">切换到dwz版</a>]&nbsp;&nbsp;</li><li> [<a href="#" onclick="logout()">退出</a>]&nbsp;&nbsp;</li>
		</ul>
		</div>
	 </div>
</div>
</body>
</html>