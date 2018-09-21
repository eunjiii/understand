<%--
  Created by IntelliJ IDEA.
  User: hb2005
  Date: 2018-09-19
  Time: 오후 5:16
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="includes/header.jsp"%>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">회원 정보</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <style>
        .form {
            display: inline;
        }
    </style>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    정보를 확인하세요
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-6">
                            <form action="info" method="post" class="form" >
                                <%--<input hidden name="qno" value="${qus.qno}">--%>
                                <div class="form-group">
                                    <label>회원번호</label>
                                    <input class="form-control" type="text" name="uno" value="${info.uno}" disabled>
                                </div>
                                <div class="form-group">
                                    <label>아이디</label>
                                    <input class="form-control" type="text" name="userid" value="${info.userid}" disabled>
                                </div>
                                <div class="form-group">
                                    <label>이름</label>
                                    <input class="form-control" type="text" name="uname" value="${info.uname}" disabled>
                                </div>
                                <div class="form-group">
                                    <label>가입일</label>
                                    <input class="form-control" type="text" name="regdate" value="${info.regdate}" disabled>
                                </div>
                                <%--<a href="read?page=${curPage}&mno=${movie.mno}"> ${movie.rtitle}</a>--%>
                                <%--<a href="/remove" methods="post"><input type="button" class="btn btn-default" value="삭제"></a>--%>
                                <%--<a href="/list"><input type="button" class="btn btn-default" value="목록으로"></a>--%>
                            </form>
                            <form action="userRemove" method="post" class="form" >
                                <input hidden name="uno" value="${param.uno}">
                                <button class="btn btn-danger" onclick="alert('비활성화되었어요!')" >비활성화</button>
                            </form>

                            <a href="userList"><button  class="btn btn-primary">회원목록</button></a>

                        </div>

                    </div>
                    <!-- /.row (nested) -->

                </div>
                <!-- /.panel-body -->

            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    답변 내역
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" >
                        <thead>
                        <tr>
                            <th>관리자질문</th>
                            <th>사용자답변</th>
                            <th>사용자 이해도</th>
                            <th>사용자 답변날짜</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="question" items="${qlist}" >
                            <tr class="odd gradeX">
                                <td>${question.unqtitle}</td>
                                <td>${question.anqtitle}</td>
                                <td>${question.anqty}</td>
                                <td>${question.anqregdate}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    </form>
                    <!-- /.table-responsive -->
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

</div>
<!-- /#wrapper -->


<script type="text/javascript">

    function goBack() {
        window.history.go(-2);
    }
</script>

<%@include file="includes/footer.jsp"%>
