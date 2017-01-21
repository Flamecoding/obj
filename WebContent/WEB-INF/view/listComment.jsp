<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach var="comment" items="${commentList}">
	<div class="panel panel-white post">
		<div class="post-heading" id="commentTable">
			<div class="title h5">
				<a href="#"><b>${comment.name}</b></a> made a comment.
			</div>
			<h6 class="text-muted time">${comment.regDate}</h6>
		</div>
		<div class="post-description">
			<span id="content3" name="content3">${comment.commentContent}</span>
			<c:if test="${authUser.id == comment.memberId}">
				<div class="stats" id="stat">
					<a class="btn btn-success btn-xs" role="button" name="pEdit"><i class="fa fa-pencil"></i>Modify</a>
					<a class="btn btn-warning btn-xs" role="button" name="pDel" data-toggle="modal" data-target="#myModal4"><i class="fa fa-trash-o"></i>Delete</a>
					<input type="hidden" name="commentNo" id="commentNo" value="${comment.commentNo}">
				</div>
			</c:if>
		</div>
	</div>
</c:forEach>