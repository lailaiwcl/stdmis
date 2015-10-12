<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<%@include file="/common/common.jsp" %>
</head>
<body>
<div align="center">
    <img id="photoUrl" width="75px" height="100px" src="" alt="学生照片" />
    <br/>
    <font color="#0000ff">
    支持*.jpg,*.jpeg,*.png,*.gif,*.bmp格式
   <br/>
    建议文件大小不超过1M
    </font>
</div>
<form id="uploadPhotoForm" action="<%=basePath%>studentmgr/uploadPhoto" method="post" enctype="multipart/form-data">
<input id="studentNo" name="studentNo" class="nui-hidden"/> 
<div style="padding-left: 100px">
<input class="nui-htmlfile" name="photoFile" id="photoFile" limitType="*.jpg;*.JPG;*.jpeg;*.JPEG;*.png;*.PNG;*.gif;*.GIF;*.bmp;*.BMP" required="true" requiredErrorText="上传图片不能为空"/>
<br/>
<br/>
<a class="nui-button" width="60px" onclick="submit()">上传</a>
&nbsp;&nbsp;&nbsp;
<a class="nui-button" width="60px" onclick="cancel()">取消</a>
</div>
</form>
    <script type="text/javascript">
        
        nui.parse();
        var form = new nui.Form("uploadPhotoForm");
        var studentNo;      
               
        function SetData(data) {
              data = nui.clone(data);
              studentNo = data.studentNo;
              document.getElementById("photoUrl").src = data.photoUrl;
              var studentNoFormItem =  nui.get("studentNo");
              studentNoFormItem.setValue(studentNo);
              //alert(nui.get("studentNo").value());
        }
        
        
        function submit(){
	     	  var fileform =$("#uploadPhotoForm");
	     	  var form = new nui.Form("#uploadPhotoForm");
	     	  //alert(document.getElementById("photoFile").getLimitType());
	          form.validate();
	          if (form.isValid() == false) return;
	     	  fileform.submit();
	       	  //nui.loading("上传导入中，请稍后......");
	       	  alert("图片上传成功。");
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