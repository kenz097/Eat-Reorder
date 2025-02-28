<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
	AccountUtenteRegistrato_Bean utente = null;
	AccountCliente_Bean cliente = null;
	Boolean canContinue = true;
	utente = (AccountUtenteRegistrato_Bean) session.getAttribute("utente");
	if (utente == null || !utente.getTipo().equals(AccountUtenteRegistrato_Bean.Cliente)) {
		response.sendRedirect("Homepage.jsp");
		canContinue = false;
	}
	if (canContinue) {
		cliente = (AccountCliente_Bean) utente;
	}
%>

<!DOCTYPE html>
<html>
<head>
<!--Viewport for toggle-->
<meta charset="utf-8">
<meta name="viewport"content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!--Title-->
<title>Modifica Profilo</title>
<!--Preset imports-->
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/Login-Form-Clean.css">
<link rel="stylesheet" href="assets/css/styles.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!--Custom imports-->
<link rel="stylesheet" href="css/eat-reorder-style.css">
<%@page import="model.bean.AccountUtenteRegistrato_Bean"%>
<%@page import="model.bean.AccountCliente_Bean"%>
</head>

<!--Start of body-->
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<%
		if (canContinue) {
	%>
	<!--Form for registration-->
	<div class="container-form-floating">
		<form class="custom-border-red login-form-style" action="DoModificaProfiloCliente" method="post"onsubmit="return checkModifica();">
			<div class="registration-title">Modifica il tuo profilo</div>
			<!--Image on the form-->
			<img id="image-login-form" src="assets/img/LogomarcoIS%20PNG.png">
			<!--Form group that contains fields and confirm button-->
			<div class="form-group">
				<label class="std-label">Nome</label>
				<input class="custom-border-red border-rounded-small form-control input-style-login"type="text" name="nome" placeholder="Nome"  value="<%=cliente.getNome()%>">
				<label class="std-label">Cognome</label>
				<input class="custom-border-red border-rounded-small form-control input-style-login"type="text" name="cognome" placeholder="Cognome" value="<%=cliente.getCognome()%>"> 
				<label class="std-label">Password</label>
				<input class="custom-border-red border-rounded-small form-control input-style-login" type="password" name="password" placeholder="Password" id="password"   value="<%=cliente.getPassword()%>">
				<p class="errorText" style="margin-top: 20px;">
					<% if (request.getAttribute("msg_error") != null){ %>
						<%=request.getAttribute("msg_error") %>
					<% } %>
				</p>
				<button class="btn form-list-button bg-red border-rounded-small"type="submit">Modifica</button>
			</div>
		</form>
	</div>
	<%
		}
	%>
	<!-- SCRIPT -->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		function checkModifica() {
			var password = document.getElementById("password");
			var nome = document.getElementById("nome");
			var cognome = document.getElementById("cognome");
			if (password.value == "" || nome.value == ""||cognome.value == "") {
				alert("ATTENZIONE! Errore nella validazione dei campi!")
				return false;
			}
			return true;
		}
	</script>
	<%@include file="Footer.html"%>
</body>
</html>