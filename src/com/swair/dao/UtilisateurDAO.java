package com.swair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.swair.entities.utilisateur;

@Stateless
public class UtilisateurDAO {
	
	private static final String ALGO_CHIFFREMENT = "SHA-256";
	private static final String JPQL_SELECT_PAR_EMAIL = "SELECT u FROM utilisateur u WHERE u.email=:email";
    private static final String PARAM_EMAIL           = "email";
	    
    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "softwair" )
    private EntityManager em;

    public utilisateur trouver( long id ) throws DAOException {
        try {
            return em.find( utilisateur.class, id );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }
    
    //Recherche d'un utilisateur à partir de son adresse email
    public utilisateur trouver( String email ) throws DAOException {
        utilisateur utilisateur = null;
        Query requete = em.createQuery( JPQL_SELECT_PAR_EMAIL );
        requete.setParameter( PARAM_EMAIL, email );
        try {
            utilisateur = (utilisateur) requete.getSingleResult();
        } catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        return utilisateur;
    }
    
    public void creer( utilisateur utilisateur ) throws DAOException {
        try {
            em.persist( utilisateur );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }
    


    public List<utilisateur> lister() throws DAOException {
        try {
            TypedQuery<utilisateur> query = em.createQuery( "SELECT c FROM utilisateur c ORDER BY c.user_id", utilisateur.class );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void supprimer( utilisateur utilisateur ) throws DAOException {
        try {
            em.remove( em.merge( utilisateur ) );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }	

}
