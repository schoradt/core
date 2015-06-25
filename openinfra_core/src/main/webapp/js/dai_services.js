/**
 * @fileOverview This library or script file contains the essential 
 * functionalities of the DAI service interfaces.<br/><br/>
 * 
 * This script file is build upon the following libraries:
 * <ul>
 * 	<li><b>jQuery version 1.11.1</b> {@link http://jquery.com/}</li>
 *  <li><b>jQuery table sorter plug-in</b> {@link http://tablesorter.com/}</li>
 * </ul> 
 * 
 * The current implementation only reflects the functionalities of the user
 * interface. The functions to the back end (e.g. storage of ids, fetching
 * of ids) is currently not implemented. However, the functions are present and
 * it should be quite easy to extend this library by an appropriate and subject 
 * specific implementation.
 * 
 * In order to use this library the following HTML elements are required in the
 * belonging HTML file(s):
 * <ul>
 * 	<li><b>input</b> type: text with id="[some text]Query"</li>
 *  <li><b>input</b> type: button with id="[some text]Go"</li>
 *  <li><b>span</b> with id="[some text]SearchHistory"</li>
 *  <li><b>table</b> with id="[some text]Results"</li>
 *  <li><b>table</b> with id="[some text]Store"</li>
 * </ul>
 * The above mentioned ids are used to access and manipulate the belonging HTML 
 * component. The related HTML element is retrieved by the following function: 
 * {@link DAI_SERVICES.Display.getSource}. Extend this function in order to add 
 * a new service.
 * 
 * There already exists a HTML implementation for each service which can be 
 * used and integrated as a widget into a third party HTML page. The div 
 * container with id = 'content' entails the necessary afore mentioned parts. 
 * 
 * @version 0.1
 */

/**
 * The namespace <b>DAI_SERVICES</b> is used for classes and functions which are 
 * related to the service interfaces of the German Archaeological Institute 
 * (DAI).
 * @namespace 
 */ 
var DAI_SERVICES = DAI_SERVICES || {};

// +++ +++ +++ URIs +++ +++ +++

/** 
 * This class contains only static members and methods which refer to URI's of
 * the Zenon service. It is recommended to use these members and methods in a 
 * static way. <br/>
 * @memberof DAI_SERVICES
 * @class URIsZenon
 */
DAI_SERVICES.URIsZenon = {
	/** 
	 * @property {string} zenonURI The URI to the Zenon service.
	 */
	zenonURI : 'http://zenon.dainst.org:8080/elwms-zenon/',

	/** 
	 * @property {string} uriRegex The ZENON URI as regular expression.
	 */
	// The value '.+' means any char like '*'.
	uriRegex : 'http://zenon.dainst.org/#book/.+',
		
	/**
	 * @property {string} zenonURI as regular expression.
	 */
	zenonURI_Regex : 'http://zenon.dainst.org:8080/elwms-zenon/.+',
}; // end class URIsZenon

/**
 * This class contains only static members and methods which refer to to URI 
 * strings of the Gazetteer service. It is recommended to use these members 
 * and methods in a static way.
 * @memberof DAI_SERVICES
 * @class URIsGazett
 */
DAI_SERVICES.URIsGazett = {
	/**
	 * @property {string} gazettURI The URI to the Gazetteer JSON service.
	 */	
	gazettURI : 'https://gazetteer.dainst.org/search.json?q=',
	
	/**
	 * @property {string} gazettHTML_URI The URI to the Gazetteer HTML service. 
	 */
	gazettHTML_URI : 'https://gazetteer.dainst.org/app/#!/search?q=',
	
	/**
	 * @property {string} gazettHTML_SigleURI The URI to the Gazetteer HTML 
	 * single service. 
	 */
	gazettHTML_SigleURI : 'https://gazetteer.dainst.org/app/#!/show/',
	
	/**
	 * @prperty {string} gazettSingleURI The URI to the Gazetteer service when
	 * only a single item is requested. 
	 */
	gazettSingleURI : 'https://gazetteer.dainst.org/doc/',

	/**
	 * @property {string} uriRegex1 Regular expression of the Gazetteer service.
	 */	
	uriRegex1 : 'http://gazetteer.dainst.org/place/.+',

	/**
	 * @property {string} uriRegex2 Regular expression of the Gazetteer service.
	 */	
	uriRegex2 : 'https://gazetteer.dainst.org/app/#!/show/.+',
	
	/**
	 * @property {string} idRegex Regular expression of the Gazetteer id.
	 */		
	idRegex : /^[0-9]+$/,
	
	/**
	 * This function returns a query string depending on the parameter prefix.
	 * @param {boolean} prefix True if the prefix is necessary else false.
	 * @param {string} queryString The desired query string.
	 * @returns {string} The query string to the Gazetteer search API.
	 */
	queryString : function(prefix, queryString) {
		if(prefix == true) {
			return DAI_SERVICES.URIsGazett.gazettURI + 
						queryString + '&type=prefix';
		} else {
			return DAI_SERVICES.URIsGazett.gazettURI + queryString;
		} // end if else	
	} // end function
}; // end class URIsGazett

/**
 * This class contains only static members and methods which refer to to URI 
 * strings of the Arachne service. It is recommended to use these members 
 * and methods in a static way.
 * @memberof DAI_SERVICES
 * @class URIsArachne
 */
DAI_SERVICES.URIsArachne = {
	/**
	 * @property {string} arachneURI The URI to the Arachne JSON service.
	 */
	arachneURI : "http://arachne.dainst.org/data/search?q=",
	
	/**
	 * @property {string} arachneSingleURI The URI to a single Arachne 
	 * JSON entity. 
	 */
	arachneSingleURI : "http://arachne.dainst.org/data/entity/",
	
	/**
	 * @property {string} arachneHTML_URI The URI to a single HTML Arachne 
	 * entity.
	 */
	arachneHTML_URI : "http://arachne.dainst.org/entity/",
	
	/**
	 * @property {string} uriRegex The Arachne URI as regular expression.
	 */
	uriRegex : "http://arachne.dainst.org/entity/.+", 
}; // end class URIsArachne

/**
 * This class contains only static members and methods. It is a display utility 
 * which collects all functions and variables used to control the display (e.g. 
 * of display components and strings). Override these functions to achieve 
 * different behaviors. It is recommended to use these members and methods in 
 * a static way.
 * @memberof DAI_SERVICES 
 * @class Display
 */
DAI_SERVICES.Display = {
	/** 
	 * @property {integer} titleMaxLength The maximum length of the title to 
	 * be displayed.
	 */
	titleMaxLength : 90,
	
	/** 
	 * @property {integer} authorsMaxLength The maximum length of the authors 
	 * to be displayed.
	 */
	authorsMaxLength : 40,
	
	/**
	 * @property {integer} imprintMaxLength The maximum length of the imprint 
	 * to be displayed.
	 */
	imprintMaxLength : 40,

	/**
     * This function organizes the length of a given string by a variable. If 
	 * there are too many characters, this function will replace them by three 
	 * dots.
	 * @param {string} originString The original string which should be 
	 * tailored.
	 * @param {integer} length The maximum length.
	 * @returns {string} The tailored string which might contain dots.
	 */
	getTailoredString : function(originString, length) {
		if (originString != null && originString.length > length) {
			return originString.substring(0, length - 1) + '...';
		} else {
			return originString;
		} // end if else			
	}, // end function
	
	/**
	 * This function detects the result container by the given source string.
	 * @param {string} source The id of the source which has called this 
	 * function. <br/> These include for example the <b>input fields</b>: 
	 * zenonQuery, arachneQuery and gazettQuery as well as the <b>buttons</b>: 
	 * zenonGo, arachneGo and gazettGo.
	 * @returns {string} If the given source is known, this function returns 
	 * the first part of an id string e.g. zenon, arachne or gazett. If the 
	 * given source is unknown this function throws an alert.
	 */
	getSource : function(source) {
		switch (source) {
		case "zenonSearchHistory" :
		case "zenonResult" :
		case "zenonStore" :
		case "zenonQuery" :
		case "zenonGo" :
		case "zenon" :
			return "zenon";
		case "arachneSearchHistory" :
		case "arachneResult" :
		case "arachneStore" :
		case "arachneQuery" : 
		case "arachneGo" :
		case "arachne" :
			return "arachne";
		case "gazettSearchHistory" :
		case "gazettResult" :
		case "gazettStore" :
		case "gazettQuery" :
		case "gazettGo" :
		case "gazett" :
			return "gazett";
		default:
			alert('Error: undefined source! ' + source);
			return "";
		} // end switch		
	}, // end function
	
	/**
	 * This function is used to display a spinner on screen while the ajax 
	 * query is in progress. Override this function when another <i>in progress
	 * behavior</i> or a different spinner icon is desired.
	 * @param {string} source The id of the source which has called this 
	 * function.
	 */
	inProgress : function(source) {
		var container = DAI_SERVICES.Display.getSource(source) + "Result";
		DAI_SERVICES.Display.clearResultList(container);
		$('#' + container + ' tbody').append(
				'<tr>' + 
					'<td colspan="6" class="emptyRow">' + 
						'<i class="fa fa-spinner fa-spin fa-2x"></i>' +
					'</td>' + 
				'</tr>');
	}, // end function
	
	/**
	 * This function prints a sign on the screen that no results were found.
	 * Override this function when another behavior is desired.
	 * @param {string} source The id of the source which has called this 
	 * function.
	 */
	printNoResults : function(source)  {
		var container = DAI_SERVICES.Display.getSource(source) + "Result";
		DAI_SERVICES.Display.clearResultList(container);
		$('#' + container + ' tbody').append(
			'<tr>' + 
				'<td colspan="6" class="emptyRow">' + 
					DAI_SERVICES.StringsCore.strNoResults + 
				'</td>' + 
			'</tr>');
	}, // end function
	
	/**
	 * This function removes any content out of the result container.
	 * @param {string} resultContainer The id of the result container. 
	 */
	clearResultList : function(resultContainer) {
		$('#' + resultContainer + ' tbody tr').remove();
	}, // end function
	
	/**
	 * This function replaces an empty string by a bar.
	 * @param {string} origin The string which should be examined.
	 * @returns {string} If the given string is not empty, the original string 
	 * is returned. Otherwise a bar is returned.
	 */
	replaceEmptyString : function(origin) {
		if (origin != null && origin.length > 0) {
			return origin;
		} else {
			return '-';
		} // end if else
	}, // end function
	
	/**
	 * This function returns a table cell (table data) for saving an entry
	 * of the result table.
	 * @returns {string} Table cell with save icon.
	 */
	addSaveIcon : function() {
		return '<td>' + 
			   		'<i class="fa fa-floppy-o save pointer" title="' + 
			   			DAI_SERVICES.StringsCore.strSave + '"></i>' +
			   '</td>';
	}, // end function
	
	/**
	 * This function returns a table cell (table data) for deleting an entry
	 * out of the store table.
	 * @returns {string} Table cell with delete icon.
	 */
	addDeleteIcon : function() {
		return '<td title="Eintrag l&ouml;schen">' +
					'<i class="fa fa-trash delete pointer"></i>' +
			   '</td>'; 
	}, // end function
}; // end class Display

/**
 * This class contains only static members and methods which refer to Zenon
 * results retrieved by queries. It is recommended to use these members and 
 * methods in a static way. <br/>
 * <b>Zenon</b> is a library catalog for archaeological research.
 * @memberof DAI_SERVICES
 * @class ResultsZenon
 */
DAI_SERVICES.ResultsZenon = {
	
	/**
     * This function returns an icon tag which is related to the resource type.
	 * @param {string} resourceType The resource type of the image.
	 * @returns {string} The icon plus the title by the given resource type.
	 */
	getZenonIconTypeString : function(resourceType) {
		var iconTag = '';
		switch (resourceType) {
		case 'eResource':
			iconTag = '<i class="fa fa-download cursorDefault" ';
			break;
		case 'bookResource':
			iconTag = '<i class="fa fa-book cursorDefault" ';
			break;
		default:
			iconTag = '<i class="fa fa-file-o cursorDefault" ';
		} // end switch case
		return iconTag += 'title="Typ: ' + resourceType + '"></i>';
	}, // end function

	/**
	 * This function returns an image tag and a span tag for the authors 
	 * column. The image is used to query by author. 
	 * @param {string} author The author of the related publication (when 
	 * unknown it could be null).
	 * @param {string} editor The editor of the related publication (when 
	 * unknown it could be null). 
	 * @returns {string} A string which contains either the author or the
	 * editor. When both are unknown (both are null) a bar is returned.
	 */
	getZenonAuthors : function(author, editor) {
		if (author != null) {
			return '<i class="fa fa-search searchImg pointer"' +
			       '   data-subject="' + author + '" title="' + 
			       DAI_SERVICES.StringsZenon.strSearchAuthor + ' ' + 
			       author + '"></i>&nbsp;' +
			       '<span title="Autor(en): ' + author +'">' 
				   		+ DAI_SERVICES.Display.getTailoredString(author, 
				   				DAI_SERVICES.Display.authorsMaxLength) + 
				   '</span>';
		} else if (editor != null) {
			return '<i class="fa fa-search searchImg pointer"' +
				   '   data-subject="' + editor + '" title="' + 
			       DAI_SERVICES.StringsZenon.strSearchEditor + ' ' + 
			       editor + '"></i>&nbsp;' + 
			       '<span title="Editor(en): ' + editor +'">' 
						+ DAI_SERVICES.Display.getTailoredString(editor, 
								DAI_SERVICES.Display.authorsMaxLength) + 
				   '</span>';
		} else {
			return '-';
		} // end if else
	}, // end function
		
	/**
	 * This function appends a JSON entry as table row to the result table.
	 * If the title and the authors are null, this entry will not be
	 * displayed. You should override this function if you would like to use 
	 * another presentation of the data (e.g. div container than table layout). 
	 * @param {string} entry The JSON entry.
	 */
	appendZenonResult : function(entry) {
		if(entry.title != null && 
				DAI_SERVICES.ResultsZenon.getZenonAuthors(entry.author, 
						entry.editor) != '-') {
			$('#zenonResult tbody').append(
				'<tr id=' + entry.id + '>' +
					'<td class="center">' + 
						DAI_SERVICES.ResultsZenon
							.getZenonIconTypeString(entry.resourceType) + 
					'</td>' + 
					'<td title="Imprint: ' + 
						DAI_SERVICES.Display
							.replaceEmptyString(entry.imprint) + '">' + 
						DAI_SERVICES.Display
							.replaceEmptyString(entry.imprint) + 
					'</td>' +
					'<td>' + 
						DAI_SERVICES.ResultsZenon
							.getZenonAuthors(entry.author, entry.editor) + 
					'</td>' +
					'<td title="' + entry.title + '">' +
						'<a href="http://zenon.dainst.org/#book/' + 
							entry.id + '" target="_blank" title="' +
							DAI_SERVICES.StringsZenon.strOpenLinkZenon + ': ' +
							entry.title + '">' +
							'<i>' + 
								DAI_SERVICES.Display
									.getTailoredString(entry.title, 
											DAI_SERVICES.Display
												.titleMaxLength) + 
							'</i>' +
						'</a>' +
					'</td>' +
					DAI_SERVICES.Display.addSaveIcon() +
				'</tr>');			
		} // end if
	}, // end function
	
	
	/**
	 * This function appends a JSON entry as table row to the store table. 
	 * @param {string} entry The JSON entry.
	 */
	appendZenonStore : function(entry) {
		$('#zenonStore tbody').append(
				'<tr id=' + entry.id + '>' + 
					'<td class="center">' + 
						DAI_SERVICES.ResultsZenon
							.getZenonIconTypeString(entry.resourceType) + 
					'</td>' + 
					'<td title="Imprint: ' + 
						DAI_SERVICES.Display
							.replaceEmptyString(entry.imprint) + '">' + 
						DAI_SERVICES.Display.getTailoredString(
							DAI_SERVICES.Display.replaceEmptyString(
								entry.imprint),	
									DAI_SERVICES.Display.imprintMaxLength) + 
					'</td>' +
					'<td>' + 
						DAI_SERVICES.ResultsZenon
							.getZenonAuthors(entry.author, entry.editor) + 
					'</td>' +
					'<td title="' + entry.title + '">' +
						'<a href="http://zenon.dainst.org/#book/' + 
							entry.id + '" target="_blank" title="' +
							DAI_SERVICES.StringsZenon.strOpenLinkZenon + ': ' +
							entry.title + '">' +
							'<i>' + 
								DAI_SERVICES.Display
									.getTailoredString(entry.title, 
											DAI_SERVICES.Display
												.titleMaxLength) + 
							'</i>' +
						'</a>' +
					'</td>' +
					'<td>' + 
					DAI_SERVICES.ResultsZenon.getGazetteerSubject(entry) + 
					'</td>' + 					
					DAI_SERVICES.Display.addDeleteIcon() +
				'</tr>');
	}, // end function
	
	/**
	 * This function returns a Gazetteer subject of an entry when present. 
	 * Examples are the ids: 000925589, 000996914
	 * @param {string} entry The JSON entry.
	 */
	getGazetteerSubject : function(entry) {
		var gazett = '';

		if (entry.subjects != null) {
			entry.subjects.forEach(function(subject) {
				if (subject.gazetteer != null) {
					gazett += '<span>' + 
								'<i class="fa fa-search searchImg pointer"' +
					            ' data-subject="' + subject.subjectKey + '"' +
					            ' title="' + DAI_SERVICES.StringsGazett
					            				.strSearchGazettKey	
					            		   + subject.subject + ' (' 
					            		   + subject.subjectKey + ')" ></i>' +
								'&nbsp;' + 
								'<a href="' + DAI_SERVICES.URIsGazett
												.gazettHTML_URI + 
												subject.subjectKey + 
									'" target="_blank" ' + 'title="' 
									+ DAI_SERVICES.StringsGazett
										.strOpenGazettKey
									+ subject.subject  + '">'
									+ subject.subject + 
								'</a>' + 
							  '</span><br/>';
				} // end if
			}); // end for each
		} // end if

		if (gazett == '') {
			return '-';
		} else {
			return gazett;
		} // end if else
	} // end function
}; // end class ResultsZenon

/**
 * This class contains only static members and methods which refer to Arachne
 * results retrieved by queries. It is recommended to use these members and 
 * methods in a static way. <br/>
 * <b>Arachne</b> is a digital image and object archive.
 * @memberof DAI_SERVICES
 * @class ResultsArachne
 */
DAI_SERVICES.ResultsArachne = {
	/**
	 * This function returns the title or subtitle of a given entry.
	 * @param {string} entry The given entry.
	 * @return {string} The title or the subtitle of the given entry.
	 */
	getTitle : function(entry) {
		var value = '';
		if(entry.title == '') {
			value = entry.subtitle;
		} else {
			value = entry.title;
		} // end if else
		return DAI_SERVICES.Display.getTailoredString(value, 
				DAI_SERVICES.Display.titleMaxLength); 
	}, // end function
	
	/**
	 * This function returns an icon of the desired resource type. Currently, a
	 * switch case statement is used. The usage of a key value store is also 
	 * possible in the future.
	 * @param {string} resourceType The type of the desired resource.
	 * @return {string} Font Awesome tag with specified icon.
	 */
	getIcon : function(resourceType) {
		switch(resourceType) {
			case 'Personen': 
				return '<i class="fa fa-male"></i>';
			case 'Orte': 
				return '<i class="fa fa-globe"></i>';
			case 'B�cher': 
				return '<i class="fa fa-book"></i>';
			case 'Einzelobjekte': 
				return '<i class="fa fa-cogs"></i>';
			case 'Inschriften': 
				return '<i class="fa fa-bars"></i>';
			case 'Literatur': 
				return '<i class="fa fa-newspaper-o"></i>';
			case 'Buchseiten': 
				return '<i class="fa fa-files-o"></i>';
			case 'Szenen': 
				return '<i class="fa fa-file-image-o"></i>';
			case 'Bauwerke':
				return '<i class="fa fa-university"></i>';
			default: 
				return '<i class="fa fa-file-o"></i>';
		} // end switch case
	}, // end function
		
	/**
	 * This function appends a JSON entry as table row to the result table.
	 * @param {string} entry The JSON entry to append.
	 */	
	appendArachneResult : function(entry) {
		$('#arachneResult tbody').append(
			'<tr id="' + entry.entityId + '">' +
				'<td class="center" title="' + entry.type + '">' + 
					DAI_SERVICES.ResultsArachne.getIcon(entry.type) +
				'</td>' + 
				'<td>' +
					entry.type +
				'</td>' +
                '<td title="Link in Arachne &ouml;ffnen: (' + 
                	DAI_SERVICES.Display.replaceEmptyString(entry.subtitle) + 
                		') ' + entry.title +'">' + 
                	'<a href="' + DAI_SERVICES.URIsArachne.arachneHTML_URI +
                					entry.entityId + '" target="_blank">' + 
                		DAI_SERVICES.Display.replaceEmptyString( 
                				DAI_SERVICES.ResultsArachne.getTitle(entry)) + 
                	'</a>' + 
                '</td>' + 
                DAI_SERVICES.Display.addSaveIcon() +
			'</tr>');
	}, // end function
	
	/**
	 * This function appends a JSON entry as table row to the store table.
	 * @param {string} entry The JSON entry to append.
	 */
	appendArachneStore : function(entry) {
		$('#arachneStore tbody').append(
				'<tr id="' + entry.entityId + '">' +
					'<td class="center" title="' + entry.type + '">' + 
						DAI_SERVICES.ResultsArachne.getIcon(entry.type) +
					'</td>' + 
					'<td>' +
						entry.type +
					'</td>' +
	                '<td title="Link in Arachne &ouml;ffnen: (' + 
                	DAI_SERVICES.Display.replaceEmptyString(entry.subtitle) + 
                		') ' + entry.title +'">' + 
                		'<a href="' + DAI_SERVICES.URIsArachne.arachneHTML_URI +
                						entry.entityId + '" target="_blank">' + 
                			DAI_SERVICES.Display.replaceEmptyString( 
                				DAI_SERVICES.ResultsArachne.getTitle(entry)) + 
                		'</a>' + 
                	'</td>' +  
	                DAI_SERVICES.Display.addDeleteIcon() +
				'</tr>');
	}, // end function
}; // end class ResultsArachne

/**
 * This class contains only static members and methods which refer to Gazetteer
 * results retrieved by queries. It is recommended to use these members and 
 * methods in a static way. <br/>
 * <b>Gazetteer</b> is a geographical dictionary. Usually, a Gazetteer also 
 * provides a map. In this case the map is not implemented since this 
 * implementation focusses on the search results and integration into another
 * website. If the map feature is required it is recommented to use the original
 * Gezetteer website and to copy and pasete search results (URL or id).
 * @memberof DAI_SERVICES
 * @class ResultsGazett
 */
DAI_SERVICES.ResultsGazett = {
	
	/**
	 * This function returns the parent id of a search result.
	 * @param {string} parent The parent in JSON format retrieved by a query.
	 * @returns {string} The associated parent id.
	 */
	getParentId : function(parent) {
		if(parent != null) {
			return parent.substring(34);
		} else {
			return null;
		} // end if else
	}, // end function
	
	/**
	 * This function implements another ajax query in order to retrieve the
	 * parent of a specific geographical location. This is done because there
	 * is only the parent id as information about the parant in the result.
	 * In order to retrieve full information about the parent it is necessary
	 * to query the Gazetteer service again with this id.
	 * @param {string} parentId The id of the parent geographical location.
	 * @returns {string} HTML string including an icon plus a hyperlink to the
	 * Gezetteer service.
	 */
	getParent : function(parentId) {
		if(parentId != null) {
			$.ajax({
				url: DAI_SERVICES.URIsGazett.gazettSingleURI + 
						parentId + '.json',
				cache: false,
				error : function() {
					return '-';
				} // end anonymous error function
			}).done(function(jresult) { 
				$('#parent' + parentId).html(
						'<i class="fa fa-search searchImg pointer"' +
					       ' data-subject="' + jresult.prefName.title + 
					       ' " title="Suche nach Ort: ' + 
					       jresult.prefName.title + '"></i>&nbsp;' +
						'<a href="' +
							DAI_SERVICES.URIsGazett.gazettHTML_SigleURI + 
							parentId + '"  target="_blank" title="' + 
							DAI_SERVICES.StringsGazett.strOpenGazettKey + 
							jresult.prefName.title + '">' + 
							jresult.prefName.title + 
						'</a>'); 
				$("#gazettResult").trigger("update");
			}); // end done
		} // end if else
		return '-';
	}, // end function

	/**
	 * This functio is used to appaned a Gazetteer result as table row to the 
	 * result table.
	 * @param {string} entry The JSON entry.
	 */
	appendGazettResult : function(entry) {
		var parent = DAI_SERVICES.ResultsGazett.getParentId(entry.parent);
		$('#gazettResult tbody').append(
				'<tr id=' + entry.gazId + '>' +
		          '<td class="center" title="Ort">' +
		          	'<i class="fa fa-globe"></i>' + 
		          '</td>' + 
		          '<td>' +
				  	'<a href="' + DAI_SERVICES.URIsGazett.gazettHTML_SigleURI + 
				  		entry.gazId + '" target="_blank" title="' + 
				  		DAI_SERVICES.StringsGazett.strOpenGazettKey +
				  		entry.prefName.title + '">' +
		          		entry.prefName.title + 
					'</a>' +
		          '</td>' + 
				  '<td id="parent' + parent + '">' + 
				  		DAI_SERVICES.ResultsGazett.getParent(parent) +
				  '</td>' + 
				  DAI_SERVICES.Display.addSaveIcon() +
				 '</tr>');
	}, // end function
	
	/**
	 * This function is used to append a result as table row to the store table.
	 * @param {string} entry The entry as JSON string.
	 */
	appendGazettStore : function(entry) {
		var parent = DAI_SERVICES.ResultsGazett.getParentId(entry.parent);
		$('#gazettStore tbody').append(
				'<tr id=' + entry.gazId + '>' +
		          '<td class="center" title="Ort">' +
		          	'<i class="fa fa-globe"></i>' + 
		          '</td>' +  
		          '<td>' +
				  	'<a href="' + DAI_SERVICES.URIsGazett.gazettHTML_SigleURI + 
				  		entry.gazId + '" target="_blank" title="' + 
				  		DAI_SERVICES.StringsGazett.strOpenGazettKey +
				  		entry.prefName.title + '">' +
		          		entry.prefName.title + 
					'</a>' +
		          '</td>' + 
				  '<td id="parent' + parent + '">' + 
				  		DAI_SERVICES.ResultsGazett.getParent(parent) +
				  '</td>' +
				  DAI_SERVICES.Display.addDeleteIcon() +
				'</tr>');
	} // end function
}; // end ResultsGazett

/**
 * This class contains only static members and methods which refer to ajax 
 * queries. It is recommended to use these members and methods in a static way.
 * @memberof DAI_SERVICES
 * @class Ajax
 */
DAI_SERVICES.Ajax = {
	/** 
	 * @property {integer} minCharLength The minimum length of a
	 * character string to start a query.
	 */
	minCharLength : 2,
	
	/**
	 * This function returns the search url by means of the given source.
	 * @param {string} source The id of the function caller.
	 * @returns {string} The url destination.
	 */
	getUrlBySource : function(source) {
		switch (DAI_SERVICES.Display.getSource(source)) {
		case "zenon":
			return DAI_SERVICES.URIsZenon.zenonURI + 
					'search?q=' + $("#zenonQuery").val();
		case "arachne":
			return DAI_SERVICES.URIsArachne.arachneURI + 
					$("#arachneQuery").val();
		case "gazett":
			if($("#gazettQuery").val()
					.match(DAI_SERVICES.URIsGazett.uriRegex1)) {
				// 1. Remove white space characters.
				var queryString = $("#gazettQuery").val().replace(/\s/g, '');
				// 2. Extract the id of the given url.
				queryString = queryString.substring(34);
				// 3. Lastly, return the query url.
				return DAI_SERVICES.URIsGazett.queryString(false, queryString);
			} else if($("#gazettQuery").val()
					.match(DAI_SERVICES.URIsGazett.uriRegex2)) {
				// Extract the id of the given url and return the string.
				return DAI_SERVICES.URIsGazett.queryString(false, 
						$("#gazettQuery").val().substring(41));
			} else if($("#gazettQuery").val()
					.match(DAI_SERVICES.URIsGazett.idRegex)) {
				return DAI_SERVICES.URIsGazett.queryString(false, 
						$("#gazettQuery").val());
			} else {
				return DAI_SERVICES.URIsGazett.queryString(true, 
						$("#gazettQuery").val());
			} // end if else
		default:
			alert('Error (getUrlBySource): source unknown: ' + source + '!');
			return "";
		} // end switch		
	}, // end function
	
	/**
	 * This function returns the URI of a resource when only one sigle item is
	 * requested.
	 * @param {integer} itemId The id of the desired item.
	 * @param {string} source The source of the object which invoked this 
	 * method.
	 * @returns {string} The URI of a single resource.
	 */
	getSingleUrlBySource : function(itemId, source) {
		switch (DAI_SERVICES.Display.getSource(source)) {
		case "zenon":
			return DAI_SERVICES.URIsZenon.zenonURI + 'resource/' + itemId + 
						'?format=standard';
		case "arachne":
			return DAI_SERVICES.URIsArachne.arachneSingleURI + itemId;
		case "gazett":
			return DAI_SERVICES.URIsGazett.gazettSingleURI + itemId + '.json';
		default:
			alert('Error (getSingleUrlBySource): source unknown: ' + 
					source + '!');
			return "";
		} // end switch		
	}, // end function
	
	/**
	 * This function returns the data type by means of the given source.
	 * @param {string} source The id of the source which has called this 
	 * function.
	 * @returns {string} The data type which is expected by the search.
	 */
	getDataTypeBySource : function(source) {
		switch (DAI_SERVICES.Display.getSource(source)) {
		case "zenon" :
			return "jsonp";
		case "arachne" :
		case "gazett" :
			return "json";
		default :
			return "json";
		} // end switch		
	}, // end function
	
	/**
	 * This function releases an ajax query to one of the DAI services (defined
	 * by the source parameter) and handles the success and error cases. This 
	 * function does not start when the search string is less then a defined 
	 * value {@link DAI_SERVICES.Ajax.minCharLength}.
	 * @param {string} source The id of the source which has called this 
	 * function.
	 */
	exeAjaxQuery : function(source) {		
		// 1. Step: The search wont work with strings shorter than 1.
		var container = DAI_SERVICES.Display.getSource(source);
		var queryVal = $("#" + container + "Query").val(); 
		if(queryVal.length < DAI_SERVICES.Ajax.minCharLength) {
			DAI_SERVICES.Display.printNoResults(source);
			return;
		} // end if
		
		// 2. Step: Store the current search
		switch (container) {
		case 'zenon':
			DAI_SERVICES.Ajax.searchZenon = queryVal; 
			break;
		case 'arachne':
			DAI_SERVICES.Ajax.searchArachne = queryVal;
			break;
		case 'gazett':
			DAI_SERVICES.Ajax.searchGazetteer = queryVal;
			break;
		} // end switch case
		
		// 3. Step: Clear the result list which relates to the source and 
		// add a spinner to show the user that the search is in progress.
		DAI_SERVICES.Display.inProgress(source);

		// 3. Step: Execute the query.
		$.ajax({
			url : encodeURI(DAI_SERVICES.Ajax.getUrlBySource(source)),
			dataType : DAI_SERVICES.Ajax.getDataTypeBySource(source),
			type : "GET",
			cache : false,
			crossDomain : true,
			success : function(msg) {
				DAI_SERVICES.Display.clearResultList(
						DAI_SERVICES.Display.getSource(source) + "Result");
			}, // end anonymous inner function
			error : function(msg) {
				/* 
				 * Throw an alert if something goes wrong. This error message
				 * could also be a short message in the result table.
				 */ 
				alert("An error has occured: Status: " + result.status +
					  " -- Source: " + source +
					  " -- Status Text: " + result.statusText	+ 
					  " -- Error Result: " + result); 
			} // end anonymous inner function
		}).done(function(jresult) {
			var src = DAI_SERVICES.Display.getSource(source);
			switch (src) {
			case "zenon":
				if(jresult.data != null) {
					jresult.data.forEach(function(entry) {
						DAI_SERVICES.ResultsZenon.appendZenonResult(entry);
					}); // end for each					
				} else {
					DAI_SERVICES.Display.printNoResults(source);
				} // end if else
				break;
			case "arachne":
				if(jresult.entities != null) {
					jresult.entities.forEach(function(entry) {
						DAI_SERVICES.ResultsArachne.appendArachneResult(entry);
					}); // end for each
				} else {
					DAI_SERVICES.Display.printNoResults(source);
				} // end if else
				break;
			case "gazett":
				if(jresult.total != '0') {
					jresult.result.forEach(function(entry) {
						DAI_SERVICES.ResultsGazett.appendGazettResult(entry);
					}); // end for each
				} else {
					DAI_SERVICES.Display.printNoResults(source);
				} // end if else
				break;
			default :
				alert("Error: source was not found!");
			} // end switch
			
			$("#" + src + "Result").trigger("update");
			DAI_Services_SearchHistoryZenon.addQuery(src + "Query");
		}); // end done
	}, // end function

	/**
	 * This function executes an ajax query when only a single item is 
	 * requested.
	 * @param {integer} itemId The id of the desired item. 
	 * @param {string} source The source of the object which invoked this query.
	 */
	exeAjaxSingleItem : function(itemId, source) {
		$.ajax({
			url : encodeURI(DAI_SERVICES.Ajax.getSingleUrlBySource(itemId, 
					source)),
			dataType : DAI_SERVICES.Ajax.getDataTypeBySource(source),
			type : "GET",
			cache : false,
			crossDomain : true,
			success : function(msg) {
				// Do nothing! Placeholder for future functions.
			}, // end anonymous inner function
			error : function(msg) {
				/* 
				 * Throw an alert if something goes wrong. This error message
				 * could also be a short message in the result table.
				 */ 
				alert("An error has occured: Status: " + result.status +
					  " -- Source: " + source +
					  " -- Status Text: " + result.statusText	+ 
					  " -- Error Result: " + result); 
			} // end anonymous inner function
		}).done(function(jresult) {
			switch (DAI_SERVICES.Display.getSource(source)) {
			case "zenon":
				DAI_SERVICES.ResultsZenon.appendZenonStore(jresult.data);
				break;
			case "arachne":
				DAI_SERVICES.ResultsArachne.appendArachneStore(jresult);
				break;
			case "gazett":
				DAI_SERVICES.ResultsGazett.appendGazettStore(jresult);
				break;
			default :
				alert("Error: source were not found!");
			} // end switch
		});
	} // end function
};

/**
 * This is a dynamic class which should be used by the <b>new operator</b>. It 
 * is simple utility for displaying history queries. The <i>Document.ready</i>
 * function initializes a single search history object in order to provide a 
 * dedicated object for each search history. These include:
 * <ul>
 * 	<li>DAI_Services_SearchHistoryZenon</li>
 *  <li>DAI_Services_SearchHistoryArachne</li>
 *  <li>DAI_Services_SearchHistoryGazetteer</li>
 * </ul>
 *
 * @memberof DAI_SERVICES
 * @class SearchHistory
 */
DAI_SERVICES.SearchHistory = function() {
	/** 
	 * @property {integer} maxQueries Defines the maximum number of history 
	 * queries. Change this value if a different value is desired.
	 */
	this.maxQueries = 2;
	
	/**
	 * @property {string[]} historyQueries Array to hold queries of the past. 
	 */
	this.historyQueries = new Array(this.maxQueries);
	
	/**
	 * @property {integer} queryCounter Counts the history queries. 
	 */
	this.queryCounter = 0;
}; // end search history

/**
 * This function is a very simple implementation of a search history. It is a
 * prototype function and belongs to the search history class and is member of
 * this dynamic class.
 * @param {string} source The source of this query (e.g. Zenon, Arachne or
 * Gazetteer).
 */
DAI_SERVICES.SearchHistory.prototype.addQuery = function(source) {
	var query = $('#' + source).val();
	// Does this query string already exist?
	for (var i = 0; i < this.historyQueries.length; i++) {
		if (this.historyQueries[i] == query) {
			return;
		} // end if
	} // end for

	// implement a simple ring buffer
	if (this.queryCounter < this.maxQueries) {
		this.queryCounter++;
	} else {
		this.queryCounter = 0;
	} // end if else

	// add the new query to the desired bucket
	this.historyQueries[this.queryCounter] = query;
	var strSearchHistory = '';

	// refresh new search history
	for (var j = 0; j < this.historyQueries.length; j++) {
		if (this.historyQueries[j] != null) {
			strSearchHistory += '<span class="searchHistory pointer" '
					+ 'title="Suche starten: ' + this.historyQueries[j] + '" '
					+ 'data-subject="' + this.historyQueries[j] + '">'
					+ this.historyQueries[j] + '</span>' + '&nbsp;';
			if ((j + 1) < this.historyQueries.length && 
					this.historyQueries[j] != null) {
				strSearchHistory += '|&nbsp;';
			} // end if
		} // end if
	} // end for

	// clear search history and append new string
	source = DAI_SERVICES.Display.getSource(source);
	$("#" + source + "SearchHistory").empty();
	$("#" + source + "SearchHistory").append(strSearchHistory);
}; // end prototype function

// +++ +++ +++ Strings +++ +++ +++

/**
 * This class contains only static members and methods which refer core strings.
 * This might be replaced by a more sophisticated i18n approach. It is 
 * recommended to use these members and methods in a static way.  
 * @memberof DAI_SERVICES
 * @class StringsCore
 */
DAI_SERVICES.StringsCore = {
		/** 
		 * @propterty {string} strNoResults A string which is displayed when no
		 * results were found. 
		 */
		strNoResults : 'Die Suche ergab leider keine Treffer.',
		
		/** 
		 * @propterty {string} strDelete A string for delete buttons.
		 */
		strDelete : 'L&ouml;schen',
		
		/**
		 * @propterty {string} strSave A string for save buttons. 
		 */
		strSave : 'Speichern'		
};

/**
 * This class contains only static members and methods which refer to the Zenon 
 * service. This might be replaced by a more sophisticated i18n approach. It is 
 * recommended to use these members and methods in a static way.
 * @memberof DAI_SERVICES
 * @class StringsZenon
 */
DAI_SERVICES.StringsZenon = {
	/** 
	 * @propterty {string} strSearchInfo A string which holds information about
	 * the search modalities.
     */
	strSearchInfo : 'Suche starten: Freitext, Systemnummer, ISBN, '
			+ 'ZENON-URI oder Gazetteer-Schl�ssel eingeben.',
			
	/** 
	 * @propterty {string} strOpenLinkZenon A string for opening a link to the 
	 * Zenon service. 
	 */
	strOpenLinkZenon : 'Link im Bibliothekskatalog Zenon &ouml;ffnen',
	
	/** 
	 * @propterty {string} strSearchAuthor A string for search by author. 
	 */
	strSearchAuthor : 'Suche nach Autor: ',
	
	/** 
	 * @propterty {string} strSearchEditor A string for search by editor. 
	 */
	strSearchEditor : 'Suche nach Editor: '		
};

/**
 * This class contains only static members and methods which refer to the 
 * Gazetteer service. This might be replaced by a more sophisticated i18n 
 * approach. It is recommended to use these members and methods in a static way.
 * @memberof DAI_SERVICES
 * @class StringsGazett
 */
DAI_SERVICES.StringsGazett = {
	/** 
	 * @propterty {string} strSearchInfo A string for search information. 
	 */
	strSearchInfo : 'Suche starten: Freitext, URL oder Gazetteer-Id.',
	
	/** 
	 * @propterty {string} strSearchGazettKey A string for search by location. 
	 */
	strSearchGazettKey : 'Literatur nach Ort suchen: ',
	
	/** 
	 * @propterty {string} strOpenGazettKey A string for opening a link to the 
	 * Gazetteer service 
	 */
	strOpenGazettKey : 'Ort im Gazetteer &ouml;ffnen: '
};

/**
 * This class contains only static members and methods which refer to the 
 * Arachne service. This might be replaced by a more sophisticated i18n 
 * approach. It is recommended to use these members and methods in a static way.
 * @memberof DAI_SERVICES
 * @class StringsArachne
 */
DAI_SERVICES.StringsArachne = {
	/**
	 * @propterty {string} strSearchInfo A string for search information.
	 */
	strSearchInfo : 'Suche starten: Freitext, URL oder Arachne-Id.'
};

// +++ +++ +++ Document stuff +++ +++ +++

// Objects to store the search history.
var DAI_Services_SearchHistoryZenon = '';
var DAI_Services_SearchHistoryArachne = '';
var DAI_Services_SearchHistoryGazetteer = '';

var DAI_Services_Display_Query = "#zenonQuery, #gazettQuery, #arachneQuery";

// On document ready
$(document).ready(function() {	
	init();
}); // end document ready

init = function() {
	// Instantiate all search history objects. I don't care if they are really 
	// needed. This might be improved in the future. However, for each service
	// one dedicated object is requred.
	DAI_Services_SearchHistoryZenon = new DAI_SERVICES.SearchHistory();
	DAI_Services_SearchHistoryArachne = new DAI_SERVICES.SearchHistory(); 
	DAI_Services_SearchHistoryGazetteer = new DAI_SERVICES.SearchHistory();
	
	// Set autofocus on all input fields. Unfortionetly this doesn't work for
	// the Bootstrp modal windows (why?).
	$(DAI_Services_Display_Query).focus();

	// Set title tag of search buttons and info images.
	$('#zenonGo').attr('title', DAI_SERVICES.StringsZenon.strSearchInfo);
	$('#zenonInfoImg').attr('title', DAI_SERVICES.StringsZenon.strSearchInfo);
	
	$('#gazettGo').attr('title', DAI_SERVICES.StringsGazett.strSearchInfo);
	$('#gazettInfoImg').attr('title', DAI_SERVICES.StringsGazett.strSearchInfo);
	
	$('#arachneGo').attr('title', DAI_SERVICES.StringsArachne.strSearchInfo);
	$('#arachneInfoImg').attr('title', DAI_SERVICES.StringsArachne.strSearchInfo);

	// Enable tablesorter for the Zenon service.
	$("#zenonResult").tablesorter({
		// Pass the headers argument and set the property 'sorter'
		// to false (counting starts at zero).
		headers : {
			0 : {sorter : false},
			4 : {sorter : false},
			5 : {sorter : false}
		}
	}); // end enable tablesorter
	
	// Enable tablesorter for the Arachne service.
	$("#arachneResult").tablesorter({
		// Pass the headers argument and set the property 'sorter'
		// to false (counting starts at zero).
		headers : {
			0 : {sorter : false},
			3 : {sorter : false}
		}
	}); // end enable tablesorter
	
	// Enable tablesorter for the Gazetter service.
	$("#gazettResult").tablesorter({
		// Pass the headers argument and set the property 'sorter'
		// to false (counting starts at zero).
		headers : {
			0 : {sorter : false},
			3 : {sorter : false}
		}
	}); // end enable tablesorter
	
	// +++ +++ +++ jQuery Stuff here +++ +++ +++
	
	// Release ajax query when input has changed.
	$(DAI_Services_Display_Query).on("input", function() {		
		// The time im milliseconds to wait until the function is executetd.
		var wait = 550;
		
		// Store the original string detected after input has changed.
		var cont = $(this).val();

		// Check the lenght of the search query.
		if ($(this).val().length < DAI_SERVICES.Ajax.minCharLength) { 
			return;
		} // end if
		
		// Simulate a debouncing method with setTimeout. This is used because
		// the current jQuery framework does not supports this debounce or
		// throttle function. In order to keep the caller id it is necessary
		// to store it before the timeout function is called.
		var objId = $(this).attr("id");
		setTimeout(function() {
			// Exit this function when the input has changed.
			if($('#' + objId).val() != cont) {
				return;
			}
		
			// Execute some extras for each service when necessary
			switch (DAI_SERVICES.Display.getSource(objId)) {
			case "zenon" : 
				// If the value is an URL
				if ($('#' + objId).val().match(
						DAI_SERVICES.URIsZenon.uriRegex)) {
					// Extract the id of the given url.
					$('#' + objId).val($('#' + objId).val().substring(30));
				} else if($('#' + objId).val().match(
						DAI_SERVICES.URIsZenon.zenonURI_Regex)) {
					$('#' + objId).val(
							$('#' + objId).val().substring(50));
					if($('#' + objId).val().indexOf("?") > -1) {
						$('#' + objId).val(
								$('#' + objId).val().split('?')[0]);						
					} else if($('#' + objId).val().indexOf(".") > -1) {
						$('#' + objId).val(
								$('#' + objId).val().split('.')[0]);
					}
				} // end if else
				break;
			case "arachne" :
				if ($('#' + objId).val().match(
						DAI_SERVICES.URIsArachne.uriRegex)) {
					$('#' + objId).val($('#' + objId).val().substring(33));
					if($('#' + objId).val().indexOf("?") > -1) {
						$('#' + objId).val(
								$('#' + objId).val().split('?')[0]);
					} // end if
				} // end if
			} // end switch
			// call the ajax query 	
			DAI_SERVICES.Ajax.exeAjaxQuery(objId);
		}, wait); // end setTimeout
				
	}); // end keyup
	
	// Release ajax query when keyboard button 'return' was pressed.
	$(DAI_Services_Display_Query)
					.on("keypress", function(event) {
		if (event.which == '13') {
			DAI_SERVICES.Ajax.exeAjaxQuery($(this).attr("id"));
		} // end if
	}); // end key pressed event
	
	// Release ajax query when the go button was clicked.
	$("#zenonGo, #arachneGo, #gazettGo").click(function() {
		alert('test');
		DAI_SERVICES.Ajax.exeAjaxQuery($(this).attr("id"));
	}); // end click event
	
	// +++ +++ +++ Change color when mouse over +++ +++ +++

	var DAI_Services_Display_Results = 
		'#zenonResult tbody, #arachneResult tbody, #gazettResult tbody';
	var DAI_Services_Display_Store =
		'#zenonStore tbody, #arachneStore tbody, #gazettStore tbody';
	
	// Set color of results when mouse over.
	$(DAI_Services_Display_Results).on("mouseover", "tr", function(event) {
		$(this).css("background-color", "#D3D3D3");
	});

	// Set color of results when mouse out.
	$(DAI_Services_Display_Results).on("mouseout", "tr", function(event) {
		$(this).css("background-color", "white");
	});

	// Set color of store when mouse over.
	$(DAI_Services_Display_Store).on("mouseover", "tr", function(event) {
		$(this).css('background-color', '#D3D3D3');
	});

	// Set color of store when mouse out.
	$(DAI_Services_Display_Store).on("mouseout", "tr", function(event) {
		$(this).css('background-color', 'white');
	});

	// +++ +++ +++ Manage content +++ +++ +++

	// Delete result and append in store.
	$(DAI_Services_Display_Results).on("click", ".save", function(event) {
		DAI_SERVICES.Ajax.exeAjaxSingleItem(
				$(this).closest("tr").attr("id"), 
				$(this).closest("table").attr('id'));
		$(this).closest("tr").remove();
	});

	// Delete entry from store.
	$(DAI_Services_Display_Store).on("click", ".delete", function(event) {
		$(this).closest("tr").remove();
	});

	// Search by a dynamic value. Replace the value of the search field 
	// by the given subject string and release ajax query.
	$(DAI_Services_Display_Results + ", " + DAI_Services_Display_Store)
		.on("click", ".searchImg", function(event) {
		var source = DAI_SERVICES.Display
						.getSource($(this).closest("table").attr("id"));
		$("#" + source + "Query").val($(this).closest("i").attr('data-subject'));
		DAI_SERVICES.Ajax.exeAjaxQuery(source);
	});

	// Use the search history.
	$("body").on('click', ".searchHistory", function() {
		var source = DAI_SERVICES.Display
							.getSource($(this).parent().attr("id"));
			$("#" + source + "Query").val($(this).closest('span')
					.attr('data-subject'));
			DAI_SERVICES.Ajax.exeAjaxQuery(source);
	});

};