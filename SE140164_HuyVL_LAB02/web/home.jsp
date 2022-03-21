<%-- 
    Document   : home.jsp
    Created on : Sep 26, 2021, 7:10:09 PM
    Author     : HUYVU
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style.css" type="text/css">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
        <title>Hotel Booking</title>
    </head>
    <body>
        <c:set var="isLogin" value="${sessionScope.ISLOGIN}"/>
        <c:set var="isAdmin" value="${sessionScope.ADMIN}"/>
        <c:set var="userEmail" value="${sessionScope.USERID}"/>
        <c:set var="err" value="${requestScope.ERR}"/>
        <c:set var="pickHotelID" value="${sessionScope.HOTELID}"/>
        <c:set var="pickDate" value="${sessionScope.DATE}"/>
        <c:set var="isActive" value="${requestScope.ISACTIVE}"/>
        <c:set var="typeList" value="${sessionScope.TYPELIST}"/>
        <c:set var="fbList" value="${sessionScope.FBLIST}"/>
        <c:set var="areaList" value="${sessionScope.AREALIST}"/>
        <c:set var="hotelList" value="${requestScope.HOTELLIST}"/>
        <c:set var="roomList" value="${requestScope.ROOMLIST}"/>
        <c:set var="isRegister" value="${requestScope.REGISTER}"/>
        <c:set var="isLoginFail" value="${requestScope.ERRORLOGIN}"/>
        <c:if test="${not empty isLogin}">
            <c:set var="welcomeName" value="${sessionScope.FULLNAME}"/>
        </c:if>
        <!-- thanh navbar -->
        <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
            <a class="navbar-brand"><font color="white">Hotel Booking</font></a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ">    
                    <c:if test="${isAdmin eq 'True'}">
                        <li class="nav-item">
                            <a class="nav-link" href="DispatchServlet?btnAction=#   ">Admin role</a>
                        </li> 
                    </c:if>  
                    <c:if test="${not isAdmin eq 'True'}">
                        <li class="nav-item">
                            <a class="nav-link" href="DispatchServlet?btnAction=View Cart">Booking Cart</a>
                        </li> 
                    </c:if>
                    <c:if test="${isAdmin eq 'False'}">
                        <li class="nav-item">
                            <a class="nav-link" href="DispatchServlet?btnAction=My Order">Booking History</a>
                        </li> 
                    </c:if>                

                </ul> 
            </div>
            <div class="form-inline">
                <c:if test="${empty isLogin}">
                    <a class="nav-link" href="#" style="color: lightgray"
                       onclick="document.getElementById('register').style.display = 'block'"
                       >Register</a>
                    <a href="login.jsp" class="btn btn-danger" style="background-color: #d9534f; width: 100px" >Login</a>
                </c:if>
                <c:if test="${not empty isLogin}">
                    <form action="DispatchServlet" method="POST" class="form-inline">
                        <font color="white">Welcome, ${welcomeName}</font>
                        <button class="btn btn-danger form-inline" style="width: 100px"
                                type="submit" value="Logout" name="btnAction">Logout</button>
                    </form>
                </c:if>
            </div>   
        </nav>
        <!-- Toast -->
        <c:if test="${not empty err}">
            <div class="row" style="padding-top: 60px">
                <div class="col"></div>
                <div class="col">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <div class="col">
                            <strong>Warning!!</strong>${err}.
                        </div>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"
                                style="padding-left: 600px; padding-top: 10px">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty isLoginFail}">
            <div class="row" style="padding-top: 60px">
                <div class="col"></div>
                <div class="col">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <div class="col">
                            <strong>Warning!!</strong>${isLoginFail}.
                        </div>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"
                                style="padding-left: 600px; padding-top: 10px">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty isRegister}">
            <c:if test="${isRegister eq 'Fail'}">
                <div class="row" style="padding-top: 60px">
                    <div class="col"></div>
                    <div class="col">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            <div class="col">
                                <strong>Warning!!</strong>This Email already registered.
                            </div>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"
                                    style="padding-left: 600px; padding-top: 10px">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${isRegister eq 'Success'}">
                <div class="row" style="padding-top: 60px">
                    <div class="col"></div>
                    <div class="col">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <div class="col">
                                <strong>Congratulation!!</strong>You have successfully registered.
                            </div>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"
                                    style="padding-left: 600px; padding-top: 10px">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:if>
        <!-- Body -->
        <div style="padding-top: 60px">
            <h1 style="text-align: center;">Book Now</h1>
        </div>

        <div style="left: 0px;position: absolute; width: 30%;background-color: white;height: 1000px;position: fixed" >
            <div style="margin-left: 35px;padding: 15px; background-color: #FEBB02;width: 80%;border-radius: 5px">
                <form class="form-inline " style="text-align: center" action="DispatchServlet" method="POST">
                    <label><b>Hotel Name</b></label>
                    <input data-toggle="tooltip" title="Enter Hotel Name" data-placement="bottom" 
                           class="form-control mr-sm-2 " style="height: 38px;width: 100%"type="text" placeholder="Enter Hotel Name" 
                           name="searchName" value="${param.searchName}" >
                    <label><b>Hotel Area</b></label>
                    <select data-toggle="tooltip" title="Select Hotel Area" data-placement="bottom" 
                            name="searchArea" class="custom-select" style="width: 98%">
                        <option selected >Hotel Area</option>
                        <c:if test="${not empty areaList}">
                            <c:forEach var="dto" items="${areaList}">
                                <option value="${dto.areaName}" <c:if test="${param.searchArea eq dto.areaName}">
                                        selected
                                    </c:if>
                                    > ${dto.areaName} </option>
                            </c:forEach>
                        </c:if>
                    </select> 
                    <label><b>Checkin & Checkout Date</b></label>
                    <input type="text" name="searchDate" 
                           style="width: 98%; height: 38px; border-radius: 4px;"
                           data-toggle="tooltip"  data-placement="bottom" title="Choose Checkin & Checkout Date"
                           <c:if test="${not empty param.searchDate}"> placeholder="${param.searchDate}"</c:if>
                           <c:if test="${empty param.searchDate}"> placeholder="Choose Checkin & Checkout Date"</c:if>
                               value=""  />
                           <label><b>Room Amount</b></label>
                           <input data-toggle="tooltip" title="Enter Room Amount" data-placement="bottom" 
                                  class="form-control mr-sm-2 " style="height: 38px;width: 100%"type="text" placeholder="Enter Room Amount" 
                                  name="searchAmount" value="${param.searchAmount}" >
                    <input type="hidden" name="currentPage" value="LoadHotelServlet" />
                    <input class="btn btn-primary " style="width: 98%"type="submit" value="Search" name="btnAction" />  
                </form>
            </div>
        </div>
        <div style="right: 0px;position: absolute; width: 70%;background-color: white;height: 1000px">
            <c:if test="${not empty roomList}">
                <c:if test="${not empty hotelList}">
                    <c:forEach var="hotelDTO" items="${hotelList}" varStatus="counter">
                        <div style="margin-bottom: 10px;padding-bottom: 10px;width: 90%;
                             border-radius: 5px; border-style: solid;border-width: thin;border-color: lightgray">
                            <div class="row" >
                                <div class="col-4">
                                    <img class="" 
                                         data-toggle="tooltip" title="" data-placement="bottom"
                                         style="margin-left: 15px;margin-top: 15px; border-radius: 5px"
                                         src="Image/${hotelDTO.hotelImage}" width="250px" height="250px"/>
                                </div>
                                <div class="col-4" style="margin-top: 5px">
                                    <label><b style="font-size: 30px;">${hotelDTO.hotelName} Hotel</b></label><br>
                                    <p style="font-weight: 100">${hotelDTO.hotelAddress}</p>
                                    <c:forEach var="typeDTO" items="${typeList}" varStatus="counter">
                                        <c:set var="count" value="${0}"/>
                                        <c:forEach var="roomDTO" items="${roomList}" varStatus="counter">
                                            <c:if test="${roomDTO.roomType.typeName eq typeDTO.typeName && roomDTO.roomHotel.hotelName eq hotelDTO.hotelName}">
                                                <c:set var="count" value="${count+1}"/>
                                                <c:set var="typeName" value="${roomDTO.roomType.typeName}"/>
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${count gt 0}">
                                            <span class="badge badge-pill badge-info">&nbsp;${count}x&nbsp;</span>
                                            ${typeName} Room <br>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <div class="col-4">
                                    <c:if test="${not empty fbList}">
                                        <c:forEach var="fbDTO" items="${fbList}" varStatus="counter">
                                            <c:if test="${fbDTO.hotelID.hotelID eq hotelDTO.hotelID}">
                                                <div class="row" style="margin-top: 10px">
                                                    <div class="col-8">
                                                        <b><p style="margin: 0px;text-align: right">${fbDTO.fbRank}</p></b>
                                                        <p style="font-weight: 100;text-align: right" >${fbDTO.amountOfFb} feedbacks</p>
                                                    </div>
                                                    <div class="col-3" style="border-radius: 100px">
                                                        <h2><span class="badge badge-success" style="margin-top: 5px">${fbDTO.rating}</span></h2>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                        <input class="btn btn-primary " style="width: 92%;margin-top: 153px"
                                               onclick="document.getElementById('detail${hotelDTO.hotelID}').style.display = 'block';"
                                               type="submit"value="View Detail"/>
                                    </c:if>
                                    <c:if test="${ empty fbList}">
                                        <input class="btn btn-primary " style="width: 92%;margin-top: 228px"
                                               onclick="document.getElementById('detail${hotelDTO.hotelID}').style.display = 'block';"
                                               type="submit"value="View Detail"/>
                                    </c:if>


                                </div>
                            </div>
                        </div>
                    </c:forEach> 

                </c:if>
            </c:if>
            <c:if test="${empty roomList}">
                <p style="text-align: center; font-size: 150px">Can't found something!</p>
            </c:if>
        </div>







        <!-- FROM -->
        <div id="login" class="loginForm" >
            <form class="modal-content animate" action="DispatchServlet" method="POST" style="width: 500px; border-radius: 10px">
                <div class="container">
                    <label><b>Email</b></label>
                    <input type="text" placeholder="Enter Email" name="username" required>

                    <label><b>Password</b></label>
                    <input type="password" placeholder="Enter Password" name="password" required>
                    <input type="hidden" name="currentPage" value="LoadHotelServlet" />        
                    <button type="submit" name="btnAction" value="Login"style="border-radius: 5px">Login</button>
                    <button type="button" style="border-radius: 5px; margin-top: 15px"onclick="document.getElementById('login').style.display = 'none'" class="cancelbtn">Cancel</button>

                </div>
            </form>
        </div>
        <div id="register" class="loginForm" >
            <form class="modal-content animate" action="DispatchServlet" method="POST" 
                  style="width: 500px; border-radius: 10px">
                <div class="container">
                    <label><b>Email</b></label>
                    <input type="text" placeholder="Enter Email" 
                           onkeyup="checkEmail();" id="email"
                           name="username" required>
                    <div class="row">
                        <div class="col">
                            <label><b>Password</b></label>
                        </div>
                        <div class="col">
                            <label style="float: right"><b id="messageEmail"></b></label>
                        </div>
                    </div>
                    <input type="password" placeholder="Enter Password" 
                           onkeyup="checkPassword();" id="password"
                           name="password" required>

                    <label><b>Confirm Password</b></label>
                    <input type="password" placeholder="Enter Password" 
                           onkeyup="checkPassword();" id="confirmPassword"
                           name="confirmPassword" required>

                    <div class="row">
                        <div class="col">
                            <label><b>Full Name</b></label>
                        </div>
                        <div class="col">
                            <label style="float: right"><b id="messagePassword"></b></label>
                        </div>
                    </div>
                    <input type="text" placeholder="Enter Full Name" 
                           id="fullname" onkeyup="checkFullname()();"
                           name="fullname" required>

                    <div class="row">
                        <div class="col">
                            <label><b>Address</b></label>
                        </div>
                        <div class="col">
                            <label style="float: right"><b id="messageFullname"></b></label>
                        </div>
                    </div>
                    <input type="text" placeholder="Enter Address" 
                           id="address" onkeyup="checkAddress()();"
                           name="address" required>
                    <div class="row">
                        <div class="col">
                        </div>
                        <div class="col">
                            <label style="float: right"><b id="messageAddress"></b></label>
                        </div>
                    </div>

                    <input type="hidden" name="currentPage" value="LoadHotelServlet" />        
                    <button type="submit" name="btnAction" value="Register"style="border-radius: 5px">Register</button>
                    <button type="button" style="border-radius: 5px; margin-top: 15px"onclick="document.getElementById('register').style.display = 'none'" class="cancelbtn">Cancel</button>

                </div>
            </form>
        </div>
        <div id="detail1" class="loginForm"  >
            <c:if test="${not empty roomList}">
                <c:set var="hotelID" value="${1}"/>
                <c:forEach var="roomDTO" items="${roomList}" varStatus="counter">
                    <c:if test="${roomDTO.roomHotel.hotelID eq hotelID}">
                        <form class="modal-content animate" action="DispatchServlet" method="POST" 
                              style="width: 40%; margin-bottom: -50px;border-radius: 10px;">

                            <div style="padding-bottom: 10px;width:100%;
                                 border-radius: 5px; border-style: solid;border-width: thin;border-color: lightgray">
                                <div class="row" >
                                    <div class="col" >
                                        <img class="" 
                                             data-toggle="tooltip" title="" data-placement="bottom"
                                             style="margin-left: 15px;margin-top: 15px; border-radius: 5px"
                                             src="Image/${roomDTO.roomImage}" width="200px" height="200px"/>
                                    </div>
                                    <div class="col" style="margin-top: 5px;">
                                        <label><b style="font-size: 30px;">${roomDTO.roomType.typeName} Room</b></label><br>
                                        <i><p style="font-weight: 100">${roomDTO.roomPrice}$/day</p></i>
                                        <p style="font-weight: 300">Checkin & Checkout Date</p>
                                        <input type="text" name="daterange" 
                                               style="width: 90%; height: 38px; border-radius: 4px;margin-top: -10px"
                                               data-toggle="tooltip" title="Choose Checkin & Checkout Date" data-placement="bottom" 
                                               placeholder="Checkin & Checkout Date"
                                               required="true"
                                               <c:if test="${not empty pickHotelID}"> 
                                                   value="${pickDate}"
                                                   required="false" disabled="true"
                                               </c:if>
                                               <c:if test="${not empty param.searchDate}"> 
                                                   disabled="true" required="false" 
                                                   value="${param.searchDate}"
                                               </c:if>  />
                                        <input type="hidden" name="daterangeBackup" value="${pickDate}" />
                                        <input type="hidden" name="roomID" value="${roomDTO.roomID}" />
                                        <input type="hidden" name="searchName" value="${param.searchName}" />
                                        <input type="hidden" name="searchArea" value="${param.searchArea}" />
                                        <input type="hidden" name="searchDate" value="${param.searchDate}" />
                                        <input class="btn btn-primary " style="width: 90%;margin-top: 0px"
                                               type="submit" name="btnAction" value="Add To Wishlist"
                                               <c:if test="${isAdmin eq 'True'}">
                                                   disabled="true"
                                               </c:if>/>
                                    </div>
                                </div>
                            </div>    

                        </form>
                    </c:if>
                </c:forEach> 
            </c:if>     
        </div>     
        <div id="detail2" class="loginForm"  >
            <c:if test="${not empty roomList}">
                <c:set var="hotelID" value="${2}"/>
                <c:forEach var="roomDTO" items="${roomList}" varStatus="counter">
                    <c:if test="${roomDTO.roomHotel.hotelID eq hotelID}">
                        <form class="modal-content animate" action="DispatchServlet" method="POST" 
                              style="width: 40%; margin-bottom: -50px;border-radius: 10px;">

                            <div style="padding-bottom: 10px;width:100%;
                                 border-radius: 5px; border-style: solid;border-width: thin;border-color: lightgray">
                                <div class="row" >
                                    <div class="col" >
                                        <img class="" 
                                             data-toggle="tooltip" title="" data-placement="bottom"
                                             style="margin-left: 15px;margin-top: 15px; border-radius: 5px"
                                             src="Image/${roomDTO.roomImage}" width="200px" height="200px"/>
                                    </div>
                                    <div class="col" style="margin-top: 5px;">
                                        <label><b style="font-size: 30px;">${roomDTO.roomType.typeName} Room</b></label><br>
                                        <i><p style="font-weight: 100">${roomDTO.roomPrice}$/day</p></i>
                                        <p style="font-weight: 300">Checkin & Checkout Date</p>
                                        <input type="text" name="daterange" 
                                               style="width: 90%; height: 38px; border-radius: 4px;margin-top: -10px"
                                               data-toggle="tooltip" title="Choose Checkin & Checkout Date" data-placement="bottom" 
                                               placeholder="Checkin & Checkout Date"
                                               required="true" 
                                               <c:if test="${not empty pickHotelID}"> 
                                                   value="${pickDate}"
                                                   required="false" disabled="true"
                                               </c:if>
                                               <c:if test="${not empty param.searchDate}"> 
                                                   disabled="true" required="false" 
                                                   value="${param.searchDate}"
                                               </c:if>  />
                                        <input type="hidden" name="daterangeBackup" value="${pickDate}" />
                                        <input type="hidden" name="roomID" value="${roomDTO.roomID}" />
                                        <input type="hidden" name="searchName" value="${param.searchName}" />
                                        <input type="hidden" name="searchArea" value="${param.searchArea}" />
                                        <input type="hidden" name="searchDate" value="${param.searchDate}" />
                                        <input class="btn btn-primary " style="width: 90%;margin-top: 0px"
                                               type="submit" name="btnAction" value="Add To Wishlist"
                                               <c:if test="${isAdmin eq 'True'}">
                                                   disabled="true"
                                               </c:if>/>
                                    </div>
                                </div>
                            </div>    

                        </form>
                    </c:if>
                </c:forEach> 
            </c:if>     
        </div>
        <div id="detail3" class="loginForm"  >
            <c:if test="${not empty roomList}">
                <c:set var="hotelID" value="${3}"/>
                <c:forEach var="roomDTO" items="${roomList}" varStatus="counter">
                    <c:if test="${roomDTO.roomHotel.hotelID eq hotelID}">
                        <form class="modal-content animate" action="DispatchServlet" method="POST" 
                              style="width: 40%; margin-bottom: -50px;border-radius: 10px;">

                            <div style="padding-bottom: 10px;width:100%;
                                 border-radius: 5px; border-style: solid;border-width: thin;border-color: lightgray">
                                <div class="row" >
                                    <div class="col" >
                                        <img class="" 
                                             data-toggle="tooltip" title="" data-placement="bottom"
                                             style="margin-left: 15px;margin-top: 15px; border-radius: 5px"
                                             src="Image/${roomDTO.roomImage}" width="200px" height="200px"/>
                                    </div>
                                    <div class="col" style="margin-top: 5px;">
                                        <label><b style="font-size: 30px;">${roomDTO.roomType.typeName} Room</b></label><br>
                                        <i><p style="font-weight: 100">${roomDTO.roomPrice}$/day</p></i>
                                        <p style="font-weight: 300">Checkin & Checkout Date</p>
                                        <input type="text" name="daterange" 
                                               style="width: 90%; height: 38px; border-radius: 4px;margin-top: -10px"
                                               data-toggle="tooltip" title="Choose Checkin & Checkout Date" data-placement="bottom" 
                                               placeholder="Checkin & Checkout Date"
                                               required="true"
                                               <c:if test="${not empty pickHotelID}"> 
                                                   value="${pickDate}"
                                                   required="false" disabled="true"
                                               </c:if>
                                               <c:if test="${not empty param.searchDate}"> 
                                                   disabled="true" required="false" 
                                                   value="${param.searchDate}"
                                               </c:if>  />
                                        <input type="hidden" name="daterangeBackup" value="${pickDate}" />
                                        <input type="hidden" name="roomID" value="${roomDTO.roomID}" />
                                        <input type="hidden" name="searchName" value="${param.searchName}" />
                                        <input type="hidden" name="searchArea" value="${param.searchArea}" />
                                        <input type="hidden" name="searchDate" value="${param.searchDate}" />
                                        <input class="btn btn-primary " style="width: 90%;margin-top: 0px"
                                               type="submit" name="btnAction" value="Add To Wishlist"
                                               <c:if test="${isAdmin eq 'True'}">
                                                   disabled="true"
                                               </c:if>/>
                                    </div>
                                </div>
                            </div>    

                        </form>
                    </c:if>
                </c:forEach> 
            </c:if>    
        </div>
        <div id="detail4" class="loginForm"  >
            <c:if test="${not empty roomList}">
                <c:set var="hotelID" value="${4}"/>
                <c:forEach var="roomDTO" items="${roomList}" varStatus="counter">
                    <c:if test="${roomDTO.roomHotel.hotelID eq hotelID}">
                        <form class="modal-content animate" action="DispatchServlet" method="POST" 
                              style="width: 40%; margin-bottom: -50px;border-radius: 10px;">

                            <div style="padding-bottom: 10px;width:100%;
                                 border-radius: 5px; border-style: solid;border-width: thin;border-color: lightgray">
                                <div class="row" >
                                    <div class="col" >
                                        <img class="" 
                                             data-toggle="tooltip" title="" data-placement="bottom"
                                             style="margin-left: 15px;margin-top: 15px; border-radius: 5px"
                                             src="Image/${roomDTO.roomImage}" width="200px" height="200px"/>
                                    </div>
                                    <div class="col" style="margin-top: 5px;">
                                        <label><b style="font-size: 30px;">${roomDTO.roomType.typeName} Room</b></label><br>
                                        <i><p style="font-weight: 100">${roomDTO.roomPrice}$/day</p></i>
                                        <p style="font-weight: 300">Checkin & Checkout Date</p>
                                        <input type="text" name="daterange" 
                                               style="width: 90%; height: 38px; border-radius: 4px;margin-top: -10px"
                                               data-toggle="tooltip" title="Choose Checkin & Checkout Date" data-placement="bottom" 
                                               placeholder="Checkin & Checkout Date"
                                               required="true"
                                               <c:if test="${not empty pickHotelID}"> 
                                                   value="${pickDate}"
                                                   required="false" disabled="true"
                                               </c:if>
                                               <c:if test="${not empty param.searchDate}"> 
                                                   disabled="true" required="false" 
                                                   value="${param.searchDate}"
                                               </c:if>  />
                                        <input type="hidden" name="daterangeBackup" value="${pickDate}" />
                                        <input type="hidden" name="roomID" value="${roomDTO.roomID}" />
                                        <input type="hidden" name="searchName" value="${param.searchName}" />
                                        <input type="hidden" name="searchArea" value="${param.searchArea}" />
                                        <input type="hidden" name="searchDate" value="${param.searchDate}" />
                                        <input class="btn btn-primary " style="width: 90%;margin-top: 0px"
                                               type="submit" name="btnAction" value="Add To Wishlist"
                                               <c:if test="${isAdmin eq 'True'}">
                                                   disabled="true"
                                               </c:if>/>
                                    </div>
                                </div>
                            </div>    

                        </form>
                    </c:if>
                </c:forEach> 
            </c:if>    
        </div>
        <div id="detail5" class="loginForm"  >
            <c:if test="${not empty roomList}">
                <c:set var="hotelID" value="${5}"/>
                <c:forEach var="roomDTO" items="${roomList}" varStatus="counter">
                    <c:if test="${roomDTO.roomHotel.hotelID eq hotelID}">
                        <form class="modal-content animate" action="DispatchServlet" method="POST" 
                              style="width: 40%; margin-bottom: -50px;border-radius: 10px;">
                            <div style="padding-bottom: 10px;width:100%;
                                 border-radius: 5px; border-style: solid;border-width: thin;border-color: lightgray">
                                <div class="row" >
                                    <div class="col" >
                                        <img class="" 
                                             data-toggle="tooltip" title="" data-placement="bottom"
                                             style="margin-left: 15px;margin-top: 15px; border-radius: 5px"
                                             src="Image/${roomDTO.roomImage}" width="200px" height="200px"/>
                                    </div>
                                    <div class="col" style="margin-top: 5px;">
                                        <label><b style="font-size: 30px;">${roomDTO.roomType.typeName} Room</b></label><br>
                                        <i><p style="font-weight: 100">${roomDTO.roomPrice}$/day</p></i>
                                        <p style="font-weight: 300">Checkin & Checkout Date</p>
                                        <input type="text" name="daterange" 
                                               style="width: 90%; height: 38px; border-radius: 4px;margin-top: -10px"
                                               data-toggle="tooltip" title="Choose Checkin & Checkout Date" data-placement="bottom" 
                                               placeholder="Checkin & Checkout Date"
                                               required="true"
                                               <c:if test="${not empty pickHotelID}"> 
                                                   value="${pickDate}"
                                                   required="false" disabled="true"
                                               </c:if>
                                               <c:if test="${not empty param.searchDate}"> 
                                                   disabled="true" required="false" 
                                                   value="${param.searchDate}"
                                               </c:if>  />
                                        <input type="hidden" name="daterangeBackup" value="${pickDate}" />
                                        <input type="hidden" name="roomID" value="${roomDTO.roomID}" />
                                        <input type="hidden" name="searchName" value="${param.searchName}" />
                                        <input type="hidden" name="searchArea" value="${param.searchArea}" />
                                        <input type="hidden" name="searchDate" value="${param.searchDate}" />
                                        <input class="btn btn-primary " style="width: 90%;margin-top: 0px"
                                               type="submit" name="btnAction" value="Add To Wishlist"
                                               <c:if test="${isAdmin eq 'True'}">
                                                   disabled="true"
                                               </c:if>/>
                                    </div>
                                </div>
                            </div>    

                        </form>
                    </c:if>
                </c:forEach> 
            </c:if>    
        </div>
        <script>
            $(document).ready(function () {
                $('[data-toggle="tooltip"]').tooltip();

            });
            var login = document.getElementById('login');
            var register = document.getElementById('register');
            var detail1 = document.getElementById('detail1');
            var detail2 = document.getElementById('detail2');
            var detail3 = document.getElementById('detail3');
            var detail4 = document.getElementById('detail4');
            var detail5 = document.getElementById('detail5');

            window.onclick = function (event) {
                if (event.target === login) {
                    login.style.display = "none";
                }
                if (event.target === register) {
                    register.style.display = "none";
                }
                if (event.target === detail1) {
                    detail1.style.display = "none";
                }
                if (event.target === detail2) {
                    detail2.style.display = "none";
                }
                if (event.target === detail3) {
                    detail3.style.display = "none";
                }
                if (event.target === detail4) {
                    detail4.style.display = "none";
                }
                if (event.target === detail5) {
                    detail5.style.display = "none";
                }
            };
            var checkEmail = function () {
                var emailFormat = ".+\\@.+\\..+";
                if (document.getElementById('email').value.match(emailFormat)) {
                    document.getElementById('messageEmail').style.color = 'green';
                    document.getElementById('messageEmail').innerHTML = '✔';
                } else {
                    document.getElementById('messageEmail').style.color = 'red';
                    document.getElementById('messageEmail').innerHTML = 'Invalid Email Format';
                }
            };
            var checkPassword = function () {
                if (document.getElementById('password').value === document.getElementById('confirmPassword').value) {
                    document.getElementById('messagePassword').style.color = 'green';
                    document.getElementById('messagePassword').innerHTML = '✔';
                } else {
                    document.getElementById('messagePassword').style.color = 'red';
                    document.getElementById('messagePassword').innerHTML = 'Not matching';
                }
            };
            var checkFullname = function () {
                var format = "^[a-zA-Z0-9 ]{1,}$";
                if (document.getElementById('fullname').value.match(format)) {
                    document.getElementById('messageFullname').style.color = 'green';
                    document.getElementById('messageFullname').innerHTML = '✔';
                } else {
                    document.getElementById('messageFullname').style.color = 'red';
                    document.getElementById('messageFullname').innerHTML = 'Invalid Format';
                }

            };
            var checkAddress = function () {
                var format = "^[a-zA-Z0-9 ]{1,}$";
                if (document.getElementById('address').value.match(format)) {
                    document.getElementById('messageAddress').style.color = 'green';
                    document.getElementById('messageAddress').innerHTML = '✔';
                } else {
                    document.getElementById('messageAddress').style.color = 'red';
                    document.getElementById('messageAddress').innerHTML = 'Invalid Format';
                }
            };
            var today = new Date();
            var date = (today.getMonth() + 1) + '/' + today.getDate() + '/' + today.getFullYear();
            $(function () {
                $('input[name="searchDate"]').daterangepicker({
                    autoUpdateInput: false,
                    minDate: date,
                    locale: {
                        cancelLabel: 'Clear'
                    }
                });

                $('input[name="searchDate"]').on('apply.daterangepicker', function (ev, picker) {
                    $(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
                });

                $('input[name="searchDate"]').on('cancel.daterangepicker', function (ev, picker) {
                    $(this).val('');
                    $(this).attr("placeholder", "Choose Checkin & Checkout Date").placeholder();
                });

            });
            $(function () {
                $('input[name="daterange"]').daterangepicker({
                    autoUpdateInput: false,
                    minDate: date,
                    locale: {
                        cancelLabel: 'Clear'
                    }
                });

                $('input[name="daterange"]').on('apply.daterangepicker', function (ev, picker) {
                    $(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
                });

                $('input[name="daterange"]').on('cancel.daterangepicker', function (ev, picker) {
                    $(this).val('');
                    $(this).attr("placeholder", "Choose Checkin & Checkout Date").placeholder();
                });

            });
        </script>


    </body>
</html>
