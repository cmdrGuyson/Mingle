package Controller;

import Modal.HandlePost;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeletePostController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        deletePost(request, response);
    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Create HandlePost object object and call delete post method using selected postID
        HandlePost handlePost = new HandlePost();

        handlePost.deletePost(Integer.parseInt((String) request.getParameter("postID")));

        //Redirect to SendHomeController servlet
        response.sendRedirect("SendHomeController");

    }

}
