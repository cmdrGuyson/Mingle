<%-- 
    Document   : profile
    Created on : Jul 19, 2020, 7:22:04 PM
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
        <link rel="stylesheet" type="text/css" href="./css/profile.css" />
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

                        <a class="nav-item nav-link" href="GetProfileController"
                           >Profile<span class="sr-only">(current)</span></a
                        >
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
            <% User user = (User) request.getAttribute("userProfileData");
                String loggedEmail = (String) request.getSession().getAttribute("email");
            %>

            <div
                class="card align-self-center"
                style="margin-top: 20px; position: relative;"
                >
                <img
                    style="object-fit: cover;
                    height: 300px"
                    src="<%= user.getCoverImageURL()%>""
                    alt=""
                    />
                <img
                    src="<%= user.getProfileImageURL()%>"
                    alt="Avatar"
                    class="profile-image"
                    />
                <!-- Show edit profile button only if logged in user is profile owner -->
                <% if (loggedEmail.equals(user.getEmail())) { %>
                <i
                    class="fas fa-pen-square edit-icon"
                    title="Edit Profile"
                    data-toggle="modal"
                    data-target="#edit-modal"
                    ></i>
                <% } else { %>

                <!-- Show buttons for add friend and unfriend and accept friend request -->

                <% String friendRequestSent = (String) request.getAttribute("friendRequestSent");

                    if (friendRequestSent.equals("sent")) {%>

                <form method="POST" action="AcceptFriendRequestController">
                    <input name="email" value="<%=user.getEmail()%>" hidden />
                    <button class="btn btn-primary" style="position: absolute; top: 50%; left: 70%;" type="submit">Accept Request</button>
                </form>

                <% } else { %>


                <% String friendStatus = (String) request.getAttribute("friendStatus");

                    if (friendStatus.equals("None")) {%>
                <!-- Show option to send friend request if not already a friend-->
                <form method="POST" action="SendFriendRequestController">
                    <input name="email" value="<%=user.getEmail()%>" hidden />
                    <button class="btn btn-primary" style="position: absolute; top: 50%; left: 75%;" type="submit">Add Friend</button>
                </form>

                <%        } else if (friendStatus.equals("Accepted")) { %>
                <!-- If alreadya friend show option to remove friend -->
                <button href="${remove}" class="btn btn-danger" style="position: absolute; top: 50%; left: 70%;" data-toggle="modal" data-target="#removeFriendModal">Remove Friend</button>
                <%} else {%>
                <!-- Friend request is already pending -->
                <button class="btn btn-warning" style="position: absolute; top: 50%; left: 70%;">Request Pending</button>
                <%    }%>

                <%}%>

                <%}%>

                <div class="card-body" style="text-align: center;">
                    <h5 class="card-title"><%= user.getFirstName() + " " + user.getLastName()%></h5>
                    <br />
                    <p>
                        <%= user.getBio()%>
                    </p>
                </div>
            </div>
        </div>

        <!-- Edit Profile Dialog/Modal -->
        <div
            class="modal fade"
            id="edit-modal"
            tabindex="-1"
            role="dialog"
            aria-hidden="true"
            >
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            Edit Profile
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
                        <form method="POST" action="ChangeBioController">
                            <div class="form-row">
                                <label for="response">Bio</label>
                                <textarea
                                    class="form-control"
                                    id="responseTextArea"
                                    rows="5"
                                    placeholder="A little bit about yourself."
                                    name="bio"
                                    required
                                    maxlength="255"
                                    ></textarea>
                            </div>
                            <button
                                class="btn btn-primary"
                                type="submit"
                                value="submit"
                                style="margin-top: 15px;"
                                >
                                Change Bio
                            </button>
                        </form>
                        <form
                            action="UploadProfileImageController"
                            method="POST"
                            enctype="multipart/form-data"
                            onsubmit="return beforeSubmit();"
                            >
                            <div class="input-group mb-3" style="margin-top: 10px;">
                                <div class="input-group-prepend" type="submit">
                                    <button class="input-group-text">Upload</button>
                                </div>
                                <div class="custom-file">
                                    <input
                                        type="file"
                                        class="custom-file-input"
                                        name="profileImage"
                                        accept=".png, .jpeg, .jpg"
                                        id="inputImage"
                                        required
                                        />
                                    <label class="custom-file-label" for="inputGroupFile01"
                                           >Upload Profile Image</label
                                    >
                                </div>
                            </div>
                        </form>
                        <form
                            action="UploadCoverImageController"
                            method="POST"
                            enctype="multipart/form-data"
                            onsubmit="return beforeSubmitImage();"
                            >
                            <div class="input-group mb-3" style="margin-top: 10px;">
                                <div class="input-group-prepend" type="submit">
                                    <button class="input-group-text">Upload</button>
                                </div>
                                <div class="custom-file">
                                    <input
                                        type="file"
                                        class="custom-file-input"
                                        name="coverImage"
                                        accept=".png, .jpeg, .jpg"
                                        id="inputCoverImage"
                                        required
                                        />
                                    <label class="custom-file-label" for="inputGroupFile01"
                                           >Upload Cover Image</label
                                    >
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Posts -->

        <div class="container" style="padding-left: 300px; padding-right: 300px;">

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

        <!-- Remove Friend Confirmation Modal -->
        <div
            class="modal fade"
            id="removeFriendModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="removeFriendModalLabel"
            aria-hidden="true"
            >
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            Remove Friend
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
                        <p>Are you sure you want to remove this friend?</p>
                        <form method="POST" action="RemoveFriendController">
                            <input id='modalEmail' name='email' value='<%= user.getEmail()%>' hidden />
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                Close
                            </button>
                            <button class="btn btn-primary" type="submit" value="submit">
                                Remove Friend
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
        //Script to check if file being uploaded is of valid format
        function beforeSubmit()
        {
            var fname = document.getElementById("inputImage").value;
            //console.log(fname);
            
            if(fname === ""){
                return true;
            }
            else if (fname.endsWith(".png") || fname.endsWith(".jpeg") || fname.endsWith(".jpg")) {
                return true;
            } else {
                window.alert("Invalid image type!");
                return false;
            }
            
            return false;
        }

    </script>
    
    <script>
        //Script to check if file being uploaded is of valid format
        function beforeSubmitImage()
        {
            var fname = document.getElementById("inputCoverImage").value;
            //console.log(fname);
            
            if(fname === ""){
                return true;
            }
            else if (fname.endsWith(".png") || fname.endsWith(".jpeg") || fname.endsWith(".jpg")) {
                return true;
            } else {
                window.alert("Invalid image type!");
                return false;
            }
            
            return false;
        }

    </script>
    
</html>
