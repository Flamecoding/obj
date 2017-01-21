package article.service;

import java.sql.Connection;
import java.util.List;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import util.PageBean;
import util.PageUtility;

public class ListArticleService {

	private ArticleDao articleDao = new ArticleDao();

	public List<Article> getArticleList(PageBean bean) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = articleDao.getTotalCount(conn, bean);
			PageUtility bar = new PageUtility(bean.getInterval(), total, bean.getPageNo());
			bean.setPageLink(bar.getPageBar());
			return articleDao.getArticleList(conn, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
