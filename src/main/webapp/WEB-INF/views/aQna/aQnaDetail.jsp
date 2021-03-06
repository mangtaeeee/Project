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
	
		<!-- 모바일 웹 페이지 설정 -->
		<link rel="shortcut icon" href="/resources/image/icon.png" />
		<link rel="apple-touch-icon" href="/resources/image/icon.png" />
		<link rel="stylesheet" type="text/css" href="/resources/include/dist/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/resources/include/dist/css/bootstrap-theme.css">
		<style type="text/css">
			.highlight1{
				width : 150px !important;
				background-color:#A4AFBC; 
				color : white;
			}
		</style>
		
		<script type="text/javascript" src="/resources/include/js/jquery-1.12.4.min.js"></script>
		<script type="text/javascript" src="/resources/include/js/common.js"></script>
		<script type="text/javascript" src="/resources/include/dist/js/bootstrap.min.js"></script>
		<script type="text/javascript">
			$(function () {
				$("#QnaDeleteBtn").click(function () {
					if (confirm("게시글을 삭제하시겠습니까?")) {
						goUrl = "/aQna/aQnaDelete";
						$("#f_data").attr("action", goUrl);
						$("#f_data").submit();
					}
				})
				
				$("#goToQnaListBtn").click(function () {
					location.href="/aQna/aQnaList";
				})
			})
		</script>
	
	</head>
	<body>
		<div style="margin-bottom : 100px;">
			<h2 style="color : #1A5276;"><strong>QnA 게시판 관리</strong></h2><br>
			
			<%-- 수정, 삭제 시 가져갈 글번호, 원본파일명을 전달하는 폼 테이터 --%>
			<form name="f_data" id="f_data" method="post">
				<input type="hidden" name="q_num" value="${detail.q_num }">
				<input type="hidden" name="q_file" value="${detail.q_file }">
			</form>
			<%-- =============== 상세 정보 보여주기 시작 =============== --%>
			<div >
				<table class="table table-bordered">
		 			<tr>
		 				<td class="highlight1">글번호</td>
		 				<td style="width : 300px;">${detail.q_num }</td>
		 				<td class="highlight1">작성일</td>
		 				<td>${detail.q_regdate }</td>
		 			</tr>
		 			<tr>
		 				<td class="highlight1">작성자</td>
		 				<td colspan="3">${detail.m_id }</td>
		 			</tr>
		 			<tr>
		 				<td class="highlight1">글제목</td>
		 				<td colspan="3">${detail.q_title }</td>
		 			</tr>
		 			<tr class="table-height" style="height : 200px">
		 				<td class="highlight1">글내용</td>
		 				<td colspan="3">${detail.q_content }</td>
		 			</tr>
		 			<c:if test="${not empty detail.q_file }">
		 				<tr>
		 					<td class="highlight1">이미지</td>
		 					<td colspan="3" class="text-left">
		 						<img style="max-width : 500px; max-height:500px;" src="/uploadStorage/QnA/${detail.q_file }">
		 					</td>
		 				</tr>
		 			</c:if>
				</table>
			</div>
			<%-- =============== 상세 정보 보여주기 종료 =============== --%>
			<div class="text-right contentBtn ">
				<input type="button" id="QnaDeleteBtn" name="QnaDeleteBtn" class="btn btn-default btn-sm" value="삭제"/>
				<input type="button" id="goToQnaListBtn" name="goToQnaListBtn" class="btn btn-default btn-sm" value="목록"/>
				<hr>
			</div>
			<jsp:include page="reply.jsp"/>
			
		</div>
	</body>
</html>