<%-- 
    Document   : login
    Created on : Nov 30, 2021, 8:41:50 AM
    Author     : HUYVU
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
        <title>Login Page</title>
        <style>
            .header{
                padding-top: 20px;
                padding-left: 70px;
                padding-bottom: 20px;
                background-color: #EEEEEE; 
                margin-bottom: 1cm;
            }

            .header h1{
                font-size: 50px;
            }


            .login {
                width: 350px;
                height: 175px;
                text-align: center;
                border:1px solid grey;
                border-radius: 5px;
                margin: auto;
                background-color: white;
                padding: auto;
            }

            .loginWithGG {
                width: 350px;
                height: 175px;
                text-align: center;
                margin: auto;
                background-color: white;
                padding: auto;
            }

            .sign {
                width: 343px;
                height: 29px;
                font-size: 18px;
                background-color: #EEEEEE;
                text-align: left;
                padding: 2px;
            }


            input[type=text], select {
                width: 250px;
                height: 25px;
                margin-bottom: 10px;
                border-radius: 5px;
                border: 1px solid grey;
                padding-left: 20px;
                display: inline-block;
                margin-top: 10px;
            }
            input[type=password], select {
                width: 250px;
                height: 25px;
                margin-bottom: 10px;
                border-radius: 5px;
                border: 1px solid grey;
                padding-left: 20px;
                display: inline-block;
            }

            input[type=submit]{
                width: 270px;
                height: 40px;
                border-radius: 5px;
                border:none;
                background-color: #04B45F;
                color: white;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <h1>Hotel Booking</h1>
        </div>
        <form action="DispatchServlet" method="POST">
            <div class="login">
                <div class="sign">
                    Please sign in  
                </div> 

                <input type="text" placeholder="Enter Username" name="username"> <br/>
                <input type="password" placeholder="Enter Password" name="password">
                <input type="hidden" name="currentPage" value="LoadHotelServlet" />         <br/>
                <input type="submit" name="btnAction" value="Login"/>
            </div> 
            <div class="loginWithGG">
                <a href="DispatchServlet?btnAction=Home Page">Go Booking</a>
            </div>

        </form>
        <c:set var="errLogin" value="${requestScope.ERRORLOGIN}"/>
        <c:if test="${not empty errLogin}">
            <div class="row" style="padding-top: 60px">
                <div class="col"></div>
                <div class="col">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <div class="col">
                            <strong>Incorrect account!</strong> ${errLogin}.
                        </div>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"
                                style="padding-left: 600px; padding-top: 10px">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </div>
        </c:if>
    </body>
</html>
