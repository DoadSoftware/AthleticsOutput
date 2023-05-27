<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
  <sec:csrfMetaTags/>
  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  <title>Athletics</title>
  <script type="text/javascript" src="<c:url value="/webjars/jquery/1.9.1/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/index.js"/>"></script>
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>"/>  
</head>
<body onload="afterPageLoad('CLOCK');">
<form:form name="football_form" autocomplete="off" action="match" method="POST" enctype="multipart/form-data">
<!-- <input type ="button" onClick ="multiplyBy()" Value = "Multiply"/> -->
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
  <div class="container">
	<div class="row">
	 <div class="col-md-8 offset-md-2">
       <span class="anchor"></span>
         <div class="card card-outline-secondary">
           <div class="card-header">
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
	               <h6 id="match_time_hdr"></h6>
	          </div>
           </div>
          <div class="card-body">
			  <div class="panel-group" id="match_configuration">
			    <div class="panel panel-default">
			      <div class="panel-heading">
			        <h4 class="panel-title">
			          <a data-toggle="collapse" data-parent="#match_configuration" href="#load_setup_match">Configuration</a>
			        </h4>
			      </div>
			      <div id="load_setup_match" class="panel-collapse collapse">
					<div class="panel-body">
 					  <div id="start_pause_match_time_div" style="margin-bottom:5px;">
						<div class="row">
						 <div class="col-sm-4 col-md-4">
						    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
						  		name="name_super" id="name_super" onclick="processUserSelection(this);">Name Super</button>
						 </div>
						</div>
					  </div> 
				    </div>
			      </div>
			    </div>
			  </div> 
		    <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			  <div id="select_event_div" style="display:none;"></div>
           </div>
          </div>
         </div>
       </div>
    </div>
  </div>
 </div>
 <input type="hidden" name="selectedBroadcaster" id="selectedBroadcaster" value="${session_selected_broadcaster}"/>
</form:form>
</body>
</html>