<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header class="page-header">
    <nav class="navbar navbar-expand-lg justify-content-between">
        <a href="/" class="navbar-brand main-logo main-logo-smaller">
            Zaplanuj <span>Jedzonko</span>
        </a>
        <div class="d-flex justify-content-around">
            <h4 class="text-light mr-3">
                <c:choose>
                    <c:when test="${sessionScope.adminName != null}">
                        <%= session.getAttribute("adminName") %>
                    </c:when>
                    <c:otherwise>
                        Imie
                    </c:otherwise>
                </c:choose>
            </h4>
            <div class="circle-div text-center"><i class="fas fa-user icon-user"></i></div>
            <a href="Logout" class="btn btn-success rounded-0 text-light-center m-1">Wyloguj</a>
        </div>
    </nav>
</header>
