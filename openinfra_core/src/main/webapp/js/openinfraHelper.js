/**
 * The namespace <b>OPENINFRA_HELPER</b> is used for classes and functions which
 * are related to different front end functionalities.
 * 
 * @namespace 
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
	 * This function executes a DELETE ajax request to the passed URI and
	 * handles the success and error cases.
	 * 
	 * @param {string} uri The uri of the DELETE request.
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
	 * @param {uuid}   localeId
	 */
	execPutOrPostQuery : function(method, getUri, setUri, newData, localeId) {
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
					// check if the name exists before
					if (data.names.localizedStrings != "") {
						data.names.localizedStrings[0].characterString = newData[i];
					} else {
						if (newData[i] != "") {
							// build a name object
							data["names"] = 
								OPENINFRA_HELPER.JSONObjectBuilder.localizedString(
										newData[i], localeId, data.names.uuid);
						}
					}
					break;
				case "descriptions":
					// check if the description exists before
					if (data.descriptions.localizedStrings != "") {
						// if the new data is not set
						if (newData[i] == "") {
							// delete the description object
							delete data.descriptions;
						} else {
							// set the new description
							data.descriptions.localizedStrings[0].characterString = newData[i];
						}
					} else {
						if (newData[i] != "") {
							// build a description object
							data["descriptions"] = 
								OPENINFRA_HELPER.JSONObjectBuilder.localizedString(
										newData[i], localeId, data.descriptions.uuid);
						}
					}
					break;
				case "visibility":
					data.visibility = newData[i];
					break;
				case "belongsToValueList":
					data.belongsToValueList = newData[i];
					break;
				default:
					alert("The property " + i + " is not implemented.");
					break;
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
	/**
	 * This function opens the confirm dialog.
	 */
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
	}
};


/**
 * This class contains different object builder functions. This functions will
 * return JSON parts, filled with the passed values.
 * 
 * @memberof OPENINFRA_HELPER
 * @class ObjectBuilder
 */
OPENINFRA_HELPER.JSONObjectBuilder = {
	/**
	 * This function returns a localizedString object that can be used to fill
	 * an empty description or name object.
	 * 
	 * @param {string} content The content of the localizedString.
	 * @param {uuid} localeId  The locale id of the content.
	 * @param {uuid} uuid      The uuid of the localizedString (null is valid)
	 * @returns {object}       A localizedString JSON.
	 */
	localizedString : function(content, localeId, uuid) {
		return {
			"uuid":uuid,
			"localizedStrings":[{"characterString":content,
				"locale":{"uuid":localeId}}]};
	}
	
	
};

/**
 * This class contains several different functions.
 * 
 *  @memberof OPENINFRA_HELPER
 *  @class Misc
 */
OPENINFRA_HELPER.Misc = {
	/**
	 * This function retrieve the base path of the current URL until the
	 * appearance of the word "rest".
	 * 
	 * @returns {string} The base path of the current URL.
	 */
	getBasePath : function() {
		var url = document.location.href;
		// return the path of the url until the word "rest" appears
		return url.match(/.+?(?=rest)/);
	}
};