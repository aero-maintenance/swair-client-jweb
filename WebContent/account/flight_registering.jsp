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
	<c:import url="/inc/menu.jsp" ></c:import>
	<form method="post" action="<c:url value="/enregistrementVol" />">
            <fieldset>
                <legend>Enregistrement d'un vol</legend>
                <p>Enregistrer vos vols via ce formulaire</p>
                <c:set var='session' value='${sessionScope}'/>
                <p>Utilisateur connecter : ${session.sessionUtilisateur.nom_aeroclub}</p>
                
                <label for="date">Date <span class="requis">*</span></label>
                <input type="date" id="date" name="date" value="<c:out value="${vol.date_heure}"/>" size="4" maxlength="2" />
                <span class="erreur">${form.erreurs['date']}</span>
                <br />
                
                <label for="time">heure <span class="requis">*</span></label>
                <input type="time" id="time" name="time" value="<c:out value="${vol.date_heure}"/>" size="4" maxlength="2" />
                <span class="erreur">${form.erreurs['heure']}</span>
                <br />
                
                <label for="listAircrafts">Immatriculation <span class="requis">*</span></label>
                <select name="listAircrafts" id="listAircrafts">
                	<option value="">immatriculation...</option>
                    <%-- Boucle sur la map des clients --%>
                    <c:forEach items="${ sessionScope.clients }" var="mapClients">
                    <%--  L'expression EL ${mapClients.value} permet de cibler l'objet Client stocké en tant que valeur dans la Map, 
                    et on cible ensuite simplement ses propriétés nom et prenom comme on le ferait avec n'importe quel bean. --%>
                    <option value="${ mapClients.key }">${ mapClients.value.email }</option>
                    </c:forEach>
                </select>
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
                <br />
                <input type="submit" value="Enregistrer" class="sansLabel" />
                <br />
                
            </fieldset>
    </form>
</body>
</html>