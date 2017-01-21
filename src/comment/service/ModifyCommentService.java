package comment.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.service.PermissionDeniedException;
import comment.dao.CommentDao;
import comment.model.Comment;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ModifyCommentService {

	private CommentDao commentDao = new CommentDao();

	public void modifyComment(Comment comment) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			commentDao.modifyComment(conn, comment);

			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

//	private boolean canModify(String modfyingUserId, Article article) {
//		return article.getWriter().getId().equals(modfyingUserId);
//	}
}
