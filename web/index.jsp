<%-- 
    Document   : login
    Created on : Jul 17, 2020, 9:31:07 PM
    Author     : Ebisu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <html lang="en">
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
            <link rel="stylesheet" type="text/css" href="css/index.css" />
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

                            <a class="nav-item nav-link active" href="#"
                               >Login<span class="sr-only">(current)</span></a
                            >
                            <a class="nav-item nav-link" href="register.jsp">Register</a>
                        </div>
                    </div>
                </div>
            </nav>

            <!-- Posts -->
            <div class="container" style="padding-left: 300px; padding-right: 300px;">
                <div class="card align-self-center" style="margin-top: 70px; width: 80%;">
                    <div class="card-body" style="padding: 50px;">
                        <div style="text-align: center;">
                            <img
                                src="images/icon.png"
                                class="card-img-top"
                                alt=""
                                style="width: 50%;"
                                />
                            <h1 class="display-4" style="font-size: 3rem; margin: 30px;">
                                Login
                            </h1>
                        </div>
                        <form class="form-signin" method="POST" action="LoginController">
                            <br />
                            <div class="text-center">
                                <label for="inputEmail" class="sr-only">Email</label>
                                <input
                                    type="email"
                                    id="inputEmail"
                                    class="form-control"
                                    name="email"
                                    placeholder="Email"
                                    maxlength="50"
                                    required
                                    autofocus
                                    />
                                <br />
                                <label for="inputPassword" class="sr-only">Password</label>
                                <input
                                    type="password"
                                    maxlength="255"
                                    id="inputPassword"
                                    class="form-control"
                                    name="password"
                                    placeholder="Password"
                                    required
                                    />
                                <br />

                                <button class="btn btn-md btn-primary btn-block" type="submit">
                                    Login
                                </button>
                                <p class="mt-5 mb-3 text-muted">
                                    Don't have an account? <a href="register.jsp">Register!</a>
                                </p>
                            </div>
                        </form>

                        <% if ((String) request.getAttribute("registered") != null) { %>

                        <div class="alert alert-warning" role="alert">
                            Successfully Registered! Please Login!
                        </div>

                        <% }%>
                    </div>
                </div>

            </div>


            <!-- The footer is inserted here -->
            <%@ include file="util/footer.html" %>

        </body>
    </html>
