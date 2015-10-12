<%
	String contextPath=request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
	String user = "has_login";
%>

<script type="text/javascript" src="<%=contextPath%>/common/nui/jquery/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/nui/nui-min.js"></script>

<link href="<%=contextPath%>/common/nui/themes/default/miniui.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/common/nui/themes/icons.css" rel="stylesheet" type="text/css" />
<%@ taglib uri="http://wucl.stdmis.authority.com" prefix="c"%> 