<%-- 
    Document   : viewCart
    Created on : Oct 2, 2021, 11:12:45 PM
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
        <c:set var="isActive" value="${requestScope.ISACTIVE}"/>
        <c:set var="typeList" value="${sessionScope.TYPELIST}"/>
        <c:set var="areaList" value="${sessionScope.AREALIST}"/>
        <c:set var="hotelList" value="${requestScope.HOTELLIST}"/>
        <c:set var="roomList" value="${requestScope.ROOMLIST}"/>
        <c:set var="isRegister" value="${requestScope.REGISTER}"/>
        <c:set var="isLoginFail" value="${requestScope.ERRORLOGIN}"/>
        <c:set var="isSendCode" value="${requestScope.VERIFY}"/>
        <c:set var="pickDate" value="${sessionScope.DATE}"/>
        <c:set var="pickHotelDate" value="${sessionScope.HOTELNAME}"/>
        <c:set var="isErrOTP" value="${requestScope.ERROTP}"/>
        <c:set var="isErrBooking" value="${requestScope.ERRBOOKING}"/>
        <c:set var="isBookingSuccess" value="${requestScope.SUCCESS}"/>
        <c:set var="cartList" value="${sessionScope.CART}"/>
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
                            <a class="nav-link" href="DispatchServlet?btnAction=My Order">Booking History</a>
                        </li> 
                    </c:if>                
                </ul> 
            </div>
            <div class="form-inline">
                <c:if test="${empty isLogin}">
                    <a class="nav-link" href="#" style="color: lightgray"
                       onclick="document.getElementById('register').style.display='block'"
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
        <c:if test="${not empty isErrOTP}">
            <div class="row" style="padding-top: 60px">
                <div class="col"></div>
                    <div class="col">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <div class="col">
                            <strong>Warning!!</strong>${isErrOTP}.
                        </div>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"
                                style="padding-left: 600px; padding-top: 10px">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty isErrBooking}">
            <div class="row" style="padding-top: 60px">
                <div class="col"></div>
                    <div class="col">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <div class="col">
                            <strong>Warning!!</strong>${isErrBooking}.
                        </div>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"
                                style="padding-left: 600px; padding-top: 10px">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty isBookingSuccess}">
            <div class="row" style="padding-top: 60px">
                <div class="col"></div>
                    <div class="col">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <div class="col">
                            <strong>Congratulation!!</strong>${isBookingSuccess}.
                        </div>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"
                                style="padding-left: 600px; padding-top: 10px">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty isSendCode}">
            <c:if test="${isSendCode eq 'True'}">
                <div class="row" style="padding-top: 60px">
                <div class="col"></div>
                    <div class="col">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <div class="col">
                            <strong>Success!!</strong>Verify Code send successfull
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
        <h1 style="text-align: center; padding-top: 60px">Cart Detail</h1>
        <c:if test="${not isAdmin eq 'True'}">
            <c:if test="${not empty cartList}">
                <c:set var="item" value="${cartList.items}"/>
                <div id="cart" class="cartForm" >
                    <div class="modal-content animate" style="width: 70%; height: auto;border-radius: 10px; padding-top: 15px">
                        <b style="text-align: center;font-size: 25px">${pickHotelDate} Hotel</b>
                        <div class="row">
                            <div class ="col" style="margin-left: 60px"><b>Room Type</b></div>
                            <div class ="col" ><b>Unit Price</b></div>
                            <div class ="col" ><b>Total</b></div>
                            <div class ="col" ><b></b></div>
                        </div>  
                        <c:set var="totalCash" value="0"/>
                        <c:forEach var="dto" items="${item}" varStatus="counter">
                            <form action="DispatchServlet" method="POST">
                                <div class="row">
                                    <div class="col" style="margin-left: 60px"> 
                                        <input data-toggle="tooltip" title="Room Type" data-placement="bottom" 
                                               type="text" placeholder="Room Type" name="roomType" 
                                               style="border-radius: 5px;height: 35px; width: 150px" 
                                               value="${dto.value.room.roomType.typeName} Room" disabled="disable">
                                    </div>
                                    <div class ="col" >
                                        <input data-toggle="tooltip" title="Unit Price" data-placement="bottom" 
                                               type="text" placeholder="Unit Price" name="roomPrice" 
                                               style="border-radius: 5px;height: 35px; width: 150px;text-align: right" 
                                               value="${dto.value.room.roomPrice}$" disabled="disable">
                                    </div>
                                    <div class ="col">
                                        <input data-toggle="tooltip" title="Total Price" data-placement="bottom" 
                                               type="text" placeholder="Unit Price" name="roomPrice" id ="totalPrice"
                                               style="border-radius: 5px;height: 35px; width: 150px;margin-left: -10px;text-align: right" 
                                               value="${dto.value.amountDate * dto.value.room.roomPrice}$" disabled="disable">
                                        <c:set var="totalCash" value="${totalCash+(dto.value.amountDate * dto.value.room.roomPrice)}"/>
                                    </div>
                                    <input type="hidden" name="bookingID" value="${dto.key}" />
                                    <div class="col" style="padding-top: 8px; padding-right: 20px;padding-left: 0px">
                                        <input type="submit" class="btn btn-danger" 
                                               onclick="return confirm('Do you want to remove form cart?')"
                                               value="Remove" name="btnAction" 
                                               style="height: 35px; width: 90px"/>
                                    </div>
                                </div>
                            </form>
                        </c:forEach>
                        <form action="DispatchServlet" method="POST">
                            <div class="row">
                                <div class="col">
                                    <b style="padding-left: 200px">Checkin & Checkout Date</b>
                                    <input type="text" name="daterange" 
                                            style="width: 270px; height: 35px; border-radius: 4px;padding-right: 60px"
                                            data-toggle="tooltip" title="Choose Checkin & Checkout Date" data-placement="bottom" 
                                            placeholder="Checkin & Checkout Date"
                                            value="${pickDate}" required="true" />
                                    <input type="submit" class="btn btn-primary" value="Update" name="btnAction" 
                                           style="height: 35px;width: 90px;margin-top: -5px;margin-left: 67px"/>
                                </div>
                            </div>
                        </form>
                        
                        <div class="row" >
                            <b style="padding-left: 535px">Total Cash: ${totalCash}$</b>
                        </div>
                        <div class="row">
                            <button type="button" class="btn btn-success"
                                    style="height: 35px; width: 120px; margin-left: 42.7%;margin-bottom: 15px;margin-top: 25px"
                                    style="background-color: #d9534f; width: 100px;"
                                    onclick="document.getElementById('checkOut').style.display='block';document.getElementById('cart').style.display='none'"
                                    type="submit">Booking</button>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${empty cartList}">
                <p style="text-align: center; font-size: 180px">You cart is empty!</p>
            </c:if>
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
                    <input type="hidden" name="currentPage" value="viewCart.jsp" />        
                    <button type="submit" name="btnAction" value="Login"style="border-radius: 5px">Login</button>
                    <button type="button" style="border-radius: 5px; margin-top: 15px"onclick="document.getElementById('login').style.display='none'" class="cancelbtn">Cancel</button>
                                  
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
                    
                    <input type="hidden" name="currentPage" value="viewCart.jsp" />        
                    <button type="submit" name="btnAction" value="Register"style="border-radius: 5px">Register</button>
                    <button type="button" style="border-radius: 5px; margin-top: 15px"onclick="document.getElementById('register').style.display='none'" class="cancelbtn">Cancel</button>
                                
                </div>
            </form>
        </div>    
        <div id="checkOut" class="checkOutForm" style="display: none">
            <c:if test="${empty isLogin}">
                <div class="modal-content animate" action="DispatchServlet" method="POST" style="width: 500px; border-radius: 10px">
                    <p style="text-align: center; font-size: 100px">You must login first!</p>
                    <div class="row">
                        <div class="col"></div>
                        <div class="col">
                            <button type="button" style="border-radius: 5px; margin-top: 15px; width: 250px; margin-bottom: 25px"
                                    onclick="document.getElementById('checkOut').style.display='none';document.getElementById('cart').style.display='block'" 
                                    class="cancelbtn"
                                    >Cancel</button>
                            
                        </div>
                        <div class="col"></div>
                    </div>
                </div>
            </c:if>
            <c:if test="${not empty isLogin}">
                <form class="modal-content animate" action="DispatchServlet" method="POST" style="width: 500px; border-radius: 10px">
                    <div class="container">
                        <label><b>Full Name</b></label>
                        <input type="text" placeholder="Your name" name="fullname" 
                               style="border-radius: 15px"
                               value="${welcomeName}" disabled="disable">

                        <label><b>Address</b></label>
                        <input type="text" placeholder="Enter your address" name="address" 
                               style="border-radius: 15px" pattern="[a-zA-Z0-9 ]{1,255}"
                               data-toggle="tooltip" title="No special character & maximum 255 charactes" data-placement="bottom"
                               value="${param.address}" required>    
                        <div class="row">
                            <div class="col">
                                <label><b>Phone number</b></label>
                                <input type="text" placeholder="Enter your phone number" name="phone" 
                                       style="border-radius: 15px" pattern="[0-9]{0,10}"
                                       data-toggle="tooltip" title="Required 10 number & accept number only"
                                       value="${param.phone}" required>
                            </div>
                            <div class="col">
                                <label><b>Payment</b></label>
                                <select data-toggle="tooltip" title="Select payment methods" data-placement="bottom" 
                                        style="margin-top: 7px; height: 50px; border-radius: 15px"
                                        name="payment" class="custom-select">
                                    <option selected>Cash</option>
                                    <option <c:if test="${param.payment eq 'Paypal'}">
                                                selected
                                            </c:if>
                                        >Paypal</option> 
                                </select>
                            </div>
                        </div>
                        <label><b>Discount Code</b><i>(If any)</i></label>
                        <input type="text" placeholder="Enter discount code" name="discount" 
                               style="border-radius: 15px" pattern="[a-zA-Z0-9]{1,255}"
                               data-toggle="tooltip" title="No special character & maximum 255 charactes" data-placement="bottom"
                               value="${param.discount}" >
                        <div class="row">
                            <div class="col">
                                <button type="button" style="border-radius: 5px;"
                                        onclick="document.getElementById('checkOut').style.display='none';document.getElementById('cart').style.display='block'" 
                                        class="cancelbtn"
                                        >Cancel</button>
                            </div>
                            <div class="col">
                                <input type="hidden" name="fullname" value="${welcomeName}" />
                                <input type="hidden" name="totalCash" value="${totalCash}" />
                                <input type="hidden" name="email" value="${userEmail}" />
                                <input type="submit" value="Check out" name="btnAction"
                                       style="border-radius: 5px; width: 100%;height: 52px"
                                       class="btn btn-primary"/>
                            </div>
                        </div>
                    </div>
                </form>
            </c:if>
        </div>                
        <div id="verify" class="loginForm"  <c:if test="${not empty isSendCode}">
                                                <c:if test="${isSendCode eq 'True'}">
                                                    style="display: block"
                                                </c:if>
                                            </c:if>
                                            <c:if test="${empty isSendCode}">
                                                    style="display: none"
                                            </c:if>
             >
            <form class="modal-content animate" action="DispatchServlet" method="POST" style="width: 500px; border-radius: 10px">
                <div class="container">
                    <label><b>Verify</b></label> 
                    <p style="font-weight: 200" id="messageVerify">Verification code has been sent to your email</p>
                    <div style="border-radius: 100px;border:1px solid;width: 36px;height: 36px;
                                padding-top:5px;padding-left: 1px;
                                margin-left: 46%" id="countdownBox">
                        <b id="countdown" ></b>
                    </div>
                    <input type="text" id="inputVerify" placeholder="Enter Verify Code" name="verifyCode" value="" required>
                    <input type="hidden" name="currentPage" value="viewCart.jsp" />        
                    <input type="hidden" name="address" value="${param.address}" />          
                    <input type="hidden" name="fullname" value="${param.fullname}" />          
                    <input type="hidden" name="phone" value="${param.phone}" />          
                    <input type="hidden" name="discount" value="${param.discount}" />          
                    <input type="hidden" name="payment" value="${param.payment}" />   
                    <input type="hidden" name="time" value="" id="time" />   
                    <input type="hidden" name="totalCash" value="${totalCash}" />
                    <button type="submit" name="btnAction" value="Submit"style="border-radius: 5px">Submit</button>
                    <button type="button" style="border-radius: 5px; margin-top: 15px" class="cancelbtn"
                            onclick="document.getElementById('verify').style.display='none'; 
                                     document.getElementById('checkOut').style.display='none';
                                     document.getElementById('cart').style.display='block';"
                            >Cancel</button>
                                  
                </div>
            </form>
        </div>                
                        
         <script>
            $(document).ready(function(){
                $('[data-toggle="tooltip"]').tooltip();
                
            });
            var login = document.getElementById('login');
            var register = document.getElementById('register');
            var verify = document.getElementById('verify');
            var cart = document.getElementById('cart');
            window.onclick = function(event) {
                if (event.target === login) {
                    login.style.display = "none";
                }
                if (event.target === register) {
                    register.style.display = "none";
                }
                if (event.target === verify) {
                    verify.style.display = "none";
                    cart.style.display = "block";
                }
            };
            
            var checkEmail = function() {
                var emailFormat = ".+\\@.+\\..+";
                if(document.getElementById('email').value.match(emailFormat)){
                    document.getElementById('messageEmail').style.color = 'green';
                    document.getElementById('messageEmail').innerHTML = '✔';
                } else {
                    document.getElementById('messageEmail').style.color = 'red';
                    document.getElementById('messageEmail').innerHTML = 'Invalid Email Format';
                }
            };
            var checkPassword = function() {
                if (document.getElementById('password').value === document.getElementById('confirmPassword').value) {
                    document.getElementById('messagePassword').style.color = 'green';
                    document.getElementById('messagePassword').innerHTML = '✔';
                } else {
                    document.getElementById('messagePassword').style.color = 'red';
                    document.getElementById('messagePassword').innerHTML = 'Not matching';
                }
            };
            var checkFullname = function(){
                var format = "^[a-zA-Z0-9 ]{1,}$";
                if(document.getElementById('fullname').value.match(format)){
                    document.getElementById('messageFullname').style.color = 'green';
                    document.getElementById('messageFullname').innerHTML = '✔';
                } else {
                    document.getElementById('messageFullname').style.color = 'red';
                    document.getElementById('messageFullname').innerHTML = 'Invalid Format';
                }
                
            };
            var checkAddress = function(){
                var format = "^[a-zA-Z0-9 ]{1,}$";
                if(document.getElementById('address').value.match(format)){
                    document.getElementById('messageAddress').style.color = 'green';
                    document.getElementById('messageAddress').innerHTML = '✔';
                } else {
                    document.getElementById('messageAddress').style.color = 'red';
                    document.getElementById('messageAddress').innerHTML = 'Invalid Format';
                }
            };
            var today = new Date();
            var date = (today.getMonth()+1)+'/'+today.getDate()+'/'+today.getFullYear();
            $(function() {
                $('input[name="daterange"]').daterangepicker({
                    autoUpdateInput: false,
                    minDate: date,
                    locale: {
                        cancelLabel: 'Clear'
                    }
                });

                $('input[name="daterange"]').on('apply.daterangepicker', function(ev, picker) {
                    $(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
                });

                $('input[name="daterange"]').on('cancel.daterangepicker', function(ev, picker) {
                    $(this).val('');
                    $(this).attr("placeholder", "Choose Checkin & Checkout Date").placeholder();
                });

            });
            const startingMinus = 2;
            let time = startingMinus * 60;
            
            setInterval(timerCountdown,1000);
            
            function timerCountdown(){
                const minutes = Math.floor(time/60);
                let seconds = time%60;
                seconds = seconds < 10 ? '0'+seconds : seconds;
                if(minutes < 0){
                    document.getElementById('countdown').innerHTML = '0:00';
                    document.getElementById('time').value = '0:00';
                    document.getElementById('messageVerify').innerHTML = 'Time up!!! Verify code has been expired';
                    document.getElementById('messageVerify').style.color = 'red';
                    document.getElementById('inputVerify').disabled = 'True';
                    document.getElementById('inputVerify').required = 'False';
                    document.getElementById('countdownBox').style.display = "none";
                }
                else{
                    document.getElementById('countdown').innerHTML = minutes+':'+seconds;
                    document.getElementById('time').value = minutes+':'+seconds;
                }
                time--;
            };
        </script>
    </body>
</html>
