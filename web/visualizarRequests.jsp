<%-- 
    Document   : visualizarRequests
    Created on : 28/01/2016, 11:22:54
    Author     : Principal
--%>

<%@page import="DAO.ConexaoMySQL"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAO.MeasurementRequestDAO"%>
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
        <link href="css/formatacao.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body background="imagens/consultar-receitas.jpg">
        <form method="POST" action="consulta_itens.php" name="carrinho">
            <table class="lista">
                <tr>
                    <td class="cabecalho">Pedidos de medição</td>
                </tr>
                <tr>
                    <td>
                        <table>
                            <%
                                    HttpSession ss = request.getSession();
                                    String login = "";
                                    login = (String) ss.getAttribute("user");
                                    ResultSet rs = new MeasurementRequestDAO().getMeasurementRequest(login);
                                    if (rs == null) {
                                        out.print("<tr>");
                                        out.print("<td class=\"centro\">");
                                        out.print("Nenhuma requisição!");
                                        out.print("</td>");
                                    } else {
                                        out.print("<tr>");
                                        out.print("<td class=\"centro\"><b>Obscuration</b></td>");
                                        out.print("<td class=\"centro\"><b>View Mode</b></td>");
                                        out.print("<td class=\"centro\"><b>Control Device</b></td>");
                                        out.print("<td class=\"centro\"><b>Nav Method</b></td>");
                                        out.print("<td class=\"centro\"><b>Dias</b></td>");
                                        out.print("<td class=\"centro\"><b>Início</b></td>");
                                        out.print("<td class=\"centro\"><b>Término</b></td>");

                                        while (rs.next()) {
                                            out.print("<tr>");
                                            out.print("<td class=\"centro\">" + rs.getInt("obscuration") + "</td>");
                                            out.print("<td class=\"centro\">" + rs.getInt("viewmode") + "</td>");

                                            out.print("<td class=\"centro\">" + rs.getInt("controldevice") + "</td>");

                                            out.print("<td class=\"centro\">" + rs.getInt("navmethod") + "</td>");
                                            int id = rs.getInt("id");
                                            ResultSet r = new MeasurementRequestDAO().getPeriod(id);
                                            while (r.next()) {
                                                out.print("<td class=\"centro\">" + r.getString("dia") + "</td>");

                                            }
                                            ResultSet rt;
                                            int idSched = rs.getInt("measurementschedule.id");
                                            rt = new MeasurementRequestDAO().getHours(idSched);

                                            while (rt.next()) {
                                                out.print("<td class=\"centro\">" + rt.getString("startTime") + "</td>");
                                                out.print("<td class=\"centro\">" + rt.getString("endTime") + "</td>");

                                            }
                                        }

                                    }

                                


                            %>

                        </table>
                        <input type="hidden" name="form_operacao" value="carrinho">
                    </td>
                </tr>
            </table>
        </form>
        <p align="center"><a href="javascript:history.go(-1);">Voltar</a> | <a href="index.jsp">Página inicial</a></p>
    </body>
</html>
