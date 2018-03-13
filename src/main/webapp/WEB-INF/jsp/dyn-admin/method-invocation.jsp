<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div class="container">

	<form:form method="post" modelAttribute="dynAdminInfo">

		<fieldset class="form-group">
			<form:label path="applicationName">Application</form:label>
			<form:select path="applicationName" class="form-control"
				required="required">
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
			<form:label path="methodName">Method Name</form:label>
			<form:input path="methodName" type="text" class="form-control"
				required="required" />
			<form:errors path="methodName" cssClass="text-warning" />
		</fieldset>

		<fieldset class="form-group">
			<form:label path="cmDetails">Comment</form:label>
			<form:input path="cmDetails" type="text" class="form-control"
				required="required" />
			<form:errors path="cmDetails" cssClass="text-warning" />
		</fieldset>

		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<fieldset class="form-group">
				<form:label path="parallelExecution">Do You Wish Parallel Execution?</form:label>
				<form:checkbox path="parallelExecution" class="form-control"
					value="Yes" />
				<form:errors path="parallelExecution" cssClass="text-warning" />
			</fieldset>

			<fieldset class="form-group">
				<form:label path="parallelThreadCount">No. of servers to be executed in parallel?</form:label>&nbsp;&nbsp;
				<form:radiobutton path="parallelThreadCount" value="5" />&nbsp;
				<form:label path="parallelThreadCount">5</form:label>&nbsp;&nbsp;
				<form:radiobutton path="parallelThreadCount" value="10" />&nbsp;
				<form:label path="parallelThreadCount">10</form:label>&nbsp;&nbsp;
				<form:radiobutton path="parallelThreadCount" value="20" />&nbsp;
				<form:label path="parallelThreadCount">20</form:label>&nbsp;&nbsp;
				<form:radiobutton path="parallelThreadCount" value="30" />&nbsp;
				<form:label path="parallelThreadCount">30</form:label>
			</fieldset>
		</sec:authorize>

		<button type="submit" class="btn btn-success">Invoke Method</button>
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