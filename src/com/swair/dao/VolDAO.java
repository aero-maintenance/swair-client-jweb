package com.swair.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import com.swair.entities.Aircraft;
import com.swair.entities.Vol;

@Stateless
public class VolDAO {

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "softwair" )
    private EntityManager em;
    @Resource SessionContext context;
    
    private AircraftDAO aircratfDao;
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void creerVolTransaction(Vol vol, Aircraft aircraft) {
    	    	
    	Aircraft acToUpdate = aircraft;
    	/* Mise a jou des totaux FC et FH de l'aeronef*/
    	acToUpdate.setTotal_FC(acToUpdate.getTotal_FC() + vol.getFC());
    	acToUpdate.setTotal_FH(acToUpdate.getTotal_FH() + vol.getFH());
    	try {
    		creer(vol);
        	aircratfDao.update(acToUpdate);
    	}catch ( Exception e) {
    		context.setRollbackOnly();
    		throw e;
    	}
    }
    
    public Vol update(Vol vol)throws DAOException {
    	try {
    		return em.merge(vol);
    	}catch ( Exception e ){
    		throw new DAOException( e );
    	}
    }

    public Vol trouver( long id ) throws DAOException {
        try {
            return em.find( Vol.class, id );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void creer( Vol vol ) throws DAOException {
        try {
            em.persist( vol );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public List<Vol> lister() throws DAOException {
        try {
            TypedQuery<Vol> query = em.createQuery( "SELECT v FROM Vol v ORDER BY v.flight_id", Vol.class );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void supprimer( Vol vol ) throws DAOException {
        try {
            em.remove( em.merge( vol ) );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }
}
