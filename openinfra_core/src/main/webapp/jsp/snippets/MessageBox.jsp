<!-- 
This snippet will generate a container for status messages. The content of the
message must be set separately.
 -->
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<div class="alert alert-success" style="display: none;" role="alert" id="success">
	<div align="right">
		<i class="fa fa-times"  onclick="OPENINFRA_HELPER.MessageBox.hide(this)"></i>
	</div>
	<div id="successDiv"></div>
</div>
<div class="alert alert-danger" style="display: none;" role="alert" id="alert">
	<div align="right">
		<i class="fa fa-times" onclick="OPENINFRA_HELPER.MessageBox.hide(this)"></i>
	</div>
	<div id="alertDiv"></div>
</div>

