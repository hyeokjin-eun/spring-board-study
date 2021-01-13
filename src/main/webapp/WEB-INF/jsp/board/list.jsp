<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="../common/header.jsp" />
<script type="text/javascript" src="/js/board/board.js" charset="UTF-8"></script>
<section class="container">
    <h1>게시판 글 목록</h1>
    <hr/>
    <div>
        <table class="table table-bordered">
            <colgroup>
                <col width="5%">
                <col width="25%">
                <col width="60%">
                <col width="10%">
            </colgroup>
            <thead>
            <tr>
                <th>No</th>
                <th>제목</th>
                <th>내용</th>
                <th>등록일자</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <a class="btn btn-default pull-right" href="/board/write/0">글쓰기</a>
    </div>
</section>
<jsp:include page="../common/footer.jsp" />