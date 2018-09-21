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
            <h1 class="page-header">학생들의 이해도 결과</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    결과
                </div>
                <div class="panel-body">
                    <h2>학생들의 이해도 결과</h2>
                    <table width="100%" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>이름</th>
                            <th>사용자 상세질문</th>
                            <th>이해도</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${list}" var="result">
                        <tr>
                        <td>${result.uname}</td>
                        <td>${result.anqtitle}</td>
                        <td>${result.anqty}%</td>

                        </tr>
                        </c:forEach>
                        </tbody>

                    </table>

                    <h2> 이해도 통계</h2>
                        <table width="100%" class="table table-striped table-bordered table-hover" >
                            <thead>
                            <tr>
                                <th>이해도</th>
                                <th>사람수</th>
                                <th>퍼센트</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="result" items="${resultList}" >
                                <tr class="odd gradeX">
                                    <td>${result.qct}</td>
                                    <td>${result.cnt}</td>
                                    <td>${result.total}%</td>
                                </tr>
                            </c:forEach>


                            </tbody>

                        </table>

                    <a href="understand"><button class="btn btn-primary">다음질문</button></a>
                    <a href="question"><button class="btn btn-primary">목록으로</button></a>
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