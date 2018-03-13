<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">

	<c:if test="${not empty error}">
		<div class="error"><font color="red">${error}</font></div>
	</c:if>
	<c:if test="${not empty msg}">
		<div class="msg"><font color="red">${msg}</font></div>
	</c:if>
	
	<form:form method="POST" modelAttribute="userInfo" action="/login">
	
		<fieldset class="form-group">
			<form:label path="loginId">Login</form:label>
			<form:input path="loginId" type="text" class="form-control"
				required="required" />
			<form:errors path="loginId" cssClass="text-warning" />
			
		</fieldset>

		<fieldset class="form-group">
			<form:label path="password">Password</form:label>
			<form:input path="password" type="password" class="form-control"
				required="required" />
			<form:errors path="password" cssClass="text-warning" />
		</fieldset>

		<button type="submit" class="btn btn-success">Login</button>
	</form:form>
</div>
<%@ include file="common/footer.jspf"%>