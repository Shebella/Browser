<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <title>SafeBox</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link href="../css/reset.css" rel="stylesheet" type="text/css" />
        <link href="../css/cupertino/jquery-ui-1.8.19.custom.css" type="text/css" rel="stylesheet"  />
        <link href="../css/safebox_css.css" type="text/css" rel="stylesheet">
        <link href="../css/itriFileBrowser.css" rel="stylesheet" type="text/css" />
        <link href="../css/blue/style.css" rel="stylesheet" type="text/css" />
        <link href="../css/jquery.jgrowl.css" rel="stylesheet" type="text/css" />
        <jsp:include page="/WEB-INF/views/included/jsArea.jsp" />
        <script type="text/javascript">  utils.pageInit({tabId:'help'}); </script>
        <script src="../js/jquery.itriCSSJsonParser_help.js" type="text/javascript"></script>
        <script type="text/javascript">
        $(function(){
             $jsonParser = $.itriCSSJsonParser_help({
              url: '../js/response_help.json',
              type: 'tableList',
              rootId: "tableList",   
              success: function(elements, rawData){
                  $("#wrapDiv").empty();
                  $("#wrapDiv").append(elements);
  
                  
                  //clone head
				  utils.theadClone($("#tableList"), $("#tClone"));
                  
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
            <div id="wrapDiv" class="innerTableDiv wrapDiv"></div>
        </div>
        <div id="bottomPanel"></div>
      </div>
    </body>
</html>