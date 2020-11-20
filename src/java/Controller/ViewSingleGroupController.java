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

public class ViewSingleGroupController extends HttpServlet {

    
    private int groupID;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Get the groupID of the group to be viewed from params
        groupID = Integer.parseInt(request.getParameter("groupID"));
        getGroup(request, response);
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Get the groupID of the group to be viewed from attributes
        groupID = Integer.parseInt(String.valueOf((request.getAttribute("groupID"))));
        getGroup(request, response);
        
    }
    
    private void getGroup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // View information on a single selected group
        
        HttpSession session = request.getSession();
        HandleGroup handleGroup = new HandleGroup();
        HandleUser handleUser = new HandleUser();
        HandlePost handlePost = new HandlePost();
        
        String email = (String) session.getAttribute("email");
        
        //Get user data of logged in user
        User user = handleUser.getUserProfile(email);
        
        //Check if logged in user is the group's administrator
        boolean isGroupAdmin = handleGroup.isGroupAdmin(groupID, (String) session.getAttribute("email"));
        
        //Get posts belonging to the group
        List<Post> posts = handlePost.getAllGroupPosts(groupID);
        
        //Set request attributes
        request.setAttribute("group", handleGroup.getSingleGroup(groupID));
        request.setAttribute("isGroupAdmin", isGroupAdmin);
        request.setAttribute("userProfileData", user);
        request.setAttribute("posts", posts);
        
        //Get number of friend requests
        int numberOfRequests = handleUser.getAllFriendRequests(email).size();
        request.setAttribute("numberOfRequests", numberOfRequests);
        
        //Get number of posts to be approved in user's own groups
        int groupNotifications = handleGroup.getGroupNotifications(email);
        request.setAttribute("groupNotifications", groupNotifications);
        
        //Forward the request to the required page
        RequestDispatcher dispatcher = request.getRequestDispatcher("group.jsp");
        dispatcher.forward(request, response);
        
    }

}
