<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>导入数据库文件</title>
<%@include file="/common/common.jsp" %>
    <style type="text/css">
    body
    {
        width:100%;height:100%;margin:0;overflow:hidden;
    }
    </style>
</head>
<body>
<h3>数据库恢复</h3>
<hr />
    <form id="fileupload" action="<%=basePath%>backupandrecoverymgr/loadDatabase" method="post" enctype="multipart/form-data">
	    <table>
	    <tr><td>Sql文件：</td>
	    <td><input class="nui-htmlfile" name="sqlFile" limitType="*.sql" required="true" requiredErrorText="导入文件不能为空"/></td></tr>
	    <tr><td>&nbsp;</td><td>
	    <br />
		<a class="nui-button" iconCls="icon-upload" style="margin-right: 20px;"
				onclick="startUpload()">上传并恢复</a>
		<a class="nui-button" iconCls="icon-cancel" id="cancle_link" style="width: 60px;"
				onclick="cancel()">取消</a>
	    </td></tr>
	    </table>
    </form>
    <script type="text/javascript">
    
          nui.parse();
        
          function startUpload(){
	     	  var fileform =$("#fileupload");
	     	  var form = new nui.Form("#fileupload");
	          form.validate();
	          if (form.isValid() == false) return;
              var cf =window.top.confirm("确认恢复数据库？恢复后原有数据会被覆盖。");
              if (cf==true){
	     	      fileform.submit();
 	       	      alert("数据库恢复完成。");
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