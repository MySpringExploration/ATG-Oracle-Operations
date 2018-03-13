<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	
	<form:form method="post" modelAttribute="userInfo">
	
	<c:if test="${not empty failureMsg}">
		<div class="alert alert-${css} alert-danger" role="alert">
			<button type="button" class="close" data-dismiss="alert"
                                aria-label="Close">
				<span aria-hidden="true">×</span>
			</button>
			<strong>${failureMsg}</strong>
		</div>
	</c:if>	
	
	<c:if test="${not empty successMsg}">
		<div class="alert alert-${css} alert-success" role="alert">
			<button type="button" class="close" data-dismiss="alert"
                                aria-label="Close">
				<span aria-hidden="true">×</span>
			</button>
			<strong>${successMsg}</strong>
		</div>
	

	<h1>User Details</h1>
	<br />

	<div class="row">
		<form:label class="col-sm-2" path="loginId">Login</form:label>
		<div class="col-sm-10">${userInfo.loginId}</div>
	</div>

	<div class="row">
		<form:label class="col-sm-2" path="fullName">Full Name</form:label>
		<div class="col-sm-10">${userInfo.fullName}</div>
	</div>

	<div class="row">
		<form:label class="col-sm-2" path="email">Email</form:label>
		<div class="col-sm-10">${userInfo.email}</div>
	</div>

	<div class="row">
		<form:label class="col-sm-2" path="role">Role</form:label>
		<div class="col-sm-10">${userInfo.role}</div>
	</div>
	<c:set var = "modifiedRole" scope = "request" value = "${userInfo.modifiedRole}"/>
		<c:if test="${not empty modifiedRole}">
			<div class="row">
				<form:label class="col-sm-2" path="modifiedRole">Modified Role</form:label>
				<div class="col-sm-10">${userInfo.modifiedRole}</div>
			</div>
		</c:if>
	</c:if>	
	</form:form>
</div>
<%@ include file="common/footer.jspf"%>