package Controller;

import Modal.HandleUser;
import Modal.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Used to handle registration procedure on form submit */
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            registerUser(request, response);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        // Get data from request parameters
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email").toLowerCase();
        
        try {

            HandleUser handleUser = new HandleUser();
            
            //User object is created using user entered form data and this object is used to register user using handleUser object
            User user = new User(email, password, firstName, lastName);
            handleUser.registerUser(user);

        } catch (MySQLIntegrityConstraintViolationException e) {

            /*If user is trying to create an account with an existing username or password MySQLIntegrityConstraintViolationException is thrown
            from registerUser() method. It is caught here and user sent to same-account.jsp page */
            
            System.out.println(e);
            RequestDispatcher dispatcher = request.getRequestDispatcher("util/same-account.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        request.setAttribute("registered", "registered");

        //After successfull registration user is sent to successful-register.jsp page
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
