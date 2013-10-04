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

        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" media="screen"/>
        <link href="${pageContext.request.contextPath}/css/safebox_css.css" type="text/css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/fontAwesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

        <link href="${pageContext.request.contextPath}/css/reset.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/cupertino/jquery-ui-1.8.19.custom.css" type="text/css" rel="stylesheet"  />
        <link href="${pageContext.request.contextPath}/css/itriFileBrowser.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/blue/style.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/jquery.jgrowl.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/uploadBox/jquery.superbox.css" rel="stylesheet" type="text/css" media="all" />
        <link href="${pageContext.request.contextPath}/uploadBox/plupload/jquery.plupload.queue/css/jquery.plupload.queue.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="${pageContext.request.contextPath}/treeViewBox/skin-vista/ui.dynatree.css" rel="stylesheet" type="text/css" id="skinSheet" />
		
        <style type="text/css">
			/* Custom Theme */
			#superbox-overlay{background:#e0e4cc;}
			#superbox-container .loading{width:32px;height:32px;margin:0 auto;text-indent:-9999px;background:url(${pageContext.request.contextPath}/images/loader.gif) no-repeat 0 0;}
			#superbox .close a{float:right;padding:0 5px;line-height:20px;background:#333;cursor:pointer;}
			#superbox .close a span{color:#fff;}
			
			select, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input {
   				margin-bottom: 0px;
    			padding: 0px;
    			font-size: 12px;
    			width:150px;
			}
		</style>
		
        <jsp:include page="/WEB-INF/views/included/jsArea.jsp" />
        <script src="${pageContext.request.contextPath}/js/jquery.paginate.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
		
		<script type="text/javascript">  utils.pageInit({tabId:'files'}); </script>
		<script src="http://bp.yahooapis.com/2.4.21/browserplus-min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.itriCSSJsonParser.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.itriFileBrowser2.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.customselect.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/uploadBox/jquery.superbox.js" type="text/javascript"></script>		
		<script src="${pageContext.request.contextPath}/uploadBox/plupload/plupload.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/uploadBox/plupload/plupload.flash.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/uploadBox/plupload/plupload.html5.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/uploadBox/plupload/jquery.plupload.queue/jquery.plupload.queue.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/treeViewBox/jquery.dynatree.min.js" type="text/javascript"></script>
        
        <script type="text/javascript">
		
  		$(function () {
		
		var vspUtils = {
  	    	  	loadingMask: {
  	  	    	  	$ui: null,
  	  	    	  	show: function () {
  	  	  	    	  	if (vspUtils.loadingMask.$ui == null) {
  	  	  	  	    	  	vspUtils.loadingMask.$ui = $("#loadingMask");
  	  	  	  	    	  	
  	  	  	  	    	  	if (vspUtils.loadingMask.$ui.length == 0) {
  	  	  	  	  	    	  	vspUtils.loadingMask.$ui = null;
  	  	  	  	  	    	}

  	  	  	  	  	    	$(window).unbind("scroll").scroll(function(){
  	  	  	  	  	  	    	vspUtils.loadingMask.$ui.css("top", $(window).scrollTop());
  	  	  	  	  	  	    });
  	  	  	  	  	  	}

  	  	  	  	  	  	vspUtils.loadingMask.$ui.css("top", $(window).scrollTop());
  	  	  	  	  	  	vspUtils.loadingMask.$ui.fadeIn();
  	  	  	  	  	},
  	  	  	  	  	 
  	  	  	  	  	hide: function () {
  	  	  	  	  	  	vspUtils.loadingMask.$ui.fadeOut();
  	  	    		}
  	    		}
  	    };

  		$('#customselector').customSelect();
            function diskSizeRenderer(value){
                var bytes = parseInt(value);
                if (isNaN(bytes)) {
                    return "";
                }
                
                var s = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];
                var e = Math.floor(Math.log(bytes) / Math.log(1024));
                return (bytes / Math.pow(1024, Math.floor(e))).toFixed(2) + " " + s[e];
      	}

      function diskSizeRenderer(value) {
          var bytes = parseInt(value);
          if (isNaN(bytes)) {
              return "";
          }

          var s = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];
          var e = Math.floor(Math.log(bytes) / Math.log(1024));
          return (bytes / Math.pow(1024, Math.floor(e))).toFixed(2) + " " + s[e];
      }
	  
      //yyyy-MM-dd HH:mms
      // add parser through the tablesorter addParser method 
      $.tablesorter.addParser({
          // set a unique id 
          id: 'mbsorter',
          is: function (s) {
              // return false so this parser is not auto detected 
              return false;
          },
          format: function (s, b, c) {
              //for render
              var rawValue = parseInt($(c).html());
              //for sorting
              var num = 0;
              if (rawValue > 0 && !isNaN(rawValue)) {
                  $(c).html(diskSizeRenderer(rawValue));
                  num = rawValue;
              } else {
            	  if(rawValue == 0) {
            		  $(c).html("0 Bytes");
            	  } else {
            		  $(c).html("--");
            	  }
                  num = 0;
              }

              return num;
          },
          // set type, either numeric or text 
          type: 'numeric'
      });
      //January 6, 1978 9:12 AM or Jan. 6, 2001 9:12 AM
      var monthNames = {};
      monthNames["Jan"] = "01";
      monthNames["Feb"] = "02";
      monthNames["Mar"] = "03";
      monthNames["Apr"] = "04";
      monthNames["May"] = "05";
      monthNames["Jun"] = "06";
      monthNames["Jul"] = "07";
      monthNames["Aug"] = "08";
      monthNames["Sep"] = "09";
      monthNames["Oct"] = "10";
      monthNames["Nov"] = "11";
      monthNames["Dec"] = "12";

      $.tablesorter.addParser({
          id: 'monthDayYear',
          is: function (s) {
              return false;
          },
          format: function (s) {
              var date = s.match(/^(\w{3})[ ](\d{1,2}),[ ](\d{4})$/);
              var m = monthNames[date[1]];
              var d = String(date[2]);
              if (d.length == 1) {
                  d = "0" + d;
              }
              var y = date[3];
              return '' + y + m + d;
          },
          type: 'numeric'
      });

      var $jsonParser = null;
      var $tableSorter = null;
      var $fileBrowser = null;

	  var curPage = 1;
	  
      function loadFileData(page) {
          //--itriCSSJsonParser plugin
		  vspUtils.loadingMask.show();
		  
		  if (page == undefined){
			  page = 1;
          }
		  
          var keyword = "${searchKeyword}";
          //var jsonUrl = "./json/files/${bucketName}${folderStart}?curPage=" + page + "&pageSize=${pageSize}";
          var jsonUrl = "../../sbx_svr/rest/EBS/listobjects?bucket=${bucketName}&page=" + page + "&pagesize=50&folder=${folderStart}";
          
          if("" != keyword) {
        	  jsonUrl = jsonUrl + "?searchKeyword=" + keyword;
          }
          
          $jsonParser = $.itriCSSJsonParser({
        	  url: jsonUrl,
        	  //url: '../js/response.json',
              type: 'fileBrowser',
              rootId: "fileList",
              success: function (elements, resData) {
            	  rawData = resData.objBeanList;
            	  
				  vspUtils.loadingMask.hide();
				  curPage = page;
				  
                  $("#wrapDiv").empty();
                  //append result to Div
				  //var $elememts = $(elements).hide();
                  $("#wrapDiv").append(elements);
				  //$elememts.slideUp(0).slideDown(2000);

                  if(null != rawData && 0 < rawData.length) {
	                  //--table sorter plugin
	                  $tableSorter = $("#fileList").tablesorter({
	                      sortList: [
	                          [0, 0]//,
	                          //[1, 0]//,
	                          //[2, 0]
	                      ],
	                      headers: {
	                          "1": {
	                              sorter: 'mbsorter'
	                          }
	                      }
	                  });
                  } else {
                	  if("" != keyword) {
                		  $.jGrowl('No result here');
                	  }
                  }
				  
                  //--itriFileBrowser plugin
                  $fileBrowser = $("#fileList").itriFileBrowser({
                      rawData: rawData,
                      bucketName: "${bucketName}",
                      folderStart: "${folderStart}",
                      //showScrollArea: true,
                      scrollSensitivity: {top: 180, bottom: 50},
                      onDrop: function (sel, target, selData, targetData) {
                    	//$.jGrowl("Moved " + selData[0].objectName + " to folder '" + target.find('.name').text() + "'");
                        sel.empty().remove();
                      },
                      onUpdateSel: function (sel, selData, isAdd, status) {
                  	  
                      },
                      onRename: function (htmlObj, objData, text, status) {
						  if('success' == status) {
                    		  $.jGrowl(objData.objectName + " renamed to " + text);
							  
							  loadFileData();
							  /*setTimeout(function(){
                        		  window.location.reload();
                        	  }, 1000);
							  
							  objData.objectName = text;*/							  
                    	  } else {
                    		  $.jGrowl(text + " is already existed");
                    	  }
                      }
                  });
				  
                //clone head
				utils.theadClone($("#fileList"), $("#tClone"));
				
				pageCount = resData.pagingInfo.totalpages;
				
				if (pageCount != 0) {
		              $("#jPaginate").paginate({
		                  count 					:	pageCount,
		                  start 					: 	1,
		                  display     				: 	5,
		                  border					: 	false,
		                  text_color  				: 	'#79B5E3',
		                  background_color    		: 	'none',	
		                  text_hover_color			: 	'#2573AF',
		                  background_hover_color	: 	'none', 
		                  images					: 	false,
		                  mouse						: 	'press',
		                  onChange     				: 	function(page) {
		                      loadFileData(page);
		                  }
		              });
		          }
              }
          });
      }

      var folderStart = "${folderStart}";
      if(folderStart != "") {
	  
    	  loadFileData();
    	  
    	  $('#addBtn').click(function() {
              $fileBrowser.addFolder({
                  img: $jsonParser.getImgSrc().folder,
                  name: "New Folder",
                  bucketName: "${bucketName}",
                  folderStart: "${folderStart}",
                  callback: function (text, status) {
                	  if('success' == status) {
                		  $.jGrowl("Folder " + text + " created here");
                	  } else {
                		  $.jGrowl(text + " is already existed");
                	  }
					  
					  loadFileData();
                  }
              });
          });
          
         $('#renameBtn').click(function() {
              $fileBrowser.rename({
            	  bucketName: "${bucketName}",
                  folderStart: "${folderStart}",
                  callback: function (htmlObj, objData, text, status) {
				  
                  }
              });
          });
         
         $('#deleteBtn').click(function() {
		 
			 //vspUtils.loadingMask.show();
			 
        	 $fileBrowser.delObj({
           	  	 bucketName: "${bucketName}",
                 folderStart: "${folderStart}",
                 callback: function (callbackText) {
               	 	$.jGrowl(callbackText);
					
               	 	loadFileData();
                 }
             });
         });
          
         $('#downloadBtn').click(function() {
             $fileBrowser.download({
           	  	 bucketName: "${bucketName}",
                 folderStart: "${folderStart}"
             });         
         });
         
         $('#moveBoxLink').click(function(e) {        	 
           	if(0 >= $fileBrowser.getSelections().length) {
           		e.preventDefault();
           		e.stopImmediatePropagation();
           	} else {
           		var selectedObjs = '';
           		for(var j=0; j< $fileBrowser.getSelections().length; j++) {
           			$tr = $("#"+ $fileBrowser.getSelections()[j].id);
       				$a = $tr.find('a');
       				selectedObjs = $a.text() + ',' +  selectedObjs;
           		}
       			
           		$.superbox.wait(function() {
           		  	$.get('../treeView/${bucketName}${folderStart}?srcObjects=' + selectedObjs, function(data) {
           		    	$.superbox.open(data);
           		  	});
           		});
           	}
         });
		 
      	 // for uploadBox
         $.superbox.settings = {
    			closeTxt: "Close",
    			overlayOpacity: .4,
    			boxWidth: "400",
    			boxHeight: "220",
    			boxClasses: "",
    			afterHideAll: loadFileData
    	   };
         $.superbox();
      }
	 
	  var tempMsgInfo = "${msgInfo}";
      if('' != tempMsgInfo) {
    	 $.jGrowl(tempMsgInfo);
      }
      
      $('#searchForm').submit(function() {
    	  if(folderStart == "") {
    		  return false;
    	  }
    	  
    	  if($('#searchKeyword').attr('value') == '') {
    		  $.jGrowl('Please enter keyword to search');
    		  return false;
    	  }
    	  
    	  return true;
      });
      /*
      $.get("../../sbx_svr/rest/EBS/listobjects?bucket=${bucketName}&page=1&pagesize=50&folder=${folderStart}", function(json) {
          if (json.pagingInfo.totalpages != 0) {
              $("#jPaginate").paginate({
                  count 					:	json.pagingInfo.totalpages,
                  start 					: 	1,
                  display     				: 	5,
                  border					: 	false,
                  text_color  				: 	'#79B5E3',
                  background_color    		: 	'none',	
                  text_hover_color			: 	'#2573AF',
                  background_hover_color	: 	'none', 
                  images					: 	false,
                  mouse						: 	'press',
                  onChange     				: 	function(page) {
                      loadFileData(page);
                  }
              });
          }
      }).fail(function(){});
	  */
	  var $pathField= $("#pathField");
      var $pathField2= $("#pathField2");
      var $dropDown = $pathField2.find(".dropdown-menu");
      var $pathMenuBtn = $pathField2.find(".pathMenuBtn");
      
      function parsePathField(){
        var showPathDeep = 3;
        var pathAry = [];

        $pathField.find("a").each(function(n,v){
            var pathObj = {
                text: $(this).text(),
                href: $(this).attr("href")
            }
            
            pathAry.push(pathObj);
        });
         
        var currentPath = $pathField.find("b").text();
            var currentPathObj = {
                text: currentPath,
                href: "#"
            }
            
            pathAry.push(currentPathObj);
      
        if (pathAry.length > showPathDeep){
            $pathMenuBtn.show();
            
            var str0 = "<li>" +
                                "<a class='pathA inMenu' href='" + pathAry[0].href  + "' title='" +  pathAry[0].text +"' ><i class='safeboxRoot icon-cloud-download'></i></a>" +
                       "</li>";
            $dropDown.prepend(str0);
              
            for (var i = 1; i < pathAry.length - showPathDeep;i++){
              var str = "<li>" +
                                 "<a class='pathA inMenu' href='" + pathAry[i].href  + "' title='" +  pathAry[i].text +"' >" + pathAry[i].text + "</a>" +
                        "</li>" ;
              $dropDown.prepend(str);
            }

            var str = "";
            
            for (pathAry.length - showPathDeep; i< pathAry.length -1; i++){
                str = str + "<span class='pathSpan'>/</span><a class='pathA' style='' href='" + pathAry[i].href + "' title='" + pathAry[i].text + "' >" + pathAry[i].text + "</a>";  
            }
            
            $pathField2.append(str);
        }

        else {
            $pathMenuBtn.hide();
        
            var str = "";
            
            var str0 = "<a class='pathA' style='' href='" + pathAry[0].href +"' title='" + pathAry[0].text + "' ><i class='safeboxRoot icon-cloud-download'></i></a>";
            $pathField2.append(str0);
              
            for (i = 1; i < pathAry.length - 1;i++) {
              str = str + "<span class='pathSpan'>/</span><a class='pathA' style='' href='" + pathAry[i].href + "' title='" + pathAry[i].text + "' >" + pathAry[i].text + "</a>";  
            }
            
            $pathField2.append(str);
        }
           
        str = "<span class='pathSpan'>/</span><span class='pathA' href='" + pathAry[pathAry.length - 1].href + "' title='" + pathAry[pathAry.length - 1].text + "' >" + pathAry[pathAry.length - 1].text + "</span>";  
        $pathField2.append(str);
      }
      
      parsePathField();
  });
  
        </script>
    </head>
  <body>
  
	<div id="loadingMask" class="image" style="display:none;">
		<div class='loading'></div> 
	</div>
	
        <div id= "layoutWrap">
            <div id="topPanel">
                <jsp:include page="/WEB-INF/views/included/topArea.jsp" />
                <div id="ctlArea" class="ctlArea">
                    <div class="wrap">
                        <table width="100%" border="0">
                            <tr>
							
                                <td id="pathField">
                                	/ <a href="files?bucket=${bucketName}"><c:out value="${bucketName}" /></a>
                                	<c:set var="folderPrefix" value="" />
                                	<c:forEach items="${folderList}" var="folder">
                                		/ <a href="files?bucket=${bucketName}&folder=${folderPrefix}/${folder}"><c:out value="${folder}" /></a>
                                		<c:set var="folderPrefix" value="${folderPrefix}/${folder}" />
                                	</c:forEach>
                                    / <b><c:out value="${folderEnd}" /></b>
                                </td>
                                
                                <td id="pathField2">
                                    <div class='pathMenuBtn' class="btn-group" style="margin:0px;height:15px;width:auto;  display:block; float:left; margin-right:3px;">
                                    <a class="btn dropdown-toggle btn-mini" data-toggle="dropdown" href="#">
                                       <span class="icon-folder-close"></span>
                                    ...
                                    </a>
                                    <ul class="dropdown-menu">
                                    </ul>
                                    </div>     
                        		</td>
								
                                <td width="200px" align="right">
                                    <img id="renameBtn" src="${pageContext.request.contextPath}/images/webicon_png/webmenu_rename18.png" title="rename" style="margin:7px 5px 0 0"/>&nbsp;
                                    <a id="moveBoxLink"><img id="moveBtn" src="${pageContext.request.contextPath}/images/webicon_png/webmenu_move18.png" title="move" style="margin:7px 5px 0 0"/></a>
									<img id="deleteBtn" src="${pageContext.request.contextPath}/images/webicon_png/web_delete_folder18.png" title="delete" style="margin:7px 5px 0 0"/>&nbsp;
									<img id="downloadBtn" src="${pageContext.request.contextPath}/images/webicon_png/web_downloadfolder18.png" title="download" style="margin:7px 5px 0 0"/>&nbsp;
                                    <img id="addBtn" src="${pageContext.request.contextPath}/images/webicon_png/web_addnewfolder18.png" title="add" style="margin:7px 5px 0 0"/>&nbsp;                                    <a href="#uploadBox" rel="superbox[ajax][../upload/form/${bucketName}${folderStart}]">
									<a href="#uploadBox" rel="superbox[ajax][../upload/form/${bucketName}${folderStart}]">
										<img id="uploadBtn" src="${pageContext.request.contextPath}/images/webicon_png/web_uploadfolder18.png" title="upload" style="margin:7px 5px 0 0"/>
									</a>
                                </td>
                                <td align="right" width="200">
                                <form id="searchForm" name="searchForm" method="get" action="${pageContext.request.contextPath}/pages/files">
                                	<input type="hidden" name="bucket" value="${bucketName}" />
                                	<input type="hidden" name="folder" value="${folderStart}" />
                                    <input type="text" placeholder="Search SafeBox" name="searchKeyword" id="searchKeyword" value="${searchKeyword}" />
                                    &nbsp;
                                    <input type="image" src="${pageContext.request.contextPath}/images/webicon_png/webmenu_search18.png" id="searchBtn" />
                                </form>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="cloneHeaderArea">
                    <div class="wrapDiv wrap">
                        <table id="tClone">
                        </table>
                    </div>
                </div>
                <!--div class="lineArea">
                </div-->
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
				
				<div id="jPaginate">
            	</div>
				
            </div>
            <div id="bottomPanel">
            </div>
        </div>
    </body>
</html>