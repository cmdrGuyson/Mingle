package Controller;

import Modal.HandleGroup;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Used to activate or deactivate a group (Used by admin)
public class ManageGroupController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        manageGroup(request, response);
        
        // Redirect admin to manageble groups page
        response.sendRedirect("ViewManagedGroupsController");
        
    }
    
    //Like or unlike post
    private void manageGroup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Get groupID from request parameters
        int groupID = Integer.parseInt(request.getParameter("groupID"));

        // Change status of selected group
        HandleGroup handleGroup = new HandleGroup();
        handleGroup.manageGroup(groupID);
        
    }

}
