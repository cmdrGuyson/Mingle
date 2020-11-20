package Controller;

import Modal.HandleComment;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCommentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        deleteComment(request, response);
        
        // Redirect user to the post
        request.getRequestDispatcher("ViewSinglePostController").include(request, response);
        
    }
    
    //Delete a specified comment
    private void deleteComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Get commentID of comment to be deleted
        int commentID = Integer.parseInt(request.getParameter("commentID"));

        HandleComment handleComment = new HandleComment();
        
        //Get postID to be directed to after deleting comment (same post)
        int postID = Integer.parseInt(request.getParameter("postID"));
        request.setAttribute("postID", postID);
        
        //Use handleComment object to delete the comment
        handleComment.deleteComment(commentID);

    }

}
