<%-- 
    Document   : manage-groups
    Created on : Aug 4, 2020, 8:31:11 PM
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

            <h3 class='display-4' style='margin-top: 40px; margin-bottom: 40px; font-size: 40px'>Manage Groups</h3>


            <!-- Groups -->
            <c:forEach var="group" items="${groups}">

                <c:url var="selected" value="ViewSingleGroupController">
                    <c:param name="groupID" value="${group.getGroupID()}"/>
                </c:url>

                <div class="card align-self-center group-box" style="margin-top: 20px; text-decoration: none" href="${selected}" >
                    <div class="card-body">
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
                                <%Group g = (Group) pageContext.getAttribute("group");
                                    if (g.getStatus().equals("Inactive")) { %>
                                <span class="badge badge-danger" style='height: 20px; margin-bottom: 10px'>Inactive</span>
                                <%}%>
                                <p class="posted-time-p">${group.getDescription()}</p>


                                <% if (g.getStatus().equals("Inactive")) { %>
                                <button data-toggle="modal" data-target="#manageGroupModal" type="button" class="btn btn-outline-primary fluid" onclick="change(${group.getGroupID()})">Activate Group</button>
                                <% } else { %>
                                <button data-toggle="modal" data-target="#manageGroupModal" type="button" class="btn btn-outline-danger fluid" onclick="change(${group.getGroupID()})">Deactivate Group</button>
                                <% }%>

                            </div>

                        </div>
                    </div>
                </div>
            </c:forEach>

            <!-- Change Group Status Confirmation Modal -->
            <div
                class="modal fade"
                id="manageGroupModal"
                tabindex="-1"
                role="dialog"
                aria-labelledby="manageGroupModalLabel"
                aria-hidden="true"
                >
                <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLongTitle">
                                Change Group Status
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
                            <p>Are you sure you want to change group status?</p>
                            <form method="POST" action="ManageGroupController">
                                <input hidden name="groupID" id="modalGroupID">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                    Close
                                </button>
                                <button class="btn btn-primary" type="submit" value="submit">
                                    Change Status
                                </button>
                            </form>
                        </div>

                    </div>
                </div>
            </div>

        </div>

        <!-- The footer is inserted here -->
        <%@ include file="util/footer.html" %>

    </body>

    <script>
        //Script used to change the groupID hidden input field inside the dialog's form according to selected group
        function change(value) {

            document.getElementById("modalGroupID").value = value;
            console.log(document.getElementById("modalGroupID").value);
        }

    </script>

</html>
