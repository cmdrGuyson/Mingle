package Controller;

import Modal.HandleUser;
import Modal.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchUsersController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        searchUsers(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private void searchUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HandleUser handleUser = new HandleUser();

        String searchKey = (String) request.getParameter("searchKey");
        
        //Get list of users matching search parameters
        List<User> users = handleUser.searchUser(searchKey);

        request.setAttribute("users", users);
        
        //Direct to page displaying users (results)
        RequestDispatcher dispatcher = request.getRequestDispatcher("search-users.jsp");
        dispatcher.forward(request, response);
    }

}
