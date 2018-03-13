<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div class="container">

	<form:form method="post" modelAttribute="dBOperations">

		<fieldset class="form-group">
			<form:label path="dbQuery">Query Panel</form:label>
			<form:textarea cols="70" rows="5" path="dbQuery" class="form-control"
				required="required" />
			<form:errors path="dbQuery" cssClass="text-warning" />
		</fieldset>
		<div>
		<button type="submit" class="btn btn-success">Execute</button>
		&nbsp;
		<c:set var="tableMetadata" scope="request"
				value="${dBOperations.tableMetadata}" />
			<c:if test="${not empty tableMetadata}">
				<a type="button" class="btn btn-success" href="/export?format=ToExcel">Export
					As Excel</a>
				&nbsp;	
				<a type="button" class="btn btn-success" href="/export?format=ToPDF">Export
					As PDF</a>
				
					
			</c:if>
		</div>
		<div>
			<p>&nbsp;</p>
		</div>

		<c:set var="successMsg" scope="request"
			value="${dBOperations.successMsg}" />
		<c:if test="${not empty successMsg}">
			<div class="alert alert-${css} alert-success" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">x</span>
				</button>
				<strong>${successMsg}</strong>
			</div>
		</c:if>

		<c:set var="exceptionMsg" scope="request"
			value="${dBOperations.exceptionMsg}" />
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
			
			<div style="height: 400px; overflow: auto">
				<table class="table table-striped table-bordered table-hover">

					<c:if test="${not empty tableMetadata}">
						<thead>
							<tr>
								<c:forEach items="${tableMetadata.tableMetadata}"
									var="tableMetadata">
									<c:forEach items="${tableMetadata}" var="item">
										<th>${item}</th>
									</c:forEach>
								</c:forEach>
							</tr>
						</thead>
					</c:if>
					<tbody>
						<c:set var="allRowsData" scope="request"
							value="${dBOperations.allRowsData}" />
						<c:if test="${not empty allRowsData}">
							<c:forEach items="${allRowsData.allRows}" var="allRows">
								<tr>
									<c:forEach items="${allRows.eachRow}" var="eachRow">
										<c:forEach items="${eachRow}" var="columnData">
											<c:if test="${empty columnData}">
												<td>&nbsp;</td>
											</c:if>
											<c:if test="${not empty columnData}">
												<td>${columnData}</td>
											</c:if>

										</c:forEach>
									</c:forEach>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</c:if>
	</form:form>
</div>
<%@ include file="../common/footer.jspf"%>