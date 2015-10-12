<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>导入班级信息</title>
<%@include file="/common/common.jsp" %>
</head>
<body>
    <form id="fileupload" action="<%=basePath%>classmgr/importFromExcel" method="post" enctype="multipart/form-data">
	    <table>
	    <tr><td>Excel文件：</td>
	    <td><input class="nui-htmlfile" name="excelFile" limitType="*.xls" required="true" requiredErrorText="导入文件不能为空"/></td></tr>
	    <tr><td></td><td><input name="ignoreHead" class="nui-checkbox" text="忽略头信息？" trueValue="1" falseValue="0" value="1"/></td></tr>
	     <tr><td></td><td><input name="importOneSheet" class="nui-checkbox" text="只导入第一张工作簿？" trueValue="1" falseValue="0" value="1"/></td></tr>
	    </table>
		<div style="text-align: left; padding: 5px;">
		<a class="nui-button" iconCls="icon-upload" style="margin-right: 20px;"
				onclick="startUpload()">上传并导入</a>
		<a class="nui-button" iconCls="icon-cancel" id="cancle_link" style="width: 60px;"
				onclick="cancel()">取消</a>
		</div>
    </form>
    <script type="text/javascript">
    
          nui.parse();
        
          function startUpload(){
	     	  var fileform =$("#fileupload");
	     	  var form = new nui.Form("#fileupload");
	          form.validate();
	          if (form.isValid() == false) return;
	     	  fileform.submit();
	       	  //nui.loading("上传导入中，请稍后......");
	       	  alert("信息导入完成。");
	       	  cancel();
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