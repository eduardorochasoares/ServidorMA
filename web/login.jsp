<%-- 
    Document   : login
    Created on : 17/01/2016, 19:20:47
    Author     : Principal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/formatacao.css" rel="stylesheet" type="text/css"/>
         
    </head>
    <body>
        <form method="get" action ="LoginController">
            <body background="imagens/entrada.jpg">
                <table class="form">
                    <td class="cabecalho">ENTRAR</td>
                    <tr>
                        <td>
                            <table>
                                <form name="form_acesso" method="post">
                                    <tr>
                                        <td>Login:
                                        <td><input name="user" type="text" size="10" maxlength="10"></td>
                                    </tr>
                                    <tr>
                                        <td>Senha:
                                        <td><input name="senha" type="password" size="15" maxlength="15"
                                                   style="border-radius:2px; border:1px solid #000000; width:100%; padding: 5px;"></td>
                                    </tr>
                                </form>
                            </table>
                        </td>
                    <tr>
                        <td class="centro"><input type="submit" name="Button" value="Entrar" >
                        </td>
                </table>
        </form>
    </body>
</html>
