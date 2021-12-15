<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<script>
 
    var link = '${pageContext.request.contextPath}/main';
    window.location.href = link;       
    window.location.replace(link);     
    window.open(link,"_self");                 
</script>

</body>	
</html>
