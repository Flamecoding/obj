package comment.service;

import java.sql.Connection;
import java.sql.SQLException;

import comment.dao.CommentDao;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class DeleteCommentService {

	private CommentDao commentDao = new CommentDao();

	public void deleteComment(String commentNo) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			commentDao.deleteComment(conn, commentNo);
			
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
