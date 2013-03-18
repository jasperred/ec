<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <base href="<%=basePath%>">  
      
    <title>My JSP 'imageIframe.jsp' starting page</title>  
    <script type="text/javascript">  
        function change(){  
            document.location.reload();  
        }  
    </script>  
    <style type="text/css">  
        a{text-decoration:none;}  
        a:visited{text-decoration:none;}  
        a:active{text-decoration:none;}   
        a:link{text-decoration:none;}  
        a:hover{text-decoration:none;}  
  
    </style>  
  </head>  
    
  <body style="margin:0px;">  
    <div id='img-div'><a href="javascript: change();" title="点击重新获取图片"><img src='rand' style='border : none;'/></a></div>  
  </body>  
</html> 