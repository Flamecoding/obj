<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>회원제 게시판 예제</title>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/common/import.jsp" />
<%
	Map<String, Boolean> errors = (Map<String, Boolean>) request.getAttribute("errors");
%>
<script type="text/javascript">
$(function () {
	var errors = <%= errors == null ? true : errors.isEmpty() %>
	if (!errors) {
		$('#myModal').attr('style', 'display: block;');
		$('#myModal').attr('aria-hidden', 'false');
		$('#myModal').attr('class', 'modal fade in');
	}
});
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-6">
								<a href="#" class="active" id="login-form-link">Login</a>
							</div>
							<div class="col-xs-6">
								<a href="#" id="register-form-link">Register</a>
							</div>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form id="login-form" action="login.do" method="post" role="form" style="display: block;">
									<div class="form-group">
										<input type="text" name="id" id="id" tabindex="1" class="form-control" placeholder="User Id" value="" required="required">
									</div>
									<div class="form-group">
										<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password" required="required">
									</div>
									<!-- <div class="form-group text-center">
										<input type="checkbox" tabindex="3" class="" name="remember" id="remember">
										<label for="remember"> Remember Me</label>
									</div> -->
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
											</div>
										</div>
									</div>
									<!-- <div class="form-group">
										<div class="row">
											<div class="col-lg-12">
												<div class="text-center">
													<a href="http://phpoll.com/recover" tabindex="5" class="forgot-password">Forgot Password?</a>
												</div>
											</div>
										</div>
									</div> -->
								</form>
								<form id="register-form" action="join.do" method="post" role="form" style="display: none;">
									<div class="form-group">
										<input type="text" name="id" id="id" tabindex="1" class="form-control" placeholder="User Id" value="" required="required">
									</div>
									<div class="form-group">
										<input type="text" name="name" id="name" tabindex="1" class="form-control" placeholder="User Name" value="" required="required">
									</div>
									<div class="form-group">
										<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password" required="required">
									</div>
									<div class="form-group">
										<input type="password" name="confirmPassword" id="confirmPassword" tabindex="2" class="form-control" placeholder="Confirm Password" required="required">
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Register Now">
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title" id="myModalLabel"><i class="fa fa-exclamation-triangle"></i> 오류</h4>
				</div>
				<div class="modal-body">
					<h4>
						<c:choose>
							<c:when test="${ errors.idOrPwNotMatch }">
								아이디와 암호가 일치하지 않습니다.
							</c:when>
							<c:when test="${ errors.id }">
								ID를 입력하세요.
							</c:when>
							<c:when test="${ errors.password }">
								암호를 입력하세요.
							</c:when>
							<c:when test="${ errors.duplicateId }">
								이미 사용중인 아이디입니다.
							</c:when>
							<c:when test="${ errors.confirmPassword }">
								확인 암호를 입력하세요.
							</c:when>
							<c:when test="${ errors.notMatch }">
								두 암호가 일치하지 않습니다.
							</c:when>
						</c:choose>
					</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="closeBtn">Close</button>
				</div>
			</div>
		</div>
	</div>


	<%-- <u:isLogin>
	CT: ${authUser.name}님, 안녕하세요.
	<a href="logout.do">[로그아웃하기]</a>
		<a href="changePwd.do">[암호변경하기]</a>
	</u:isLogin>
	<u:notLogin>
	CT: <a href="join.do">[회원가입하기]</a>
		<a href="login.do">[로그인하기]</a>
	</u:notLogin>
	<br /> --%>
</body>
</html>