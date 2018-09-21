<%--
  Created by IntelliJ IDEA.
  User: 5CLASS-184
  Date: 2018-09-18
  Time: 오후 6:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="includes/header.jsp" %>
<div id="page-wrapper">

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">회원 정보</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>회원번호</th>
                            <th>아이디</th>
                            <th>이름</th>
                            <th>가입일</th>
                            <th>아이디사용여부</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${userList}" var="user">
                            <tr>
                                <td>${user.uno}</td>
                                <td>${user.userid}</td>
                                <td><a href="userManager?uno=${user.uno}"> ${user.uname}</a></td>
                                <td>${user.regdate}</td>
                                <td>${user.userusing}</td>
                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>
                    <a href="board"><button class="btn btn-primary">첫페이지</button></a>
                    <%--<form action="/movie/write" method="get">--%>
                        <%--<input type="hidden" name="page" value="${pageMaker.pageDTO.page}">--%>
                        <%--<input type="hidden" name="size" value="${pageMaker.pageDTO.size}">--%>

                        <%--<button class="btn btn-primary btn-lg btn-block">등록</button>--%>
                    <%--</form>--%>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->

    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->

<%@include file="includes/footer.jsp" %>