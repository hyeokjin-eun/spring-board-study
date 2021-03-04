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
    <link href="/css/home/main.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <nav class="home-nav">
            <div class="home-nav-links">
                <a href="#" class="product-name">Toystagram</a>
                <a href="/login">Login</a>
            </div>
        </nav>

        <section class="scroll-section" id="scroll-section-0">
            <h1>Toystagram</h1>
            <div class="sticky-elem sticky-elem-canvas">
                <canvas id="video-canvas-0" width="1920" height="1080"></canvas>
            </div>
            <div class="sticky-elem main-message a">
                <p>친구에게<br>자신의 일상 공유</p>
            </div>
            <div class="sticky-elem main-message b">
                <p>누구나 쉽게<br>포스트</p>
            </div>
            <div class="sticky-elem main-message c">
                <p>해시 태그로<br>포스트 홍보</p>
            </div>
        </section>
    </div>
    <script src="/js/home/main.js"></script>
</body>
</html>