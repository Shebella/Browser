<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="uploadBox">
  	<div class="cloneHeaderArea">
    	 <div class="wrapDiv wrap">
    	 	<h2>Upload to Safebox</h2>
    	 				
			<div id="container">
	    		<div id="filelist">No runtime found.</div>
	    		<br />
	    		<a id="pickfiles" href="#">[Select files]</a>
	    		<a id="uploadfiles" href="#">[Upload files]</a>
			</div>
			
			<script>
				$(function() {
					var stopFlag = false;
					var uploader = new plupload.Uploader({
						runtimes : 'html5,flash',
						browse_button : 'pickfiles',
						container : 'container',
						max_file_size : '1gb',
						url : '${pageContext.request.contextPath}/upload/${bucketName}${folderStart}',
						flash_swf_url : '${pageContext.request.contextPath}/uploadBox/plupload/plupload.flash.swf'
					});
	
					uploader.bind('Init', function(up, params) {
						//$('#filelist').html("<div>Current runtime: " + params.runtime + "</div>");
						$('#filelist').html("");
					});
	
					$('#uploadfiles').click(function(e) {
						uploader.start();
						e.preventDefault();
					});
	
					uploader.init();
	
					uploader.bind('FilesAdded', function(up, files) {
						$.each(files, function(i, file) {
							$('#filelist').append(
								'<div id="' + file.id + '">' +
								file.name + ' (' + plupload.formatSize(file.size) + ') <strong></strong>' +
							'</div>');
						});
	
						up.refresh(); // Reposition Flash/Silverlight
					});
	
					uploader.bind('UploadProgress', function(up, file) {
						$('#' + file.id + " strong").html(file.percent + "%");
					});
	
					uploader.bind('Error', function(up, err) {
						$('#filelist').append("<div>Error: " + err.code +
							", Message: " + err.message +
							(err.file ? ", File: " + err.file.name : "") +
							"</div>"
						);
	
						up.refresh(); // Reposition Flash/Silverlight
					});
	
					uploader.bind('FileUploaded', function(up, file, info) {						
						var respInfo = $.parseJSON(info.response.trim());
						$('#' + file.id + " strong").html(respInfo.result);
						if(!respInfo.statusCode) {
							stopFlag = true;
						}
					});
					
					uploader.bind('UploadComplete', function(up, file) {
						if(!stopFlag) {
							setTimeout(function() {
								location.href = '${pageContext.request.contextPath}/pages/files?bucket=${bucketName}&folder=${folderStart}';	
							}, 1000);
						}
					});
					
					$('#fileUploadForm').submit(function() {
						if('' == $('#fileName').attr('value')) {
							return false;
						} else {
						  	return true;
						}
					});
				});
			</script>

<br />
<br />

			<div>
				<p>Having problems? Try the basic uploader here: </p>
				<form id="fileUploadForm" method="post" action="${pageContext.request.contextPath}/upload/${bucketName}${folderStart}" enctype="multipart/form-data">
				  		<input type="file" name="file" id="fileName" />
				  		<input type="hidden" name="need302"  value="redirect" />
				  		<input type="submit" value=" upload " />
				</form>
			</div>
			
		</div>
	</div>
</div>
