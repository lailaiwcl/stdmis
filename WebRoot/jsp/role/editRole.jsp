<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>编辑角色</title>
		<%@include file="/common/common.jsp"%>
 <style type="text/css">
    html, body
    {
        font-size:12px;
        padding:3px 10px 10px 3px;
        margin:0;
        border:0;
        height:100%;
        overflow:hidden;
    }
        
    .errorText
    {
        color:red;
    }
    </style>
	</head>
	<body>
		<form id="editForm" action="<%=basePath%>rolemgr/editRole" method="post">
		  <input name="obj.roleID" class="nui-hidden" />
				<table style="width: 100%;">
					<tr>
						<td style="width: 80px;">角色名称：</td>
						<td style="width: 300px;">
							<input id="roleName" name="obj.roleName" emptyText="角色名称" required="true" requiredErrorText="角色名称不能为空" class="nui-textbox" />
						</td>
						<td id="role.roleName_error" class="errorText"></td>
					</tr>
					<tr>
						<td style="width: 80px;">角色描述：</td>
						<td style="width: 300px;">
							<input name="obj.notes" emptyText="角色描述" class="nui-textarea" />
						</td>
						<td id="role.notes_error" class="errorText"></td>
					</tr>
				</table>
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
		 
		 var form = new nui.Form("editForm");
		 var b = true;
		 
		 function save(){
       	    var editUserForm =$("#editForm");
       	    form.validate();
            if (form.isValid() == false || b == false) return;
       	    editUserForm.submit();
       	    nui.loading("保存中，请稍后......");
       	    cancel();
		 }		 
		 
        function updateError(e) {
            var id = e.sender.name + "_error";
            var el = document.getElementById(id);
            if (el) {
                el.innerHTML = e.errorText;
            }
        }	 
          
        function SetData(data) {
              data = nui.clone(data);
              $.ajax({
                  url: "<%=basePath%>rolemgr/getRole?id=" + data.id,
                  cache: false,
                  success: function (text) {
                      var o = nui.decode(text);
                      form.setData(o);
                      form.setChanged(false);
                    }
                });
        }

        function GetData() {
            var o = form.getData();
            return o;
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