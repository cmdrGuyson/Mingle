package Controller;

import Modal.HandlePost;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RejectGroupPostController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        deletePost(request, response);
    }

    //The rejected post will be deleted
    private void deletePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Get data from request
        int postID = Integer.parseInt(request.getParameter("postID"));
        int groupID = Integer.parseInt(request.getParameter("groupID"));
        
        //Create HandlePost object object and call delete post method using selected postID
        HandlePost handlePost = new HandlePost();

        handlePost.deletePost(postID);

        //Direct the user back to the group page
        request.setAttribute("groupID", groupID);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ViewSingleGroupController");
        dispatcher.forward(request, response);

    }

}
