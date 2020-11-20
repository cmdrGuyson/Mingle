package Controller;

import Modal.HandleGroup;
import Modal.HandleUser;
import Modal.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewFriendsController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        getAllFriends(request, response);
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getAllFriends(request, response);
    }
    
    private void getAllFriends(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        HandleGroup handleGroup = new HandleGroup();
        
        //Get email of the logged in user
        String email = (String) session.getAttribute("email");
        
        //Get groups to be displayed using handleGroup object and forward reques to groups.jsp page
        HandleUser handleUser = new HandleUser();
        
        //Get all friend requests
        List<User> friendRequests = handleUser.getAllFriendRequests(email);
        
        request.setAttribute("friendRequests", friendRequests);
        
        //Get all friends
        List<User> friends = handleUser.getAllFriends(email);
        request.setAttribute("friends", friends);
        
        //Get number of friend requests
        int numberOfRequests = handleUser.getAllFriendRequests(email).size();
        request.setAttribute("numberOfRequests", numberOfRequests);
        
        //Get number of posts to be approved in user's own groups
        int groupNotifications = handleGroup.getGroupNotifications(email);
        request.setAttribute("groupNotifications", groupNotifications);
        
        //Direct to friends page
        RequestDispatcher dispatcher = request.getRequestDispatcher("friends.jsp");
        dispatcher.forward(request, response);
        
    }

}
