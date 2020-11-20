package Controller;

import Modal.Comment;
import Modal.HandleComment;
import Modal.HandleGroup;
import Modal.HandleLike;
import Modal.HandlePost;
import Modal.HandleUser;
import Modal.Post;
import Modal.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewSinglePostController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getSinglePost(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getSinglePost(request, response);
    }

    private void getSinglePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //View information on a single post
        
        HandleUser handleUser = new HandleUser();
        HandlePost handlePost = new HandlePost();
        HandleLike handleLike = new HandleLike();
        HandleGroup handleGroup = new HandleGroup();
        int postID = Integer.parseInt(request.getParameter("postID"));
        
        Post post = handlePost.getPost(postID);
        HttpSession session = request.getSession();
        
        String email = (String) session.getAttribute("email");
        
        //Get number of friend requests
        int numberOfRequests = handleUser.getAllFriendRequests(email).size();
        request.setAttribute("numberOfRequests", numberOfRequests);
        
        //Get number of posts to be approved in user's own groups
        int groupNotifications = handleGroup.getGroupNotifications(email);
        request.setAttribute("groupNotifications", groupNotifications);
        
        User user = handleUser.getUserProfile(email);
        
        post.setIsPostLiked(handleLike.isPostLiked(postID, email));
        
        //Getting all comments for the post
        HandleComment handleComment = new HandleComment();
        List<Comment> comments = handleComment.getAllComments(postID);
        
        
        
        request.setAttribute("comments", comments);
        request.setAttribute("post", post);
        request.setAttribute("userProfileData", user);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("single-post.jsp");
        dispatcher.forward(request, response);
        
        
        
    }

}
