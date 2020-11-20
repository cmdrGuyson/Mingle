package Controller;


import Modal.HandlePost;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AcceptGroupPostController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        acceptPost(request, response);
    }

    //Accept a certain post and make it publically available in a group
    private void acceptPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Get data from request
        int postID = Integer.parseInt(request.getParameter("postID"));
        int groupID = Integer.parseInt(request.getParameter("groupID"));
        
        //Accept the group post and make it visible to the public
        HandlePost handlePost = new HandlePost();
        handlePost.acceptPost(postID);

        //Direct the user back to the group page
        request.setAttribute("groupID", groupID);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ViewSingleGroupController");
        dispatcher.forward(request, response);

    }

}
