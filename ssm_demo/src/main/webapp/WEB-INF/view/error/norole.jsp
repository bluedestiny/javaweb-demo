<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  
String path = request.getContextPath();  
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("base", base);
%>
<!DOCTYPE html>
<html style="height: 100%;">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>幼儿园评估系统</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${base}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/plugins/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${base}/plugins/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="${base}/adminlte/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${base}/adminlte/css/skins/_all-skins.min.css">
    <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  <style type="text/css">
  .main-footer{margin-left:0;}
   html,body {
      margin: 0;
      padding: 0;
      width: 100%;
      height: 100%;
   } 
  </style>
</head>
<body>
     
        <!-- Main include -->
       
 	    <!-- Content Wrapper. Contains page content -->
         
            <!-- Content Header (Page header) -->           
            <!-- Main content -->
            <section class="content container-fluid" >
            	<img alt="" style="width:100%" src="${base }/imgage/norole.jpg"/>
            	<img alt="" style="text-align: center;position: fixed;top: 70%;" src="${base }/imgage/return.png">
            </section>
            <!-- /.content -->
         
        
     
    
</body>

</html>