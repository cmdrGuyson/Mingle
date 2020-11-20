package Controller;

import Modal.Group;
import Modal.HandleGroup;
import Modal.HandlePost;
import Modal.HandleUser;
import Modal.Post;
import Modal.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendHomeController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        sendUserHome(request, response);
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    //Redirect users to home page after getting all posts to be displayed
    private void sendUserHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HandleUser handleUser = new HandleUser();
        HandlePost handlePost = new HandlePost();
        HandleGroup handleGroup = new HandleGroup();
        
        //Get logged in user's email
        HttpSession session = request.getSession();
        String email =(String) session.getAttribute("email");
        
        //Get posts to be displayed
        List<Post> posts = handlePost.getAllPosts(email);
        
        //Get user data of logged in user
        User user = handleUser.getUserProfile(email);
        
        //Attach request attributes
        request.setAttribute("userProfileData", user);
        request.setAttribute("posts", posts);
        
        //Get number of friend requests
        int numberOfRequests = handleUser.getAllFriendRequests(email).size();
        request.setAttribute("numberOfRequests", numberOfRequests);
        
        //Get number of posts to be approved in user's own groups
        int groupNotifications = handleGroup.getGroupNotifications(email);
        request.setAttribute("groupNotifications", groupNotifications);
        
        //Get list of friends
        List<User> users = handleUser.getAllFriends(email);
        List<User> friends = new ArrayList<>();
        
        for(int i=0; i<users.size(); i++){
            friends.add(users.get(i));
            if(i==4) break;
        }
        
        //Get list of friends
        List<Group> gps = handleGroup.getAllGroups("Active");
        List<Group> groups = new ArrayList<>();
        
        for(int i=0; i<gps.size(); i++){
            groups.add(gps.get(i));
            if(i==4) break;
        }
        
        request.setAttribute("friends", friends);
        request.setAttribute("groups", groups);
        
        //Direct user to home page
        RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
        dispatcher.forward(request, response);
        
    }

}
