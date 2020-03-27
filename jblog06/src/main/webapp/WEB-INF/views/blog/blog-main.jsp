<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<div id="header">
		<a href="${pageContext.request.contextPath }/${blogVo.blogId}">
			<h1> ${blogVo.title} </h1></a>
			<ul>
				<c:if test="${empty authUser }">
				<li><a href="${pageContext.request.contextPath }/user/login">로그인</a></li>
				</c:if>
				<c:if test="${not empty authUser }">
				<li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a></li>
				</c:if>
				<li><a href="${pageContext.request.contextPath }/${authUser.id}/admin">블로그 관리</a></li>
			</ul>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<c:if test="${empty post.title}"> 
						<h4>포스트가 존재하지 않습니다.</h4>
					</c:if>
					<c:if test="${not empty post.title }">
					<h4>${post.title }</h4>
						<p>
							${post.contents }
						<p>
					</c:if>
				</div>
				
				<ul class="blog-list">				
					<c:forEach var="vo" varStatus="status" items="${pList }">
						<li><a href="${pageContext.request.contextPath}/${blogVo.blogId }/${caNo}/${vo.no}">${vo.title }</a> <span>${vo.dateTime }</span> </li>					
					</c:forEach>
					
				</ul>
				
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<c:choose>
					<c:when test="${blogVo.logo=='1234.PNG' }">
						<img src="${pageContext.request.contextPath}/assets/images/spring-logo.jpg">
					</c:when>					
					<c:otherwise>
						<img src="${pageContext.request.contextPath}${blogVo.logo}">					
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
				<ul>
					<c:if test = "${empty list}">
						<li><a href="">기타</a></li>
					</c:if>
					<c:forEach var="vo" varStatus="status" items="${list }">					
						<li>
							<a href="${pageContext.request.contextPath}/${blogVo.blogId }/${vo.no}">${vo.name }</a>							
						</li>					
					</c:forEach>
				</ul>
		</div>
		
		<div id="footer">
			<p>
				<strong>${blogVo.title}</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>