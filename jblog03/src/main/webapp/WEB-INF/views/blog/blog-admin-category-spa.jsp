<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.4.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script>
var id = '${authUser.id}';
var listItemTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs"
});
var listTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
});
var number = "번호";
var category = "카테고리명";
var post = "포스트 수";
var des = "설명";
var del = "삭제";

console.log(id);
var getList = function() {
	
	$.ajax({
	    url: '${pageContext.request.contextPath }/api/category/' + id,
	    async: true,
	    type: 'get',	    
	    dataType: 'json',
	    data: "",
	    success: function(response){
	    	console.log("ㅎㅇ");
	    	
	    	var image = "${pageContext.request.contextPath}/assets/images/delete.jpg";
	    	response.data.image = image;
	    	console.log(response.data);
	    	
			var html = listTemplate.render(response);
			$(".admin-cat").append(html);
	    },

	    error: function (request, status, error){   
	    }
	  });
};
$(document).ready(getList());

$(document).on("click","#cat-del",function(){
	var no = ($(this).val());

	$.ajax({
	    url: '${pageContext.request.contextPath }/api/category/delete/' + no,
	    async: true,
	    type: 'delete',	    
	    dataType: 'json',
	    data: '',
	    success: function(response){	    	
			console.log("성공~!");
		if(response.data != -1){
				console.log(response.data + "리스폰스데이터");
				 $(".admin-cat tr[data-no=" + response.data + "]").remove(); 
				 $('.admin-cat *').remove();
				 getList();
				console.log("as실행");
				return;
			}
	    },

	    error: function (request, status, error){   
	    }
	  });
	
	
});

$(document).on("click","#add-cate",function(e){
	event.preev
	var title = $('#title').val();
	var desc = $('#desc').val();
	
	
	var vo = {};
	vo.name = $('#title').val();
	vo.description = $('#desc').val();
	vo.id = '${authUser.id}';
	console.log(title + desc);
	console.log(vo.name + vo.description + vo.id);
	$.ajax({
		url: '${pageContext.request.contextPath }/api/category/add',
		async: true,
		type: 'post',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(vo),
		success: function(response){
			
			var image = "${pageContext.request.contextPath}/assets/images/delete.jpg";
	    	response.data.image = image;
	    	
			if(response.result != "success"){
				console.error(response.message);
				return;
			}
			response.data.len= $('.admin-cat').find('tr').length;
			console.log(response);
			// rendering
			// render(response.data, true);
			var html = listItemTemplate.render(response.data);
			// $('.admin-cat *').remove();
			// getList();
			$('.admin-cat tr').last().after(html);
			$("#add-form")[0].reset();
			e.preventDefault();
			
		},
		error: function(xhr, status, e){
			console.error(status + ":" + e);
		}
	});
});

</script>
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${bVo.title }</h1>
			<ul>
				<c:if test="${empty authUser }">
					<li><a href="${pageContext.request.contextPath }/user/login">로그인</a></li>
				</c:if>
				<c:if test="${not empty authUser }">
					<li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a></li>
				</c:if>
				<li><a
					href="${pageContext.request.contextPath }/${authUser.id }/admin">블로그
						관리</a></li>
			</ul>
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a
						href="${pageContext.request.contextPath }/${authUser.id }/admin">기본설정</a></li>
					<li><a
						href="${pageContext.request.contextPath }/${authUser.id }/category">카테고리</a></li>
					<li class="selected">카테고리(spa)</li>
					<li><a
						href="${pageContext.request.contextPath }/${authUser.id }/write">글작성</a></li>
				</ul>
				<table class="admin-cat">

					
													
				</table>
				<form id="add-form">					
					<h4 class="n-c">새로운 카테고리 추가</h4>
					<table id="admin-cat-add">
						<tr>
							<td class="t">카테고리명</td>
							<td><input type="text" name="title" id='title'></td>
						</tr>
						<tr>
							<td class="t">설명</td>
							<td><input type="text" name="desc" id='desc' id="des-text"></td>
						</tr>
						<tr>
							<td class="s">&nbsp;</td>
							<td><input id='add-cate' type="button" value="카테고리 추가"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>