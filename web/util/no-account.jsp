<%-- 
    Document   : no-account
    Created on : Mar 31, 2020, 10:28:58 PM
    Author     : Ebisu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Mingle | Error</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />
        <link rel="stylesheet" type="text/css" href="css/errors.css" />
        <link rel="icon" href="images/icon.png" />
    </head>

    <body class="sameAccount">
        <!--Content-->
        <div class="container container-home content">
            <div class="row">
                <div class="col-sm-12 col-md-12 col-xs-12 centerAlign">
                    <div class="image-hack">
                        <img src="images/no-account.gif" height="400" />
                    </div>
                </div>
                <div class="col-sm-12 col-md-12 col-xs-12 accessDeniedText">
                    <h2 class="whiteText boldText">
                        Oops! You entered invalid credentials...
                    </h2>
                     <p> <a href="index.jsp" class="whiteText"> Click here to sign in </a> </p>
                </div>
            </div>
        </div>

        <%@ include file="footer.html" %>

    </body>

</html>
