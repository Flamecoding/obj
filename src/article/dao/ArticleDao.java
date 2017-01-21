package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import article.model.Article;
import article.model.Writer;
import jdbc.JdbcUtil;
import util.PageBean;

public class ArticleDao {

	@SuppressWarnings("resource")
	public Article insertArticle(Connection conn, Article article) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ARTICLE \n");
			sql.append(" (WRITER_ID, WRITER_NAME, TITLE, REGDATE, MODDATE, READ_CNT) \n");
			sql.append(" values (?, ?, ?, ?, ?, 0) ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, article.getWriter().getId());
			stmt.setString(2, article.getWriter().getName());
			stmt.setString(3, article.getTitle());
			stmt.setTimestamp(4, toTimestamp(article.getRegDate()));
			stmt.setTimestamp(5, toTimestamp(article.getModifiedDate()));
			int insertedCount = stmt.executeUpdate();

			if (insertedCount > 0) {
				StringBuilder sql2 = new StringBuilder();
				sql2.append(" SELECT MAX(ARTICLE_NO) FROM ARTICLE ");
				stmt = conn.prepareStatement(sql2.toString());
				rs = stmt.executeQuery();
				if (rs.next()) {
					Integer newNo = rs.getInt(1);
					return new Article(newNo,
							article.getWriter(),
							article.getTitle(),
							article.getRegDate(),
							article.getModifiedDate(),
							0);
				}
			}
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}

	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	public int getTotalCount(Connection conn, PageBean bean) throws SQLException {
//		Statement stmt = null;
//		ResultSet rs = null;
//		try {
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery("select count(*) from article");
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//			return 0;
//		} finally {
//			JdbcUtil.close(rs);
//			JdbcUtil.close(stmt);
//		}
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String word = bean.getWord();
			String key = bean.getKey();

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(*) CNT FROM ARTICLE WHERE 1 = 1 ");
			if (word != null && !word.trim().equals("") && !key.equals("ALL")) {
				if (key.equals("USER_ID")) {
					sql.append(" AND WRITER_ID LIKE ? \n");
				} else if (key.equals("USER_NAME")) {
					sql.append(" AND WRITER_NAME LIKE ? \n");
				} else if (key.equals("ARTICLE_TITLE")) {
					sql.append(" AND TITLE LIKE ? \n");
				} 
			}
			stmt = conn.prepareStatement(sql.toString());
			
			int idx = 1;
			if (word != null && !word.trim().equals("") && !key.equals("ALL")) {
				if (key.equals("USER_ID") || key.equals("USER_NAME") || key.equals("ARTICLE_TITLE")) {
					stmt.setString(idx++, "%" + word + "%");
				} 
			}
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("CNT");
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		return 1;
	}

	public List<Article> getArticleList(Connection conn, PageBean bean) throws SQLException {
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			pstmt = conn.prepareStatement("select * from article " +
//					"order by article_no desc limit ?, ?");
//			pstmt.setInt(1, startRow);
//			pstmt.setInt(2, size);
//			rs = pstmt.executeQuery();
//			List<Article> result = new ArrayList<>();
//			while (rs.next()) {
//				result.add(convertArticle(rs));
//			}
//			return result;
//		} finally {
//			JdbcUtil.close(rs);
//			JdbcUtil.close(pstmt);
//		}
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String key = bean.getKey();
			String word = bean.getWord();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ARTICLE WHERE 1 = 1 \n");
			if (word != null && !word.trim().equals("") && !key.equals("ALL")) {
				if (key.equals("USER_ID")) {
					sql.append(" AND WRITER_ID LIKE ? \n");
				} else if (key.equals("USER_NAME")) {
					sql.append(" AND WRITER_NAME LIKE ? \n");
				} else if (key.equals("ARTICLE_TITLE")) {
					sql.append(" AND TITLE LIKE ? \n");
				} 
			}
			sql.append(" ORDER BY ARTICLE_NO DESC \n");
			sql.append(" LIMIT ?, ? \n");
			stmt = conn.prepareStatement(sql.toString());

			int idx = 1;
			if (word != null && !word.trim().equals("") && !key.equals("all")) {
				if ( key.equals("USER_ID") || key.equals("USER_NAME") || key.equals("ARTICLE_TITLE") ) {
					stmt.setString(idx++, "%" + word + "%");
				} 
			}
			stmt.setInt(idx++, bean.getStart());
			stmt.setInt(idx++, bean.getInterval());
			rs = stmt.executeQuery();
			
			List<Article> list = new ArrayList<Article>();
			while (rs.next()) {
				list.add(convertArticle(rs));
			}
			return list;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}

	private Article convertArticle(ResultSet rs) throws SQLException {
		return new Article(rs.getInt("article_no"),
				new Writer(rs.getString("writer_id"),
						rs.getString("writer_name")),
						rs.getString("title"),
						toDate(rs.getTimestamp("regdate")),
						toDate(rs.getTimestamp("moddate")),
						rs.getInt("read_cnt"));
	}

	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
	
	public Article selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from article where article_no = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			Article article = null;
			if (rs.next()) {
				article = convertArticle(rs);
			}
			return article;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public void increaseReadCount(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = 
				conn.prepareStatement(
						"update article set read_cnt = read_cnt + 1 "+
						"where article_no = ?")) {
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
	}
	
	public int update(Connection conn, int no, String title) throws SQLException {
		try (PreparedStatement pstmt = 
				conn.prepareStatement(
						"update article set title = ?, moddate = now() "+
						"where article_no = ?")) {
			pstmt.setString(1, title);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}
	}
}
