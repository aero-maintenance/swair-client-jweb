package com.swair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.swair.entities.Vol;

@Stateless
public class VolDAO {

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "softwair" )
    private EntityManager em;

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
