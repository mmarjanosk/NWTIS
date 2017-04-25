<%-- 
    Document   : login
    Created on : 11-Apr-2017, 13:48:09
    Author     : Matija
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prijavljivanje korisnika</title>
    </head>
    <body>
        <h1>Prijavljivanje korisnika</h1>
        <form method="POST" action="${pageContext.servletContext.contextPath}/ProvjeraKorisnika"> 
            Korisničko ime : <input name="korisnickoIme"/></br>
            Lozinka : <input name="lozinka" type="password"/></br>
            <input type="submit" value="Prijavi se"/>
        </form>
    </body>
</html>
