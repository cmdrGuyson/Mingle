package Controller;


import Modal.HandleUser;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AcceptFriendRequestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        acceptRequest(request, response);
    }

    //Accept a certain friend request
    private void acceptRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        //Get email of the logged in user
        String email = (String) session.getAttribute("email");
        //Get email of user from which the request is sent from
        String r_email = (String) request.getParameter("email");

        HandleUser handleUser = new HandleUser();
        handleUser.acceptFriendRequest(email, r_email);

        request.getRequestDispatcher("GetProfileController").include(request, response);

    }

}
