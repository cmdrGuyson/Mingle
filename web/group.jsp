<%-- 
    Document   : index
    Created on : Jul 17, 2020, 9:25:03 PM
    Author     : Ebisu
--%>

<%@page import="java.util.List"%>
<%@page import="Modal.Post"%>
<%@page import="Modal.User"%>
<%@page import="Modal.Group"%>
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
        <link rel="stylesheet" type="text/css" href="css/group.css" />
        <link rel="stylesheet" type="text/css" href="css/home.css" />
        <title>Mingle</title>
    </head>

    <body style="background: radial-gradient(circle, rgba(2,0,36,1) 0%, rgba(255,255,255,1) 0%, rgba(243,220,255,1) 48%, rgba(183,243,255,1) 100%, rgba(0,212,255,1) 100%);">

        <% Group group = (Group) request.getAttribute("group");
        %>

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

            <!-- Group Details -->
            <div class="card align-self-center group-box" style="margin-top: 20px; position: relative">
                <div class="card-body">
                    <div class="row">
                        <div class="col">
                            <img
                                src="<%= group.getGroupImageURL()%>"
                                alt="Avatar"
                                style="height: 100px; width: 100px"
                                class="post-profile-image"
                                />
                        </div>
                        <div class="col-9">
                            <h5 class="card-title"><%= group.getGroupName()%></h5>
                            <p class="posted-time-p"><%= group.getDescription()%></p>
                        </div>

                        <!-- Show delete option only if logged in user is the group admin -->

                        <% if ((boolean) request.getAttribute("isGroupAdmin")) { %>
                        <i class="fas fa-pen-square" title="Edit Group Details" 
                           data-toggle="modal"
                           data-target="#editGroupModal"></i>
                        <% }%>

                    </div>
                </div>
            </div>

            <!-- Edit Group Modal -->
            <div
                class="modal fade"
                id="editGroupModal"
                tabindex="-1"
                role="dialog"
                aria-labelledby="createGroupModalLabel"
                aria-hidden="true"
                >
                <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLongTitle">
                                Edit Group
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
                            <form method="POST" action="ChangeGroupDescriptionController">

                                <div class="form-row" style="margin-top: 10px">
                                    <label for="description">Description</label>
                                    <input type="text" hidden value="<%= group.getGroupID()%>" name="groupID">
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
                                <br />
                                <button class="btn btn-primary" type="submit" value="submit">
                                    Change Description
                                </button>
                            </form>
                            <form
                                action="UploadGroupImageController"
                                method="POST"
                                enctype="multipart/form-data"
                                onsubmit="return beforeSubmitImage();"
                                >
                                <input name="groupID" hidden value="<%= group.getGroupID()%>">
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
                                               >Upload Group Image</label
                                        >
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>


            <% User user = (User) request.getAttribute("userProfileData"); %>

            <!-- If Group is Active show ability to create a group post -->
            <% if (group.getStatus().equals("Active")) {%>



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
                        <div class="col-10">
                            <form action="CreateGroupPostController"
                                  method="POST"
                                  enctype="multipart/form-data"
                                  onsubmit="return beforeSubmit();">
                                <input name="groupID" value="<%=group.getGroupID()%>" hidden />
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
                                    <div class="col-2">

                                    </div>
                                    <div class="col-4">
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

            <% }%>


            <!-- Posts -->

            <!-- If no posts are available -->
            <% List<Post> posts = (List<Post>) request.getAttribute("posts");
                if (posts.size() == 0) {
            %>
            <div class="jumbotron" style="background-color: transparent">
                <h1 class="display-4" style="background-color: transparent">No posts available!</h1>
            </div>
            <%  }
            %>

            <c:forEach var="post" items="${posts}">

                <% Post post = (Post) pageContext.getAttribute("post"); %>

                <!-- Logged in user is the group admin -->
                <% if ((boolean) request.getAttribute("isGroupAdmin")) { %>

                <div class="card align-self-center" style="margin-top: 10px; margin-bottom: 10px; position: relative; text-decoration: none; color: black;">
                    <%if (post.getStatus().equals("Pending")) {%>

                    <span class="badge badge-warning" style='height: 20px; position: absolute;'>Pending</span>
                    <form method="POST" action="AcceptGroupPostController">
                        <input hidden name="groupID" value='<%= group.getGroupID()%>' />
                        <input hidden name="postID" value='<%= post.getPostID()%>' />
                        <button type="submit" class="btn btn-outline-primary" style="position: absolute; right: 19%; top: 10%; z-index: 20">Accept</button>
                    </form>
                    <form method="POST" action="RejectGroupPostController">
                        <input hidden name="groupID" value='<%= group.getGroupID()%>' />
                        <input hidden name="postID" value='<%= post.getPostID()%>' />
                        <button class="btn btn-outline-danger" style="position: absolute; right: 5%; top: 10%; z-index: 20">Reject</button>
                    </form>

                    <%} else {%>

                    <!-- Show delete button to group administrator -->
                    <button data-toggle="modal" title="Delete Post" data-target="#deletePostModal" class="btn btn-outline-danger" style="position: absolute; right: 5%; top: 10%; z-index: 20" onclick="change(<%= post.getPostID() %>)">Delete</button>

                    <% } %>
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
                                <p class="posted-time-p">${post.getCreatedAt()}</p>
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
                <!-- Logged in user is the post owner -->
                <%    } else if (post.getUserEmail().equals(user.getEmail())) { %>

                <div class="card align-self-center" style="margin-top: 10px; margin-bottom: 10px; position: relative; text-decoration: none; color: black;">
                    <%if (post.getStatus().equals("Pending")) { %>

                    <span class="badge badge-warning" style='height: 20px; position: absolute;'>Pending</span>

                    <%}%>
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
                                <p class="posted-time-p">${post.getCreatedAt()}</p>
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

                <!-- Post is an active post -->
                <%     } else if (post.getStatus().equals("Active")) { %>

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
                                <p class="posted-time-p">${post.getCreatedAt()}</p>
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

                <%  }%>


            </c:forEach>

        </div>

        <!-- Delete Post Confirmation Modal -->
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
                        <form method="POST" action="RejectGroupPostController">
                            <input hidden name="groupID" value="<%= group.getGroupID() %>" >
                            <input id='modalPostID' name='postID' hidden />
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

        <!-- The footer is inserted here -->
        <%@ include file="util/footer.html" %>

    </body>
    
    <script>
        //Script used to change the postID hidden input field inside the dialog's form according to selected post
        function change(value) {

            document.getElementById("modalPostID").value = value;
            console.log(document.getElementById("modalPostID").value);
        }

    </script>
    
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
