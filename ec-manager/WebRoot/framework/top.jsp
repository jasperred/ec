	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>传释电商管理系统</title>
		<%@ taglib prefix="s" uri="/struts-tags"%>
		<link rel="stylesheet" type="text/css"
			href="js/ext/resources/css/ext-all.css" />
		<script type="text/javascript" src="js/jquery/jquery-1.6.2.js"></script>
		<script type="text/javascript" src="js/ext/bootstrap.js"></script>
		<script type="text/javascript" src="js/ext/locale/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="js/common/common.js"></script>
		<script type="text/javascript" src="js/common/ext-common.js"></script>
	</head>
	<s:action name="verifyRequestAction" id="verifyRequestAction"></s:action>
	<s:push value="verifyRequestAction">
		<input type="hidden" value=<s:property value="crumb"/> id="crumb" />
	</s:push>