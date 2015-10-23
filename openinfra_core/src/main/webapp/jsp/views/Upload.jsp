<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../snippets/Head.jsp" %>
<script language="javascript" type="text/javascript">
//copy from http://creativejs.com/tutorials/advanced-uploading-techniques-part-1/
$(document).ready(function() {
    var upload_form = $('#upload_form'),
        file_input = $('#file_input'),
        file_list = $('#file_list'),
        submit_btn = $('#submit_btn'),
        uploaders = [];
 
    file_input.on('change', onFilesSelected);
    upload_form.on('submit', onFormSubmit);
 
    /**
     * Loops through the selected files, displays their file name and size
     * in the file list, and enables the submit button for uploading.
     */
    function onFilesSelected(e) {
        var files = e.target.files,
            file;
 
        for (var i = 0; i < files.length; i++) {
            file = files[i];
            uploaders.push(new ChunkedUploader(file));
            file_list.append('<li>' + file.name + '(' + file.size.formatBytes() + ')</li>');
        }
 
        file_list.show();
        submit_btn.attr('disabled', false);
    }
 
    /**
     * Loops through all known uploads and starts each upload
     * process, preventing default form submission.
     */
    function onFormSubmit(e) {
        $.each(uploaders, function(i, uploader) {
            uploader.start();
        });
 
        // Prevent default form submission
        e.preventDefault();
    }
});

/**
 * Utility method to format bytes into the most logical magnitude (KB, MB,
 * or GB).
 */
Number.prototype.formatBytes = function() {
    var units = ['B', 'KB', 'MB', 'GB', 'TB'],
        bytes = this,
        i;
 
    for (i = 0; bytes >= 1024 && i < 4; i++) {
        bytes /= 1024;
    }
 
    return bytes.toFixed(2) + units[i];
}

function ChunkedUploader(file, options) {
    if (!this instanceof ChunkedUploader) {
        return new ChunkedUploader(file, options);
    }
 
    this.file = file;
 
    this.options = $.extend({
        url: './upload/' + file.name
    }, options);
 
    this.chunk_size = (1024 * 100); // 100KB
    this.range_start = 0;
    this.range_end = this.file.size;;
 
    if ('mozSlice' in this.file) {
        this.slice_method = 'mozSlice';
    }
    else if ('webkitSlice' in this.file) {
        this.slice_method = 'webkitSlice';
    }
    else {
        this.slice_method = 'slice';
    }
 
    this.upload_request = new XMLHttpRequest();
    this.upload_request.onload = this._onChunkComplete;
}
 
ChunkedUploader.prototype = {
 
// Internal Methods __________________________________________________
 
    _upload: function() {
        var self = this,
            chunk;
 
        // Slight timeout needed here (File read / AJAX readystate conflict?)
        setTimeout(function() {
            // Prevent range overflow
            if (self.range_end > self.file_size) {
                self.range_end = self.file_size;
            }
 
            chunk = self.file[self.slice_method](self.range_start, self.range_end);
 
            self.upload_request.open('POST', self.options.url, true);
            self.upload_request.overrideMimeType('application/octet-stream');
 
            if (self.range_start !== 0) {
                self.upload_request.setRequestHeader('Content-Range', 'bytes ' + self.range_start + '-' + self.range_end + '/' + self.file_size);
            }
 
            self.upload_request.send(chunk);
 
            // TODO
            // From the looks of things, jQuery expects a string or a map
            // to be assigned to the "data" option. We'll have to use
            // XMLHttpRequest object directly for now...
            /*$.ajax(self.options.url, {
                data: chunk,
                type: 'PUT',
                mimeType: 'application/octet-stream',
                headers: (self.range_start !== 0) ? {
                    'Content-Range': ('bytes ' + self.range_start + '-' + self.range_end + '/' + self.file_size)
                } : {},
                success: self._onChunkComplete
            });*/
        }, 20);
    },
 
// Event Handlers ____________________________________________________
 
    _onChunkComplete: function() {
        // If the end range is already the same size as our file, we
        // can assume that our last chunk has been processed and exit
        // out of the function.
        if (this.range_end === this.file_size) {
            //this._onUploadComplete();
            return;
        }
 
        // Update our ranges
        this.range_start = this.range_end;
        this.range_end = this.range_start + this.chunk_size;
 
        // Continue as long as we aren't paused
        if (!this.is_paused) {
            this._upload();
        }
    },
 
// Public Methods ____________________________________________________
 
    start: function() {
        this._upload();
    },
 
    pause: function() {
        this.is_paused = true;
    },
 
    resume: function() {
        this.is_paused = false;
        this._upload();
    }
};
</script> 
	<title>OpenInfRA <fmt:message key="system.label"/></title>
</head>
<body>
	<%@ include file="../snippets/Menu.jsp" %>
	<h1>Unterscheidung zwischen zwei Upload-Methoden aus technischen Gründen!</h1>
	<br/>
	<br/>
	<h2>Browser-basierter Upload für mehrere Dateien:</h2>
	<form action="./upload" method="post" enctype="multipart/form-data">
		<label for="file_browser">Datei(en) auswählen:</label>
		<input id="file_browser" type="file" name="files" multiple="multiple"/>
		<input type="submit" value="Upload It" />
	</form>
	
	<hr/>
	<h2>JavaScript-basierter (chunked) Upload für mehrere Dateien 
	<br/> (intern wird für jede Datei ein eigenständiger POST ausgelöst):</h2>
	<form id="upload_form" action="./upload" method="post">
	    <label for="file_input">Datei(en) auswählen:</label>
	    <input id="file_input" type="file" name="files" multiple="multiple">
	    <div>
	        <input id="submit_btn" type="submit" value="Upload" disabled>
	    </div>
	</form>
	<ul id="file_list" style="display: none;">
	    <!-- File data will be listed here -->
	</ul>

	
</body>
</html>