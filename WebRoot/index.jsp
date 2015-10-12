<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>
		<%@include file="/common/common.jsp"%>
	</head>
	<body>
		<form action="" id="form2">
			用户名：
			<input type="text" name="userName" />
			<br />
			密码：
			<input type="password" name="pwd" />

		</form>
				<form action="" id="form1">
			用户名：
			<input type="text" name="userName" />
			<br />
			密码：
			<input type="password" name="pwd" />

		</form>
<script type="text/javascript">

getAllFormsElement();

/*
*获取页面中所有表单的表单域
*/
function getAllFormsElement(){
  var forms = document.forms;//获取页面中所有表单
  for(i=0;i<forms.length;i++){
      for(j=0;j<forms[i].length;j++){//变了表单中的表单域
          var element = forms[i].elements[j];
          var formID = getName(forms[i]);//获取表单域id或name属性
          verify(formID,element);//验证并修改表单域元素
      }
  }
}

/*
*获取页面元素ID或name，首先获取元素ID，
*如果没有ID，则获取name,都没有则返回空
*/
function getName(obj){
  var name = "";
  name = obj.id;
  if(name != null && name != ""){
      return name;
  }
  name = obj.name;
    if(name != null && name != ""){
      return name;
  }
  return null;
}

/*
通过后台验证表单域权限，并对表单域修改
*/
function verify(formID,element){
    var aj = $.ajax( {    
        url:'verifyFormItems.action?userID' + '<%=session.getAttribute("userID")%>' +'&formID=' + formID + '&formItemsID=' + getName(element)// 验证请求       
        type:'post',    
        dataType:'text',    
        success:function(data) {    
            if(data == "none" ){ //'none'不显示当前表单域
                element.value="";   
                element.style.display="none";   
            }else if(data == "readonly"){//'readonly'把表单域设为只读
                element.disabled = "disabled";    
            }    
         },    
         error : function() {    
              alert("异常！");    
         }    
    }); 
}



</script>

	</body>
</html>