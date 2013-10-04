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
        <jsp:include page="/WEB-INF/views/included/jsArea.jsp" />
        <script type="text/javascript">  utils.pageInit({tabId:'event'}); </script>
        <script src="../js/jquery.itriCSSJsonParser_event.js" type="text/javascript"></script>
        <script type="text/javascript">
        $(function(){
           $("#datepicker").datepicker({
                showOn: "button",
                dateFormat: "mm-dd-yy",
                buttonImage: "../images/img/calendar.gif",
                buttonImageOnly: true
           });
           
           function loadFileData(log_date) {        	   
	           $jsonParser = $.itriCSSJsonParser_event({
	        	   url: './json/event/' + log_date,
	              type: 'eventList',
	              rootId: "eventList",   
	              success: function(elements, rawData){
	                  $("#wrapDiv").empty();
	                  $("#wrapDiv").append(elements);
	                  
	                  if(0 < rawData.length) {
	                	  /*
		                  $tableSorter = $("#eventList").tablesorter({
		                      sortList: [
		                          [0, 0]
		                      ]
		                  });
	                	  */
	                  }
	                  
	                  //clone head
					  utils.theadClone($("#eventList"), $("#tClone"));
	                  
	              }
	           });
	       }
           
           loadFileData('');
           
           $('#searchBtn').click(function() {
        	   if('' != $('#datepicker').attr('value')) {
        		   loadFileData($('#datepicker').attr('value'));
        	   } else {
        		   $.jGrowl('Please select date first');
        	   }
           });
        
        });
        </script>
    </head>
    <body>
        <div id= "layoutWrap">
            <div id="topPanel">
                <jsp:include page="/WEB-INF/views/included/topArea.jsp" />
                <div id="ctlArea" class="ctlArea">
                    <table border="0" width="100%">
                        <tr>
                            <td>
                            	
                            </td>
                            <td align="right">
                                <div style="padding:20px 20px 0 20px;">
                                    <p>
                                        <input type="text" id="datepicker" name="logDate" />
                                        <input type="button" id="searchBtn" value="show events" />
                                    </p>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="cloneHeaderArea">
                    <div class="wrapDiv wrap">
                        <table id="tClone">
                        </table>
                    </div>
                </div>
                <div class="shadow">
                </div>
            </div>
            <div id="leftPanel">
            </div>
            <div id="contentPanel">
                <div id="fakeHeader">
                </div>
                <div id="wrapDiv" class="innerTableDiv wrapDiv">
                </div>
            </div>
            <div id="bottomPanel">
            </div>
        </div>
    </body>
</html>
