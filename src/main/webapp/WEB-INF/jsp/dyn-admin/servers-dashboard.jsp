<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div class="container">

	<form:form method="post" modelAttribute="dashboardInfo">

		<fieldset class="form-group">
			<form:label path="applicationName">Application</form:label>
			<form:select path="applicationName" class="form-control"
				required="required">
				<form:options items="${dashboardInfo.applications}" />
			</form:select>
			<form:errors path="applicationName" cssClass="text-warning" />
		</fieldset>

		
		<button type="submit" class="btn btn-success">Get Status</button>

		<div>
			</br>
		</div>
		
		<c:forEach items="${dashboardInfo.serversStatusMap}" var="serverStatusMap"
			varStatus="status">
			<c:set var = "enabled" scope = "request" value = "${serverStatusMap.value}"/>
			
				<c:if test = "${enabled == 1}">
					<a type="button" class="btn btn-success"
									href="#">${serverStatusMap.key}</a>&nbsp;
				</c:if>
				<c:if test = "${enabled == 0}">
					<a type="button" class="btn btn-danger"
									href="#">${serverStatusMap.key}</a>&nbsp;
				</c:if>
				
		</c:forEach>
		
		<c:set var="exceptionMsg" scope="request"
			value="${dashboardInfo.errorMsg}" />
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
				value="${dashboardInfo.successMsg}" />
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