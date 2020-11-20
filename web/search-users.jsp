<%-- 
    Document   : search-users
    Created on : Aug 3, 2020, 8:49:00 PM
    Author     : Ebisu
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="Modal.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="icon" href="images/icon.png" />
        <link
            rel="stylesheet"
            href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
            integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
            crossorigin="anonymous"
            />
        <link
            href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap"
            rel="stylesheet"
            />
        <link rel="stylesheet" type="text/css" href="css/my-groups.css" />
        <title>Mingle</title>
    </head>

    <body style="background: radial-gradient(circle, rgba(2,0,36,1) 0%, rgba(255,255,255,1) 0%, rgba(243,220,255,1) 48%, rgba(183,243,255,1) 100%, rgba(0,212,255,1) 100%);">

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand" href="#">
                    <img src="images/icon.png" width="40" height="40" alt="" />
                </a>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">

                        <a class="nav-item nav-link" href="SendHomeController">Home</a>
                        <a class="nav-item nav-link" href="GetProfileController">Profile</a>
                        <a class="nav-item nav-link" href="ViewFriendsController">Friends</a>
                        <a class="nav-item nav-link" href="ViewAllGroupsController"
                           >Groups<span class="sr-only">(current)</span></a
                        >
                        <a class="nav-item nav-link" href="LogoutController">Logout</a>
                    </div>
                </div>
                <form class="form-inline" action="SearchUsersController">
                    <input
                        class="form-control mr-sm-2"
                        type="search"
                        placeholder="Search"
                        aria-label="Search"
                        name="searchKey"
                        />
                    <button class="btn btn-outline-primary my-2 my-sm-0" type="submit">
                        Search
                    </button>
                </form>
            </div>
        </nav>







        <div class="container" style="padding-left: 300px; padding-right: 300px;">     


            <!-- If no users a shown -->
            <% List<User> users = (List<User>) request.getAttribute("users");
                if (users.size() == 0) {
            %>

            <div class="jumbotron" style="background-color: transparent">
                <h1 class="display-4" style="background-color: transparent">No users found!</h1>
            </div>
            <%  }

            %>

            <!-- Users -->
            <c:forEach var="user" items="${users}">

                <c:url var="selected" value="GetProfileController">
                    <c:param name="email" value="${user.getEmail()}"/>
                </c:url>

                <a class="card align-self-center group-box" style="margin-top: 20px; text-decoration: none" href="${selected}">
                    <div class="card-body">
                        <div class="row">
                            <div class="col">
                                <img
                                    src="${user.getProfileImageURL()}"
                                    alt="Avatar"
                                    style="height: 100px; width: 100px"
                                    class="post-profile-image"
                                    />
                            </div>
                            <div class="col-9">
                                <h5 class="card-title">${user.getFirstName()} ${user.getLastName()}</h5>
                                <p class="posted-time-p">${user.getBio()}</p>
                            </div>
                        </div>
                    </div>
                </a>
            </c:forEach>

        </div>

        <!-- The footer is inserted here -->
        <%@ include file="util/footer.html" %>

    </body>

</html>
