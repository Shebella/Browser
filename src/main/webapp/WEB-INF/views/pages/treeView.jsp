<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script src="${pageContext.request.contextPath}/js/jquery.jgrowl_minimized.js"></script>
<link  href="${pageContext.request.contextPath}/css/jquery.jgrowl.css" rel="stylesheet" type="text/css" />

<div id="uploadBox">
  	<div class="cloneHeaderArea">
    	 <div class="wrapDiv wrap">
    	 	<h2>Move ${moveItemsInfo} to...</h2>
    	 				
			<div id="container">
	    		<div id="treeView" class="filetree">
	    			<ul id="treeData" style="display: none;"></ul>
				</div>
			</div>
			
			<div>Selected folder: <span id="echoSelected">-</span></div>
			
			<br />
			
			<input type="hidden" name="folderStart" id="folderStart" value="${folderStart}" />
			<input type="hidden" name="srcObjects" id="srcObjects" value="${srcObjects}" />
			<input type="hidden" name="targetFolder" id="targetFolder" value="" />
			
			<form id="updateForm" name="updateForm" method="post">
				<input id="treeMoveBtn" name="treeMoveBtn" type="button" value="move" onclick="formSubmit();" />
			</form>
			
			
			<script>
				$("#treeView").dynatree({
					autoFocus: false,
					
					initAjax: {
	              		url: "../treeView/json/${bucketName}",
	              	},
	              	onFocus: function(node) {
	                    $("#echoSelected").text( node.data.title );
	                    $("#targetFolder").attr('value', node.data.key);
	                },
	                onLazyRead: function(node) {
	                	node.appendAjax({
	                		url: "../treeView/json/${bucketName}" + node.data.key
	                	});
	                }
				});
				
				function formSubmit() {
					if('' == $("#targetFolder").attr('value') || '' == $("#srcObjects").attr('value') || '' == $("#folderStart").attr('value')) {
				    	alert('Please select folder first');
				    	return;
				    } else {
				    	$('#treeMoveBtn').attr('disabled', 'disabled');
				    	$('#treeMoveBtn').attr('value', 'moving, please wait...');
				    	var actionURL = "json/tree/${bucketName}";
				    	$.ajax({
				    		url: actionURL,
				          	type: 'POST',
				          	dataType: 'json',
				          	data: 'folderStart=${folderStart}&srcObjNames=' + $("#srcObjects").attr('value') + '&targetFolder=' + $("#targetFolder").attr('value'),
				          	success: function(data, textStatus, jqXHR){
				          		$('#treeMoveBtn').attr('value', 'Moved items to folder ' + $("#echoSelected").text());
				          	},
				          	error : function(jqXHR, textStatus, errorThrown){
				          		alert('move file failed');
				          	},
				          	complete : function(jqXHR, textStatus){
				          		setTimeout(function() {
				          			window.location.reload();
				          		}, 1000);
				            }
				    	});
				    }
				}
			</script>
		</div>
	</div>
</div>

