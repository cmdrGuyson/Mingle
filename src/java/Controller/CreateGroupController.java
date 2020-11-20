package Controller;

import Modal.Group;
import Modal.HandleGroup;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateGroupController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        createGroup(request, response);
        
    }
    
    private void createGroup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get session details and user input from request, then create new group object
        HttpSession session = request.getSession();
        Group group = new Group(request.getParameter("name"), request.getParameter("description"), (String) session.getAttribute("email"));
        
        //Create a new handleGroup object and call createGroup method using created group object
        HandleGroup handleGroup = new HandleGroup();
        handleGroup.createGroup(group);
        
        //Redirect user to ViewMyGroupsController controller
        response.sendRedirect("ViewMyGroupsController");
    }

}
