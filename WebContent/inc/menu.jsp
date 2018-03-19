<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="menu">
    <table>
    	<tr>
        	<th>Nom aeroclub</th>
            <th>Adresse</th>
            <th>Ville</th>
            <th>Code Postale</th>
            <th>Email</th>
            <th>Téléphone</th>
            <th>Email</th>
            
            <th class="action">Action</th>                    
                </tr>
                <%-- Parcours de la Map des clients en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ sessionScope.utilisateur }" var="mapUtilisateurs" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage des propriétés du bean Client, qui est stocké en tant que valeur de l'entrée courante de la map --%>
                    <td><c:out value="${ mapUtilisateurs.value.nom_aeroclub }"></c:out></td>
                    <td><c:out value="${ mapUtilisateurs.value.adresse }"></c:out></td>
                    <td><c:out value="${ mapUtilisateurs.value.ville }"></c:out></td>
                    <td><c:out value="${ mapUtilisateurs.value.code_postale }"></c:out></td>
                    <td><c:out value="${ mapUtilisateurs.value.email }"></c:out></td>
                    <td><c:out value="${ mapUtilisateurs.value.telephone }"></c:out></td>
                    
                    
                </tr>
                </c:forEach>
            </table>
</div>