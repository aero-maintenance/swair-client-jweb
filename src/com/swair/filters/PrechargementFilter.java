package com.swair.filters;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.swair.dao.AircraftDAO;
import com.swair.dao.UtilisateurDAO;
import com.swair.dao.VolDAO;
import com.swair.entities.Aircraft;
import com.swair.entities.Utilisateur;
import com.swair.entities.Vol;

@WebFilter( urlPatterns = { "/*" } )
public class PrechargementFilter implements Filter {
    public static final String ATT_SESSION_CLIENTS   = "clients";
    public static final String ATT_SESSION_COMMANDES = "commandes";

    @EJB
    private UtilisateurDAO          utilisateurDao;
    @EJB
    private VolDAO        			volDao;
    @EJB
    private AircraftDAO				aircraftDao;

    public void init( FilterConfig filterConfig ) throws ServletException {
    }

    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException,
            ServletException {
        /* Cast de l'objet request */
        HttpServletRequest request = (HttpServletRequest) req;

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        
        
        /*ajout d'un utilisateur pour test*/
       
        /*ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( "SHA-256" );
        passwordEncryptor.setPlainDigest( false );
        String motDePasseChiffre = passwordEncryptor.encryptPassword( "test" );
        
        
        utilisateur user = new utilisateur();
        user.setnom_aeroclub("test1");
        user.setAdresse("8 rue du test");
        user.setVille("testville");
        user.setCode_postale("36753");
        user.setEmail("test1@test.com");
        user.setTelephone("0642742598");
        user.setPassword(motDePasseChiffre);
        utilisateurDao.creer(user);
        */
        /* ajout aircraft pour test*/
       // Utilisateur proprietaire = utilisateurDao.trouver(1);
       
        /*Aircraft plane = new Aircraft();
        plane.setProprietaire(proprietaire);
        plane.setConstructeur("Beechcraft");
        plane.setModele("King Air C90");
        plane.setImmatriculation("F-MOPF");
        plane.setTotal_FH(5284.5);
        plane.setTotal_FC(new Long(8086));
        plane.setMsn(new Long(458));
        plane.setStatut("LSA");
        plane.setDate_Kardex(Date.valueOf("2017-5-13"));
        plane.setRemarque("RAS just for test1");
        aircraftDao.creer(plane);*/
        /*
         * Si la map des clients n'existe pas en session, alors l'utilisateur se
         * connecte pour la première fois et nous devons précharger en session
         * les infos contenues dans la BDD.
         */
        /*if ( session.getAttribute( ATT_SESSION_CLIENTS ) == null ) {
            
            List<Utilisateur> listeUtilisateurs = utilisateurDao.lister();
            Map<Long, Utilisateur> mapClients = new HashMap<Long, Utilisateur>();
            for ( Utilisateur utilisateur : listeUtilisateurs ) {
                mapClients.put( utilisateur.getUser_id(), utilisateur );
            }
            session.setAttribute( ATT_SESSION_CLIENTS, mapClients );
        }*/

        /*
         * De même pour la map des commandes
         */
       /* if ( session.getAttribute( ATT_SESSION_COMMANDES ) == null ) {
            List<vol> listeVol = volDao.lister();
            Map<Long, vol> mapCommandes = new HashMap<Long, vol>();
            for ( vol vol : listeVol ) {
                mapCommandes.put( vol.getId_vol(), vol );
            }
            session.setAttribute( ATT_SESSION_COMMANDES, mapCommandes );
        }*/

        /* Pour terminer, poursuite de la requête en cours */
        chain.doFilter( request, res );
    }

    public void destroy() {
    }
}