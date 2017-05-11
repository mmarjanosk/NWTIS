<%-- 
    Document   : ispisKorisnika_2
    Created on : 11-Apr-2017, 14:36:46
    Author     : Matija
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ispis korisnika 2</title>
    </head>
    <body>
        <h1>Ispis korisnika 2</h1>
        <sql:setDataSource var="bp"
                           driver="${applicationScope.BP_Konfiguracija.driverDatabase}"
                           url="${applicationScope.BP_Konfiguracija.serverDB}${applicationScope.BP_Konfiguracija.userDatabase}"
                           user="${applicationScope.BP_Konfiguracija.userUsername}"
                           password="${applicationScope.BP_Konfiguracija.userPassword}" />
        <sql:query dataSource="${bp}" var="rs">
            select prezime,ime from polaznici
        </sql:query>

        <table border="1">
            <tr><td>Prezime</td><td>Ime</td></tr>
            <c:forEach var="redak" items="${rs.rows}">
                <tr>
                    <td><c:out value="${redak.prezime}"/></td>
                    <td><c:out value="${redak.ime}"/></td>
                </tr>
            </c:forEach>
        </table>    

    </body>
</html>
