<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <title>SafeBox</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link type="text/css" href="Scripts/safebox_css.css" rel="stylesheet" />
		<script type="text/javascript" src="js/safeboxComponents.js"></script>
        <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
        <script type="text/javascript" src="js/utils.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.8.19.custom.min.js"></script>
        <style type="text/css">
            .help_link {
                font-family: sans-serif;
                font-size: 10px;
                color: #666;
            }
            
            .download {
                font-family: sans-serif;
                font-size: 12px;
                color: #666;
            }
            
            .logotxt {
                font-family: Verdana, Geneva, sans-serif;
                font-size: 11px;
                font-style: normal;
                color: #09F;
                margin-top: 20px;
            }
            
            .hint {
                font-family: Arial, Helvetica, sans-serif;
                font-size: 12px;
                font-style: normal;
                color: #999;
            }
        </style>
        <script type="text/javascript">
        $(function(){
			if ($("#msgInfo").attr("type") == "login failed") {
				$('#loginForm').effect("shake", {}, "fast");
			}
			
            $("#loginBtn").click(function(){
        	   $('#loginForm').submit();
            });
        });
		
	var wait=false;
	function changeCursor(i){
		wait=i;
		if(i){
			document.body.style.cursor='wait';
		}else{
			document.body.style.cursor='default';
		}
	}
	function getLinkByOS(){
		if(navigator.appVersion.indexOf("Windows")!=-1){
			return "${downloadLinkWindows}";
		}else{
			return "${downloadLinkLinux}";
		}
	}
	function downloadClick(){		if(!wait){
			document.getElementById('appletFrame').src=getLinkByOS();
			changeCursor(true);
			setTimeout("changeCursor(false);",10000);
		}
	}

        </script>
    </head>
    <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('images/img/enter_over.png','images/img/download_over.png','images/img/noupdate_over.png');">
    	<form id="loginForm" name="loginForm" method="post" action="${pageContext.request.contextPath}/login">
        <table width="600" height="258" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td colspan="5" height="100">
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td colspan="5" align="center">
                    <img src="images/img/safebox_logo/splash.png" width="400" height="400">
                </td>
            </tr>
            <tr>
                <td width="300">
                    &nbsp;
                </td>
                <td>
                    &nbsp;
                </td>
                <td width="80">
                    &nbsp;
                </td>
                <td width="70" align="right">
                    <a href="${faqLink}" target="_blank" class="help_link">Get help?</a>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;
                </td>
                <td colspan="2">
				<span id="msgInfo" type="${msgInfo}">
                	<c:out value="${msgInfo}" />
				</span>
                    <p>
                        <label for="textfield3"></label>
                        <input type="text" placeholder="Username" name="userId" id="userId"   style="width:100pt"/>
                    </p>
                    <p>
                        <label for="textfield"></label>
						<input name="password" placeholder="Password" type="password" id="password"  style="width:100pt"/>
					</p>
				</td>
				<td>
                	<input type="image" id="loginBtn" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('enter','','images/img/enter_over.png',1);" src="images/img/enter_up.png" width="38" height="38" border="0" align="left" /></td>
                <td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
            	<td>&nbsp;</td>
                            <td colspan="3" align="right">
                                <span class="hint">Don't have</span>
                                <strong><span class="logotxt">SAFEBOX</span></strong>&nbsp;<span class="hint">yet?</span>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td colspan="3" valign="top">
                                <a onClick="downloadClick();" onMouseOut="MM_swapImgRestore();" onMouseOver="MM_swapImage('download','','images/img/download_over.png',1);"><img src="images/img/download_up.png" name="download" width="183" height="47" border="0"></a><a onClick="downloadClick();" onMouseOut="MM_swapImgRestore();" onMouseOver="MM_swapImage('update','','images/img/noupdate_over.png',1);"><img src="images/img/noupdate_up.png" name="update" width="66" height="47" border="0"></a></td>
                            <td width="320">&nbsp;</td>
                        </tr>
						<tr>
                            <td>
                                &nbsp;
                            </td>	
						</tr>							
                        <tr>
                            <td>
                                &nbsp;
                            </td>
                            <td colspan="3" align="center">
                                <span class="logotxt">Once the download has finished, click "Run" to install Safebox.</span>
                            </td>
                            <td>
                                &nbsp;
                            </td>
                        </tr>		
								
                                </table>
                                </form>
                                <iframe id="appletFrame" name="appletFrame" width=0 height=0></iframe>
                            </body>
</html>
