<%-- 
    Document   : index
    Created on : 19/12/2015, 15:41:30
    Author     : Eduardo
--%>



<%@page import="DAO.CanaisDAO"%>
<%@page import="DAO.DiasDAO"%>
<%@page import="java.util.List"%>
<%@page import="Model.MeasurementRequest.ChannelList"%>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Measurement Request for IPTV</title>
        <script language="javascript" type="text/javascript" src="Jquery/jquery-1.11.3.js"></script>
        <link rel="stylesheet" type="text/css" href="css/formatacao.css">





        <script language="javascript">
            function DataHora(evento, objeto) {
                var keypress = (window.event) ? event.keyCode : evento.which;
                campo = eval(objeto);
                if (campo.value === '00/00/0000 00:00:00')
                {
                    campo.value = "";
                }

                caracteres = '0123456789';
                separacao1 = '/';
                separacao2 = ' ';
                separacao3 = ':';
                conjunto1 = 2;
                conjunto2 = 5;
                conjunto3 = 10;
                conjunto4 = 13;
                conjunto5 = 16;
                if ((caracteres.search(String.fromCharCode(keypress)) !== -1) && campo.value.length < (19))
                {
                    if (campo.value.length === conjunto1)
                        campo.value = campo.value + separacao1;
                    else if (campo.value.length === conjunto2)
                        campo.value = campo.value + separacao1;
                    else if (campo.value.length === conjunto3)
                        campo.value = campo.value + separacao2;
                    else if (campo.value.length === conjunto4)
                        campo.value = campo.value + separacao3;
                    else if (campo.value.length === conjunto5)
                        campo.value = campo.value + separacao3;
                }
                else
                    event.returnValue = false;
            }
            function desabilitarDiv(objeto, s) {
                if (objeto.selectedIndex !== 2) {
                    document.getElementById(s).style.visibility = 'hidden';
                } else {
                    document.getElementById(s).style.visibility = 'visible';
                }
            }

            function dois_pontos(event, tempo) {

                if (event.keyCode < 48 || event.keyCode > 57) {

                    event.returnValue = false;
                }

                if (tempo.value.length === 2 || tempo.value.length === 5 || tempo.value.length === 8) {

                    tempo.value += ":";
                }

            }

            function valida_horas(tempo) {

                horario = tempo.value.split(":");
                var horas = horario[0];
                var minutos = horario[1];
                var segundos = horario[2];
                if (horas > 24) { //para relógio de 12 horas altere o valor aqui

                    alert("Horas inválidas");
                    event.returnValue = false;
                    tempo.focus();
                }

                if (minutos > 59) {

                    alert("MINUTOS inválidos");
                    event.returnValue = false;
                    tempo.focus();
                }

                if (segundos > 59) {

                    alert("Segundos inválidos");
                    event.returnValue = false;
                    tempo.focus();
                }

            }
        </script>
    </head>
    <body>



        <form name="request" method="post" action ="CriarRequestController">

            <table id ="a"  class="form">

                <tr>
                    <td> O que medir? : </td>

                </tr>
                <tr>
                    <td> Nav Method? </td>
                    <td> <input type="checkbox" name="nav" value="ON"  /></td>
                </tr>
                <tr>
                    <td> Control Device</td>
                    <td>
                        <select name="controlDevice">
                            <option >0 - STB remote control</option>
                            <option>1 - STB </option>
                            <option>2 - STB keyboard</option>
                            <option>10 - PC </option>
                            <option>20 - Tablet</option>
                            <option>30 - Mobile Phone </option>
                            <option>40 - Other</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td> View Mode </td>
                    <td> <input type="checkbox" name="viewMode" value="ON"  /></td>
                </tr>
                <tr>
                    <td> Obscuration</td>
                    <td> <input type="checkbox" name="obscuration" value="ON"  /></td>

                </tr>
                <tr>
                    <td>
                        Canais a serem mensurados
                    </td>
                    <td> <select name="canais" multiple="true">
                            <option>Selecione os canais...</option>
                            <%
                                ChannelList cl = CanaisDAO.getChannels();
                                for (String s : cl.getServiceIdentifier()) {
                            %>
                            <option><%=s%></option>
                            <%}%>
                        </select>
                </tr>

                <tr>
                    <td> Dia da semana : </td>
                    <td>  <select name="days" multiple="true">
                            <option>Selecione os dias...</option>
                            <%

                                List<String> g = DiasDAO.getDias();
                                for (String a : g) {
                            %>
                            <option><%=a%></option>
                            <%}%>
                        </select> </td>
                </tr>
                <tr>
                    <td> Horários de início (separados por '/') </td>
                    <td> <input type = "text" name = "startTime"  /></td>
                </tr>
                <tr>
                <tr>
                    <td> Horários de término (Separados por '/')</td>
                    <td> <input type = "text" name = "endTime"  /></td>
                </tr>
                <tr>
                    <td>
                        Periodicity (in seconds)
                    </td>
                    <td>
                        <input type="text" name="periodicity"/>
                    </td>
                </tr>
                <tr>
                    <td> Data de efetividade :</td>
                    <td> <input type ="text" name = "effectiveTime" onKeyPress="DataHora(event, this)" maxlength="19"/> </td>
                </tr>
                <tr><td><input type="submit" name="submeter" value="Próximo" /></td></tr>
            </table>

            
        </form>
        <p align="center"><a href="javascript:history.go(-1);">Voltar</a> | <a href="index.jsp">Página inicial</a></p>
    </body>
</html>
