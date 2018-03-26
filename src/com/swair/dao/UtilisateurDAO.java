package com.swair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.swair.entities.Utilisateur;

@Stateless
public class UtilisateurDAO {	
	private static final String JPQL_SELECT_PAR_EMAIL = "SELECT u FROM Utilisateur u WHERE u.email=:email";
    private static final String PARAM_EMAIL           = "email";
	    
    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "softwair" )
    private EntityManager em;

    public Utilisateur trouver( long id ) throws DAOException {
        try {
            return em.find( Utilisateur.class, id );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }
    
    //Recherche d'un utilisateur à partir de son adresse email
    public Utilisateur trouver( String email ) throws DAOException {
        Utilisateur utilisateur = null;
        Query requete = em.createQuery( JPQL_SELECT_PAR_EMAIL );
        requete.setParameter( PARAM_EMAIL, email );
        try {
            utilisateur = (Utilisateur) requete.getSingleResult();
        } catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        return utilisateur;
    }
    
    public void creer( Utilisateur utilisateur ) throws DAOException {
        try {
            em.persist( utilisateur );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }
    


    public List<Utilisateur> lister() throws DAOException {
        try {
            TypedQuery<Utilisateur> query = em.createQuery( "SELECT c FROM Utilisateur c ORDER BY c.user_id", Utilisateur.class );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void supprimer( Utilisateur utilisateur ) throws DAOException {
        try {
            em.remove( em.merge( utilisateur ) );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }	

}
