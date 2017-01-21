package comment.service;

import java.sql.Connection;
import java.util.List;

import article.dao.ArticleDao;
import article.model.Article;
import comment.dao.CommentDao;
import comment.model.Comment;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import util.PageBean;
import util.PageUtility;

public class ListCommentService {

	private CommentDao commentDao = new CommentDao();

	public List<Comment> getCommentList(int articleNo) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			return commentDao.getCommentList(conn, articleNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}
	
}
