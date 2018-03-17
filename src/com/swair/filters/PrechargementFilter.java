package com.swair.filters;

import java.io.IOException;
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

import com.swair.dao.UtilisateurDAO;
import com.swair.dao.VolDAO;
import com.swair.entities.utilisateur;
import com.swair.entities.vol;

@WebFilter( urlPatterns = { "/*" } )
public class PrechargementFilter implements Filter {
    public static final String ATT_SESSION_CLIENTS   = "clients";
    public static final String ATT_SESSION_COMMANDES = "commandes";

    @EJB
    private UtilisateurDAO          utilisateurDao;
    @EJB
    private VolDAO        volDao;

    public void init( FilterConfig filterConfig ) throws ServletException {
    }

    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException,
            ServletException {
        /* Cast de l'objet request */
        HttpServletRequest request = (HttpServletRequest) req;

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /*
         * Si la map des clients n'existe pas en session, alors l'utilisateur se
         * connecte pour la première fois et nous devons précharger en session
         * les infos contenues dans la BDD.
         */
        if ( session.getAttribute( ATT_SESSION_CLIENTS ) == null ) {
            /*
             * Récupération de la liste des clients existants, et enregistrement
             * en session
             */
            List<utilisateur> listeUtilisateurs = utilisateurDao.lister();
            Map<Long, utilisateur> mapClients = new HashMap<Long, utilisateur>();
            for ( utilisateur utilisateur : listeUtilisateurs ) {
                mapClients.put( utilisateur.getUser_id(), utilisateur );
            }
            session.setAttribute( ATT_SESSION_CLIENTS, mapClients );
        }

        /*
         * De même pour la map des commandes
         */
        if ( session.getAttribute( ATT_SESSION_COMMANDES ) == null ) {
            /*
             * Récupération de la liste des commandes existantes, et
             * enregistrement en session
             */
            List<vol> listeVol = volDao.lister();
            Map<Long, vol> mapCommandes = new HashMap<Long, vol>();
            for ( vol vol : listeVol ) {
                mapCommandes.put( vol.getId_vol(), vol );
            }
            session.setAttribute( ATT_SESSION_COMMANDES, mapCommandes );
        }

        /* Pour terminer, poursuite de la requête en cours */
        chain.doFilter( request, res );
    }

    public void destroy() {
    }
}