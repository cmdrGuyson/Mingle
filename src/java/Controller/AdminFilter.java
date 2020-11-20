package Controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/* Prevent users from accessing servlets that should only be accessed by admins */
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        //Cast the ServletRequest to an HttpServletRequest
        HttpServletRequest http_request = (HttpServletRequest) request;

        //Check if there is a session attribute called typeOfUser
        if (http_request.getSession().getAttribute("typeOfUser") != null) {
            String type = (String) http_request.getSession().getAttribute("typeOfUser");

            if (!type.equals("admin")) {
                
                //If typeOfUser is not admin forward request to access-denied page
                RequestDispatcher dispatcher = request.getRequestDispatcher("util/access-denied.jsp");
                dispatcher.forward(request, response);
            }else{
                
                //If the type of user is admin then send the user to the servlet
                chain.doFilter(request, response);
            }
            
        } else {
            
            //If there is no session attribute called type of user forward request to access-denied page
            RequestDispatcher dispatcher = request.getRequestDispatcher("util/access-denied.jsp");
            dispatcher.forward(request, response);
        }

    }

}
