package com.swair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.swair.entities.aircraft;
import com.swair.entities.utilisateur;

@Stateless
public class AircraftDAO {
	
	private static final String JPQL_SELECT_PAR_IMMAT 	= "SELECT ac FROM aircraft ac WHERE ac.immatriculation=:immatriculation";
	private static final String JPQL_SELECT_BY_OWNER 	= "SELECT ac FROM aircraft ac WHERE ac.id_user=:id_user";
    private static final String PARAM_IMMAT           	= "immatriculation";
    private static final String PARAM_OWNER_ID 			= "id_user";

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "softwair" )
    private EntityManager em;

    public aircraft trouver( long id ) throws DAOException {
        try {
            return em.find( aircraft.class, id );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }
   
  //Recherche d'un utilisateur à partir de son adresse email
    public aircraft trouver( String immat ) throws DAOException {
        aircraft ac = null;
        Query requete = em.createQuery( JPQL_SELECT_PAR_IMMAT );
        requete.setParameter( PARAM_IMMAT, immat );
        try {
            ac = (aircraft) requete.getSingleResult();
        } catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        return ac;
    }

    public void creer( aircraft aircraft ) throws DAOException {
        try {
            em.persist( aircraft );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }
    
    public List<aircraft> filter(Long user_id) throws DAOException {
        try {
            TypedQuery<aircraft> query = em.createQuery( JPQL_SELECT_BY_OWNER, aircraft.class );
            query.setParameter( PARAM_OWNER_ID, user_id );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public List<aircraft> lister() throws DAOException {
        try {
            TypedQuery<aircraft> query = em.createQuery( "SELECT c FROM aircraft c ORDER BY c.ac_id", aircraft.class );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void supprimer( aircraft aircraft ) throws DAOException {
        try {
            em.remove( em.merge( aircraft ) );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }
}
