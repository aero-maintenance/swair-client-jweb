<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Enregistrement d'un vol</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<form method="post" action="<c:url value="/enregistrementVol" />">
            <fieldset>
                <legend>Enregistrement d'un vol</legend>
                <p>Enregistrer vos vols via ce formulaire</p>
                
                <label for="date">Date <span class="requis">*</span></label>
                <input type="text" id="date" name="date" value="<c:out value="${vol.date_heure}"/>" size="4" maxlength="2" />
                <span class="erreur">${form.erreurs['date']}</span>
                <br />
                
                <label for="immatriculation">Immatriculation <span class="requis">*</span></label>
                <input type="text" id="immatriculation" name="immatriculation" value="<c:out value="${immatricalation}"/>" size="20" maxlength="20" />
                <!--<span class="erreur">${form.erreurs['date']}</span>-->
                <br />
                
                <label for="FH">FH <span class="requis">*</span></label>
                <input type="text" id="FH" name="FH" value="<c:out value="${vol.FH}"/>" size="5" maxlength="5" />
                <!-- <span class="erreur">${form.erreurs['date']}</span> -->
                <br />
                
                <label for="FC">FC <span class="requis">*</span></label>
                <input type="text" id="FC" name="FC" value="<c:out value="${vol.FC}"/>" size="5" maxlength="5" />
                <!-- <span class="erreur">${form.erreurs['date']}</span> -->
                <br />
                
                <label for="huile">Huile <span class="requis">*</span></label>
                <input type="text" id="huile" name="huile" value="<c:out value="${vol.huile}"/>" size="5" maxlength="5" />
                <!-- <span class="erreur">${form.erreurs['date']}</span> -->
                <br />
                
                <label for="carburant">Carburant <span class="requis">*</span></label>
                <input type="text" id="carburant" name="carburant" value="<c:out value="${vol.carburant}"/>" size="5" maxlength="5" />
                <!-- <span class="erreur">${form.erreurs['date']}</span> -->
                <br />
                
            </fieldset>
    </form>
</body>
</html>