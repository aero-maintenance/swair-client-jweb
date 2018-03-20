package com.swair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.swair.entities.aircraft;

@Stateless
public class AircraftDAO {	

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
   
    public List<aircraft> trouver( String Immat_avion ) throws DAOException {
        try {
        	TypedQuery<aircraft> query = em.createQuery( "SELECT c FROM aircraft c WHERE c.immatriculation = "+ Immat_avion, aircraft.class );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void creer( aircraft aircraft ) throws DAOException {
        try {
            em.persist( aircraft );
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
