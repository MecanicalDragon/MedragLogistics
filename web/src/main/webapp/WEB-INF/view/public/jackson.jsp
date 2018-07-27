<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Medrag Logistics</title>
    <link href="/resources/vendor/images/favicon.ico" rel="shortcut icon">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <style>
        body {
            background: #000000
        }

        /*-- signin-card animation ---------------------------------------- */
        .signin-card {
            -webkit-animation: cardEnter 0.75s ease-in-out 0.5s;
            animation: cardEnter 0.75s ease-in-out 0.5s;
            -webkit-animation-fill-mode: both;
            animation-fill-mode: both;
            opacity: 0;
        }

        /* -- login paper styles ------------------------------ */
        .signin-card {
            max-width: 350px;
            border-radius: 2px;
            margin: 20px auto;
            padding: 20px;
            background-color: #eceff1;
        / / Blue Grey 50 box-shadow: 0 10 px 20 px rgba(0, 0, 0, .19),
        0 6 px 6 px rgba(0, 0, 0, .23);
        / / shadow depth 3
        }

        .signin-card .logo-image, h1, p {
            text-align: center;
        }

        /* -- font styles ------------------------------------- */
        .display1 {
            font-size: 28px;
            font-weight: 100;
            line-height: 1.2;
            color: #000000;
            text-transform: inherit;
            letter-spacing: inherit;
        }

        .subhead {
            font-size: 16px;
            font-weight: 300;
            line-height: 1.1;
            color: #757575;
            text-transform: inherit;
            letter-spacing: inherit;
        }

        /* card animation from Animate.css -------------------- */
        @-webkit-keyframes cardEnter {
            0%, 20%, 40%, 60%, 80%, 100% {
                -webkit-transition-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
                transition-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
            }
            0% {
                opacity: 0;
                -webkit-transform: scale3d(0.3, 0.3, 0.3);
                -ms-transform: scale3d(0.3, 0.3, 0.3);
                transform: scale3d(0.3, 0.3, 0.3);
            }
            20% {
                -webkit-transform: scale3d(1.1, 1.1, 1.1);
                -ms-transform: scale3d(1.1, 1.1, 1.1);
                transform: scale3d(1.1, 1.1, 1.1);
            }
            40% {
                -webkit-transform: scale3d(0.9, 0.9, 0.9);
                -ms-transform: scale3d(0.9, 0.9, 0.9);
                transform: scale3d(0.9, 0.9, 0.9);
            }
            60% {
                opacity: 1;
                -webkit-transform: scale3d(1.03, 1.03, 1.03);
                -ms-transform: scale3d(1.03, 1.03, 1.03);
                transform: scale3d(1.03, 1.03, 1.03);
            }
            80% {
                -webkit-transform: scale3d(0.97, 0.97, 0.97);
                -ms-transform: scale3d(0.97, 0.97, 0.97);
                transform: scale3d(0.97, 0.97, 0.97);
            }
            100% {
                opacity: 1;
                -webkit-transform: scale3d(1, 1, 1);
                -ms-transform: scale3d(1, 1, 1);
                transform: scale3d(1, 1, 1);
            }
        }

        @keyframes cardEnter {
            0%, 20%, 40%, 60%, 80%, 100% {
                -webkit-transition-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
                transition-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
            }
            0% {
                opacity: 0;
                -webkit-transform: scale3d(0.3, 0.3, 0.3);
                -ms-transform: scale3d(0.3, 0.3, 0.3);
                transform: scale3d(0.3, 0.3, 0.3);
            }
            20% {
                -webkit-transform: scale3d(1.1, 1.1, 1.1);
                -ms-transform: scale3d(1.1, 1.1, 1.1);
                transform: scale3d(1.1, 1.1, 1.1);
            }
            40% {
                -webkit-transform: scale3d(0.9, 0.9, 0.9);
                -ms-transform: scale3d(0.9, 0.9, 0.9);
                transform: scale3d(0.9, 0.9, 0.9);
            }
            60% {
                opacity: 1;
                -webkit-transform: scale3d(1.03, 1.03, 1.03);
                -ms-transform: scale3d(1.03, 1.03, 1.03);
                transform: scale3d(1.03, 1.03, 1.03);
            }
            80% {
                -webkit-transform: scale3d(0.97, 0.97, 0.97);
                -ms-transform: scale3d(0.97, 0.97, 0.97);
                transform: scale3d(0.97, 0.97, 0.97);
            }
            100% {
                opacity: 1;
                -webkit-transform: scale3d(1, 1, 1);
                -ms-transform: scale3d(1, 1, 1);
                transform: scale3d(1, 1, 1);
            }
        }

    </style>
</head>
<body>
<br>
<br>
<br>
<div class="container">
    <div id="login" class="signin-card">
        <div class="logo-image">
            <img src="/resources/vendor/images/favicon.png" alt="Logo" title="Logo"
                 width="150">
        </div>
        <h1 class="display1">Medrag Logistics</h1>
        <p class="subhead">Next Generation Trucking Company</p>
        <c:choose>
            <c:when test="${pageContext.request.userPrincipal.name != null}">
                <form method="post" action="${contextPath}/logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="text-primary">
                        You signed in under ${pageContext.request.userPrincipal.name} Personal Number
                    </div>
                    <p><a href="${contextPath}/title/identify">Back to work</a></p>
                    <div>
                        <button class="btn btn-danger">Logout</button>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <form class="form-signin" method="post" action="${contextPath}/tryLog">
                    <div id="form-login-username" class="form-group">
                        <input class="form-control" placeholder="Personal ID" name="username"
                               type="text" size="18" alt="login" id="username" autofocus>
                        <%--<span class="form-highlight"></span>--%>
                        <%--<span class="form-bar"></span>--%>
                    </div>
                    <br>
                    <div id="form-login-password" class="form-group">
                        <input id="password" class="form-control" name="password" placeholder="Password" type="password"
                               size="18" alt="password">
                        <%--<span class="form-highlight"></span>--%>
                        <%--<span class="form-bar"></span>--%>
                    </div>
                    <div class="row justify-content-center">
                        <div class="secondary-text text-danger">
                            <c:choose>
                                <c:when test="${error == null}">
                                    <br>
                                </c:when>
                                <c:otherwise>
                                    <span>${error}</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <br>
                    <button class="btn btn-block btn-info ripple-effect" type="submit" name="Submit" alt="sign in">Sign
                        in
                    </button>
                </form>
            </c:otherwise>
        </c:choose>


        <div>
        </div>
        </form>
    </div>
</div>


<!-- jQuery -->
<script src="/resources/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<script>
    $(document).ready(function ($) {
        $(".ripple-effect").click(function (e) {
            var rippler = $(this);

            // create .ink element if it doesn't exist
            if (rippler.find(".ink").length == 0) {
                rippler.append("<span class='ink'></span>");
            }

            var ink = rippler.find(".ink");

            // prevent quick double clicks
            ink.removeClass("animate");

            // set .ink diametr
            if (!ink.height() && !ink.width()) {
                var d = Math.max(rippler.outerWidth(), rippler.outerHeight());
                ink.css({height: d, width: d});
            }

            // get click coordinates
            var x = e.pageX - rippler.offset().left - ink.width() / 2;
            var y = e.pageY - rippler.offset().top - ink.height() / 2;

            // set .ink position and add class .animate
            ink.css({
                top: y + 'px',
                left: x + 'px'
            }).addClass("animate");
        })
    });
</script>
</body>
</html>
