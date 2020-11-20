package Controller;

import Modal.HandleGroup;
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

//Servlet to view the profile of a single user
public class GetProfileController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String param = (String) request.getParameter("email");

        //If an email parameter exists then direct to profile owned by email else direct to personal profile
        if (param != null) {
            viewOtherProfile(request, response);
        } else {
            viewPersonalProfile(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void viewPersonalProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HandleUser handleUser = new HandleUser();
        HttpSession session = request.getSession();
        HandleGroup handleGroup = new HandleGroup();

        String email = (String) session.getAttribute("email");

        // Make user object using email of logged in user and get all details of the user
        User user = handleUser.getUserProfile(email);
        // Set request attributes and forward the request to profile page
        request.setAttribute("userProfileData", user);
        
        //Get number of friend requests
        int numberOfRequests = handleUser.getAllFriendRequests(email).size();
        request.setAttribute("numberOfRequests", numberOfRequests);
        
        //Get number of posts to be approved in user's own groups
        int groupNotifications = handleGroup.getGroupNotifications(email);
        request.setAttribute("groupNotifications", groupNotifications);
        

        //Get user's posts
        HandlePost handlePost = new HandlePost();
        List<Post> posts = handlePost.getUsersPosts(email, "Self");

        // Set request attributes and forward the request to profile page
        request.setAttribute("posts", posts);

        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);

    }

    private void viewOtherProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HandleUser handleUser = new HandleUser();
        HandleGroup handleGroup = new HandleGroup();

        HttpSession session = request.getSession();
        

        //Get logged in user's email using session
        String my_email = (String) session.getAttribute("email");

        //Get email of user who's profile should be displayed
        String email = (String) request.getParameter("email");

        //Find out if profile owner is a friend and assign to request attributes
        String status = handleUser.getFriendStatus(my_email, email);
        request.setAttribute("friendStatus", status);
        
        //Find out if the user has sent you a request
        String friendRequestSent = handleUser.friendRequestSent(my_email, email);
        request.setAttribute("friendRequestSent", friendRequestSent);
        
        // Make user object using email of logged in user and get all details of the user
        User user = handleUser.getUserProfile(email);

        //Get user's posts
        HandlePost handlePost = new HandlePost();
        List<Post> posts = handlePost.getUsersPosts(email, status);

        request.setAttribute("posts", posts);
        
        
        //Get number of friend requests
        int numberOfRequests = handleUser.getAllFriendRequests(my_email).size();
        request.setAttribute("numberOfRequests", numberOfRequests);
        
        //Get number of posts to be approved in user's own groups
        int groupNotifications = handleGroup.getGroupNotifications(my_email);
        request.setAttribute("groupNotifications", groupNotifications);

        // Set request attributes and forward the request to profile page
        request.setAttribute("userProfileData", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);

    }

}
