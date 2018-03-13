<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
	
	<div class="container">
	<h2>Registered User Details</h2>
	<div style="height:600px; overflow:auto">
		<table class="table table-striped table-bordered table-hover" >
		
			
			<thead>
				<tr>
					<th>Login</th>
					<th>Name</th>
					<th>Email</th>
					<th>Role</th>
					<th>Modified Role</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="userInfo">
					<tr>
						<td>${userInfo.loginId}</td>
						<td>${userInfo.fullName}</td>
						<td>${userInfo.email}</td>
						<td>${userInfo.role}</td>
						<c:set var = "modifiedRole" scope = "request" value = "${userInfo.modifiedRole}"/>
						<c:if test="${not empty modifiedRole}">
						<td>${userInfo.modifiedRole}</td>
						</c:if>
						<c:if test="${empty modifiedRole}">
						<td align="center">N/A</td>
						</c:if>
						<c:set var = "enabled" scope = "request" value = "${userInfo.enabled}"/>
						<c:if test = "${enabled == 1}">
							<c:if test="${empty modifiedRole}">
								<td><font color="green">Approved</font></td>
							</c:if>	
							<c:if test="${not empty modifiedRole}">	
								<td><font color="blue">Pending</br>Role Change</font></td>
							</c:if>	
						</c:if>
						<c:if test = "${enabled == 0}">	
						<td><font color="orange">Pending</br>Activation</font></td>
						</c:if>	
						<c:if test = "${enabled == 1}">
							<c:if test="${not empty modifiedRole}">
								<td><a type="button" class="btn btn-success"
									href="/approve-user?loginId=${userInfo.loginId}">Approve</a>
									<a type="button" class="btn btn-warning"
									href="/reject-user?loginId=${userInfo.loginId}">Reject</a></td>
							</c:if>
							<c:if test="${empty modifiedRole}">	
								<td><a type="button" class="btn btn-danger"
									href="/delete-user?loginId=${userInfo.loginId}">Delete</a></td>
							</c:if>
						</c:if>
						<c:if test = "${enabled == 0}">	
						<td><a type="button" class="btn btn-success"
							href="/approve-user?loginId=${userInfo.loginId}">Approve</a>
							<a type="button" class="btn btn-warning"
							href="/delete-user?loginId=${userInfo.loginId}">Reject</a></td>
						</c:if>	
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>	
	</div>
<%@ include file="common/footer.jspf" %>