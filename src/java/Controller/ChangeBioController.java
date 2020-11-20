package Controller;

import Modal.HandleUser;
import Modal.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeBioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        //Get new bio from request
        String newBio = (String) request.getParameter("bio");
        HandleUser handleUser = new HandleUser();
        
        User user = new User();
        
        //Initialize new user object from data
        user.setBio(newBio);
        user.setEmail((String) session.getAttribute("email"));
        
        //Call updateBio() method on handleUser object using the created user object
        handleUser.updateBio(user);
        
        //Redirect to the GetProfileController to load profile again
        response.sendRedirect("GetProfileController");
    }
    
}
