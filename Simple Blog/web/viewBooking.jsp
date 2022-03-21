<%-- 
    Document   : viewBooking
    Created on : Oct 4, 2021, 9:05:19 PM
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
        <c:set var="isRegister" value="${requestScope.REGISTER}"/>
        <c:set var="isLoginFail" value="${requestScope.ERRORLOGIN}"/>
        <c:set var="bookingList" value="${requestScope.BOOKING}"/>
        <c:set var="detailList" value="${requestScope.BOOKINGDETAIL}"/>
        <c:set var="cancel" value="${requestScope.CANCEL}"/>
        <c:set var="rating" value="${requestScope.RATING}"/>
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
                            <a class="nav-link" href="DispatchServlet?btnAction=Home">Home</a>
                        </li> 
                        <li class="nav-item">
                            <a class="nav-link" href="DispatchServlet?btnAction=#">Admin role</a>
                        </li> 
                    </c:if>  
                    <c:if test="${not isAdmin eq 'True'}">
                        <li class="nav-item">
                            <a class="nav-link" href="DispatchServlet?btnAction=Home">Home</a>
                        </li> 
                    </c:if>
                    <c:if test="${isAdmin eq 'False'}">
                        <li class="nav-item">
                            <a class="nav-link" href="DispatchServlet?btnAction=View Cart">Booking Cart</a>
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
        <c:if test="${not empty cancel}">
            <c:if test="${cancel eq 'true'}">
                <div class="row" style="padding-top: 60px">
                    <div class="col"></div>
                    <div class="col">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <div class="col">
                                <strong>Congratulation!!</strong>Cancel booking successful.
                            </div>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"
                                    style="padding-left: 600px; padding-top: 10px">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${cancel eq 'false'}">
                <div class="row" style="padding-top: 60px">
                    <div class="col"></div>
                    <div class="col">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            <div class="col">
                                <strong>Warning!!</strong>Cancel booking fail.
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
        <c:if test="${not empty rating}">
            <c:if test="${rating eq 'true'}">
                <div class="row" style="padding-top: 60px">
                    <div class="col"></div>
                    <div class="col">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <div class="col">
                                <strong>Congratulation!!</strong>Rating booking successful.
                            </div>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"
                                    style="padding-left: 600px; padding-top: 10px">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${rating eq 'false'}">
                <div class="row" style="padding-top: 60px">
                    <div class="col"></div>
                    <div class="col">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            <div class="col">
                                <strong>Warning!!</strong>Rating booking fail.
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
        <c:if test="${not isAdmin eq 'True'}">
            <div style="left: 0px;position: absolute; width: 30%;background-color: white;height: 1000px;position: fixed" >
                <div style="margin-left: 35px;padding: 15px; background-color: #FEBB02;width: 80%;border-radius: 5px">
                    <form class="form-inline " style="text-align: center" action="DispatchServlet" method="POST">
                        <label><b>Booking ID</b></label>
                        <input data-toggle="tooltip" title="Enter Booking ID" data-placement="bottom" 
                               class="form-control mr-sm-2 " style="height: 38px;width: 100%"type="text" placeholder="Enter Booking ID" 
                               name="searchID" value="${param.searchID}" >
                        <label><b>Booking Date</b></label>
<!--                        <input type="text" name="searchDate" 
                               style="width: 98%; height: 38px; border-radius: 4px;"
                               data-toggle="tooltip"  data-placement="bottom" title="Choose Booking Date"
                               <c:if test="${not empty param.searchDate}"> placeholder="${param.searchDate}"</c:if>
                               <c:if test="${empty param.searchDate}"> placeholder="Choose Booking Date"</c:if>
                               value="${param.searchDate}"  />-->
                               <input type="date" name="searchDateHistory" 
                                      style="width: 98%; height: 38px; border-radius: 4px;"
                               data-toggle="tooltip"  data-placement="bottom" title="Choose Booking Date"
                               value="${param.searchDateHistory}"/>
                        <input type="hidden" name="currentPage" value="BookingHistoryServlet" />
                        <button class="btn btn-primary " style="width: 98%"type="submit" value="Search History" name="btnAction">Search</button>  
                    </form>
                </div>
            </div>
            <div style="right: 0px;position: absolute; width: 70%;background-color: white;height: 1000px">
                <c:if test="${not empty bookingList or not empty detailList}">
                    <c:forEach var="bookingDTO" items="${bookingList}" varStatus="counter">
                        <div style="margin-bottom: 10px;padding-bottom: 10px;width: 90%;
                             border-radius: 5px; border-style: solid;border-width: thin;border-color: lightgray">
                            <div class="row" >
                                <div class="col-3" >
                                    <c:if test="${bookingDTO.status eq 'true'}">
                                        <c:set var="statusImage" value="done.png"/>
                                    </c:if>
                                    <c:if test="${bookingDTO.status eq 'false'}">
                                        <c:set var="statusImage" value="canceled.png"/>
                                    </c:if>
                                    <img data-toggle="tooltip" title="" data-placement="bottom" class="img"
                                         style="margin-left: 15px; border-radius: 5px;margin-top: 15px"
                                         src="Image/${statusImage}" width="200px" height="200px"/>
                                </div>
                                <div class="col-4" style="margin-top: 5px">
                                    <label><b style="font-size: 30px;">Booking ID: ${bookingDTO.bookingID}</b></label><br>
                                    <p style="font-weight: 500">Booking Date: ${bookingDTO.bookingDate}</p>
                                    <c:forEach var="detailDTO" items="${detailList}" varStatus="counter">
                                        <c:if test="${bookingDTO.bookingID eq detailDTO.bookingID}">
                                            <p style="font-weight: 100">◉ ${detailDTO.room.roomType.typeName} room - ${detailDTO.room.roomHotel.hotelName} Hotel</p>
                                            <i><p style="font-weight: 100;margin-top: -20px;text-align: right">${detailDTO.checkinDate} -> ${detailDTO.checkoutDate}</p></i>
                                            <c:set var="hotelID" value="${detailDTO.room.roomHotel.hotelID}"/>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <div class="col-5">
                                    <div class="row" style="margin-top: 10px;margin-right: 10px">
                                        <label style="text-align: right;"><b >Total: ${bookingDTO.totalCostAfterDiscount}$</b></label>
                                    </div>
                                    <div class="row" style="margin-top: 10px;margin-right: 10px">
                                        <label style="text-align: right;">Pre-cost: ${bookingDTO.totalCostBeforeDiscount}$</label>
                                    </div>
                                    <div class="row" style="margin-top: 10px;margin-right: 10px">
                                        <label style="text-align: right;">Discount: ${bookingDTO.discount.discountCost}$</label>
                                    </div>
                                    <form action="DispatchServlet" style=";margin-left: 45px;margin-top: -35px">
                                        <div style="width: 92%">
                                            <span class="star-rating" style="margin-top: 40px;float: right">
                                                <input type="radio" name="rating" value="1" 
                                                       <c:if test="${bookingDTO.rating > 0}">
                                                           disabled="true"
                                                           <c:if test="${bookingDTO.rating eq '1'}">
                                                               checked="true"
                                                           </c:if>
                                                       </c:if>
                                                       ><i></i>
                                                <input type="radio" name="rating" value="2"
                                                       <c:if test="${bookingDTO.rating > 0}">
                                                           disabled="true"
                                                           <c:if test="${bookingDTO.rating eq '2'}">
                                                               checked="true"
                                                           </c:if>
                                                       </c:if>
                                                       ><i></i>
                                                <input type="radio" name="rating" value="3"
                                                       <c:if test="${bookingDTO.rating > 0}">
                                                           disabled="true"
                                                           <c:if test="${bookingDTO.rating eq '3'}">
                                                               checked="true"
                                                           </c:if>
                                                       </c:if>
                                                       ><i></i>
                                                <input type="radio" name="rating" value="4"
                                                       <c:if test="${bookingDTO.rating > 0}">
                                                           disabled="true"
                                                           <c:if test="${bookingDTO.rating eq '4'}">
                                                               checked="true"
                                                           </c:if>
                                                       </c:if>
                                                       ><i></i>
                                                <input type="radio" name="rating" value="5"
                                                       <c:if test="${bookingDTO.rating > 0}">
                                                           disabled="true"
                                                           <c:if test="${bookingDTO.rating eq '5'}">
                                                               checked="true"
                                                           </c:if>
                                                       </c:if>
                                                       ><i></i>
                                            </span>
                                        </div>
                                        <input type="hidden" name="bookingID" value="${bookingDTO.bookingID}" />
                                        <input type="hidden" name="hotelID" value="${hotelID}" />
                                        <div class="row" style="width: 100%; padding-top: 15px">
                                            <input class="btn btn-primary " style="width: 130px;"
                                                   type="submit" name="btnAction" value="Rating"
                                                   <c:if test="${bookingDTO.rating > 0}">
                                                       disabled="true"
                                                   </c:if>
                                                   <c:if test="${bookingDTO.status eq 'false'}">
                                                       disabled="true"
                                                   </c:if>/>
                                            <input class="btn btn-danger " style="width: 130px;margin-left: 25px"
                                                   onclick="return confirm('Do you want to cancel?')"
                                                   type="submit" name="btnAction" value="Cancel Booking" 
                                                   <c:if test="${bookingDTO.status eq 'false'}">
                                                       disabled="true"
                                                   </c:if>
                                                   <c:if test="${not empty bookingDTO.rating}">
                                                       disabled="true"
                                                   </c:if>/>
                                        </div>

                                    </form>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty bookingList && empty detailList}">
                    <p style="text-align: center; font-size: 150px">Can't found something!</p>
                </c:if>
            </div>            
        </c:if>                
        <c:if test="${isAdmin eq 'True'}">
            <p style="text-align: center; font-size: 150px; padding-top:100px">You don't have permission</p>  
        </c:if>                 









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

        <script>
            $(document).ready(function () {
                $('[data-toggle="tooltip"]').tooltip();

            });
            var login = document.getElementById('login');
            var register = document.getElementById('register');

            window.onclick = function (event) {
                if (event.target === login) {
                    login.style.display = "none";
                }
                if (event.target === register) {
                    register.style.display = "none";
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


            $(function () {
                $('input[name="searchDate"]').daterangepicker({
                    singleDatePicker: true,
                    autoUpdateInput: false,
                    locale: {
                        format: 'DD/MM/YYYY',
                        cancelLabel: 'Clear'
                    }
                });
                $('input[name="searchDate"]').on('cancel.daterangepicker', function (ev, picker) {
                    $(this).val('');
                    $(this).attr("placeholder", "Choose Booking Date").placeholder();
                });
            });
        </script>
        <style>
            .star-rating {
                margin: 25px 0 0px;
                font-size: 0;
                white-space: nowrap;
                display: inline-block;
                width: 175px;
                height: 35px;
                overflow: hidden;
                position: relative;
                background: url('data:image/svg+xml;base64,PHN2ZyB2ZXJzaW9uPSIxLjEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4IiB3aWR0aD0iMjBweCIgaGVpZ2h0PSIyMHB4IiB2aWV3Qm94PSIwIDAgMjAgMjAiIGVuYWJsZS1iYWNrZ3JvdW5kPSJuZXcgMCAwIDIwIDIwIiB4bWw6c3BhY2U9InByZXNlcnZlIj48cG9seWdvbiBmaWxsPSIjREREREREIiBwb2ludHM9IjEwLDAgMTMuMDksNi41ODMgMjAsNy42MzkgMTUsMTIuNzY0IDE2LjE4LDIwIDEwLDE2LjU4MyAzLjgyLDIwIDUsMTIuNzY0IDAsNy42MzkgNi45MSw2LjU4MyAiLz48L3N2Zz4=');
                background-size: contain;
            }
            .star-rating i {
                opacity: 0;
                position: absolute;
                left: 0;
                top: 0;
                height: 100%;
                width: 20%;
                z-index: 1;
                background: url('data:image/svg+xml;base64,PHN2ZyB2ZXJzaW9uPSIxLjEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4IiB3aWR0aD0iMjBweCIgaGVpZ2h0PSIyMHB4IiB2aWV3Qm94PSIwIDAgMjAgMjAiIGVuYWJsZS1iYWNrZ3JvdW5kPSJuZXcgMCAwIDIwIDIwIiB4bWw6c3BhY2U9InByZXNlcnZlIj48cG9seWdvbiBmaWxsPSIjRkZERjg4IiBwb2ludHM9IjEwLDAgMTMuMDksNi41ODMgMjAsNy42MzkgMTUsMTIuNzY0IDE2LjE4LDIwIDEwLDE2LjU4MyAzLjgyLDIwIDUsMTIuNzY0IDAsNy42MzkgNi45MSw2LjU4MyAiLz48L3N2Zz4=');
                background-size: contain;
            }
            .star-rating input {
                -moz-appearance: none;
                -webkit-appearance: none;
                opacity: 0;
                display: inline-block;
                width: 20%;
                height: 100%;
                margin: 0;
                padding: 0;
                z-index: 2;
                position: relative;
            }
            .star-rating input:hover + i,
            .star-rating input:checked + i {
                opacity: 1;
            }
            .star-rating i ~ i {
                width: 40%;
            }
            .star-rating i ~ i ~ i {
                width: 60%;
            }
            .star-rating i ~ i ~ i ~ i {
                width: 80%;
            }
            .star-rating i ~ i ~ i ~ i ~ i {
                width: 100%;
            }
        </style>
    </body>
</html>
