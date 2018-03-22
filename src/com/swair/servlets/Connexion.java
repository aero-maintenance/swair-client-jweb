package com.swair.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.swair.dao.UtilisateurDAO;
import com.swair.entities.utilisateur;
import com.swair.forms.ConnexionForm;


@WebServlet( name="Connexion", urlPatterns = "/connexion" )
public class Connexion extends HttpServlet {
   	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7243125524553435982L;
	public static final String ATT_USER         			= "utilisateur";
    public static final String ATT_FORM        				= "form";
    public static final String ATT_INTERVALLE_CONNEXIONS 	= "intervalleConnexions";
    public static final String ATT_SESSION_USER 			= "sessionUtilisateur";
    public static final String COOKIE_DERNIERE_CONNEXION 	= "derniereConnexion";
    public static final String FORMAT_DATE               	= "dd/MM/yyyy HH:mm:ss";
    public static final String VUE_CONNEXION              	= "/WEB-INF/connexion.jsp";
    public static final String VUE_FLIGHT_REGISTERING		= "/account/flight_registering.jsp";
    public static final String CHAMP_MEMOIRE             	= "memoire";
    public static final int    COOKIE_MAX_AGE            	= 60 * 60 * 24 * 365;  // 1 an
    
    @EJB
    private UtilisateurDAO          utilisateurDao;
    
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	
    	
        /* Tentative de récupération du cookie depuis la requête */
        String derniereConnexion = getCookieValue( request, COOKIE_DERNIERE_CONNEXION );
        /* Si le cookie existe, alors calcul de la durée */
        if ( derniereConnexion != null ) {
            /* Récupération de la date courante */
            DateTime dtCourante = new DateTime();
            /* Récupération de la date présente dans le cookie */
            DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );
            DateTime dtDerniereConnexion = formatter.parseDateTime( derniereConnexion );
            /* Calcul de la durée de l'intervalle */
            Period periode = new Period( dtDerniereConnexion, dtCourante );
            /* Formatage de la durée de l'intervalle */
            PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
                    .appendYears().appendSuffix( " an ", " ans " )
                    .appendMonths().appendSuffix( " mois " )
                    .appendDays().appendSuffix( " jour ", " jours " )
                    .appendHours().appendSuffix( " heure ", " heures " )
                    .appendMinutes().appendSuffix( " minute ", " minutes " )
                    .appendSeparator( "et " )
                    .appendSeconds().appendSuffix( " seconde", " secondes" )
                    .toFormatter();
            String intervalleConnexions = periodFormatter.print( periode );
            /* Ajout de l'intervalle en tant qu'attribut de la requête */
            request.setAttribute( ATT_INTERVALLE_CONNEXIONS, intervalleConnexions );
        }
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE_CONNEXION ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        ConnexionForm form = new ConnexionForm(utilisateurDao);
        Boolean valid_user = false;

        /* Traitement de la requête et récupération du bean en résultant */
        utilisateur utilisateur = form.verifier_utilisateur(request); //utilisateurDao.trouver("test@test.com");
        if(utilisateur != null) {
        	System.out.println("Validation de l'utilisateur");
        	valid_user = true;
        }
        	
        
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute( ATT_SESSION_USER, utilisateur );
        } else {
            session.setAttribute( ATT_SESSION_USER, null );
        }
        
        /* Si et seulement si la case du formulaire est cochée */
        if ( request.getParameter( CHAMP_MEMOIRE ) != null ) {
            /* Récupération de la date courante */
            DateTime dt = new DateTime();
            /* Formatage de la date et conversion en texte */
            DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );
            String dateDerniereConnexion = dt.toString( formatter );
            /* Création du cookie, et ajout à la réponse HTTP */
            setCookie( response, COOKIE_DERNIERE_CONNEXION, dateDerniereConnexion, COOKIE_MAX_AGE );
        } else {
            /* Demande de suppression du cookie du navigateur */
            setCookie( response, COOKIE_DERNIERE_CONNEXION, "", 0 );
        }
        
        if(valid_user) {
        	/* Stockage du formulaire et du bean dans l'objet request */
        	request.setAttribute( ATT_FORM, form );
            request.setAttribute( ATT_USER, utilisateur );
            this.getServletContext().getRequestDispatcher( VUE_FLIGHT_REGISTERING ).forward( request, response );
        }else {
        	this.getServletContext().getRequestDispatcher( VUE_CONNEXION ).forward( request, response );
        }
        
        
    }
    
    private static String getCookieValue( HttpServletRequest request, String nom ) {
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    private static void setCookie( HttpServletResponse response, String nom, String valeur, int maxAge ) {
        Cookie cookie = new Cookie( nom, valeur );
        cookie.setMaxAge( maxAge );
        response.addCookie( cookie );
    }
}
    