<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
		
		<title></title>
		
		<link rel="shortcut icon" href="/resources/image/icon.png" />
		<link rel="apple-touch-icon" href="/resources/image/icon.png" />
		
		<!--[if lt IE 9]>
		<script src="/resources/js/html5shiv.js"></script>
		<![endif]-->
		<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
			<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
		<style type="text/css">
			nav span {
				font-size: 20px;
			}
			#Main {
				font-size: 35px;
				margin-left: 300px;
				margin-right: 300px;
			}
			form {
				margin-top: 64px;
			}
			.header {
				background-color: black;
				line-height: 200px;
			}
			.header h3 {
				color: white;
				text-align: center;
				vertical-align: middle;
				padding: 10px;
			}
			.info div input, label, mile {
				font-size: 17px;
			}
			.box {
				margin-left: 31px;
				margin-bottom: 10px;
			}
			.star {
				color: blue;
				font-size: 17px;
			}
			.msg {
				margin-left: 13px;
			}
			#m_mileApply {
				text-align: right;
			}
			input[type=number]::-webkit-inner-spin-button, 
			input[type=number]::-webkit-outer-spin-button {  
   				opacity: 0;
			}
			.mileApply {
				background-color: #CBDDF5;
				line-height: 56px;
			}
			.mileApply span {
				font-size: 20px;
			}
			.applyMile1 {
				margin-left: 30px;
			}
			.applyMile2 {
				margin-left: 630px;
			}
			.n {
				width: 150px;
				margin-left: 40px;
			}
			.v {
				width: 630px;
				text-align: right;
			}
			th {
				background-color: #E2E2E2;
			}
			th label {
				padding-left: 20px;
			}
			td span {
				margin-left: 100px;
				margin-right: 100px;
			}
			#allAgreelabel {
				font-size: 22px;
			}
			#selectMessage {
				font-size: 14px;
			}
			input[type='radio'], input[type='checkbox'] {
				width: 15px;
				height: 15px;
			}
			#goPayment {
				width: 870px;
				border: none;
				height: 52px;
				color: white;
				background-color: black;
				font-size: 24px;
			}
			#goPayment:hover {
				font-weight: bold;
			}
			#proInfo tr td img{
				width: 90px;
				margin-left: 10px;
				margin-right: 30px;
			}
			#proInfo tr td {
				padding: 10px;
			}
			#toggleBtn {
				margin-left: 700px;
			}
		</style>
		<script type="text/javascript">
			$(function() {
				// 회원 정보와 동일 체크박스 처리
				$("#isEqual").click(function() {
					let isEqual = $("#isEqual").prop("checked");
					if (isEqual) {
						$("#m_name").val("${order.m_name}");
						$("#m_addr").val("${order.m_addr}");
						$("#m_addr2").val("${order.m_addr2}");
						$("#m_addr3").val("${order.m_addr3}");
						$("#m_phone").val("${order.m_phone}");
						$("#m_email").val("${order.m_email}");
						$("#m_name").prop("readonly", true);
						$("#m_addr").prop("readonly", true);
						$("#m_addr2").prop("readonly", true);
						$("#m_addr3").prop("readonly", true);
						$("#m_phone").prop("readonly", true);
						$("#m_email").prop("readonly", true);
					} else {
						$("#m_name").val("");
						$("#m_addr").val("");
						$("#m_addr2").val("");
						$("#m_addr3").val("");
						$("#m_phone").val("");
						$("#m_email").val("");
						$("#m_name").prop("readonly", false);
						$("#m_addr").prop("readonly", false);
						$("#m_addr3").prop("readonly", false);
						$("#m_phone").prop("readonly", false);
						$("#m_email").prop("readonly", false);
					}
				});
				
				// 배송 메세지 선택
				$("#message").change(function() {
					$("#message option:selected").each(function() {
						if ($(this).val() == "직접입력") { 
							$("#selectMessage").val(""); 
							$("#selectMessage").attr("readonly", false);
							$("#selectMessage").focus();
						} else if ($(this).index() == 0) {
							$("#selectMessage").val("");
							$("#selectMessage").attr("readonly", true);
						} else {
							$("#selectMessage").val($(this).text());
							$("#selectMessage").attr("readonly", true);
						}
					});
				});
				
				// 적립금 적용과 최종 결제가 처리
				$("#m_mileApply").focusout(function() {
					if ($("#m_mileApply").val() < 0) {
						alert("0이하의 적립금은 사용할 수 없습니다.");
						$("#m_mileApply").val("");
						return;
					} else {
						totalSum();
					}
				});
				
				$("#m_mileApply").keydown(function(key) {
					if ($("#m_mileApply").val() < 0) {
						alert("0이하의 적립금은 사용할 수 없습니다.");
						$("#m_mileApply").val("");
						return;
					} else {
						if (key.keyCode == 13) {
							totalSum();
						}
	                }
	            });

				// 모든 약관 동의 체크 시 모두 체크
				$("#allAgree").click(function() {
					if ($("#allAgree").prop("checked")) {
						$(".agreeChk").prop("checked", true);
					} else {
						$(".agreeChk").prop("checked", false);
					}
				});
				
				// 필수 체크 해제 시 모든 약관 동의 해제
				$("#agree").click(function() {
					if (!$("#agree").prop("checked")) {
						$("#allAgree").prop("checked", false);
					}
				})
				
				// 결제하기 처리 - 유효성 검사
				$("#goPayment").click(function() {
					if (!chkData("#m_name", "수령인을")) {
						return;
					} else if (!chkData("#m_addr2", "주소를")) {
						return;
					} else if (!chkData("#m_addr3", "나머지 주소를")) {
						return;
					} else if (!chkData("#m_phone", "전화번호를")) {
						return;
					} else if (!chkData("#m_email", "이메일을")) {
						return;
					} else if ($("input[type='radio']:checked").length == 0) {
						alert("결제 수단을 선택해 주세요.");
						$("input[type='radio']").focus();
						return;
					} else if ($("input[class='agreeChk']:checked").length == 0) {
						alert("약관에 동의해 주세요.");
						$("input[class='agreeChk']").focus();
						return;
					} else {
						$("#orderFrm").attr({
							"method" : "post",
							"action" : "/order/orderComplete"
						});
						$("#orderFrm").submit();
					}
				});
				
				// 카카오 주소 검색 api
				document.getElementById("m_addr").addEventListener("click", function(){
			        new daum.Postcode({
			            oncomplete: function(data) {
							document.getElementById("m_addr").value = data.zonecode;
			                document.getElementById("m_addr2").value = data.address;
			                document.querySelector("input[name='m_addr3']").focus();
			            }
			        }).open();
			    });
				
				// 버튼 클릭 시 상품 보이기
				let i = 0;
				$("#toggleBtn").click(function() {
					i = i + 1;
					$("#toggleBtn").fadeOut(0.000001);
					
					if (i % 2 == 1) {
						$("#toggleBtn").removeClass("glyphicon-menu-up");
						$("#toggleBtn").addClass("glyphicon-menu-down");
						$("#toggleBtn").fadeIn(1000);
						$("#proInfo").toggle();
					} else {
						$("#toggleBtn").removeClass("glyphicon-menu-down");
						$("#toggleBtn").addClass("glyphicon-menu-up");
						$("#toggleBtn").fadeIn(1000);
						$("#proInfo").toggle();
					}
					
				});
// 				$("#goPayment").click(function(){
// 					IMP.init('iamport');
// 				  	//결제시 전달되는 정보
// 				  	let addr=$("#m_addr2").val();
// 				  	addr+=" ";
// 				  	addr+=$("#m_addr3").val();
// 				  	let amount= ${order.order_totalPayment};
// 				  	let email= $("#m_email").val();
// 				  	let name=${order.m_mile}
// 				  	let tel=${order.m_phone}
// 				  	let postcode=$("#m_addr").val();
// 					IMP.request_pay({
// 							    pg : 'html5_inicis', 
// 							    pay_method : 'card',
// 							    merchant_uid : 'merchant_'+ new Date().getTime(),
// 							    name : '주문명:결제테스트'/*상품명*/,
// 							    amount : amount/*상품 가격*/, 
// 							    buyer_email : eamil/*구매자 이메일*/,
// 							    buyer_name : name,
// 							    buyer_tel : tel/*구매자 연락처*/,
// 							    buyer_addr : addr /*구매자 주소*/,
// 							    buyer_postcode : postcode/*구매자 우편번호*/
// 							}, function (rsp) { // callback
// 							      if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
// 							          // jQuery로 HTTP 요청
// 							          jQuery.ajax({
// 							              url: "/order/orderComplete", // 예: https://www.myservice.com/payments/complete
// 							              method: "POST",
// 							              data: {
// 							                  "complete":"success"
// 							              }
// 							          })
// 							        } else {
// 							          alert("결제에 실패하였습니다. 에러 내용: " +  rsp.error_msg);
// 							        }
// 							      });
			});
			
			function totalSum() {
				let m_mile = ${order.m_mile };
				let applyMile = parseInt($("#m_mileApply").val());
				if ($("#m_mileApply").val() == "") {
					return;
				}
				if (applyMile > m_mile) {
					applyMile = m_mile;
				}
				$("#applyMile2").html(applyMile.toLocaleString() + "원");
				$("#useMile").html("-" + applyMile.toLocaleString() + "원");
				
				let totalSum = (${order.order_totalPayment} - applyMile).toLocaleString();
				$(".totalSum").html(totalSum);
				
				$("input[name='m_mile']").val(${order.m_mile });
				$("input[name='m_mileApply']").val(applyMile);
				$("input[name='m_mileAdd']").val(Math.floor((${order.order_totalPayment} - applyMile) * 0.025 ));
				$("input[name='order_totalPayment']").val(${order.order_totalPayment} - applyMile);
			}
		</script>
	</head>
	<body>
		<form name="orderFrm" id="orderFrm" class="form-horizontal">
			<input type="hidden" name="m_mile">
			<input type="hidden" name="m_mileApply">
			<input type="hidden" name="m_mileAdd">
			<input type="hidden" name="order_totalPayment">
			
			<div class="header">
				<h3>주문/결제</h3>
			</div>
			<div class="title">
				<h3>배송지</h3><hr>
			</div>
			<div class="box">
				<input type="checkbox" id="isEqual" value="equal">
				<label>&nbsp;회원 정보와 동일</label>
			</div>
			<div class="info form-group">
				<div class="form-group">
					<label for="m_name" class="col-sm-2 control-label">받는 사람<span class="star"> *</span></label>
					<div class="col-sm-8">
 						<input type="text" name="m_name" id="m_name" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label for="m_addr" class="col-sm-2 control-label">우편번호<span class="star"> *</span></label>
					<div class="col-sm-8">
 						<input type="text" name="m_addr" id="m_addr" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label for="m_addr2" class="col-sm-2 control-label">주소<span class="star"> *</span></label>
					<div class="col-sm-8">
 						<input type="text" name="m_addr2" id="m_addr2" class="form-control" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label for="m_addr3" class="col-sm-2 control-label">상세 주소<span class="star"> *</span></label>
					<div class="col-sm-8">
 						<input type="text" name="m_addr3" id="m_addr3" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label for="m_phone" class="col-sm-2 control-label">휴대전화<span class="star"> *</span></label>
					<div class="col-sm-8">
 						<input type="text" name="m_phone" id="m_phone" class="form-control" placeholder="010-0000-0000">
					</div>
				</div>
				<div class="form-group">
					<label for="m_phone" class="col-sm-2 control-label">이메일<span class="star"> *</span></label>
					<div class="col-sm-8">
 						<input type="email" name="m_email" id="m_email" class="form-control" placeholder="SmartCloset@example.com">
					</div>
				</div>
				<div class="text-center col-sm-10">
					<select id="message" class="form-control msg">
						<option value="">-- 메시지 선택 (선택사항)</option>
						<option value="배송 전에 미리 연락바랍니다.">배송 전에 미리 연락바랍니다.</option>
						<option value="부재 시 문 앞에 놓아주세요.">부재 시 문 앞에 놓아주세요.</option>
						<option value="빠른 배송 부탁드립니다.">빠른 배송 부탁드립니다.</option>
						<option value="직접입력">직접 입력</option>
					</select>
					<input type="text" name="shipMessage" id="selectMessage" class="form-control msg" readonly="readonly">
				</div><br>
			</div>
			
			<div class="title">
				<hr><h3>주문상품<span id="toggleBtn" class="glyphicon glyphicon-menu-up"></span></h3><hr>
			</div>
			<div>
				<table id="proInfo">
					<c:forEach var="info" items="${productsInfo }">
						<tr>
							<td><img src="/uploadStorage/warehousing/thumb/${info.pro_colorthumb }"></td>
							<td>
								<div>${info.pr_name }</div>
								<div>[옵션: ${info.pro_size } / ${info.pro_color }]</div>
								<div>수량: ${info.cart_goodsCount }</div>
								<div><fmt:formatNumber type="currency" currencySymbol="" maxFractionDigits="0" 
										value="${info.pr_price * info.cart_goodsCount}" groupingUsed="true" />원</div>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="title">
				<hr><h3>할인/부가결제</h3><hr>
			</div>
			<div class="mile">
				<span class="text-left">적립금 (사용가능: 
				<fmt:formatNumber type="currency" currencySymbol="" maxFractionDigits="0" 
					value="${order.m_mile }" groupingUsed="true" />원)</span>
			</div>
			<div>
				<input type="number" id="m_mileApply" class="form-control" min="0">
			</div>
			<div class="mileApply">
				<span class="applyMile1">적용금액</span>
				<span class="applyMile2" id="applyMile2">원</span>
			</div>
			<div class="title">
				<hr><h3>결제정보</h3><hr>
			</div>
			<div>
				<label class="n">주문상품</label>
				<label class="v">
				<c:set var="payment" value="${order.order_totalPayment }" />
				<c:if test="${payment < 52500 }">
					<fmt:formatNumber type="currency" currencySymbol="" maxFractionDigits="0" 
					value="${order.order_totalPayment - 2500}" groupingUsed="true" />
				</c:if>
				<c:if test="${payment >= 52500 }">
					<fmt:formatNumber type="currency" currencySymbol="" maxFractionDigits="0" 
					value="${order.order_totalPayment}" groupingUsed="true" />
				</c:if>
				원</label>
			</div>
			<div>
				<label class="n">할인/부가결제</label>
				<label class="v" id="useMile">
				원</label>
			</div>
			<div>
				<label class="n">배송비</label>
				<label class="v">
				<c:if test="${payment < 52500 }">
					+2,500
				</c:if>
				<c:if test="${payment >= 52500 }">
					+0
				</c:if>
				원</label>
			</div>
			<div class="mileApply">
				<span class="applyMile1">결제금액</span>
				<span class="applyMile2"><span class="totalSum"></span>원</span>
			</div>
			<div class="title">
				<hr><h3>결제수단</h3><hr>
			</div>
			<div>
				<table class="table table-bordered">
					<tr>
						<th><label>결제수단 선택</label></th>
					</tr>
					<tr>
						<td class="text-center">
							<span><input type="radio" name="payMethod" value="실시간 계좌이체"><label>&nbsp;실시간 계좌이체</label></span>
							<span><input type="radio" name="payMethod" value="카드 결제"><label>&nbsp;카드 결제</label></span>
						</td>
					</tr>
				</table>
			</div>
			<div>
				<hr>
				<input type="checkbox" class="agreeChk" id="allAgree">
				<label id="allAgreelabel">모든 약관 동의</label><br>
				<input type="checkbox" class="agreeChk" id="agree">
				<label>[필수] 청약철회 방침 동의</label><hr>
			</div>
			<div>
				<button type="button" id="goPayment"><span class="totalSum">
				<fmt:formatNumber type="currency" currencySymbol="" maxFractionDigits="0" 
					value="${order.order_totalPayment}" groupingUsed="true" /></span>원 결제하기</button>
			</div>
		</form>
	</body>
</html>