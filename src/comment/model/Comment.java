package comment.model;

public class Comment {

	private String commentNo;
	private String articleNo;
	private String memberId;
//	private String parentCommentNo;
//	private String commentOrder;
	private String commentContent;
	private String regDate;
	private String modDate;
	private String isDelete;
	private String name;
	
	public Comment() {}

	public Comment(String commentNo, String articleNo, String memberId, String commentContent, String regDate, String modDate, String isDelete, String name) {
		this.commentNo = commentNo;
		this.articleNo = articleNo;
		this.memberId = memberId;
//		this.parentCommentNo = parentCommentNo;
//		this.commentOrder = commentOrder;
		this.commentContent = commentContent;
		this.regDate = regDate;
		this.modDate = modDate;
		this.isDelete = isDelete;
		this.name = name;
	}

	public String getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(String commentNo) {
		this.commentNo = commentNo;
	}

	public String getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(String articleNo) {
		this.articleNo = articleNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

//	public String getParentCommentNo() {
//		return parentCommentNo;
//	}
//
//	public void setParentCommentNo(String parentCommentNo) {
//		this.parentCommentNo = parentCommentNo;
//	}
//
//	public String getCommentOrder() {
//		return commentOrder;
//	}
//
//	public void setCommentOrder(String commentOrder) {
//		this.commentOrder = commentOrder;
//	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
