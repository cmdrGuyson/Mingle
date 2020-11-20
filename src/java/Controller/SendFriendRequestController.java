package Controller;


import Modal.HandleUser;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendFriendRequestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sendRequest(request, response);
    }

    //Method to send a friend request
    private void sendRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        //Get email of the logged in user
        String email = (String) session.getAttribute("email");
        //Get email of user to which the requst is sent
        String r_email = (String) request.getParameter("email");

        //Call addFriend() method on handleUser object with both emails
        HandleUser handleUser = new HandleUser();
        handleUser.addFriend(email, r_email);

        //Redirect to selected user's profile
        request.getRequestDispatcher("GetProfileController").include(request, response);

    }

}
