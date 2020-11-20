<%-- 
    Document   : index
    Created on : Jul 17, 2020, 9:25:03 PM
    Author     : Ebisu
--%>

<%@page import="Modal.Comment"%>
<%@page import="Modal.Post"%>
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

    <body style="background: radial-gradient(circle, rgba(2,0,36,1) 0%, rgba(255,255,255,1) 0%, rgba(243,220,255,1) 48%, rgba(183,243,255,1) 100%, rgba(0,212,255,1) 100%);">

        <% User user = (User) request.getAttribute("userProfileData");
            Post post = (Post) request.getAttribute("post");
        %>

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand" href="#">
                    <img src="images/icon.png" width="40" height="40" alt="" />
                </a>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-item nav-link" href="SendHomeController"
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

        <div class="container" style="padding-left: 300px; padding-right: 300px;">



            <!-- Posts -->
            <div class="card align-self-center" style="margin-top: 10px; margin-bottom: 10px; position: relative">

                <!-- Only show delete and edit buttons to post owner -->
                <% if (post.getUserEmail().equals(user.getEmail())) { %>
                <div>
                    <a style="width:50%; display: inline-block;" data-toggle="modal" title="Delete Post" data-target="#deletePostModal" type="button" class="btn btn-light" href=""><i class="delButton fas fa-trash-alt"></i></a>
                    <a style="width:49%; display: inline-block;" data-toggle="modal" title="Edit Post" data-target="#edit-post-modal" type="button" class="btn btn-light" href=""><i class="delButton fas fa-pen-square"></i></a>
                </div>
                <!-- If logged in user is a system admin show delete button -->
                <%} else if (post.getUserEmail().equals(user.getEmail()) || user.getType().equals("admin")) {%>
                    <a data-toggle="modal" title="Delete Post" data-target="#deletePostModal" type="button" class="btn btn-light" href=""><i class="delButton fas fa-trash-alt"></i></a>
                <% } %>

                <div class="card-body">
                    <div class="row">
                        <div class="col">
                            <img
                                src="<%= post.getOwnerProfileImage()%>"
                                alt="Avatar"
                                class="post-profile-image"
                                />
                        </div>
                        <div class="col-10">
                            <h5 class="card-title"><%= post.getOwner()%></h5>
                            <p class="posted-time-p"><%= post.getCreatedAt()%></p>
                        </div>
                    </div>

                    <p class="card-text">
                        <%= post.getPostBody()%>
                    </p>
                </div>
                <img
                    src="<%= post.getPostImageURL()%>"
                    class="card-img-top"
                    alt=""
                    />


                <c:url var="liked" value="LikePostController">
                    <c:param name="postID" value="<%= String.valueOf(post.getPostID())%>"/>
                </c:url>

                <a type="button" class="btn btn-light" href="${liked}">
                    <% if (post.isIsPostLiked()) {
                    %>
                    <i class="fas fa-heart" style="margin: 5px; color: red"></i><span><%= post.getLikeCount()%></span>
                        <% } else {%>
                    <i class="far fa-heart" style="margin: 5px; color: red"></i><span><%= post.getLikeCount()%></span>
                        <%}%>
                </a>


                <!-- Delete Confirmation Modal -->
                <div
                    class="modal fade"
                    id="deletePostModal"
                    tabindex="-1"
                    role="dialog"
                    aria-labelledby="deletePostModalLabel"
                    aria-hidden="true"
                    >
                    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLongTitle">
                                    Delete Post
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
                                <p>Are you sure you want to delete this post?</p>
                                <form method="POST" action="DeletePostController">
                                    <input hidden name="postID" value="<%= String.valueOf(post.getPostID())%>" >
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                        Close
                                    </button>
                                    <button class="btn btn-primary" type="submit" value="submit">
                                        Delete Post
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>


                <!-- Make Comment -->

                <div class="row" style="padding: 10px">

                    <div class="col">
                        <img
                            src="<%= user.getProfileImageURL()%>"
                            alt="Avatar"
                            class="post-profile-image"
                            />
                    </div>

                    <div class="col-10">
                        <form action="CreateCommentController" method="POST"
                              >
                            <input hidden name="postID" value="<%= String.valueOf(post.getPostID())%>" >
                            <textarea
                                class="form-control"
                                id="commentBodyTextArea"
                                rows="2"
                                required
                                name="commentBody"
                                placeholder="Make a comment!"
                                maxlength="255"
                                ></textarea>
                            <div class="form-row" style="padding-left: 4.5px; padding-right: 4.5px">

                                <button
                                    class="btn btn-primary"
                                    type="submit"
                                    style="margin-top: 10px; width: 100%"
                                    >
                                    Comment
                                </button>

                            </div>
                        </form>
                    </div>
                </div>

                <!-- Comments -->
                <c:forEach var="comment" items="${comments}">
                    <hr>
                    <div class="row" style="padding: 10px">
                        <div class="col">
                            <img
                                src="${comment.getOwnerProfileImage()}"
                                alt="Avatar"
                                class="post-profile-image"
                                />
                        </div>
                        <div class="col-10" style="position: relative">
                            <%Comment c = (Comment) pageContext.getAttribute("comment");
                                if (c.getUserEmail().equals(user.getEmail())) { %>

                            <a data-toggle="modal" data-target="#deleteCommentModal" title="Delete Comment" href="#" style="position: absolute; right: 10%" onclick="change(${comment.getCommentID()})"><i class="delButton fas fa-trash-alt"></i></a>

                            <% }%>
                            <h5>${comment.getOwner()}</h5>
                            <p style="font-size: 0.8rem">
                                ${comment.getCommentBody()}
                            </p>
                        </div>
                    </div>
                </c:forEach>

                <!-- Delete Comment Confirmation Modal -->
                <div
                    class="modal fade"
                    id="deleteCommentModal"
                    tabindex="-1"
                    role="dialog"
                    aria-labelledby="deleteCommentModalLabel"
                    aria-hidden="true"
                    >
                    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLongTitle">
                                    Delete Comment
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
                                <p>Are you sure you want to delete this comment?</p>
                                <form method="POST" action="DeleteCommentController">
                                    <input hidden name="postID" value="<%= String.valueOf(post.getPostID())%>" >
                                    <input hidden name="commentID" id="modalCommentID">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                        Close
                                    </button>
                                    <button class="btn btn-primary" type="submit" value="submit">
                                        Delete Comment
                                    </button>
                                </form>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
        </div>

        <!-- Edit Post Dialog/Modal -->
        <div
            class="modal fade"
            id="edit-post-modal"
            tabindex="-1"
            role="dialog"
            aria-hidden="true"
            >
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            Edit Post
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
                        <form method="POST" action="EditPostController">
                            <div class="form-row">
                                <label for="postBodyTextArea">Post Body</label>
                                <textarea
                                    class="form-control"
                                    id="postBodyTextArea"
                                    rows="5"
                                    name="postBody"
                                    required
                                    maxlength="255"
                                    ><%= post.getPostBody() %></textarea>
                                    <input type="text" value="<%= post.getPostID() %>" name="postID" hidden/>
                            </div>
                            <button
                                class="btn btn-primary"
                                type="submit"
                                value="submit"
                                style="margin-top: 15px;"
                                >
                                Edit Post
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- The footer is inserted here -->
        <%@ include file="util/footer.html" %>

    </body>

    <script>

        function change(value) {

            document.getElementById("modalCommentID").value = value;
            console.log(document.getElementById("modalCommentID").value);
        }

    </script>

</html>
