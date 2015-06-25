//globale Variablendeklaration
var $element;
var tt;
var farbSpeicher;
var materialSpeicher;
var projects;
var topicCharacteristics;
//var zoomed = false;



//Initialisierung, Zugriff auf X3D-Element; wird beim Laden der Seite aufgerufen
function init() {
	$element = document.getElementById('x3d');
	addEvent($element,"mousemove",saveMouseCoords, true);
	tt = document.getElementById("tooltip");
	farbSpeicher = null;
	materialSpeicher = null;
	var xml;
	var proj;
	var projectName;
	var projectUuid;
	var formAuswahl = document.getElementById("projekte");
	
	var xmlhttp;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4){
			if(xmlhttp.status==200){
				
				xml = xmlhttp.responseXML.documentElement;
				
				proj = xml.getElementsByTagName("projectPojo");
				//alert(proj.length);
				//alert(proj[1].nodeName);
				
				projects = new Array(proj.length);
				for(i=0; i<proj.length; i++){
					projectName = proj[i].getElementsByTagName('names')[0].getElementsByTagName('localizedStrings')[0].getElementsByTagName('characterString')[0].textContent;
					projectUuid = proj[i].getElementsByTagName('uuid')[0].textContent;
					//alert(projectName);
					projects[i] = new Array();
					projects[i]["project"] = projectName;
					projects[i]["uuid"] = projectUuid;
					var neu = document.createElement("option");
					neu.setAttribute("value", projectName);
						if (i==0){
						neu.setAttribute("selected", 'selected');
						}
					formAuswahl.appendChild(neu);
					neu.innerHTML = projectName;
				}
				var e = document.getElementById("projekte");
				var wert = e.options[e.selectedIndex].value;
				//alert(wert);
				changeProject(wert);
			}
			else{
				alert("Request Response Code:"+req.status);
			}
		}
	}
	xmlhttp.open("GET","http://gorse.informatik.tu-cottbus.de:8080/openinfra_backend/rest/projects?language=de-DE",true);
	xmlhttp.setRequestHeader("Accept","application/xml");
	xmlhttp.send(null);

	
	//changeProject("Palatin");
}


/*
    function toggle(button) {

        var new_size;
        var x3d_element;
		var s;
		var scale;
		
		

        x3d_element = document.getElementById('x3d');
		s = document.getElementById('view');
		scale = document.getElementsByTagName('Transform');
		
        title = document.getElementById('title')
        body  = document.getElementById('interaktion')

        if (zoomed) {
            new_size = "100%";
            button.innerHTML = "Zoom";
            //title.style.display = "block"
            body.style.paddingLeft  = '0px'
			zoomed = false;
        } else {
            new_size = "200%";
            button.innerHTML = "Unzoom";
            //title.style.display = "none"
            body.style.paddingLeft = '100px';
			scale[0].setattribute("scale", "1.5 1.5 1.5");
			zoomed = true;
        }

        

        x3d_element.style.width = new_size;
        x3d_element.style.height = new_size;
		s.style.width = new_size;
        s.style.height = new_size;
    }
	*/

//Button "Statistik anzeigen"
function toggleStats() {
	stats = $element.runtime.statistics();
	if (stats) {
		$element.runtime.statistics(false);
	}
	else {
		$element.runtime.statistics(true);
	}
}

//Button "Konsole öffnen" --> funktioniert noch nicht so wie es soll!
function toggleDebug() {
	debug = $element.runtime.debug();
	if (debug) {
		$element.runtime.debug(false);
	}
	else {
		$element.runtime.debug(true);
	}
}

//Auswahlliste "Navigationsmodus ändern"
function radio(e){
	var v = e.value;
	switch(v){
	  case "examine" :   $element.runtime.examine(); break;
	  case "walk" :      $element.runtime.walk(); break;
	  case "lookAt" :    $element.runtime.lookAt(); break;
	  case "fly" :       $element.runtime.fly(); break;
	  case "helicopter": $element.runtime.helicopter(); break;
	  case "game" :      $element.runtime.game(); break;
	  case "lookAround": $element.runtime.lookAround(); break;
	}
}

//Checkboxauswahl - Steuerung der angezeigten Modelle
function check(element){
    var v = element.value;
	var n = element.name;
	var pulldown = document.getElementById("modellAuswahl");
	var k = pulldown.getElementsByTagName("option");  
	
    if(element.checked){
		//Modell hinzufügen
		addModell(v,n); 
		//Pulldownmenü aktualisieren
		for(i=0; i<k.length; i++){
			if(k[i].value == element.value){
				k[i].removeAttribute("disabled");
			}
		}
		//Layerfarbe anzeigen
		setLayerColor(v,"default");
    }
    else{
		//Modell entfernen
		removeModell(v);
		//Pulldownmenü ausgrauen
		for(i=0; i<k.length; i++){
			if(k[i].value == element.value){
				k[i].setAttribute("disabled", "disabled");
			}
		}
		//Layerfarbe ausblenden
		removeLayerColor(v);
    }
}

//Modell in Szene hinzufügen
function addModell(value,name){
	//Neuen DOM-Knoten anlegen und Attribute setzen
	var node = document.getElementById('s');
	var neu = document.createElement('Inline');
	neu.setAttribute("url", name+'/'+value+'.x3d');
	neu.setAttribute("nameSpaceName", value);
	neu.setAttribute("id", value);
	node.appendChild(neu);
	//Sobald der Knoten erzeugt wurde, den Anchorn ihre Events hinzufügen
	waitForEventAdd(value);
	//addEvent(neu,"load",$element.runtime.showAll(), false ); <-- geht nicht deswegen workaround per Timeout, keine gute Lösung!
	//window.setTimeout("$element.runtime.showAll()", 1000);
	document.getElementById("cam").setAttribute("set_bind", true);
}

//Modell aus Szene entfernen
function removeModell(name){
	var node = document.getElementById(name);
	document.getElementById('s').removeChild(node);
}

//MouseEvents zu den Anchor hinzufügen
function waitForEventAdd(id){
	var elem = document.getElementById(id);
	if(elem == null){
	
		//Timeout wichtig, da es sein kann, dass die Funktion aufgerufen wird, bevor der Knoten komplett erzeugt wurde!
		setTimeout(function(){waitForEventAdd(id);}, 250); 
	}
	else{
		
		var group = elem.getElementsByTagName('Group');
		if(group.length != 0){
			var geom = elem.getElementsByTagName("IndexedFaceSet");
			for(i=0; i<geom.length; i++){
				geom[i].setAttribute("onmouseover", "showTT(this);");
				geom[i].setAttribute("onmouseout", "hideTT();");
				geom[i].setAttribute("onclick", "onclickTT(this);");
			}			
		}
	}
}


//Alle Modelle aus der Szene löschen
function clearSzene(){
	var szene = document.getElementById('s');
	var childs = szene.childNodes;
	var l = childs.length;
	for(i=0;i<l;i++){
		szene.removeChild(childs[0]);
	}
}
 
//Pulldownmenü "Projekte"
function changeSelect(element){			
	var wert = element.options[element.options.selectedIndex].value;
	changeProject(wert); 
	//window.setTimeout("$element.runtime.showAll()", 1000);
}

//Dynamisches Laden der Projektspezifischen Daten
function changeProject(name){
	//Modellauswahl laden per AJAX
	var uuid;
	var xml;
	var tc;
	var tcName;
	var tcUuid;
	var div = document.getElementById("modelle");
	while (div.firstChild) {
	  div.removeChild(div.firstChild);
	}
	for (var i=0;i<projects.length;i++){
		for (var Eigenschaft in projects[i]){
			if (projects[i][Eigenschaft]==name){
				uuid = projects[i]["uuid"]
			}
		}
	 }
	 
	 //alert(uuid);

	//über die REST Schnittstelle alle Themenausprägungen des Projektes holen und als Layer anzeigen 
	var xmlhttp;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4){
			if(xmlhttp.status==200){
				
				xml = xmlhttp.responseXML.documentElement;
				
				tc = xml.getElementsByTagName("topicCharacteristicPojo");
				//alert(proj.length);
				//alert(proj[1].nodeName);
				var topic = document.createElement("h3");
				div.appendChild(topic);
				topic.innerHTML = name;
				
				var form = document.createElement("form");
				form.setAttribute("name", "w3ds");
				form.setAttribute("id", "modellListe");
				div.appendChild(form);
				
				var table = document.createElement("table");
				table.setAttribute("vertical-align", "middle");
				table.setAttribute("id", "table");
				document.getElementById("modellListe").appendChild(table);
				
				
				var td = document.createElement("td");
				
				topicCharacteristics = new Array(tc.length);
				for(i=0; i<tc.length; i++){
					tcName = tc[i].getElementsByTagName('descriptions')[0].getElementsByTagName('localizedStrings')[0].getElementsByTagName('characterString')[0].textContent;
					tcUuid = tc[i].getElementsByTagName('uuid')[0].textContent;
					//alert(tcName);
					topicCharacteristics[i] = new Array();
					topicCharacteristics[i]["topicCharacteristic"] = tcName;
					topicCharacteristics[i]["uuid"] = tcUuid;
					
					var tr = document.createElement("tr");
					tr.setAttribute("id", "tr_"+i);
					document.getElementById("table").appendChild(tr);
					
					var td = document.createElement("td");
					td.setAttribute("id", "td_"+i);
					document.getElementById("tr_"+i).appendChild(td);
					
					var input1 = document.createElement("input");
					input1.setAttribute("type", "text");
					input1.setAttribute("class", "farblayer");
					input1.setAttribute("id", "color_"+tcName);
					input1.setAttribute("disabled", "disabled");
					
					var input2 = document.createElement("input");
					input2.setAttribute("type", "checkbox");
					input2.setAttribute("name", "W3DS");
					input2.setAttribute("value", tcName);
					input2.setAttribute("onchange", "checkW3DS(this)");
					
					var label = document.createElement("label");
					
					document.getElementById("td_"+i).appendChild(input1);
					document.getElementById("td_"+i).appendChild(input2);
					document.getElementById("td_"+i).appendChild(label);
					
					
					label.innerHTML = tcName;
					
				}
				
				generatePulldown();
			}
			else{
				alert("Request Response Code:"+req.status);
			}
		}
	}
	xmlhttp.open("GET","http://gorse.informatik.tu-cottbus.de:8080/openinfra_backend/rest/projects/"+uuid+"/topiccharacteristics?language=de-DE",true);
	xmlhttp.setRequestHeader("Accept","application/xml");
	xmlhttp.send(null);
	//Alle bisherigen Modelle aus der Szene löschen
	clearSzene();
	
	//Szene Projektspezifisch anpassen
	
		//Bisher keine Anpassung nötig
		setViewPoint("960 40 -700", "960 40 -835");
	
	
	//Pulldownmenü für die Einfärbung der Modelle erzeugen
	//window.setTimeout("generatePulldown()", 200);
	//Tooltip ausblenden falls noch angezeigt
	tt.style.display = "none"; 
	//W3DS-Status ausblenden
	document.getElementById("w3ds_status").innerHTML = "";
}

//Dynamsicher Aufbau des Pulldowns für die Einfärbung der Modelle
function generatePulldown(){
	//bisheige Pulldown-Liste leeren
	var formAuswahl = document.getElementById("modellAuswahl");
	var kinder = formAuswahl.childNodes;
	var k = kinder.length;
	for(i=0; i<k; i++){
		formAuswahl.removeChild(kinder[0]);
	}
	
	//Liste mit neuen Werten befüllen
	var formModelle = document.getElementById("modellListe");
	var labs = formModelle.getElementsByTagName("label");
	var inputs = formModelle.getElementsByTagName("input");
	//Checkboxes herausfiltern
	var checkboxes = new Array();
	var j = 0;
	for(i=0; i<inputs.length; i++){
		if(inputs[i].type == "checkbox"){
			checkboxes[j] = inputs[i];
			j++;
		}
	}
	var l = labs.length;
	for(i=0; i<l; i++){
		var neu = document.createElement("option");
		neu.setAttribute("value", checkboxes[i].value);
		//If-Abfrage Anpassen je nachdem was automatisch angehakt ist!!
		if(checkboxes[i].value != "fertiges_gelaende_poly" && checkboxes[i].value != "Phase0" && checkboxes[i].value !="Palatin_Phase04_Flavisch2"){
			neu.setAttribute("disabled", "disabled");
		}
		formAuswahl.appendChild(neu);
		neu.innerHTML = labs[i].childNodes[0].nodeValue;
	}
}


//Farbe der Modelle ändern
function changeColor(e){
	var hex = "#"+e.color;			
	var r = e.color.rgb[0];
	var g = e.color.rgb[1];
	var b = e.color.rgb[2];
	var farbe = r+' '+g+' '+b;
	
	//Welches Projekt ist aktiv?
	var form = document.getElementById("projekte");
	var projekt = form.options[form.options.selectedIndex].value;

	//Welches Modell ist ausgwählt?
	var formAuswahl = document.getElementById("modellAuswahl");
	var auswahl = formAuswahl.options[formAuswahl.selectedIndex].value;
	var sichtbarkeit = formAuswahl.options[formAuswahl.selectedIndex].disabled;
	
	//Prüfen ob Modell auch sichtbar in Szene
	if(!sichtbarkeit){
		var szene = document.getElementById(auswahl);
				
		var material = szene.getElementsByTagName('Material');
		for(i=0; i<material.length; i++){
			material[i].setAttribute('diffuseColor', farbe);	
		}
		//neue Farbe bei Layer anzeigen
		var layer = document.getElementById("color_"+auswahl);
		layer.style.backgroundColor = hex;
	}
	else{
		alert("Dieses Modell ist gerade nicht sichtbar. Bitte wählen Sie ein anderes aus.");
	}
	
}

//Layerbaum ein/ausblenden
function showlayer(element,layer){
	var myLayer = document.getElementById(layer);
	if(myLayer.style.display=="none" || myLayer.style.display==""){
		myLayer.style.display="block";
		element.setAttribute("src", "img/minus.png");
	} 
	else {
		myLayer.style.display="none";
		element.setAttribute("src", "img/plus.png");
	}
}

//Transparenz ändern
function changeTranparency(element){
	//Welches Projekt ist aktiv?
	var form = document.getElementById("projekte");
	var projekt = form.options[form.options.selectedIndex].value;
	//Welches Modell ist ausgewählt?
	var formAuswahl = document.getElementById("modellAuswahl");
	var auswahl = formAuswahl.options[formAuswahl.selectedIndex].value;
	
	var szene;
	szene = document.getElementById(auswahl);
		
	var material = szene.getElementsByTagName('Material');
	var wert = parseFloat(element.value) / 100;
	for(i=0; i<material.length; i++){
		material[i].setAttribute('transparency', wert);	
	}

}

function transPlus(){
	var e = document.getElementById("transparenz");
	var v = parseInt(e.value);
	if(v < 100){
		e.value = v + 10;
		changeTranparency(e);
	}
}

function transMinus(){
	var e = document.getElementById("transparenz");
	var v = parseFloat(e.value);
	if(v > 0){
		e.value = v - 10;
		changeTranparency(e);
	}	
}

//Aktuelle Mausposition abfragen und Tooltip an diese Position verschieben
function saveMouseCoords(e){
	if (!e){
		e = window.Event;
	}
	if (e.type === "mousemove") {
		var mouseX = e.clientX;
		var mouseY = e.clientY;
	}
	tt.style.left    = (mouseX + 10) + "px";
	tt.style.top     = (mouseY + 10) + "px";
}

//Tooltip für Anker zeigen
function showTT(element) {
	var text1 = element.parentNode.parentNode.getAttribute('class');
	var text2 = element.parentNode.parentNode.parentNode.getAttribute('id');
	var text = text2+": "+text1;
	materialSpeicher = element.parentNode.getElementsByTagName('Appearance')[0].getElementsByTagName('Material')[0];
	farbSpeicher = materialSpeicher.getAttribute('diffuseColor');
	materialSpeicher.setAttribute('diffuseColor', '1 0 0');
	tt.firstChild.nodeValue = text;
	tt.style.display = "block";
}
function onclickTT(element) {
	var e = document.getElementById("projekte");
	var wert = e.options[e.selectedIndex].value;
	var p_uuid;
	
	for (var i=0;i<projects.length;i++){
		for (var Eigenschaft in projects[i]){
			if (projects[i][Eigenschaft]==wert){
				p_uuid = projects[i]["uuid"]
			}
		}
	 }
	
	var ti_uuid = element.parentNode.parentNode.getAttribute('class');
	
	var url = "http://gorse.informatik.tu-cottbus.de:8080/openinfra_backend/rest/projects/"+p_uuid+"/topicinstances/"+ti_uuid+"/topic?language=de-DE";
	window.open(url);
}

//Tooltip für Anker verbergen
function hideTT() {
	tt.style.display = "none";
	materialSpeicher.setAttribute('diffuseColor', farbSpeicher);
}

//Luftbilder anzeigen/ausblenden
function checkLuftbild(element)
{
    var v = element.value;
	var n = element.name;
	var a = document.getElementById("fertiges_gelaende_poly__group_fertiges_gelaende_poly").getElementsByTagName("Appearance")[0];
	
    if(element.checked){
		var neu = document.createElement('ImageTexture');
		neu.setAttribute("url", 'Luftbilder/'+v+'.png');
		neu.setAttribute("id", v);
		a.appendChild(neu);
    }
    else{
		a.removeChild(document.getElementById(v))
    }
}

//Ursprung ändern
/*function changeOrigin(x,y,z){
	var szene = document.getElementById("s");
	var viewpoint = szene.getElementsByTagName("Viewpoint")[0];
	viewpoint.setAttribute("centerOfRotation",x+","+y+","+z);
	
	//TO DO: Mittelpunkt dse Modells ermitteln und Ursprung darauf setzen, statt festem Wert!
		/*var gruppe = document.getElementById("fertiges_gelaende_poly");
		var geom = gruppe.getElementsByTagName("Shape")[0];
		var mitte = $element.runtime.getCenter(geom);
		alert("Mitte="+mitte);
}*/

//ViewPoint und Ursprung setzen
function setViewPoint(pos, origin){
	var szene = document.getElementById("s");
	var node = document.createElement('Viewpoint');
	node.setAttribute("id", "cam");
	node.setAttribute("position", pos);
	node.setAttribute("centerOfRotation", origin);
	szene.appendChild(node);
}

//Layerfarbe in Abhängigkeit der Auswahl anzeigen
function setLayerColor(v,string){
	var szene;
	switch(string){
		case "default" : szene = document.getElementById(v+'__group_'+v); break;
		case "w3ds"    : szene = document.getElementById(v); break;
	}
	//alert(szene.value);
	//alert(szene.value);szene instanceof HTMLUnknownElement || 
	if(szene == null){
		setTimeout(function(){setLayerColor(v,string);}, 250); //Warten bis Element wirklich erzeugt wurde!
	}
	else{
		var farbfeld = document.getElementById("color_"+v);		
		var material = szene.getElementsByTagName('Material')[0];
		var farbe = material.getAttribute('diffuseColor').split(" ");
		var r = farbe[0]*100;
		var g = farbe[1]*100;
		var b = farbe[2]*100;
		//alert(r);
		farbfeld.style.backgroundColor = 'rgb('+r+'%,'+g+'%,'+b+'%)';
		
	}
}

function removeLayerColor(v){
	var farbfeld = document.getElementById("color_"+v);
	farbfeld.style.backgroundColor = 'white';
}

//Eventhandler abhängig vom Browser hinzufügen
function addEvent( obj, type, fn, bool ){
	if (obj.addEventListener) { //Rest
		obj.addEventListener( type, fn, bool );
	} 
	else if (obj.attachEvent) { //IE <9
		obj["e"+type+fn] = fn;
		obj[type+fn] = function() { obj["e"+type+fn]( window.event ); }
		obj.attachEvent( "on"+type, obj[type+fn] );
	}
}

//*******************************************************
//-----------------W3DS Erweiterungen!-------------------
//*******************************************************
//
function checkW3DS(element){
	var v = element.value;
	var n = element.name;

	var e = document.getElementById("projekte");
	var pulldown = document.getElementById("modellAuswahl");
	var k = pulldown.getElementsByTagName("option");  
	var project = e.options[e.selectedIndex].value;
	
	
	
	
    if(element.checked){
		//Szene abrufen
		getScene(v,project);
		//Pulldownmenü aktualisieren
		for(i=0; i<k.length; i++){
			if(k[i].value == element.value){
				k[i].removeAttribute("disabled");
				
			}
		}
		setLayerColor(v,"w3ds");
    }
    else{
		removeScene(v);
		//Pulldownmenü ausgrauen
		for(i=0; i<k.length; i++){
			if(k[i].value == element.value){
				k[i].setAttribute("disabled", "disabled");
			}
		}
		removeLayerColor(v);

    }
}

function getScene(value,project){
	//Boundingboxen der einzelnen Layer festlegen
	var topic_uuid;
	var p_uuid;
	var pulldown = document.getElementById("modellAuswahl");
	var k = pulldown.getElementsByTagName("option"); 
	var xml;
	
	for (var i=0;i<topicCharacteristics.length;i++){
		for (var Eigenschaft in topicCharacteristics[i]){
			if (topicCharacteristics[i][Eigenschaft]==value){
				topic_uuid = topicCharacteristics[i]["uuid"]
			}
		}
	 }
	 for (var i=0;i<projects.length;i++){
		for (var Eigenschaft in projects[i]){
			if (projects[i][Eigenschaft]==project){
				p_uuid = projects[i]["uuid"]
			}
		}
	 }
	
		
	//Per AJAX wird ein PHP-Skript aufgerufen, das die Anfrage an den W3DS schickt. Dieser Umweg ist zwingend notwendig, da AJAX nicht auf fremde Server zugreifen kann!
	var xmlhttp;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function(){
		var w3ds_status = document.getElementById("w3ds_status");
		if (xmlhttp.readyState==1){
			w3ds_status.innerHTML = "Server wird kontaktiert...";
		}
		if (xmlhttp.readyState==2){
			w3ds_status.innerHTML = "Antwort vom Server erhalten...";
		}
		if (xmlhttp.readyState==3){
			w3ds_status.innerHTML = "Verarbeite die Antwort vom Server...";
		}
		if (xmlhttp.readyState==4){
			if(xmlhttp.status==200){
				//alert(xmlhttp.responseXML);
				//alert(xmlhttp.responseXML.parseError.errorCode);
				//alert(xmlhttp.responseXML.parseError.reason);
				xml = xmlhttp.responseXML.documentElement;
				var r = (Math.floor((Math.random() * 10) + 1))/10; 
				var g = (Math.floor((Math.random() * 10) + 1))/10; 
				var b = (Math.floor((Math.random() * 10) + 1))/10; 
				
		
				if(xml.firstChild != null && xml.firstChild.nodeName == 'topicGeomzPojo'){
					
					var group1 = document.createElement("Group");
					group1.setAttribute("id", value);
					document.getElementById("s").appendChild(group1);
					
					var geomz = xml.getElementsByTagName('topicGeomzPojo');
					//alert(geomz.length);
					for(i=0; i<geomz.length; i++){
						var group2 = document.createElement("Group");
						group2.setAttribute("id", value+"_"+i);
						group2.setAttribute("class", geomz[i].getElementsByTagName('topicInstanceId')[0].textContent);
						document.getElementById(value).appendChild(group2);
						
						var shape = document.createElement("Shape");
						shape.setAttribute("id", value+"_Shape_"+i);
						document.getElementById(value+"_"+i).appendChild(shape);
						
						var app = document.createElement("Appearance");
						app.setAttribute("id", value+"_Appearance_"+i);
						document.getElementById(value+"_Shape_"+i).appendChild(app);
						
						var material = document.createElement("Material");
						material.setAttribute("id", value+"_Material_"+i);
						material.setAttribute("ambientIntensity", "0.2");
						material.setAttribute("diffuseColor", r+" "+g+" "+b);
						material.setAttribute("emissiveColor", "0 0 0");
						material.setAttribute("shininess", "0.2");
						material.setAttribute("specularColor", "0 0 0");
						material.setAttribute("transparency", "0.0");
						
						document.getElementById(value+"_Appearance_"+i).appendChild(material);
						if (window.DOMParser)
						  {
						  var parser=new DOMParser();
						  var xmlDoc=parser.parseFromString(geomz[i].getElementsByTagName('attributeValuesGeomz')[0].getElementsByTagName('geom')[0].textContent,"text/xml");
						  }
						else // Internet Explorer
						  {
						  var xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
						  xmlDoc.async=false;
						  xmlDoc.loadXML(geomz[i].getElementsByTagName('attributeValuesGeomz')[0].getElementsByTagName('geom')[0].textContent);
						  } 
						  //alert(xmlDoc.documentElement.nodeName);
						document.getElementById(value+"_Shape_"+i).appendChild(xmlDoc.documentElement);
						
						
						
					}
					
					w3ds_status.innerHTML = "Daten erfolgreich geladen!";
					waitForEventAdd(value);
					document.getElementById("cam").setAttribute("set_bind", true);
					//setLayerColor(value,"w3ds");
					
				}
				else {
					w3ds_status.innerHTML = "Themenausprägung enthält keine Geometrie";
					//document.getElementById(value).checked = false;
					//alert(value);
					 var allInputs = document.getElementsByTagName("input");
						for(var x=0;x<allInputs.length;x++){
							if(allInputs[x].value == value){
								//alert(allInputs[x]);
								allInputs[x].checked = false;
								allInputs[x].disabled = true;
								for(i=0; i<k.length; i++){
									if(k[i].value == allInputs[x].value){
										k[i].setAttribute("disabled", "disabled");
									}
								}
							}
						}
					}
					//TO-DO: Error-Handling falls sever nicht erreichbar!
				
				
			}
			else{
				alert("Request Response Code:"+req.status);
			}
		}
	}
	
	xmlhttp.open("GET","http://gorse.informatik.tu-cottbus.de:8080/openinfra_backend/rest/projects/"+p_uuid+"/topiccharacteristics/"+topic_uuid+"/topicinstances/geomz?language=de-DE&geomType=X3D",true);
	xmlhttp.setRequestHeader("Accept","application/xml");
	xmlhttp.send(null);
}

function removeScene(value){
	var node = document.getElementById(value);
	document.getElementById("s").removeChild(node);
}