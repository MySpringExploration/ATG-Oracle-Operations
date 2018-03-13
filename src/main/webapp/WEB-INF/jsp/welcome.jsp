<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">

	
	<h2>Welcome to GMO Operations! Have a nice day ahead.</h2>
	
	</br></br>
	<ul><li><h4><p><font color="blue">${comingSoonFeaturesLabel}</font></p></h4></li>
	<br/>
	<ol start="1">
    <c:forEach items="${comingSoonFeaturesList}" var="csFtrItem">
	      <li><p><font color="green">${csFtrItem}</font></p></li>
	 </c:forEach>
	 </ol>
	 </ul>
	 <br/>
    	<ul><li><h4><p><font color="blue">${futureFeaturesLabel}</font></p></h4></li>
    	<br/>
    	<ol start="1">
    <c:forEach items="${futureFeaturesList}" var="fsFtrItem">
	      <li><p><font color="navy">${fsFtrItem}</font></p></li>
	 </c:forEach>
	 </ol>
	 <ul><li><p><font color="navy">many more ..., list will be updated regularly</font></p></li></ul>
	 </ul>
   
	
</div>
<%@ include file="common/footer.jspf"%>