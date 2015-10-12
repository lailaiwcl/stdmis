<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/common.css" type="text/css" />
<title>数据库备份</title>
<%@include file="/common/common.jsp" %>
 <script type="text/javascript">
  function backupFullWorkspace(){
      var cf =window.top.confirm("确认对全部工程文件备份吗？");
        if (cf==true){
            var messageid = nui.loading("正在备份工程...<br/>请耐心等待...", "备份");
            $.ajax({
                url: "<%=basePath%>backupandrecoverymgr/backupWorkspace?type=fully",
                success: function (text) {
                    nui.hideMessageBox(messageid);
                    var download =window.top.confirm("工程文件备份成功，是否下载工程备份文件？");
                    if (download==true){
                         window.top.location.href = "<%=basePath%>backupandrecoverymgr/dowloadWorkspaceFile";
                         return;
                    }
               			                             
                },
               error: function () {
                    alert("工程文件备份失败");
                }
           });
        }else{
        }
  }
  
  function backupUserWorkspace(){
      var cf =window.top.confirm("确认用户文件备份吗？");
        if (cf==true){
            var messageid = nui.loading("正在备份工程...<br/>请耐心等待...", "备份");
            $.ajax({
                url: "<%=basePath%>backupandrecoverymgr/backupWorkspace?type=user",
                success: function (text) {
                    nui.hideMessageBox(messageid);
                    var download =window.top.confirm("工程文件备份成功，是否下载工程备份文件？");
                    if (download==true){
                         window.top.location.href = "<%=basePath%>backupandrecoverymgr/dowloadWorkspaceFile";
                         return;
                    }
               			                             
                },
               error: function () {
                    alert("工程文件备份失败");
                }
           });
        }else{
        }
  }
  
 </script>
</head>

<body style="margin: 20px 20px 20px 20px">
<br/>
<h5>全工程文件备份</h5>
<font color="#cccccc">对整个工程的文件进行备份，包括系统文件数据和用户文件数据</font>
<br />
<a width="60px" class="nui-button" iconCls="icon-folder" onclick="backupFullWorkspace()" plain="false">备份</a>
<br/>
<br/>
<hr />
<h5>用户文件备份</h5>
<font color="#cccccc">只对用户提交是数据文件备份，不包括系统文件数据</font>
<br />
<a width="60px" class="nui-button" iconCls="icon-addfolder" onclick="backupUserWorkspace()" plain="false">备份</a>
</body>
</html>
