/**
 * 
 */

var OPENINFRA_HELPER = OPENINFRA_HELPER || {};

/**
 * This class contains methods to perform an ajax request. 
 * @memberof OPENINFRA_HELPER
 * @class Ajax
 */
OPENINFRA_HELPER.Ajax = {
	/** 
	 * @property {Object} result The result that will be returned by an ajax
	 *                           request as OpenInfraResponseBuilder if the
	 *                           query was successful. 
	 */
	result : null,
	
	/** 
	 * @property {Object} xhr The error result that will be returned by an ajax
	 *                        request.
	 */
	xhr : null,
	
	/**
	 * This function executes an ajax query to the passed URI and handles the
	 * success and error cases.
	 * @param {string} source The id of the source which has called this 
	 *                        function.
	 */
	execDeleteQuery : function(uri) {
		if (uri != "") {
			// TODO get the confirm dialog to work
			//OPENINFRA_HELPER.ConfirmDialog.open();
			
			$.ajax({
				type: "DELETE",
				url: uri,
				error: function(xhr){
					OPENINFRA_HELPER.Ajax.xhr = xhr;
				}, // end error
				success: function(result) {
					OPENINFRA_HELPER.Ajax.result = result;
				} // end success
			}); // end ajax call
		}
	},
	
	/**
	 * This method will retrieve a hull object from the passed getUri, fill it
	 * with data from the data object and put or post it to the REST interface.
	 *
	 * @param {string} method The HTTP method (PUT or POST).
	 * @param {string} getUri The URI to retrieve the object or its hull
	 *                        (without parameter!).
	 * @param {string} setUri The URI to write the data (without parameter!).
	 * @param {object} data   The data as object.
	 */
	execPutOrPostQuery : function(method, getUri, setUri, newData) {
		// retrieve the URL parameter
		var parameter = window.location.search.substring(1);
		
		// get the object
		$.ajax({
			url: getUri + "?" + parameter,
		    dataType: "json",
			type: "GET",
			cache: false,
		}).done(function(data,status,jqXHR) {
			// TODO use of dynamic keys would be better if possible
			// for every new data
			for (i in newData) {
				// set the value of the hull object depending on the key
				switch (i) {
				case "names":
					data.names.localizedStrings[0].characterString = newData[i];
					break;
				case "descriptions":
					if (newData[i] == "") {
						delete data.descriptions;
					} else {
						// TODO it is not possible to update a description if it is not present (description hull?)
						data.descriptions.localizedStrings[0].characterString = newData[i];
					}
					break;
				default:
					break;
					alert("The property " + i + " is not implemented.");
				} // end switch
			} // end for
			
			// set the new data
			$.ajax({
				url: setUri,
				type: method,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				cache: false,
		 		error: function(xhr){
		 			OPENINFRA_HELPER.Ajax.xhr = xhr;
			  	}, // end error
				success: function(result) {
					OPENINFRA_HELPER.Ajax.result = result;
				} // end success
		  	}); // end ajax call
		}); // end done
	} // end putOrPost
};


/**
 * This class contains methods that modify the message box for operations on
 * the front end. This covers the success and alert message box.
 * 
 * @memberof OPENINFRA_HELPER
 * @class MessageBox
 */
OPENINFRA_HELPER.MessageBox = {
	/**
	 * This method will get the clicked item and hide its parent. The click
	 * event will be fired when the cross in the message box is clicked.
	 * Following the parent element (the message div) will be hide.
	 * @param {element} elem The clicked cross element.
	 */
	hide : function(elem) {
		$(elem).parent().parent().fadeOut();
	},
	

	/**
	 * This functions prints the result of the request into the message box that
	 * will be created by including the snippet MessageBox.jsp.
	 * 
	 * WARNING:After filling the message box, the properties result and xhr will
	 * be reseted.
	 */
	setResponse : function() {
		xhr = OPENINFRA_HELPER.Ajax.xhr;
		result = OPENINFRA_HELPER.Ajax.result;
		
		
		
		// set the success message
		if (result != null) {
			$('#successDiv').text(result.message + ": ");
			$('#successDiv').append(result.uuid);
			$('#success').fadeIn();
			// reset the result properties
			OPENINFRA_HELPER.Ajax.result = null;
		} // end if
		
		// set the error message
		if (xhr != null) {
			if(xhr.responseText.search('<body>') > -1) {
				var source = xhr.responseText.split('<body>')[1];
				$('#alertDiv').html(source.split('</body>')[0]);
				$('#alert').fadeIn();
				// reset the xhr properties
				OPENINFRA_HELPER.Ajax.xhr = null;
			} // end if
		} // end if
	}
};

/**
 * This class contains methods that belongs to the confirm dialog.
 * 
 * @memberof OPENINFRA_HELPER
 * @class ConfirmDialog
 */
OPENINFRA_HELPER.ConfirmDialog = {
		/*
	open : function() {
	    $("#confirmBox").on("show", function() {    // wire up the OK button to dismiss the modal when shown
	    	alert("lala");
	        $("#confirmBox a.btn").on("click", function(e) {
	        	
	            alert("button pressed");   			// just as an example...
	            $("#confirmBox").modal('hide');     // dismiss the dialog
	            return true;
	        });
	    });

	    $("#confirmBox").on("hide", function() {    // remove the event listeners when the dialog is dismissed
	        $("#confirmBox a.btn").off("click");
	        return false;
	    });
	    
	    $("#confirmBox").on("hidden", function() {  // remove the actual elements from the DOM when fully hidden
	        $("#confirmBox").remove();
	    });
	    
	    $("#confirmBox").modal({                    // wire up the actual modal functionality and show the dialog
	      "backdrop"  : "static",
	      "keyboard"  : true,
	      "show"      : true                     // ensure the modal is shown immediately
	    });
	*/
	}
