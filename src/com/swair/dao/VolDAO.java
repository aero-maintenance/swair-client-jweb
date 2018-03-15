package com.swair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.swair.entities.vol;


@Stateless
public class VolDAO {

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "softwair" )
    private EntityManager em;

    public vol trouver( long id ) throws DAOException {
        try {
            return em.find( vol.class, id );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void creer( vol vol ) throws DAOException {
        try {
            em.persist( vol );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public List<vol> lister() throws DAOException {
        try {
            TypedQuery<vol> query = em.createQuery( "SELECT c FROM vol c ORDER BY c.id_vol", vol.class );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void supprimer( vol vol ) throws DAOException {
        try {
            em.remove( em.merge( vol ) );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }
}
