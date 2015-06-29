<!-- 
This snippet will generate a container for status messages. The content of the
message must be set separately.
 -->
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<!-- set up the modal to start hidden and fade in and out -->
<div id="confirmBox" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- dialog body -->
			<div class="modal-body">
				<fmt:message>confirm.message</fmt:message>
			</div>
			<!-- dialog buttons -->
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message>cancel.button.label</fmt:message>
				</button>
				<button type="button" class="btn btn-primary">
					<fmt:message>ok.button.label</fmt:message>
				</button>
				
			</div>
		</div>
	</div>
</div>