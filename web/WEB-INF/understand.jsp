<%--
  Created by IntelliJ IDEA.
  User: 5CLASS-184
  Date: 2018-09-12
  Time: 오후 3:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="includes/header.jsp"%>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">질문 등록</h1>
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
                    질문을 등록하세요.
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-6">
                            <form action="/admin/understand" method="post" class="form">
                                <div class="form-group">
                                    <label>이해했니??!!!!</label>
                                    <select name="unqgubun" class="form-control">
                                        <option value="0" selected>이해했니?</option>
                                        <option value="1">만들수 있겟어요?</option>
                                        <option value="2">쉬고 싶니?</option>
                                        <option value="3">평생 쉬어</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>시간</label>
                                    <select name="unqlimit" class="form-control">
                                        <option value="5" selected>5분</option>
                                        <option value="10">10분</option>
                                        <option value="20">20분</option>
                                        <option value="30">30분</option>
                                        <option value="60">1시간</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>상세질문 (1200자 이내)</label>
                                    <textarea class="form-control" type="text" name="unqtitle"></textarea>
                                </div>
                                <button type="submit" class="btn btn-primary" onclick="alert('전송되었어요!')">전송</button>
                            </form>

                            <a href="board"><button class="btn btn-danger">취소</button></a>
                            <%--<a href="/list"> <button type="reset" class="btn btn-default">취소</button></a>--%>
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
</div>
<!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<%@include file="includes/footer.jsp"%>