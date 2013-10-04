<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
        <title>SafeBox</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="../css/reset.css" rel="stylesheet" type="text/css" />
        <link href="../css/cupertino/jquery-ui-1.8.19.custom.css" type="text/css" rel="stylesheet"  />
        <link href="../css/safebox_css.css" type="text/css" rel="stylesheet" />
        <link href="../css/itriFileBrowser.css" rel="stylesheet" type="text/css" />
        <link href="../css/blue/style.css" rel="stylesheet" type="text/css" />
        <link href="../css/jquery.jgrowl.css" rel="stylesheet" type="text/css" />
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/uploadBox/jquery.superbox.css" type="text/css" media="all" />
        <style type="text/css">
			/* Custom Theme */
			#superbox-overlay{background:#e0e4cc;}
			#superbox-container .loading{width:32px;height:32px;margin:0 auto;text-indent:-9999px;background:url(styles/loader.gif) no-repeat 0 0;}
			#superbox .close a{float:right;padding:0 5px;line-height:20px;background:#333;cursor:pointer;}
			#superbox .close a span{color:#fff;}
		</style>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/uploadBox/uploadify/uploadify.css" />
		<style type="text/css">
		#some_file_queue {
		    background-color: #FFF;
		    border-radius: 3px;
		    box-shadow: 0 1px 3px rgba(0,0,0,0.25);
		    height: 60px;
		    margin-bottom: 10px;
		    overflow: auto;
		    padding: 5px 10px;
		    width: 300px;
		}
		</style>
	
        <jsp:include page="../included/jsArea.jsp" />
        <script type="text/javascript">  utils.pageInit({tabId:'apiVersion'}); </script>
        <script src="../js/jquery.itriCSSJsonParser_apiVer.js" type="text/javascript"></script>
        
        <script type="text/javascript" src="${pageContext.request.contextPath}/uploadBox/jquery.superbox-min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/uploadBox/uploadify/jquery.uploadify-3.1.min.js"></script>
        
        <script type="text/javascript">
        $(function(){
        
         $.tablesorter.defaults.sortInitialOrder = 'asc';

            $.tablesorter.addParser({
                  // set a unique id 
                  id: 'verSorter',
                  is: function (s) {
                      // return false so this parser is not auto detected 
                      return false;
                  },
                  format: function (s, b, c) {
                      var ary = s.split("v");
                     
                      var num =  parseFloat(ary[1]);
                      return num;
                  },
                  // set type, either numeric or text 
                  type: 'numeric'
            });
            $jsonParser = $.itriCSSJsonParser_apiVer({
              url: '../js/response_apiVer.json',
              type: 'tableList',
              rootId: "tableList",   
              success: function(elements, rawData){
                  $("#wrapDiv").empty();
                  $("#wrapDiv").append(elements);
                  
                  $tableSorter = $("#tableList").tablesorter({
                      sortList: [
                          [0, 1]
                      ],
                      headers: {
                          "0": {
                              sorter: 'verSorter'
                          }
                      }
                  });
                  
                  //clone head
				  utils.theadClone($("#tableList"), $("#tClone"));
                  
              }
           });
           
           // for uploadBox
           $.superbox.settings = {
				closeTxt: "Close",
				overlayOpacity: .4,
				boxWidth: "400",
				boxHeight: "180",
				boxClasses: ""
		   };
           $.superbox();
           $('#uploadBox').hide();
        });
        </script>
    </head>
    <body>
      <div id= "layoutWrap">
        <div id="topPanel">
        	<jsp:include page="../included/topArea.jsp" />
            <div id="ctlArea" class="ctlArea">
            
            </div>
            <div class="cloneHeaderArea">
            	 <div class="wrapDiv wrap">
            	 	<table id="tClone"></table>
            	 </div>
            </div>
            <div class="shadow">
            </div>
        </div>
        <div id="leftPanel"></div>
        <div id="contentPanel">
            <div id="fakeHeader"></div>
            <a href="#uploadBox" rel="superbox[content]">upload</a>
            <div id="wrapDiv" class="innerTableDiv wrapDiv"></div>
        </div>
        <div id="bottomPanel"></div>
      </div>
      
      <div id="uploadBox">
      	<div class="cloneHeaderArea">
        	 <div class="wrapDiv wrap">
        	 	<h2>Upload to Safebox</h2>
				
				<div>
				<p>You can select more than one file to start uploading.</p>
				<div id="some_file_queue"></div>
				<input type="file" name="file_upload" id="file_upload" />
				</div>
				
				 
				
				<script>
				$(function() {
				    $('#file_upload').uploadify({
				    	'method'   : 'post',
				        'swf'       : '${pageContext.request.contextPath}/uploadBox/uploadify/uploadify.swf',
				        'uploader'  : '${pageContext.request.contextPath}/upload/${bucketName}${folderStart}',
				        'cancelImg' : '${pageContext.request.contextPath}/uploadBox/uploadify/uploadify/cancel.png',
				        'auto'     : true,
				        'multi'    : true,
				        'buttonText' : 'BROWSE',
				        'fileSizeLimit' : '100MB',
				        'queueID'  : 'some_file_queue',
				        'queueSizeLimit' : 1,
				        'onUploadStart' : function(file) {
				            $("#file_upload").uploadify();
				        },
				        'onUploadSuccess' : function(file, data, response) {
				            alert('The file was saved to: ' + data);
				        }
				    });
				});
				</script>
				
				<div>
				<p>Having problems? Try the basic uploader here: </p>
				<form method="post" action="${pageContext.request.contextPath}/upload/${bucketName}${folderStart}" enctype="multipart/form-data">
            		<input type="file" name="file" />
            		<input type="submit" value="upload" />
        		</form>
        		</div>
        		
        		
        	 </div>
		</div>
      </div>
    </body>
</html>