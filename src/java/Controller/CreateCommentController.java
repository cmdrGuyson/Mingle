package Controller;

import Modal.Comment;
import Modal.HandleComment;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CreateCommentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        makeComment(request, response);
        
        // Redirect user to the post
        request.getRequestDispatcher("ViewSinglePostController").include(request, response);
        
    }
    
    //Comment on post
    private void makeComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        int postID = Integer.parseInt(request.getParameter("postID"));
        
        String email = (String) session.getAttribute("email");
        
        String commentBody = (String) request.getParameter("commentBody");
        
        //Create comment object
        Comment comment = new Comment(commentBody, email, postID);
        
        HandleComment handleComment = new HandleComment();
        
        //Use handleComment object and create the comment
        handleComment.createComment(comment);

    }

}
