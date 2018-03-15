package com.swair.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.sdzee.tp.entities.Client;
import com.sdzee.tp.forms.DAOException;
import com.sdzee.tp.forms.FormValidationException;
import com.swair.dao.VolDAO;
import com.swair.entities.vol;

import eu.medsea.mimeutil.MimeUtil;

public class CreationVolForm {
		private static final String CHAMP_AVION     	= "Immat_Avion";
	    private static final String CHAMP_DATEHEURE  	= "Date&Heure";
	    private static final String CHAMP_FH   			= "Flight_Hours";
	    private static final String CHAMP_FC			= "Flight_Cycle";
	    private static final String CHAMP_REMARQUE      = "Remarque";
	    private static final String CHAMP_HUILE   		= "Ajout_Huile";
	    private static final String CHAMP_CARBURANT 	= "Ajout_Carburant";

	    private String              resultat;
	    private Map<String, String> erreurs         = new HashMap<String, String>();
	    private VolDAO          volDAO;

	    public CreationVolForm( VolDAO volDAO ) {
	        this.volDAO = volDAO;
	    }

	    public Map<String, String> getErreurs() {
	        return erreurs;
	    }

	    public String getResultat() {
	        return resultat;
	    }
	    
	    public vol creervol( HttpServletRequest request, String chemin ) {
	        String Immat_avion = getValeurChamp( request, CHAMP_AVION );
	        String Date_Heure = getValeurChamp( request, CHAMP_DATEHEURE );
	        String Flight_Hours = getValeurChamp( request, CHAMP_FH );
	        String Flight_Cycle = getValeurChamp( request, CHAMP_FC );
	        String Remarque = getValeurChamp( request, CHAMP_REMARQUE );
	        String Huile = getValeurChamp( request, CHAMP_HUILE );
	        String Carburant = getValeurChamp( request, CHAMP_CARBURANT );
	        
	        vol vol = new vol();

	        traiterImmat_avion( Immat_avion, vol );
	        traiterDate_Heure( Date_Heure, vol );
	        traiterFlight_Hours( Flight_Hours, vol );
	        traiterFlight_Cycle( Flight_Cycle, vol );
	        traiterRemarque( Remarque, vol );
	        traiterHuile( Huile, vol );
	        traiterCarburant( Carburant, vol );
	       
	        
	        try {
	            if ( erreurs.isEmpty() ) {
	                volDAO.creer( vol );
	                resultat = "Succès de la création du vol.";
	            } else {
	                resultat = "Echec de la création du vol.";
	            }
	        } catch ( DAOException e ) {
	            setErreur( "imprévu", "Erreur imprévue lors de la création." );
	            resultat = "Echec de la création du vol : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	            e.printStackTrace();
	        }

	        return vol;
	    }
	    
	    private void traiterImmat_avion( String Immat_avion, vol vol ) {
	        try {
	            validationImmat_avion( Immat_avion );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_AVION, e.getMessage() );
	        }
	        vol.setAc_id( Immat_avion );  // Récupérer l'ID Avion en fonction de l'immat
	    }
	    
	    private void traiterDate_Heure( String Date_Heure, vol vol ) {
	        try {
	            validationDate_Heure( Date_Heure );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_DATEHEURE, e.getMessage() );
	        }
	        vol.setDate_heure( Date_Heure);
	    }
	    
	    private void traiterFlight_Hours( String Flight_Hours, vol vol ) {
	        try {
	            validationFlight_Hours( Flight_Hours );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_FH, e.getMessage() );
	        }
	        vol.setFH( Flight_Hours);
	    }
	    
	    private void traiterFlight_Cycle( String Flight_Cycle, vol vol ) {
	        try {
	            validationFlight_Cycle( Flight_Cycle );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_FC, e.getMessage() );
	        }
	        vol.setFC( Flight_Cycle);
	    }
	    
	    private void traiterRemarque( String Remarque, vol vol ) {
	        try {
	            validationRemarque( Remarque );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_REMARQUE, e.getMessage() );
	        }
	        vol.setRemarque( Remarque);
	    }
	    
	    private void traiterHuile( String Huile, vol vol ) {
	        try {
	            validationHuile( Huile );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_HUILE, e.getMessage() );
	        }
	        vol.setHuile( Huile);
	    }
	    
	    private void traiterCarburant( String Carburant, vol vol ) {
	        try {
	            validationCarburant( Carburant );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_CARBURANT, e.getMessage() );
	        }
	        vol.setCarburant( Carburant);
	    }
	    
	    

	    
	    
	    
	    
	    
	    
	    
}
