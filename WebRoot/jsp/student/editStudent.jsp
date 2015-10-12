<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<%@include file="/common/common.jsp" %>
<style type="text/css">
    table{
        margin:0;
        padding:0;
        border:1px;
        width:100%;
        height:100%;
    }
    .tdText{
       width:100px;
       float: right;
    }  
    .tdArea1{
       width:200px;
       float: left;
    }  
    .tdArea2{
       width:300px;
       float: right;
    }
</style> 
</head>
<body>
<form id="editStudentForm" action="<%=basePath%>studentmgr/edit" method="post">
	<div id="addForm" style="padding:5px;">
			<fieldset style="border: solid 1px #aaa; padding: 4px;">
				<legend>
					基本信息
				</legend>
	    <table>
	        <tr width="100%">
	           <td align="right" width="80px">姓名：</td><td width="120px" align="left"><input name="model.studentName"  required="true"  class="nui-textbox" /></td>
	           <td align="right" width="80px">性别：</td>
	           <td align="left" width="200px">
                   <div id="model.studentSex" name="model.studentSex" class="nui-radiobuttonlist" repeatDirection="vertical"
                        textField="dictEntryName" valueField="dictEntryValue" value="0" 
                        url="<%=basePath%>dictentrymgr/getModelByType?type=gender" ></div>  
               </td>
               <td align="left" rowspan="200px">&nbsp;</td>
               <td align="left" rowspan="4">&nbsp;</td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">学号：</td><td align="left"><input id="model.studentNo" name="model.studentNo"  required="true" class="nui-textbox" /></td>
	           <td align="right" width="100px" rowspan="2">政治面貌：</td><td rowspan="2" align="left">
                   <div id="model.studentPolitics" name="model.studentPolitics" class="nui-radiobuttonlist" repeatDirection="vertical"
                        textField="dictEntryName" valueField="dictEntryValue" url="<%=basePath%>dictentrymgr/getModelByType?type=political" ></div>  
               </td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">手机：</td><td align="left"><input name="model.studentMobile" class="nui-textbox" /></td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">邮箱：</td><td align="left"><input name="model.studentEmail" vtype="email" class="nui-textbox" /></td>
	           <td align="right" width="100px">班级：</td><td align="left"><input id="model.deptNo" name="model.deptNo" width="170px" allowInput="false" class="nui-treeselect" url="<%=basePath%>deptmgr/listAll" multiSelect="false"  valueFromSelect="false"
                     textField="deptName" valueField="deptNo" parentField="parentDeptNo" onbeforenodeselect="beforenodeselect" allowInput="false"/></td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">宿舍：</td><td align="left"><input name="model.studentDormitory" vtype="rangeLength:1,10" class="nui-textbox" /></td>
	           <td align="right" width="100px">校区：</td><td align="left">
                   <div id="model.schoolArea" name="model.schoolArea" class="nui-radiobuttonlist" repeatDirection="vertical"
                        textField="dictEntryName" valueField="dictEntryValue" url="<%=basePath%>dictentrymgr/getModelByType?type=schoolarea" ></div>  	           
	           </td>
	       </tr>
	       </table>				
				
			</fieldset>	 
			<hr/>
			<fieldset style="border: solid 1px #aaa; padding: 4px;">
				<legend>
					扩展信息
				</legend>   
	    <table>
	       <tr width="100%">
	           <td align="right" width="80px">QQ号码：</td><td  width="120px"  align="left"><input name="model.studentQQ" vtype="rangeLength:4,20" class="nui-textbox" /></td>
	           <td align="right" width="80px">身份证号：</td><td align="left" width="200px" ><input name="model.studentCardID" vtype="rangeLength:15,18"  width="170px" class="nui-textbox" /></td>
	           <td align="left" width="200px" rowspan="4"><img id="photoUrl" name="photoUrl" width="75px" height="100px" alt="学生照片" />
	           </td>
	           <td align="left" rowspan="4">&nbsp;</td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">本科毕业日期：</td><td align="left"><input name="model.studentGraduateTime" vtype="date:yyyy-MM-dd" class="nui-datepicker" /></td>
	           <td align="right" width="100px">银行账号：</td><td align="left"><input name="model.studentBankNo" width="170px" vtype="rangeLength:15,30" class="nui-textbox" /></td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">家庭联系方式：</td><td align="left"><input name="model.studentFamilyPhone" class="nui-textbox" /></td>
	           <td align="right" width="100px" valign="middle" rowspan="2">家庭地址：</td><td align="left" rowspan="2"><input name="model.studentFamilyAdress" vtype="rangeLength:2,100" width="170px" class="nui-textarea" /></td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">入学时间：</td><td align="left"><input name="model.joinDate" vtype="date:yyyy-MM-dd" class="nui-datepicker" /></td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">导师：</td><td align="left">
                   <input id="model.teacherNo" name="model.teacherNo" class="nui-autocomplete"
                       valueField="teacherNo" textField="teacherName" url="<%=basePath%>teachermgr/autocomplete"/>	           
	           </td>
	           <td><a class="nui-button" iconCls="icon-user" style="width: 90px;" onclick="selectTeacher()">选择导师</a></td><td>&nbsp;</td>
	           <td>&nbsp;&nbsp;&nbsp;<a href="#" onclick="chageImage()">更换图像</a></td><td>&nbsp;</td>
	       </tr>
	    </table>
	   </fieldset>
    </div>
			<table>
				<tr>
					<td style="text-align: right; padding-top: 5px; padding-right: 20px;" align="right">
						<a class="nui-button" iconCls="icon-save" style="width: 60px;" onclick="save()">保存</a>
						<a class="nui-button" iconCls="icon-cancel" style="width: 60px;" onclick="cancel()">取消</a>
					</td>
				</tr>
			</table>
			</form>
    <script type="text/javascript">
        
        nui.parse();
        var form = new nui.Form("editStudentForm");
		 function save(){
       	    var editForm =$("#editStudentForm");
       	    form.validate();
            if (form.isValid() == false) return;
       	    editForm.submit();
       	    nui.loading("保存中，请稍后......");
       	    cancel();
		 }
		 
		 function chageImage(){
            nui.open({
                url: "<%=basePath%>jsp/student/uploadPhoto.jsp",
                title: "更换照片", width: 400, height: 300,
                onload: function () {
                       var studentNo = nui.get("model.studentNo").value;
                       var photoUrl = document.getElementById("photoUrl").src;
                       var iframe = this.getIFrameEl();
                       var data = {studentNo:studentNo ,photoUrl:photoUrl};
                       iframe.contentWindow.SetData(data);
                    },
                ondestroy: function (action) {
                    //grid.reload();
                }
            });		 
		 }          
        
        function beforenodeselect(e) {
            //禁止选中父节点
            if (e.isLeaf == false){
              nui.alert('不能选中父节点');
              //e.cancel = true;
            }
        }
        
        function selectTeacher(){
             nui.open({
                url: "<%=basePath%>jsp/teacher/selectTeacher.jsp",
                showMaxButton: false,
                title: "选择导师",
                width: 500,
                height: 380,
                ondestroy: function (action) {                    
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = nui.clone(data);
                        if (data) {
                            var teacherNoBtn = nui.get("model.teacherNo");
                            teacherNoBtn.setValue(data.teacherNo);
                            teacherNoBtn.setText(data.teacherName);
                        }
                    }
                }
            });       
        }

        function getTeacherByID(id){
            var jsonObject;
            $.ajax({
                url: "<%=basePath%>teachermgr/getModel?id=" + id,
                async : false,
                success: function (text) {
                    jsonObject = nui.decode(text);
                }
            }); 
            return jsonObject;        
        }
        
        function SetData(data) {
              data = nui.clone(data);
              $.ajax({
                  url: "<%=basePath%>studentmgr/getModel?id=" + data.id,
                  cache: false,
                  success: function (text) {
                      var o = nui.decode(text);
                      form.setData(o);
                      if(o.model.photoUrl == null){
                          document.getElementById("photoUrl").src = "<%=basePath%>" + "upload/studentImages/default.jpg";
                      }else{
                          document.getElementById("photoUrl").src = "<%=basePath%>" + o.model.photoUrl;
                      }
                      var json = getTeacherByID(data.teacherNo);
                      if(json != null){
                          var t = nui.get("model.teacherNo");
                          t.setValue(json.teacherNo);
                          t.setText(json.teacherName); 
                      }
                      form.setChanged(false);
                    }
                });
        }
        function GetData() {
            var o = form.getData();
            return o;
        }

        function onCloseClick(e) {
            var obj = e.sender;
            obj.setText("");
            obj.setValue("");
        }
        
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        function onOk(e) {
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
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