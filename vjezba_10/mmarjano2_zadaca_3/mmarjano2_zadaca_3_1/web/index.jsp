<%-- 
    Document   : index
    Created on : Apr 25, 2017, 5:50:22 PM
    Author     : grupa_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Unos IOT uređaja</h1>
         <table>
        <form action="${pageContext.servletContext.contextPath}/DodajUredaj" method="POST">
            
                <tr>
                    <td>
            <label for="naziv">Naziv : </label>
            </br>
            <input id="naziv" name="naziv" value=""/>
            </br>
            <label for="adresa">Adresa : </label>
            </br>
            <input id="adresa" name="adresa"/>
            </br>
                            </td><td>
            <input type="submit"  name="geolokacija" value="Geo Lokacija" />
            </br>
            <input type="submit"  name="spremi" value="Spremi" />
            </br>
            <input type="submit"  name="meteoPodaci" value="Meteo podaci" />
            </br>
                    </td>
                <tr>
           
             </table>
        </form>
          
            </br>
            <input size="60px" value="${lokacija}" readonly/>
            </br>
         Trenutna temp je ${temp} C
         </br>
         Vlažnost je ${vlaga} %
         </br>
         Tlak zraka je ${tlak} hPa
         </br>
         ${error}
         </br>
    </body>
</html>
