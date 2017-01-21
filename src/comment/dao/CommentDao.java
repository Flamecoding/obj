package comment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comment.model.Comment;
import jdbc.JdbcUtil;

public class CommentDao {

	public void insertComment(Connection conn, Comment comment) throws SQLException {
		PreparedStatement stmt = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO COMMENT \n");
			sql.append(" (ARTICLE_NO, MEMBERID, COMMENT_CONTENT, REGDATE, MODDATE, IS_DELETE) \n");
			sql.append(" VALUES (?, ?, ?, SYSDATE(), SYSDATE(), 'N') ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, comment.getArticleNo());
			stmt.setString(2, comment.getMemberId());
			stmt.setString(3, comment.getCommentContent());
			stmt.executeUpdate();
		} finally {
			JdbcUtil.close(stmt);
		}
	}

	public List<Comment> getCommentList(Connection conn, int articleNo) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Comment> commentList = new ArrayList<Comment>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT A.*, B.NAME FROM COMMENT A, MEMBER B ");
			sql.append(" WHERE A.MEMBERID = B.MEMBERID ");
			sql.append(" AND A.ARTICLE_NO = ? AND IS_DELETE = 'N' ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setInt(1, articleNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				commentList.add(new Comment(rs.getString("COMMENT_NO"),
											rs.getString("ARTICLE_NO"),
											rs.getString("MEMBERID"),
											rs.getString("COMMENT_CONTENT"),
											rs.getString("REGDATE"),
											rs.getString("MODDATE"),
											rs.getString("IS_DELETE"),
											rs.getString("NAME")));
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		return commentList;
	}

	public void modifyComment(Connection conn, Comment comment) throws SQLException {
		PreparedStatement stmt = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE COMMENT \n");
			sql.append(" SET COMMENT_CONTENT = ?, MODDATE = SYSDATE() \n");
			sql.append(" WHERE COMMENT_NO = ? ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, comment.getCommentContent());
			stmt.setString(2, comment.getCommentNo());
			stmt.executeUpdate();
		} finally {
			JdbcUtil.close(stmt);
		}
	}

	public void deleteComment(Connection conn, String commentNo) throws SQLException {
		PreparedStatement stmt = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE COMMENT \n");
			sql.append(" SET IS_DELETE = 'Y', MODDATE = SYSDATE() \n");
			sql.append(" WHERE COMMENT_NO = ? ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, commentNo);
			stmt.executeUpdate();
		} finally {
			JdbcUtil.close(stmt);
		}
	}
}
