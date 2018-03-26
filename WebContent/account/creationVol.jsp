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
	<form method="post" action="<c:url value="/account/enregistrementVol" />">
            <fieldset>
                <legend>Enregistrement d'un vol</legend>
                <p>Enregistrer vos vols via ce formulaire</p>
                <c:set var='session' value='${sessionScope}'/>
                <p>Utilisateur connecté : ${session.sessionUtilisateur.nom_aeroclub}</p>
                
                <label for="date">Date <span class="requis">*</span></label>
                <input type="date" id="date" name="date" value="<c:out value=""/>" size="4" maxlength="2" />
                <span class="erreur">${form.erreurs['date']}</span>
                <br />
                
                <label for="time">heure <span class="requis">*</span></label>
                <input type="time" id="time" name="time" value="<c:out value=""/>" size="4" maxlength="2" />
                <span class="erreur">${form.erreurs['time']}</span>
                <br />
                
                <label for="listAircrafts">Immatriculation <span class="requis">*</span></label>
                <select name="listAircrafts" id="listAircrafts">
                	<option value="">immatriculation...</option>
                    <%-- Boucle sur la map des clients --%>
                    <c:forEach items="${ sessionScope.sessionAircraftList }" var="mapAircraft">
                    <%--  L'expression EL ${mapClients.value} permet de cibler l'objet Client stocké en tant que valeur dans la Map, 
                    et on cible ensuite simplement ses propriétés nom et prenom comme on le ferait avec n'importe quel bean. --%>
                    <option value="${ mapAircraft.key }">${ mapAircraft.value.immatriculation }</option>
                    </c:forEach>
                </select>
                <br />
                                
                <label for="Flight_Hours">Heures de vol <span class="requis">*</span></label>
                <input type="text" id="Flight_Hours" name="Flight_Hours" value="<c:out value=""/>" size="5" maxlength="5" />
                <!-- <span class="erreur">${form.erreurs['date']}</span> -->
                <br />
                
                <label for="Flight_Cycle">Cycle <span class="requis">*</span></label>
                <input type="text" id="Flight_Cycle" name="Flight_Cycle" value="<c:out value=""/>" size="5" maxlength="5" />
                <!-- <span class="erreur">${form.erreurs['date']}</span> -->
                <br />
                
                <label for="huile">Huile <span class="requis">*</span></label>
                <input type="text" id="huile" name="huile" value="<c:out value=""/>" size="5" maxlength="5" />
                <!-- <span class="erreur">${form.erreurs['date']}</span> -->
                <br />
                
                <label for="carburant">Carburant <span class="requis">*</span></label>
                <input type="text" id="carburant" name="carburant" value="<c:out value=""/>" size="5" maxlength="5" />
                <!-- <span class="erreur">${form.erreurs['date']}</span> -->
                <br />
                
                <label for="remarques">Remarque</label>
                <textarea name="remarque" id="remarque"></textarea>
                <br />
                <br />
                <input type="submit" value="Enregistrer" class="sansLabel" />
                <br />
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                
            </fieldset>
    </form>
</body>
</html>