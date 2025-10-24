<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 식당 상세보기</title>
</head>
<body>
	<jsp:include page="../include/header.jsp"/>
	<div class="innerOuter">
		
		<div>
			<h1 id="title">식당명</h1>
			<p id="description">상세설명</p>
			<br /> <br />
			<p id="avail"></p>
			<br /> <br />
			<p id="address"></p>	
			<img id="img" src="" style="width=100% ; height=240px"/>
			<br /> <br />	
			
		</div>
		<div id="map" style="width:100%; height:400px">
				
		
		
		</div>
		<button class="btn btn-sm btn-secondary" onclick="history.back();">뒤로가기</button>	
		
		<script>
			function abc(){
				
			}
		
		
		</script>	
	<script>
	$(function(){
		$.ajax({
			url:`/spring/api/busan/detail/${num}`,
			success: response=>{
				//console.log(response.getFoo);
				const food= response.getFoodKr.item[0];
				document.getElementById("title").innerHTML=food.MAIN_TITLE;
				document.getElementById("description").innerHTML = food.ITEMCNTNTS;
				document.getElementById("avail").innerHTML = food.USAGE_DAY_WEEK_AND_TIME;
				document.getElementById("address").innerHTML=food.ADDR1;
				document.getElementById("img").src= food.MAIN_IMG_NORMAL;
				
			}
			
			
			
		})
	}
	)
	
	</script>
	
	</div>




	<jsp:include page="../include/footer.jsp"/>

</body>
</html>