<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/common.css" type="text/css" />
<title>数据库备份</title>
<%@include file="/common/common.jsp" %>
 <script type="text/javascript">
     function backupDatabase(){
        var cf =window.top.confirm("确认备份数据库？");
        if (cf==true){
            $.ajax({
                url: "<%=basePath%>backupandrecoverymgr/backupDatabase",
                success: function (text) {
                    var download =window.top.confirm("数据库备份成功，是否下载数据库备份文件？");
                    if (download==true){
                         window.top.location.href = "<%=basePath%>backupandrecoverymgr/dowloadSqlFile";
                         return;
                    }
               			                             
                },
                error: function () {
                    alert("备份数据库失败");
                }
           });
        }else{
        }
  }
  backupDatabase();
 </script>
</head>

<body>
</body>
</html>
