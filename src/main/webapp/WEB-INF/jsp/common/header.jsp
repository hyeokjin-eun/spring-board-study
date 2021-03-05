<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Toystagram</title>
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;900&display=swap" rel="stylesheet">
        <link href="/css/default.css" rel="stylesheet">
    </head>
    <body>
    <div class="container">
        <nav class="main-nav">
            <div class="main-nav-links">
                <a href="/" class="product-name">Toystagram</a>
                <sec:authorize access="isAnonymous()">
                    <a href="/login">Login</a>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <a href="/logout">Logout</a>
                </sec:authorize>
            </div>
        </nav>


