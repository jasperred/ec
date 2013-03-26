
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>传释电商管理系统</title>
	<%@ taglib prefix="s" uri="/struts-tags"%>
	<link rel="stylesheet" type="text/css"
		href="js/sencha/resources/css/sencha-touch.css" />
	<script type="text/javascript" src="js/sencha/sencha-touch-all.js"></script>
</head>
<s:action name="verifyRequestAction" id="verifyRequestAction"></s:action>
<s:push value="verifyRequestAction">
	<input type="hidden" value=<s:property value="crumb"/> id="crumb" />
</s:push>