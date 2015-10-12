<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/common.css" type="text/css" />
<title>提示信息</title>
<%@include file="/common/common.jsp" %>
</head>

<body>
<a class="nui-button" iconCls="icon-tip" plain="true"></a>提示信息：
<h2>
<%=request.getAttribute("msg")%>
</h2>
</body>
</html>
