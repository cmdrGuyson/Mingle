<%-- 
    Document   : register
    Created on : Jul 18, 2020, 1:02:33 PM
    Author     : Ebisu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <link rel="stylesheet" type="text/css" href="css/index.css" />
        <title>ᛗingle</title>
    </head>
    <body style="background: radial-gradient(circle, rgba(2,0,36,1) 0%, rgba(255,255,255,1) 0%, rgba(243,220,255,1) 48%, rgba(183,243,255,1) 100%, rgba(0,212,255,1) 100%);">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand" href="#">
                    <img src="images/icon.png" width="40" height="40" alt="" />
                </a>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-item nav-link" href="index.jsp">Login</a>
                        <a class="nav-item nav-link active" href="#"
                           >Register<span class="sr-only">(current)</span></a
                        >
                    </div>
                </div>
            </div>
        </nav>

        <!-- Posts -->
        <div class="container" style="padding-left: 300px; padding-right: 300px;">
            <div class="card align-self-center" style="margin-top: 20px; width: 80%;">
                <div class="card-body" style="padding: 50px;">
                    <div style="text-align: center;">
                        <img
                            src="images/icon.png"
                            class="card-img-top"
                            alt=""
                            style="width: 50%;"
                            />
                        <h1 class="display-4" style="font-size: 3rem; margin: 30px;">
                            Register
                        </h1>
                    </div>
                    <form
                        class="form-signin"
                        method="POST"
                        action="RegisterController"
                        oninput='confirmPassword.setCustomValidity(confirmPassword.value != password.value ? "Passwords do not match." : ""); email.setCustomValidity(
                        !email.value.endsWith("@apiit.lk")
                        ? "Please register with APIIT email"
                        : ""
                        );'
                        >
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
                            <label for="inputConfirmPassword" class="sr-only"
                                   >Confirm Password</label
                            >
                            <input
                                type="password"
                                id="inputConfirmPassword"
                                maxlength="255"
                                name="confirmPassword"
                                class="form-control"
                                placeholder="Confirm Password"
                                required
                                />
                            <br />
                            <label for="inputFirstName" class="sr-only">First Name</label>
                            <input
                                type="text"
                                id="inputFirstName"
                                class="form-control"
                                maxlength="50"
                                placeholder="First Name"
                                name="firstName"
                                required
                                autofocus
                                />
                            <br />
                            <label for="inputLastName" class="sr-only">Last Name</label>
                            <input
                                type="text"
                                id="inputLastName"
                                class="form-control"
                                maxlength="30"
                                name="lastName"
                                placeholder="Last Name"
                                required
                                autofocus
                                />
                            <br />

                            <button class="btn btn-md btn-primary btn-block" type="submit">
                                Register
                            </button>
                            <p class="mt-5 mb-3 text-muted">
                                Already have an account? <a href="index.jsp">Login!</a>
                            </p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
