<%-- 
    Document   : ispisKorisnika_3
    Created on : 11-Apr-2017, 14:36:46
    Author     : Matija
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ispis korisnika 3</title>
        <link href="${pageContext.servletContext.contextPath}/css/alternative.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Ispis korisnika 3</h1>
        <sql:setDataSource var="bp"
                           driver="${applicationScope.BP_Konfiguracija.driverDatabase}"
                           url="${applicationScope.BP_Konfiguracija.serverDB}${applicationScope.BP_Konfiguracija.userDatabase}"
                           user="${applicationScope.BP_Konfiguracija.userUsername}"
                           password="${applicationScope.BP_Konfiguracija.userPassword}" />
        <sql:query dataSource="${bp}" var="rs">
            select prezime,ime from polaznici
        </sql:query>

        <display:table name="${rs.rows}" pagesize="3">
            <display:column property="prezime"/>
            <display:column property="ime"/>
        </display:table>

    </body>
</html>
