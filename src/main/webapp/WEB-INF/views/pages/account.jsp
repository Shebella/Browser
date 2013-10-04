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
        <script src="../js/jquery.itriCSSJsonParser_account.js" type="text/javascript"></script>
        <script type="text/javascript">  utils.pageInit({tabId:'account'}); </script>       
    </head>
    <body>
        <div id= "layoutWrap">
            <div id="topPanel">
            	<jsp:include page="/WEB-INF/views/included/topArea.jsp" />
            </div>
            <div id="leftPanel">
            </div>
            <div id="contentPanel" />
            <div id="fakeHeader">
            </div>
            <div id="wrapDiv" class="innerTableDiv wrapDiv">
            </div>
            <div id="blank">
            </div>
            <div style="width:80%; margin: 0 auto; margin-top:20px;" id="progressDiv">
			
                <div style="font-size:14px; margin:5px;color:#696969;" align="center">
                    Totally storage used <c:out value="${usedPer}" /> % (<c:out value="${usedSize}" /> MB of <c:out value="${totalSize}" />)
                </div>
				
                <div>
                    <table style="width:100%; height:20px; border-style: solid;border-width: thin; border: solid 1px #000000;">
                        <tr>
						
                            <td width="<c:out value="${usedPer}" />%" style="background:#71d6f7">
                            </td>
							
                            <td>
                            </td>
                        </tr>
                    </table>
                </div>
                <div>
                </div>
                <div id="bottomPanel">
                </div>
            </div>
		</div>
	</body>
</html>