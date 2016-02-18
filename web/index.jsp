<%-- 
    Document   : index
    Created on : 17/01/2016, 21:04:04
    Author     : Principal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%
    HttpSession sessao = request.getSession();
    String usuario = ""; // É bom iniciar a variável ANTES de pegar o valor da sessão
    usuario = (String) sessao.getAttribute("user");
    if (usuario == null) {
        response.sendRedirect("login.jsp");

    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Usuário</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/formatacao.css">
    </head>
    <body class="admin">
        <table class="form">
            <td class="cabecalho">Medição de Audiência IPTV</td>
            <tr>
                <td class="centro">Área restrita | Usuário: <b><%out.print(request.getSession().getAttribute("user"));%></b></td>
            </tr>
            <tr>
                <td class="centro">
                    <input type="button" value="Criar Pedidos de Medição de Audiência"
                           onClick="location.href = 'createRequest.jsp'">
                </td>
            </tr>

            <tr>
                <td class="centro">
                    <input type="button" value="Visualizar Pedidos"
                           onClick="location.href = 'visualizarRequests.jsp'">
                </td>
            </tr>

        </table>
        <p align="center"><input type="button" value="Sair"
                                 onClick="location.href = 'destroySession.jsp'"></p>
    </body>
</html>
