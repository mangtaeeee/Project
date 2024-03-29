<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
		
		<title>Insert title here</title>
	
		<link rel="shortcut icon" href="/resources/image/icon.png" />
		<link rel="apple-touch-icon" href="/resources/image/icon.png" />
		<link rel="stylesheet" type="text/css" href="/resources/include/dist/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/resources/include/dist/css/bootstrap-theme.css">
		
		<script type="text/javascript" src="/resources/include/js/jquery-1.12.4.min.js"></script>
		<script type="text/javascript" src="/resources/include/js/common.js"></script>
		<script type="text/javascript" src="/resources/include/dist/js/bootstrap.min.js"></script>
		<style type="text/css">
		body {
			  padding-top: 170px;
			  padding-bottom: 40px;
			  background-color: #eee;
			}
			
			.form-signin {
			  max-width: 330px;
			  padding: 15px;
			  margin: 0 auto;
			}
			.form-signin .form-signin-heading,
			.form-signin .checkbox {
			  margin-bottom: 10px;
			}
			.form-signin .checkbox {
			  font-weight: normal;
			}
			.form-signin .form-control {
			  position: relative;
			  height: auto;
			  -webkit-box-sizing: border-box;
			     -moz-box-sizing: border-box;
			          box-sizing: border-box;
			  padding: 10px;
			  font-size: 16px;
			}
			.form-signin .form-control:focus {
			  z-index: 2;
			}
			.form-signin input[type="email"] {
			  margin-bottom: -1px;
			  border-bottom-right-radius: 0;
			  border-bottom-left-radius: 0;
			}
			.form-signin input[type="password"] {
			  margin-bottom: 10px;
			  border-top-left-radius: 0;
			  border-top-right-radius: 0;
			}
		</style>
		
		<script type="text/javascript">
		$(function () {
			//로그인 버튼 클릭시
			$("#loginAdminBtn").click(function() {
				if(!chkData("#ad_id","아이디를")) return;
				else if(!chkData("#ad_passwd","비밀번호를")) return; //아이디 비밀번호의 공백 확인
				else{
					$("#f_loginAdmin").attr({
						"method" : "post",
						"action" : "/admin/adminLogin"
					});
					$("#f_loginAdmin").submit();
				}
			});
		});
		</script>
	</head>
	<body>
		<div class="container text-center">
			<form id="f_loginAdmin" name="f_loginAdmin">
				<img alt="설정" src="/resources/image/logo_navy_admin.png" style="width : 350px; margin-bottom : 30px;"/>
				<p class="text-center" style="padding-botton : 40px;color : #1a5276;">${msg}</p>
				 
				 <label for="ad_id" class="sr-only">아이디</label>
				 <input type="text" name="ad_id" id="ad_id" class="form-control" placeholder="아이디" required autofocus style="margin : 20px auto; width : 50%; height : 40px">
				  <label for="ad_passwd" class="sr-only">비밀번호</label>
				 <input type="password" name="ad_passwd" id="ad_passwd" class="form-control" placeholder="비밀번호" required style="margin : 20px auto; width : 50%; height : 40px">
        		<button id="loginAdminBtn" class="btn btn-primary btn-block" style="margin : auto; width:200px !important; margin-top : 40px !important; margin-botton : 40px !important;" type="submit">로그인</button>
        		
			</form>
		</div>
	</body>
</html>