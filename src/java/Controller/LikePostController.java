package Controller;

import Modal.HandleLike;
import Modal.Like;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LikePostController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        handleLikeOnPost(request, response);
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    
    //Like or unlike post
    private void handleLikeOnPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        int postID = Integer.parseInt(request.getParameter("postID"));
        
        //Get email of the logged in user
        String email = (String) session.getAttribute("email");
        
        //Create new like object
        Like like = new Like(postID, email);
        
        HandleLike handleLike = new HandleLike();
        
        // If post is already liked by user unlike it else like it
        if(handleLike.isPostLiked(postID, email)) {
            handleLike.unlikePost(like);
        }else{
            handleLike.likePost(like);
        }
        
        // Redirect user to the post
        request.getRequestDispatcher("ViewSinglePostController").include(request, response);
    }

}
