<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../include/header.jsp" %>

<div id="container">
	<div class="cuswrap innerContent">
		<div class="content_tit">
		<div class="member_tit"><h1 class="sub_tit">공지사항</h1></div>
		<ul class="sub_menu">
			<li class="on"><a href="${pageContext.request.contextPath}/board/flist">공지사항</a></li>     
			<li><a href="${pageContext.request.contextPath}/board/blist">1:1문의</a></li>
		</ul>
	</div><!--content_tit-->
		<div class="notice">
	<div class="page_info">
		<div class="total">총 <em id="r_cnt">${pageDTO.count}</em> 건 (<em class="hilite" id="now_page">1</em>/<em id="total_page">1</em>페이지)</div>
	</div>
	
		<div class="list con_box">
			<form action="${pageContext.request.contextPath}/board/fwritePro" id="fwriteForm" method="post" enctype="multipart/form-data">
				<table border="1">
				
					<tr><td>작성자</td><td><input type="text" name="memId"></td></tr>
					<tr><td>비밀번호</td><td><input type="password" name="pass"></td></tr>
					<tr><td>제목</td><td><input type="text" name="ntcTitle"></td></tr>
					<tr><td>내용</td><td><textarea name="ntcCont" rows="10" cols="20"></textarea></td></tr>   
					<tr><td>파일첨부</td><td style="text-align:left;"><input type="file" name="file"></td></tr>
				</table>
			</form>
		</div>
			<!--  list con_box  -->
			<div>
				<input class="writeBtn red" type="submit" form="fwriteForm"
					value="글쓰기"> <a class="writeBtn gray"
					href="${pageContext.request.contextPath}/board/flist">목록가기</a>
			</div>

		</div><!--notice-->
	</div><!--cuswrap-->					
</div><!--container-->

<%@ include file="../include/footer.jsp" %>