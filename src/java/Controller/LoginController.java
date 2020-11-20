package Controller;

import Modal.HandleUser;
import Modal.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Create user object from user entered data (taken from request)
        User user = new User(request.getParameter("email"), request.getParameter("password"));
 
        HandleUser handleUser = new HandleUser();

        String accountType = "";

        try {
            
            //Authenticate user by using handleUser object.
            accountType = handleUser.authenticateUser(user);
            
        } catch (Exception e) {
            System.out.println(e);
        }

        HttpSession session = request.getSession();
        
        session.setAttribute("email", user.getEmail());
        
        switch (accountType) {
            
            //Assign attributes to session data and redirect to respective home pages
            
            case "admin":
                session.setAttribute("typeOfUser", "admin");
                response.sendRedirect("SendHomeController");
                break;
            case "user":
                session.setAttribute("typeOfUser", "user");
                response.sendRedirect("SendHomeController");
                break;
            case "inactive-user":
                //If user account is not active
                request.getRequestDispatcher("util/account-deactivated.jsp").include(request, response);
                break;
            default:
                //If user doesn't have an account
                request.getRequestDispatcher("util/no-account.jsp").include(request, response);
        }
    }

}
