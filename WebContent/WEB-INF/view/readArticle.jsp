<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>게시글 읽기</title>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/common/import.jsp" />
<script type="text/javascript">
$(document).ready(function() {
	$('.list-group').load("/comment/list.do?no=${articleData.article.number}");
});

function insertComment() {
	if ($('#commentContent').val() == "") {
		alert("댓글이 없습니다.. 댓글을 입력하세요!");
		$('#commentContent').focus();
		return;
	}
	
	$('#commentInsertBtn').attr('disabled', 'disabled');
	
	$.post('/comment/write.do', {
		commentContent : $('#commentContent').val(),
		memberId : '${articleData.article.writer.id}',
		articleNo : '${articleData.article.number}'
	}, function(data) {
		$('#commentContent').val("");
		$('#commentInsertBtn').attr("disabled", false);
		$('.list-group').load("/comment/list.do?no=${articleData.article.number}");
	});
}

$(document).on('click', '.post-description > #stat > a', function() {
	var commentNo = '';
	if ($(this).attr("name") == "pDel") {
		commentNo = $(this).next().val();
		$('#okBtn4').click(function() {
			$.post('/comment/delete.do', {
				"commentNo" : commentNo
			}, function(data) {
				$('.list-group').load("/comment/list.do?no=${articleData.article.number}");
			});
		});
	} else if ($(this).attr("name") == "pEdit") {
		// 자기 부모의 tr을 알아낸다.
		var parentElement = $(this).parent().parent(); // <div class="post-description"> ... </div>
		
		// 기존 입력 되있던 댓글 내용과 commentNo 추출
		var text = $(this).parent().prev().text();
		console.log(text);
		commentNo = $(this).next().next().val();
		console.log(commentNo);
		
		// 부모의 하단에 댓글편집 창을 삽입
		var commentEditor = "<input type='hidden' name='commentNo' id='commentNo' value='" + commentNo + "'>"
				+ "<textarea class='form-control' rows='3' id='content2' name='content2'>"
				+ text
				+ "</textarea><br>"
				+ "&nbsp;<a class='btn btn-success btn-xs' id='commentModifyBtn'><i class='fa fa-pencil'></i> Modify</a>&nbsp;"
				+ "<a class='btn btn-default btn-xs' id='commentCancelBtn'><i class='fa fa-trash-o'></i> Cancel</a>";
		parentElement.after(commentEditor);

		// 기존 댓글의 폼을 없앤다.
		parentElement.remove();
	}
	
	// 댓글 수정폼 없애기
	$("#commentCancelBtn").click(function() {
		$('.list-group').load("/comment/list.do?no=${articleData.article.number}");
	});
	
	// 댓글 수정
	$("#commentModifyBtn").on('click', function() {
		var content2 = $(this).prev().prev().val();
		cno = $(this).prev().prev().prev().val();
		$.post('/comment/modify.do', {
			"commentNo" : commentNo,
			"content2" : content2
		},
		function(data) {
			$('.list-group').load("/comment/list.do?no=${articleData.article.number}");
		});
	});
});
</script>
</head>
<body>
<section>
	<section class="wrapper site-min-height">
		<div class="col-lg-9">
			<div class="row">
				<div class="col-md-12">
					<h3>
						<i class="fa fa-angle-right"></i> 글번호 : ${articleData.article.number} / 글쓴이 : ${articleData.article.writer.name}
					</h3>
					<div class="content-panel">
						<table class="table">
							<tr>
								<td>
									<h3>제목 : ${articleData.article.title}</h3>
									<div id="myTabContent" class="tab-content">
										<div class="tab-pane fade active in" id="home">
											<p>
												${articleData.content}
											</p>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="input-group">
										<input class="form-control" placeholder="Add a comment" type="text" id="commentContent" name="commentContent"> 
										<span class="input-group-addon" id="commentInsertBtn" onclick="insertComment()" style="cursor: pointer;">
											<a href="#" class="glyphicon glyphicon-pencil"></a>
										</span>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="list-group"></div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
</section>

<div class="modal fade" id="myModal4" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="myModalLabel">Warning</h4>
			</div>
			<div class="modal-body">
				<p>정말로 이 댓글을 삭제 하시겠습니까?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" id="okBtn4" class="btn btn-primary"
					data-dismiss="modal">Yes</button>
			</div>
		</div>
	</div>
</div>

<%-- <table border="1" width="100%">
<tr>
	<td>번호</td>
	<td>${articleData.article.number}</td>
</tr>
<tr>
	<td>작성자</td>
	<td>${articleData.article.writer.name}</td>
</tr>
<tr>
	<td>제목</td>
	<td><c:out value='${articleData.article.title}' /></td>
</tr>
<tr>
	<td>내용</td>
	<td><u:pre value='${articleData.content}'/></td>
</tr>
<tr>
	<td colspan="2">
		<c:set var="pageNo" value="${empty param.pageNo ? '1' : param.pageNo}" />
		<a href="list.do?pageNo=${pageNo}">[목록]</a>
		<c:if test="${authUser.id == articleData.article.writer.id}">
		<a href="modify.do?no=${articleData.article.number}">[게시글수정]</a>
		<a href="delete.do?no=${articleData.article.number}">[게시글삭제]</a>
		</c:if>
	</td>
</tr>
</table> --%>

</body>
</html>