<%-- 
    Document   : index
    Created on : Jul 17, 2020, 9:25:03 PM
    Author     : Ebisu
--%>

<%@page import="Modal.Group"%>
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
                        <a class="nav-item nav-link" href="ViewFriendsController">Friends
                            <% int num = (int) request.getAttribute("numberOfRequests");
                                if (num > 0) {%>
                            <span class="badge badge-danger" style="margin-left: 2px"><%=num%></span>
                            <%}%>
                        </a>
                        <a class="nav-item nav-link active" href="ViewAllGroupsController">Groups
                            <% int numGroup = (int) request.getAttribute("groupNotifications");
                                if (numGroup > 0) {%>
                            <span class="badge badge-danger" style="margin-left: 2px"><%=numGroup%></span>
                            <%}%></a>
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


            <!-- My Groups Group Button -->
            <a class="card align-self-center" style="margin-top: 20px; text-align: center; text-decoration: none" href="ViewMyGroupsController">
                <div class="card-body">
                    <div class="create-group-button">
                        <h1 class="display-4" style="font-size: 3rem">My Groups</h1>
                    </div>

                </div>
            </a>

            <%
                String type = (String) request.getSession().getAttribute("typeOfUser");
                if (type == "admin") {
            %>

            <!-- Approve Groups (Only shown to admins)-->
            <a class="card align-self-center" style="margin-top: 20px; text-align: center; text-decoration: none" href="ViewManagedGroupsController">
                <div class="card-body">
                    <div class="create-group-button">
                        <h1 class="display-4" style="font-size: 3rem">Manage Groups</h1>
                    </div>

                </div>
            </a>

            <%}%>

            <!-- Create Group Button-->
            <div class="card align-self-center" style="margin-top: 20px; text-align: center">
                <div class="card-body">
                    <div class="create-group-button" data-toggle="modal" data-target="#createGroupModal">
                        <h1 class="display-4" style="font-size: 3rem"><i class="fas fa-plus-circle" style="margin-right: 25px"></i>Create Group</h1>
                    </div>

                </div>
            </div>

            <!-- Create Group Modal -->
            <div
                class="modal fade"
                id="createGroupModal"
                tabindex="-1"
                role="dialog"
                aria-labelledby="createGroupModalLabel"
                aria-hidden="true"
                >
                <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLongTitle">
                                Create Group
                            </h5>
                            <button
                                type="button"
                                class="close"
                                data-dismiss="modal"
                                aria-label="Close"
                                >
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div class="modal-body">
                            <form method="POST" action="CreateGroupController">

                                <div class="form-row">
                                    <label for="name">Group Name</label>
                                    <input class="form-control" id="name" name="name" required>
                                </div>
                                <div class="form-row" style="margin-top: 10px">
                                    <label for="description">Description</label>
                                    <textarea
                                        class="form-control"
                                        id="descriptionTextArea"
                                        rows="4"
                                        placeholder="Small description about the group."
                                        name="description"
                                        required
                                        maxlength="255"
                                        ></textarea>
                                </div>

                                <hr />
                                <br />
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                    Close
                                </button>
                                <button class="btn btn-primary" type="submit" value="submit">
                                    Create Group
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Groups -->
            <c:forEach var="group" items="${groups}">

                <c:url var="selected" value="ViewSingleGroupController">
                    <c:param name="groupID" value="${group.getGroupID()}"/>
                </c:url>

                <a class="card align-self-center group-box" style="margin-top: 20px; margin-bottom: 5px; text-decoration: none" href="${selected}">
                    <div class="card-body" style="position: relative">

                        <%Group g = (Group) pageContext.getAttribute("group");
                            if (g.getPendingPosts() > 0) { %>

                        <span class="badge badge-warning" style="position: absolute; color: white; top: 85%; left:75%"><%=g.getPendingPosts()%> Pending Posts</span>

                        <%}%>
                        <div class="row">
                            <div class="col">
                                <img
                                    src="${group.getGroupImageURL()}"
                                    alt="Avatar"
                                    style="height: 100px; width: 100px"
                                    class="post-profile-image"
                                    />
                            </div>
                            <div class="col-9">
                                <h5 class="card-title">${group.getGroupName()}</h5>
                                <p class="posted-time-p">${group.getDescription()}</p>
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
