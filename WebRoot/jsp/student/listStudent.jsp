<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>学生信息列表</title>
<%@include file="/common/common.jsp" %>
<style type="text/css">
    table{
        margin:0;
        padding:0;
        border:1px;
        width:100%;
        height:100%;
    }
        body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    } 
    
</style> 
</head>
<body>
<c:auth sessionName="<%=user%>" resourcesID="classMrg">
</c:auth>

    
    
<div class="nui-splitter" style="width:100%;height:100%;">
    <div size="200" showCollapseButton="true">
        <div class="nui-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            <span style="padding-left:5px;">名称：</span>
            <input id="treeKey" class="nui-textbox" emptyText="请输入学院名称" onenter="searchTree"/>
            <a class="nui-button" iconCls="icon-search" plain="true" onclick="searchTree()">查找</a>                  
        </div>
        <div class="nui-fit">
        <ul id="tree1" class="nui-tree" url="<%=basePath%>deptmgr/listAll" style="width:100%;padding:1px;"
            showTreeIcon="true" onnodecheck="onNodeCheck" onnodeselect="treeNodeSelect" checkRecursive="false"  expandOnLoad="0" textField="deptName" idField="deptNo" parentField="parentDeptNo" resultAsTree="false">        
        </ul> 
        </div>
    </div>
    <div showCollapseButton="true">
    <div style="width:100%;">
        <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                            <a class="nui-button" plain="true" iconCls="icon-add" onclick="add()">增加</a>
                            <span class="separator"></span>  
                            <a class="nui-button" plain="true" iconCls="icon-edit" onclick="edit()">编辑</a>
                            <span class="separator"></span>  
                            <a class="nui-button" plain="true" iconCls="icon-remove" onclick="remove()">删除</a>
                            <!-- 
                            <a class="nui-button" iconCls="icon-node" onclick="exportExcel()">导出excel</a>
                            <a class="nui-button" iconCls="icon-node" onclick="importExcel()">导入信息</a> -->
                            
                    </td>
                    <td style="white-space:nowrap;">
                        <input id="key" class="nui-textbox" emptyText="请输入学生姓名" style="width:150px;" onenter="search"/>   
                        <a class="nui-button" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div id="datagrid1" class="nui-datagrid" style="width:100%;height:520px;" 
        url="<%=basePath%>studentmgr/listWithPage" idField="studentNo"
        allowResize="true" pageSize="20" dataField="data" sizeList="[20,40,60]"
        allowCellSelect="true"  showColumnsMenu="true" multiSelect="true" onshowrowdetail="onShowRowDetail" >
        <div property="columns">
            <div type="indexcolumn" width="5%">序号</div>
            <div type="checkcolumn" width="5%"></div>
            <div type="expandcolumn" width="5%">&nbsp;</div>
            <div field="studentNo" width="9%" allowSort="true">学号</div>
            <div field="studentName" width="6%" allowSort="true">姓名</div>
            <div field="studentSex" width="4%" allowSort="true" renderer="onGenderRenderer">性别</div>
            <div field="studentMobile" width="10%" allowSort="true">手机</div>
            <div field="studentEmail" width="14%" allowSort="true">邮箱</div>
            <div field="teacherNo" width="6%" renderer="onTeacherRenderer">导师</div>
            <div field="deptNo" width="14%" renderer="onDeptRenderer" allowSort="true">班级</div>
            <div field="studentPolitics" width="8%" renderer="onPoliticsRenderer" allowSort="true">政治面貌</div>
            <div field="studentDormitory" width="7%" allowSort="true">宿舍号</div>
            <div field="schoolArea" width="7%" renderer="onSchoolAreaRenderer" allowSort="true">东校区</div>
        </div>
    </div>
	<div id="detailForm" style="display:none;padding:5px;">
			<fieldset style="border: solid 1px #aaa; padding: 4px;">
				<legend>
					基本信息
				</legend>
	    <table>
	        <tr width="100%">
	           <td align="right" width="80px">姓名：</td><td width="120px" align="left"><input name="model.studentName" allowInput="false" class="nui-textbox" /></td>
	           <td align="right" width="80px">性别：</td>
	           <td align="left" width="200px">
                   <div id="model.studentSex" name="model.studentSex" class="nui-radiobuttonlist" repeatDirection="vertical"
                        textField="dictEntryName" valueField="dictEntryValue" value="0" 
                        url="<%=basePath%>dictentrymgr/getModelByType?type=gender" readOnly="true"></div>  
               </td>
               <td align="left" rowspan="200px">&nbsp;</td>
               <td align="left" rowspan="4">&nbsp;</td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">学号：</td><td align="left"><input name="model.studentNo" allowInput="false" class="nui-textbox" /></td>
	           <td align="right" width="100px" rowspan="2">政治面貌：</td><td  rowspan="2" align="left">
                   <div id="model.studentPolitics" name="model.studentPolitics" class="nui-radiobuttonlist" repeatDirection="vertical"
                        textField="dictEntryName" valueField="dictEntryValue" readOnly="true" url="<%=basePath%>dictentrymgr/getModelByType?type=political" ></div>  
               </td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">手机：</td><td align="left"><input name="model.studentMobile" allowInput="false" class="nui-textbox" /></td>
	           
	       </tr>
	       <tr>
	           <td align="right" width="100px">邮箱：</td><td align="left"><input name="model.studentEmail" vtype="email" allowInput="false" class="nui-textbox" /></td>
              <td align="right" width="100px">班级：</td><td align="left"> <input id="model.deptNo" name="model.deptNo" width="170px" allowInput="false" class="nui-treeselect" url="<%=basePath%>deptmgr/listAll" multiSelect="false"  valueFromSelect="false"
                     textField="deptName" valueField="deptNo" parentField="parentDeptNo" readOnly="true" onbeforenodeselect="beforenodeselect" allowInput="false"/></td>	           
	       </tr>
	       <tr>
	           <td align="right" width="100px">宿舍：</td><td align="left"><input name="model.studentDormitory" allowInput="false" class="nui-textbox" /></td>
	           <td align="right" width="100px">校区：</td><td align="left">
                   <div id="model.schoolArea" name="model.schoolArea" class="nui-radiobuttonlist" repeatDirection="vertical"
                        textField="dictEntryName" valueField="dictEntryValue" readOnly="true" url="<%=basePath%>dictentrymgr/getModelByType?type=schoolarea" ></div>  
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
	           <td align="right" width="80px">QQ号码：</td><td  width="120px"  align="left"><input name="model.studentQQ" allowInput="false" class="nui-textbox" /></td>
	           <td align="right" width="80px">身份证号：</td><td align="left" width="200px" ><input name="model.studentCardID"  allowInput="false" width="170px" class="nui-textbox" /></td>
	           <td align="left" width="200px" rowspan="4"><img id="photoUrl" name="photoUrl" width="75px" height="100px" src="../upload/studentImages/default.jpg"  alt="学生照片" /></td>
	           <td align="left" rowspan="4">&nbsp;</td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">本科毕业日期：</td><td align="left"><input name="model.studentGraduateTime" allowInput="false" readOnly="true" class="nui-datepicker" /></td>
	           <td align="right" width="100px">银行账号：</td><td align="left"><input name="model.studentBankNo" allowInput="false" width="170px" class="nui-textbox" /></td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">家庭联系方式：</td><td align="left"><input name="model.studentFamilyPhone" allowInput="false"  class="nui-textbox" /></td>
	           <td align="right" width="100px" valign="middle" rowspan="2">家庭地址：</td><td align="left" rowspan="2"><input name="model.studentFamilyAdress" allowInput="false" width="170px" class="nui-textarea" /></td>
	       </tr>
	       <tr>
	           <td align="right" width="100px">入学时间：</td><td align="left"><input name="model.joinDate" allowInput="false" readOnly="true" class="nui-datepicker" /></td>
	       </tr>
	    </table>
	   </fieldset>
	   <hr />
	   <fieldset style="border: solid 1px #aaa; padding: 4px;">
				<legend>
					导师信息
				</legend>
	    <table>
	       <tr>
	           <td align="right" width="80px">姓名：</td><td align="left" width="200px"><input id="model.teacherName" name="model.teacherName" class="nui-textbox" /></td>
	           <td align="right" width="80px">手机：</td><td  width="120px"  align="left"><input id="teacherMobile" name="teacherMobile" class="nui-textbox" /></td>
	           <td align="left" width="200px">&nbsp;</td> <td align="left" rowspan="4">&nbsp;</td>
	       </tr>
	       <tr>
	           <td align="right" width="80px">工号：</td><td  width="120px"  align="left"><input name="model.teacherNo" class="nui-textbox" /></td>
	           <td align="right" width="80px">邮箱：</td><td align="left" width="200px"><input id="teacherEmail" name="teacherEmail" class="nui-textbox" /></td>
	           <td align="left" width="200px">&nbsp;</td> <td align="left" rowspan="4">&nbsp;</td>
	       </tr>
	       </table>				
				
		</fieldset>	    
    </div>
    </div>      
</div>

    <script type="text/javascript">
        
        var Genders = [{ id: 0, text: '男' }, { id: 1, text: '女'}, { id: 2, text: '未知'}];
        var Politics = [{ id: 0, text: '群众' }, { id: 1, text: '团员'}, { id: 2, text: '中共预备党员'},{ id: 3, text: '中共党员' }, { id: 4, text: '其他党派'}];
        nui.parse();
        
        var tree = nui.get("tree1");
        tree.expandLevel(0);
        
   function treeNodeSelect(e){
        var node = tree.getSelectedNode();
        grid.setUrl("<%=basePath%>studentmgr/listWithPageByDeptNo?deptNo=" + node.deptNo);
        grid.reload();
        //alert(node.deptNo);
    }
        
    function searchTree() {
        var key = nui.get("treeKey").getValue();
        if(key == ""){
            tree.clearFilter();
        }else{
            key = key.toLowerCase();
            tree.filter(function (node) {
                var text = node.deptName ? node.deptName.toLowerCase() : "";
                if (text.indexOf(key) != -1) {
                    var childNode = tree.getParentNode(node);
                    tree.expandNode (childNode);
                    return true;
                }
            });
        }
    }        
        
        
        
       function onGenderRenderer(e) {
            for (var i = 0, l = Genders.length; i < l; i++) {
                var g = Genders[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
        
        function onSchoolAreaRenderer(e){
            var value = e.value;
            if(value=="0"){
                return "东校区";
            }else if(value=="1"){
                 return "本部";
            }
            return "";
        }
        
        function onTeacherRenderer(e){
            var json = getTeacherByID(e.value);
            if(json != null){
                return json.teacherName;
            }
            return "";        
        }
        
        function onPoliticsRenderer(e){
            for (var i = 0, l = Politics.length; i < l; i++) {
                var g = Politics[i];
                if (g.id == e.value) return g.text;
            }
            return "";        
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
                
        function getDeptByID(id){
            var classJson;
            $.ajax({
                url: "<%=basePath%>deptmgr/getModel?id=" + id,
                async : false,
                success: function (text) {
                    classJson = nui.decode(text);
                }
            }); 
            return classJson;        
        }
        
        function onDeptRenderer(e){
            var json = getDeptByID(e.value);
            if(json != null){
                return json.deptName; 
            }
            return "";  
        }
        
        
        function beforenodeselect(e) {
            //禁止选中父节点
            if (e.isLeaf == false){
              nui.alert('不能选中父节点');
              //e.cancel = true;
            }
        }

        function onCloseClick(e) {
            var obj = e.sender;
            obj.setText("");
            obj.setValue("");
        }
        
        var grid = nui.get("datagrid1");
        var detailForm = document.getElementById("detailForm");  
        grid.load();

        //////////////////////////////////////////////////////

        function search() {       
            var key = nui.get("key").getValue();
            grid.load({ key: key });
        }
        
        function onKeyEnter(){
            search();
        }

		function onShowRowDetail(e) {
            var row = e.record;
            var td = grid.getRowDetailCellEl(row);
            detailForm.style.display = "block";
            td.appendChild(detailForm);
            $.ajax({
                url: "<%=basePath%>studentmgr/getModel?id=" + row.studentNo,
                success: function (text) {
                    var form = new nui.Form("detailForm");
                    var o = nui.decode(text);
                      if(o.model.photoUrl == null){
                          document.getElementById("photoUrl").src = "<%=basePath%>" + "upload/studentImages/default.jpg";
                      }else{
                          document.getElementById("photoUrl").src = "<%=basePath%>" + o.model.photoUrl;
                      }
                    form.setData(o);
                    grid.unmask();
                }
            });
            var json = getDeptByID(row.classID);
            var obj = nui.get("model.deptNo");
            obj.setValue("");  
            obj.setText("");  
            if(json != null){
                obj.setValue(json.classID);  
                obj.setText(json.className);   
            }
            var json2 = getTeacherByID(row.teacherNo);
            setTimeout(function(){
            if(json2 != null){
                var teacherName = nui.get("model.teacherName");
                var teacherEmail = nui.get("teacherEmail");
                var teacherMobile = nui.get("teacherMobile");
                teacherName.setValue(json2.teacherName);
                teacherEmail.setValue(json2.teacherEmail);
                teacherMobile.setValue(json2.teacherMobile);
            }                   
            },200)
            
        }
        
        function add(){
            nui.open({
                url: "<%=basePath%>jsp/student/addStudent.jsp",
                title: "新增学生", width: 800, height: 500,
                ondestroy: function (action) {
                    grid.reload();
                }
            });        
        }
  
        function remove() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                nui.confirm("确定删除选中的 " + rows.length + " 条记录？", "确定？",
                    function (action) {            
                        if (action == "ok") {
                            var names = [];
                            for (var i = 0, l = rows.length; i < l; i++) {
                                var r = rows[i];
                                names.push(r.studentNo);
                             }
                        var ids = names.join(',');
                        $.ajax({
                            url: "<%=basePath%>studentmgr/del?ids=" +ids,
                            success: function (text) {
                                grid.reload();
                            },
                            error: function () {
                        	    nui.alert("删除失败");
                            }
                        });
                    }
                }
            );
            } else {
                nui.alert("至少选中一条记录");
            }
        }  

        function edit() {
            var rows = grid.getSelecteds();
            if (rows.length == 0){
               nui.alert("至少选中一条记录");
            } else if(rows.length >1){
               nui.alert("只能选中一条记录");
            }else{
               var id = rows[0].studentNo;
               var teacherNo = rows[0].teacherNo;
            nui.open({
                url: "<%=basePath%>jsp/student/editStudent.jsp",
                title: "编辑学生信息", width: 800, height: 500,
                onload: function () {
                       var iframe = this.getIFrameEl();
                       var data = {id:id ,teacherNo:teacherNo};
                       iframe.contentWindow.SetData(data);
                    },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
            }
        } 
        
        function exportExcel() {
            nui.confirm("确定导出 所有学生信息", "确定？",
                function (action) {            
                    if (action == "ok") {
                       window.location="<%=basePath%>classmgr/exportExcel";
                    }
                }
            );
        } 
        
        function importExcel(){
           nui.open({
                url: "<%=basePath%>jsp/classes/importClass.jsp",
                title: "从Excel中导入学生信息", width: 350, height: 200,
                ondestroy: function (action) {
                    grid.reload();
                }
            }); 
        }
        
    </script>
</body>
</html>