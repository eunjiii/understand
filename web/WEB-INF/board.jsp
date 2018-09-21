<%--
  Created by IntelliJ IDEA.
  User: 5CLASS-184
  Date: 2018-09-18
  Time: 오후 6:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="includes/header.jsp"%>


<div id="page-wrapper">
    <h1 class="page-header">관리자 페이지</h1>
    <a href="userList"><button class="btn btn-primary btn-lg btn-block">회원관리</button></a><br>
    <a href="understand"><button class="btn btn-primary btn-lg btn-block">이해했니?</button></a><br>
    <a href="question"><button class="btn btn-primary btn-lg btn-block">질문내역</button></a><br>
    <a href="list"><button class="btn btn-primary btn-lg btn-block">질문게시판</button></a>
    </div>
</div>

<%@include file="includes/footer.jsp"%>