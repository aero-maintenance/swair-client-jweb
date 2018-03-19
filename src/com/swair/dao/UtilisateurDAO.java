package com.swair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.swair.entities.utilisateur;

@Stateless
public class UtilisateurDAO {
	
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
