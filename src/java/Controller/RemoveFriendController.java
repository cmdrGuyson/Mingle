package Controller;


import Modal.HandleUser;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RemoveFriendController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        removeFriend(request, response);
    }

    //Reject a certain friend request
    private void removeFriend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        //Get email of the logged in user
        String email = (String) session.getAttribute("email");
        //Get email of user who has sent the request
        String r_email = (String) request.getParameter("email");

        System.out.println(email);
        System.out.println(r_email);
        
        HandleUser handleUser = new HandleUser();
        //Call remove friend method using both emails
        handleUser.removeFriend(email, r_email);

        response.sendRedirect("SendHomeController");

    }

}
