<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h1>HTTP Status 403 - Access is denied</h1>

	<c:choose>
		<c:when test="${empty errorMsg}">
		  <h2>You do not have permission to access this page!</h2>
		</c:when>
		<c:otherwise>
		  <h2>UserName : ${userName} <br/>
                    You do not have permission to access this page!</h2>
		</c:otherwise>
	</c:choose>
<%@ include file="common/footer.jspf"%>