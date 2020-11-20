package Controller;

import Modal.Group;
import Modal.HandleGroup;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeGroupDescriptionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get user entered data from request
        int groupID = Integer.parseInt(request.getParameter("groupID"));

        // Create a new group and set data in object
        Group group = new Group();
        group.setGroupID(groupID);
        group.setDescription((String) request.getParameter("description"));

        // Make new handleGroup object and use group object to change the group description
        HandleGroup handleGroup = new HandleGroup();
        handleGroup.changeGroupDescription(group);  

        
        HttpSession session = request.getSession();

        // Get a new session and see if logged in user is the group admin
        boolean isGroupAdmin = handleGroup.isGroupAdmin(groupID, (String) session.getAttribute("email"));

        // Set request attributes 
        request.setAttribute("group", handleGroup.getSingleGroup(groupID));
        request.setAttribute("isGroupAdmin", isGroupAdmin);

        //Direct the user back to the group page
        request.setAttribute("groupID", groupID);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ViewSingleGroupController");
        dispatcher.forward(request, response);

    }

}
