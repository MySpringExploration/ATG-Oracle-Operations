<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div class="container">
	
	<form:form method="post" modelAttribute="dynAdminInfo">
		
		<fieldset class="form-group">
			<form:label path="applicationName">Application</form:label>
			<form:select path="applicationName" class="form-control" required="required">
				<form:options items="${dynAdminInfo.applications}" />
			</form:select>
			<form:errors path="applicationName" cssClass="text-warning" />
		</fieldset>
		
		<fieldset class="form-group">
			<form:label path="component">Component</form:label>
			<form:input path="component" type="text" class="form-control"
				required="required" />
			<form:errors path="component" cssClass="text-warning" />
		</fieldset>

		<fieldset class="form-group">
			<form:label path="property">Property</form:label>
			<form:input path="property" type="text" class="form-control"
				required="required" />
			<form:errors path="property" cssClass="text-warning" />
		</fieldset>

		<button type="submit" class="btn btn-success">Generate Report</button>
		<div>
			</br>
		</div>
		<c:set var="exceptionMsg" scope="request"
			value="${dynAdminInfo.errorMsg}" />
		<c:if test="${not empty exceptionMsg}">
			<div class="alert alert-${css} alert-danger" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">x</span>
				</button>
				<strong>${exceptionMsg}</strong>
			</div>
		</c:if>
		<c:if test="${empty exceptionMsg}">
			<c:set var="successMsg" scope="request"
				value="${dynAdminInfo.successMsg}" />
			<c:if test="${not empty successMsg}">
				<div class="alert alert-${css} alert-success" role="alert">
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">x</span>
					</button>
					<strong>${successMsg}</strong>
				</div>
			</c:if>
		</c:if>
	</form:form>
</div>
<%@ include file="../common/footer.jspf"%>