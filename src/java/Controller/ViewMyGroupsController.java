package Controller;

import Modal.Group;
import Modal.HandleGroup;
import Modal.HandleUser;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewMyGroupsController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        getMyGroups(request, response);
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    private void getMyGroups(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Get groups to be displayed using handleGroup object and forward reques to groups.jsp page
        HandleGroup handleGroup = new HandleGroup();
        
        HandleUser handleUser = new HandleUser();
        
        HttpSession session = request.getSession();
        
        String email = (String) session.getAttribute("email");
        
        //Get all active groups
        List<Group> groups = handleGroup.getMyGroups(email);
        
        request.setAttribute("groups", groups);
        
        //Get number of friend requests
        int numberOfRequests = handleUser.getAllFriendRequests(email).size();
        request.setAttribute("numberOfRequests", numberOfRequests);
        
        //Get number of posts to be approved in user's own groups
        int groupNotifications = handleGroup.getGroupNotifications(email);
        request.setAttribute("groupNotifications", groupNotifications);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("my-groups.jsp");
        dispatcher.forward(request, response);
        
    }

}
