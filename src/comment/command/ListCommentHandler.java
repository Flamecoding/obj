package comment.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.Comment;
import comment.service.ListCommentService;
import mvc.command.CommandHandler;

public class ListCommentHandler implements CommandHandler {

	private ListCommentService listService = new ListCommentService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int articleNo = Integer.parseInt(req.getParameter("no"));
		System.out.println("articleNo : " + articleNo);
		List<Comment> commentList = listService.getCommentList(articleNo);
		if (!commentList.isEmpty()) {
			req.setAttribute("commentList", commentList);
		}
		return "/WEB-INF/view/listComment.jsp";
	}

}
