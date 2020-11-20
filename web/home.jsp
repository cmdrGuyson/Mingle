<%-- 
    Document   : index
    Created on : Jul 17, 2020, 9:25:03 PM
    Author     : Ebisu
--%>

<%@page import="Modal.Post"%>
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
        <link rel="stylesheet" type="text/css" href="css/home.css" />

        <title>Mingle</title>
    </head>

    <%
        //String type = (String) request.getSession().getAttribute("typeOfUser");

        //if (type == "user") {

    %>

    <style>
        .friend-list{
            box-shadow: none !important;
            -webkit-box-shadow: none !important;
            margin-top: 5px; 
            margin-bottom: 5px;
            margin-left: 5px;
            margin-right: 5px;
        }

        .friend-list:hover{
            cursor: pointer;
            -webkit-box-shadow: 0px 0px 10px -2px rgba(0, 0, 0, 0.53);
            -moz-box-shadow: 0px 0px 10px -2px rgba(0, 0, 0, 0.53);
            box-shadow: 0px 0px 10px -2px rgba(0, 0, 0, 0.53);
        }
    </style>

    <body style="background: radial-gradient(circle, rgba(2,0,36,1) 0%, rgba(255,255,255,1) 0%, rgba(243,220,255,1) 48%, rgba(183,243,255,1) 100%, rgba(0,212,255,1) 100%);">

        <% User user = (User) request.getAttribute("userProfileData");
        %>

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand" href="#">
                    <img src="images/icon.png" width="40" height="40" alt="" />
                </a>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-item nav-link active" href="#"
                           >Home<span class="sr-only">(current)</span></a
                        >
                        <a class="nav-item nav-link" href="GetProfileController">Profile</a>
                        <a class="nav-item nav-link" href="ViewFriendsController">Friends
                            <% int num = (int) request.getAttribute("numberOfRequests");
                                if (num > 0) {%>
                            <span class="badge badge-danger" style="margin-left: 2px"><%=num%></span>
                            <%}%>
                        </a>
                        <a class="nav-item nav-link" href="ViewAllGroupsController">Groups
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





        <div class="container">
            <div class='row'>
                <div class="col-3">
                    <div class="card align-self-center" style="margin-top: 20px;">
                        <div
                            class="card-header"
                            style="font-size: 0.8rem; margin-bottom: 5px"
                            >
                            <b>Friends</b>
                        </div>
                        <c:forEach var="friend" items="${friends}">

                            <c:url var="selected" value="GetProfileController">
                                <c:param name="email" value="${friend.getEmail()}"/>
                            </c:url>
                            <a
                                style='color: black'
                                class="card friend-list"
                                href='${selected}'
                                >
                                <div class="card-body" style="padding: 10px">
                                    <div class="row">
                                        <div class="col-3">
                                            <img
                                                src="${friend.getProfileImageURL()}"
                                                alt="Avatar"
                                                style="height: 50px; width: 50px"
                                                class="post-profile-image"
                                                />
                                        </div>
                                        <div class="col-8">
                                            <p class="card-title">${friend.getFirstName()} ${friend.getLastName()}</p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                        <div
                            class="card-footer"
                            style="font-size: 0.8rem; margin-top: 5px; text-align: center"
                            >
                            <a style='color: black' href='ViewFriendsController'>Show more</a>
                        </div>
                    </div>
                </div>
                <div class='col-6'>
                    <!-- Create Post -->
                    <div class="card align-self-center" style="margin-top: 20px;">
                        <div class="card-body">
                            <div class="row">
                                <div class="col">
                                    <img
                                        src="<%= user.getProfileImageURL()%>"
                                        alt="Avatar"
                                        class="post-profile-image"
                                        />
                                </div>
                                <!--//action="CreatePostController"-->
                                <div class="col-10">
                                    <form action="CreatePostController"
                                          method="POST"
                                          enctype="multipart/form-data"
                                          onsubmit="return beforeSubmit();">
                                        <textarea
                                            class="form-control"
                                            id="postBodyTextArea"
                                            rows="3"
                                            required
                                            name="postBody"
                                            placeholder="Tell the world something!"
                                            maxlength="255"
                                            ></textarea>
                                        <div class="form-row">
                                            <div class="col-6">
                                                <div class="input-group mb-3" style="margin-top: 10px;">
                                                    <div class="custom-file">
                                                        <input
                                                            type="file"
                                                            class="custom-file-input"
                                                            id="inputImage"
                                                            accept=".png, .jpeg, .jpg"
                                                            name="postImage"
                                                            />
                                                        <label class="custom-file-label" for="inputGroupFile01"
                                                               >Select Image</label
                                                        >
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-4">
                                                <select id="inputStatus" class="form-control" name="privacy" style="margin-top: 10px;">
                                                    <option selected>Public</option>
                                                    <option>Friends</option>
                                                </select>
                                            </div>
                                            <div class="col-2">
                                                <button
                                                    class="btn btn-primary"
                                                    type="submit"
                                                    style="margin-top: 10px; width: 100%"
                                                    >
                                                    Post
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Posts -->


                    <!-- If no posts are available -->
                    <% List<Post> posts = (List<Post>) request.getAttribute("posts");
                        if (posts.size() == 0) {
                    %>
                    <div class="jumbotron" style="background-color: transparent">
                        <h1 class="display-4">No posts available!</h1>
                    </div>
                    <%  }
                    %>

                    <c:forEach var="post" items="${posts}">
                        <div class="card align-self-center" style="margin-top: 10px; margin-bottom: 10px; position: relative; text-decoration: none; color: black;">

                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <img
                                            src="${post.getOwnerProfileImage()}"
                                            alt="Avatar"
                                            class="post-profile-image"
                                            />
                                    </div>
                                    <div class="col-10">
                                        <h5 class="card-title">${post.getOwner()}</h5>
                                        <p class="posted-time-p">${post.getCreatedAt()}<span class="badge badge-primary" style="margin-left: 10px">${post.getPrivacy()}</span></p>
                                    </div>
                                </div>

                                <p class="card-text">
                                    ${post.getPostBody()}
                                </p>
                            </div>
                            <img
                                src="${post.getPostImageURL()}"
                                class="card-img-top"
                                alt=""
                                />

                            <c:url var="selected" value="ViewSinglePostController">
                                <c:param name="postID" value="${post.getPostID()}"/>
                            </c:url>

                            <a type="button" title="View Post" class="btn btn-light" href="${selected}"><i class="fas fa-heart" style="margin: 5px;"></i><i class="fas fa-comment" style="margin: 5px"></i></a>
                        </div>
                    </c:forEach>
                </div>
                <div class="col-3">
                    <div class="card align-self-center" style="margin-top: 20px;">
                        <div
                            class="card-header"
                            style="font-size: 0.8rem; margin-bottom: 5px"
                            >
                            <b>Groups</b>
                        </div>
                        <c:forEach var="group" items="${groups}">

                            <c:url var="selectedGroup" value="ViewSingleGroupController">
                                <c:param name="groupID" value="${group.getGroupID()}"/>
                            </c:url>
                            <a
                                style='color: black'
                                class="card friend-list"
                                href='${selectedGroup}'
                                >
                                <div class="card-body" style="padding: 10px">
                                    <div class="row">
                                        <div class="col-3">
                                            <img
                                                src="${group.getGroupImageURL()}"
                                                alt="Avatar"
                                                style="height: 50px; width: 50px"
                                                class="post-profile-image"
                                                />
                                        </div>
                                        <div class="col-8">
                                            <p class="card-title">${group.getGroupName()}</p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                        <div
                            class="card-footer"
                            style="font-size: 0.8rem; margin-top: 5px; text-align: center"
                            >
                            <a style='color: black' href='ViewAllGroupsController'>Show more</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- The footer is inserted here -->
        <%@ include file="util/footer.html" %>

    </body>

    <%   /* } else {

            RequestDispatcher dispatcher = request.getRequestDispatcher("util/access-denied.jsp");
            dispatcher.forward(request, response);
        } */
    %>

    <script>
        //Script to check if file being uploaded is of valid format
        function beforeSubmit()
        {
            var fname = document.getElementById("inputImage").value;
            //console.log(fname);

            if (fname === "") {
                return true;
            } else if (fname.endsWith(".png") || fname.endsWith(".jpeg") || fname.endsWith(".jpg")) {
                return true;
            } else {
                window.alert("Invalid image type!");
                return false;
            }

            return false;
        }

    </script>

</html>
