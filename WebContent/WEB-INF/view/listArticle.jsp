<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>게시글 목록</title>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/common/import.jsp" />
</head>
<body>
	<jsp:useBean id="pageBean" class="util.PageBean" scope="request" />
	<form id="frm" name="frm" method="post">
		<input type="hidden" name="no" id="no" value="" />
		<input type="hidden" name="pageNo" id="pageNo" value="${pageBean.pageNo}" />
		<section class="layer">
			<section class="wrapper site-min-height">
				<div class="col-lg-12">
					<div class="row">
						<div class="col-md-12">
							<div class="content-panel">
								<table class="table table-striped table-hover">
									<thead>
									<tr>
										<th>번호</th>
										<th>제목</th>
										<th>작성자</th>
										<th>조회수</th>
									</tr>
									</thead>
									<tbody>
									<c:if test="${empty articleList}">
										<tr>
											<td colspan="4"><div style="text-align: center;"><span>게시글이 없습니다.</span></div></td>
										</tr>
									</c:if>
									<c:forEach var="article" items="${articleList}">
										<tr>
											<td>${article.number}</td>
											<td>
											<a href="read.do?no=${article.number}">
											<c:out value="${article.title}"/>
											</a>
											</td>
											<td>${article.writer.name}</td>
											<td>${article.readCount}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								
								
								<!-- 페이징 -->
								
								<%-- <div>
									<c:if test="${articlePage.hasArticles()}">
										<tr>
											<td colspan="4">
												<c:if test="${articlePage.startPage > 5}">
												<a href="list.do?pageNo=${articlePage.startPage - 5}">[이전]</a>
												</c:if>
												<c:forEach var="pNo" 
														   begin="${articlePage.startPage}" 
														   end="${articlePage.endPage}">
												<a href="list.do?pageNo=${pNo}">[${pNo}]</a>
												</c:forEach>
												<c:if test="${articlePage.endPage < articlePage.totalPages}">
												<a href="list.do?pageNo=${articlePage.startPage + 5}">[다음]</a>
												</c:if>
											</td>
										</tr>
									</c:if>
								</div> --%>
								<!-- // 페이징 -->
								
								<div class="col-md-5 col-md-offset-5">
									<ul class="pagination">
										${pageBean.pageLink}
									</ul>
								</div>
								<div class="col-md-2 col-md-offset-5">
									<div class="form-group">
										<div>
											<select class="form-control" name="key">
												<option value="ALL" <%=pageBean.getKey("ALL")%>>전체</option>
												<option value="USER_ID" <%=pageBean.getKey("USER_ID")%>>아이디</option>
												<option value="USER_NAME" <%=pageBean.getKey("USER_NAME")%>>작성자</option>
												<option value="ARTICLE_TITLE" <%=pageBean.getKey("ARTICLE_TITLE")%>>제목</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<input type="text" class="form-control" accesskey="s" title="검색어" placeholder="검색어" name="word" id="word" value="${pageBean.word}">
									</div>
									<a href="javascript:pageList(1)" class="btn btn-default">검색</a>
								</div>
								<a href="write.do" class="btn btn-primary" style="float: right;">글쓰기</a>
							</div>
						</div>
					</div>
				</div>
			</section>
		</section>
	</form>
</body>
</html>