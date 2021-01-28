<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="../common/header.jsp" />
<link href="/css/board/main.css" rel="stylesheet">
<section class="section-content" id="section-content">
    <div class="section-left">
        <div class="section-center">
            <div class="board-create-section">
                <div class="my-content-info">
                    <img class="my-profile" src="/img/user/user-default.jpg" width="56" height="56">
                </div>
                <div class="board-create-area">
                    <input class="board-create-title" id="board-create-title" maxlength="14" placeholder="글 제목을 입력"/>
                    <textarea class="board-create-content" id="board-create-content" placeholder="자신의 일상을 공유보세요"></textarea>
                    <button class="board-create-btn" id="board-create-btn">
                        게시하기
                    </button>
                </div>
            </div>
        </div>
        <div class="section-board">
        </div>
    </div>
    <div class="section-right">
        <div class="section-right-content">
            <div class="user-list">
                <div class="my-profile-field">
                    <img class="my-profile" src="/img/user/user-default.jpg" width="56" height="56">
                    <div class="my-info">
                        <div class="my-nickname">
                            <strong>사용자 닉네임</strong>
                        </div>
                        <div class="my-name">
                            사용자 성명
                        </div>
                    </div>
                    <div class="my-link">
                        <a href="#">내정보</a>
                    </div>
                </div>
                <div class="user-recommend-content">
                    <div class="user_recommend-title">
                        <strong>추천 회원 목록</strong>
                    </div>
                    <div class="user-all-link">
                        <a href="#"><strong>모두 보기</strong></a>
                    </div>
                </div>
                <div class="user-profile-field">
                    <img class="user-profile" src="/img/user/user-default.jpg" width="32" height="32">
                    <div class="user-info">
                        <div class="user-nickname">
                            <strong>사용자 성명</strong>
                        </div>
                        <div class="user-description">
                            회원님을 위한 추천
                        </div>
                    </div>
                    <div class="user-link">
                        <a href="#">정보</a>
                    </div>
                </div>
            </div>
            <div class="page-info">
                포폴 또는 공부를 위해 제작한 개인 프로젝트 입니다. <br> Instagram Clone Coding
            </div>
        </div>
    </div>
</section>

<div id="modal">
    <div id="modal-layer">
        <div class="modal-content" id="modal-content">
            <div class="board-update-btn-field">
                <div class="board-update-btn-text modal-btn-field-text">수정</div>
            </div>
            <div class="board-delete-btn-field">
                <div class="board-delete-btn-text modal-btn-field-text">삭제</div>
            </div>
            <div class="modal-cancel-field">
                <div class="modal-cancel-text modal-btn-field-text">취소</div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="/js/board/main.js"></script>
<script src="/js/board/ajax.js"></script>
<jsp:include page="../common/footer.jsp" />