package Controller;

import Modal.HandlePost;
import Modal.Post;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditPostController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        editPost(request, response);
        
        // Redirect user to the post
        request.getRequestDispatcher("ViewSinglePostController").include(request, response);
        
    }
    
    //Edit a selected post
    private void editPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Get postID and the new post body (edited)
        int postID = Integer.parseInt(request.getParameter("postID"));
        String postBody = (String) request.getParameter("postBody");
        
        //Create new HandlePost object
        HandlePost handlePost = new HandlePost();
        
        //Invoke editPost() method on handlePost object using a new Post object to edit post body
        handlePost.editPost(new Post(postID, postBody));

    }

}
