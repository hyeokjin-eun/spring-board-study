<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Toystagram</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;900&display=swap" rel="stylesheet">
    <link href="/css/default.css" rel="stylesheet">
    <link href="/css/login/main.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <section class="login-section">
            <div class="login-content">
                <div class="login-content-field">
                    <div class="login-title">
                        <div class="login-title-img">
                            <img class="login-title-img" src="/img/board/board-default.jpg"/>
                        </div>
                        <div class="login-title-content-field">
                            <h1 class="login-title-content">Toystagram</h1>
                        </div>
                    </div>
                    <div class="login-id-input-field">
                        <input class="login-id-input" placeholder="아이디" type="text">
                    </div>
                    <div class="login-password-input-field">
                        <input class="login-password-input" placeholder="비밀번호" type="password">
                    </div>
                    <div class="login-button-field">
                        <button class="login-button">로그인</button>
                    </div>
                    <div class="login-section-department">
                        <div class="department-line"></div>
                        <div class="department-text"> 또는 </div>
                        <div class="department-line"></div>
                    </div>
                    <div class="register-a-field">
                        <a class="register-a" href="/register">회원 가입</a>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <script src="/js/login/main.js"></script>
</body>
</html>