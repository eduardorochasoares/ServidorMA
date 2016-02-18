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



       
            <table id ="a"  class="form">




                <tr>


                <tr>
                <tr>
                    <td>Data e hora de efetividade :</td>
                    <td><input type = "text" name="dataEfetividade" onKeyPress="DataHora(event, this)"/> </td>

                </tr>

                <tr>
                    <td> O que reportar : </td>

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
                    <td> <select name="days" multiple="true">
                            <option>Selecione os canais...</option>
                            <%

                                List<String> s = DiasDAO.getDias();
                                for (String a : s) {
                            %>
                            <option><%=a%></option>
                            <%}%>
                        </select>
                </tr>

                <tr>
                    <td> Horários de início (Separados por '/')</td>
                    <td> <input type = "text" name = "startTime"   ></td>
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
                    <td>
                        Delivery Adress(Separados por /):

                    </td>
                    <td> <input type ="text" name ="deliveryAddres"/>
                </tr>
                <tr> 
                    <td>Retransmit Number : </td>
                    <td> <input type="text" name = "retransmitNumber" </td>
                </tr>
                <tr>
                    <td>
                        Measurement Report Number By Push :
                    </td>
                    <td> <input type="number" name="reportNumberByPush"</td>
                </tr>
                <tr>
                    <td> Max Time Between Delivery</td>
                    <td> <input type="number" name="maxtime"</td>
                </tr>



            </table> 
            <div style="width: 100%; margin-left:30%">
                <h1> Dados que deseja que sejam medidos futuramente</h1>
            </div>

            <table>
                <tr>
                    <td> Opção : </td> 
                    <td>
                        <select name="opc2"  selected  onchange="desabilitarDiv(this, 'b');">
                            <option>1 - No AM required</option>
                            <option>2 - No Conf Package</option>
                            <option>3 - Conf Package Present:</option>
                        </select>
                    </td>
                </tr>

            </table>
            <table id ="b">




                <tr>


                <tr>
                <tr>
                    <td>Data e hora de efetividade :</td>
                    <td><input type = "text" name="dataEfetividade2" onKeyPress="DataHora(event, this)"/> </td>

                </tr>

                <tr>
                    <td> O que reportar : </td>

                </tr>
                <tr>
                    <td> Nav Method? </td>
                    <td> <input type="checkbox" name="nav2" value="ON"  /></td>
                </tr>
                <tr>
                    <td> Control Device</td>
                    <td>
                        <select name="controlDevice2">
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
                    <td> <input type="checkbox" name="viewMode2" value="ON"  /></td>
                </tr>
                <tr>
                    <td> Obscuration</td>
                    <td> <input type="checkbox" name="obscuration2" value="ON"  /></td>

                </tr>
                <tr>
                    <td>
                        Canais a serem mensurados
                    </td>
                    <td>  <select name="canais2" multiple="true">
                            <option>Selecione os canais...</option>
                            <%

                                ChannelList cl2 = CanaisDAO.getChannels();
                                for (String f : cl2.getServiceIdentifier()) {
                            %>
                            <option><%=f%></option>
                            <%}%>
                        </select> </td>
                </tr>
                <tr>
                    <td> Dia da semana : </td>
                    <td>  <select name="days2" multiple="true">
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
                    <td> <input type = "text" name = "startTime2"  /></td>
                </tr>
                <tr>
                <tr>
                    <td> Horários de término (Separados por '/')</td>
                    <td> <input type = "text" name = "endTime2"  /></td>
                </tr>
                <tr>
                    <td>
                        Periodicity (in seconds)
                    </td>
                    <td>
                        <input type="text" name="periodicity2"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Delivery Adress(Separados por /):

                    </td>
                    <td> <input type ="text" name ="deliveryAddres2"/>
                </tr>
                <tr> 
                    <td>Retransmit Number : </td>
                    <td> <input type="text" name = "retransmitNumber2" </td>
                </tr>
                <tr>
                    <td>
                        Measurement Report Number By Push :
                    </td>
                    <td> <input type="number" name="reportNumberByPush2"</td>
                </tr>
                <tr>
                    <td> Max Time Between Delivery</td>
                    <td> <input type="number" name="maxtime2"</td>
                </tr>



            </table> 
            <table>

                <tr>
                    <td>
                        Configuration Package Check Delay(days) :
                    </td>
                    <td>
                        <input type="number" name ="delay"/>
                    </td>
                </tr>
            </table>
            <input type ="submit" name="submter" value="Next"/> 

        </form>
    </body>
</html>
