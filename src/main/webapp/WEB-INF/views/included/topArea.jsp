<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="tw.org.itri.ccma.css.safebox.model.Member" %>
<div  class="topArea">
      <div class="logo"> <img src="../images/img/small-logo.png"></div>
      <div class="logotxt" style="right:10px; top:0px; position:absolute; width:auto;">
      	Welcome! <span class="hint"><%= ((Member) session.getAttribute("member")).getUserName() %></span>
      	<a href="../logout">logout</a> 
      </div>
      <div class="verisonText" style="right:10px; top:25px; position:absolute; width:auto;"><!-- empty here --></div>
</div>

<div class="tabArea">
	<div id="tabs" style="height:auto;">
	</div>
</div>