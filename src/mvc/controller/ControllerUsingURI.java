package mvc.controller;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.Comment;
import comment.service.DeleteCommentService;
import comment.service.ModifyCommentService;
import comment.service.WriteCommentService;
import mvc.command.CommandHandler;
import mvc.command.NullHandler;

public class ControllerUsingURI extends HttpServlet {

    // <커맨드, 핸들러인스턴스> 매핑 정보 저장
    private Map<String, CommandHandler> commandHandlerMap = new HashMap<>();
    private WriteCommentService writeCommentService = new WriteCommentService();
    private ModifyCommentService modifyCommentService = new ModifyCommentService();
    private DeleteCommentService deleteCommentService = new DeleteCommentService();

    public void init() throws ServletException {
        String configFile = getInitParameter("configFile");
        Properties prop = new Properties();
        String configFilePath = getServletContext().getRealPath(configFile);
        try (FileReader fis = new FileReader(configFilePath)) {
            prop.load(fis);
        } catch (IOException e) {
            throw new ServletException(e);
        }
        Iterator keyIter = prop.keySet().iterator();
        while (keyIter.hasNext()) {
            String command = (String) keyIter.next();
            String handlerClassName = prop.getProperty(command);
            try {
                Class<?> handlerClass = Class.forName(handlerClassName);
                CommandHandler handlerInstance = 
                        (CommandHandler) handlerClass.newInstance();
                commandHandlerMap.put(command, handlerInstance);
            } catch (ClassNotFoundException | InstantiationException 
            		| IllegalAccessException e) {
                throw new ServletException(e);
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getRequestURI();
		if (command.indexOf(request.getContextPath()) == 0) {
			command = command.substring(request.getContextPath().length());
		}
		if (command.equals("/comment/write.do")) {
			writeComment(request, response);
		} else if (command.equals("/comment/modify.do")) {
			modifyComment(request, response);
		} else if (command.equals("/comment/delete.do")) {
			deleteComment(request, response);
		} else {
			CommandHandler handler = commandHandlerMap.get(command);
			if (handler == null) {
				handler = new NullHandler();
			}
			String viewPage = null;
			try {
				viewPage = handler.process(request, response);
			} catch (Throwable e) {
				throw new ServletException(e);
			}
			if (viewPage != null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
				dispatcher.forward(request, response);
			}
		}
    }

	private void writeComment(HttpServletRequest request, HttpServletResponse response) {
		String commentContent = request.getParameter("commentContent");
		String memberId = request.getParameter("memberId");
		String articleNo = request.getParameter("articleNo");
		System.out.println("commentContent : " + commentContent);
		System.out.println("memberId : " + memberId);
		System.out.println("articleNo : " + articleNo);
		
		Comment comment = new Comment();
		comment.setCommentContent(commentContent);
		comment.setMemberId(memberId);
		comment.setArticleNo(articleNo);
		writeCommentService.insertComment(comment);
		try {
			BufferedWriter bw = new BufferedWriter(new Writer() {
				
				@Override
				public void write(char[] cbuf, int off, int len) throws IOException {
				}
				
				@Override
				public void flush() throws IOException {
				}
				
				@Override
				public void close() throws IOException {
				}
			});
			bw.write("end");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("댓글 등록 실패");
		}
	}

//	private void getCommentList(HttpServletRequest request, HttpServletResponse response) {
//		int articleNo = Integer.parseInt(request.getParameter("no"));
//		System.out.println("articleNo : " + articleNo);
//		List<Comment> commentList = listCommentService.getCommentList(articleNo);
//		if (!commentList.isEmpty()) {
//			request.setAttribute("commentList", commentList);
//		}
//	}

	private void modifyComment(HttpServletRequest request, HttpServletResponse response) {
		String content = request.getParameter("content2");
		String commentNo = request.getParameter("commentNo");
		System.out.println("수정할 content : " + content);
		System.out.println("수정할 commentNo : " + commentNo);
		Comment comment = new Comment();
		comment.setCommentContent(content);
		comment.setCommentNo(commentNo);
		modifyCommentService.modifyComment(comment);
		try {
			BufferedWriter bw = new BufferedWriter(new Writer() {
				
				@Override
				public void write(char[] cbuf, int off, int len) throws IOException {
				}
				
				@Override
				public void flush() throws IOException {
				}
				
				@Override
				public void close() throws IOException {
				}
			});
			bw.write("end");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("댓글 수정 실패");
		}
	}

	private void deleteComment(HttpServletRequest request, HttpServletResponse response) {
		String commentNo = request.getParameter("commentNo");
		deleteCommentService.deleteComment(commentNo);
		try {
			BufferedWriter bw = new BufferedWriter(new Writer() {
				
				@Override
				public void write(char[] cbuf, int off, int len) throws IOException {
				}
				
				@Override
				public void flush() throws IOException {
				}
				
				@Override
				public void close() throws IOException {
				}
			});
			bw.write("end");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("댓글 삭제 실패");
		}
	}
}
