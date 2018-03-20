package com.swair.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.joda.time.DateTime;

import com.swair.dao.AircraftDAO;
import com.swair.dao.DAOException;
import com.swair.dao.VolDAO;
import com.swair.entities.aircraft;
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
	    private static final String CHAMP_YEAR 			= "Année";
	    private static final String CHAMP_MONTH			= "Mois";
	    private static final String CHAMP_DAY 			= "Jour";
	    private static final String CHAMP_HOUR 			= "Heure";
	    private static final String CHAMP_MINUTE 		= "Minute";

	    AircraftDAO 				aircraftDAO ;
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
	        String Flight_Hours = getValeurChamp( request, CHAMP_FH );
	        String Flight_Cycle = getValeurChamp( request, CHAMP_FC );
	        String Remarque = getValeurChamp( request, CHAMP_REMARQUE );
	        String Huile = getValeurChamp( request, CHAMP_HUILE );
	        String Carburant = getValeurChamp( request, CHAMP_CARBURANT );
	        String Annee = getValeurChamp( request, CHAMP_YEAR );
	        String Mois = getValeurChamp( request, CHAMP_MONTH );
	        String Jour = getValeurChamp( request, CHAMP_DAY );
	        String Heure = getValeurChamp( request, CHAMP_HOUR );
	        String Minute = getValeurChamp( request, CHAMP_MINUTE );
	        
	        vol vol = new vol();

	        traiterImmat_avion( Immat_avion, vol );
	        traiterDate_Heure( Annee, Mois, Jour, Heure, Minute, vol );
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
	    	Long valeurImmat_avion = 0L;
	    	try {
	            valeurImmat_avion = validationImmat_avion( Immat_avion );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_AVION, e.getMessage() );
	        }
	        vol.setAc_id( valeurImmat_avion );  // Récupérer l'ID Avion en fonction de l'immat
	    }
	    
	    private void traiterDate_Heure( String Annee, String Mois, String Jour, String Heure, String Minute, vol vol ) {
	    	DateTime valeurDate_Heure = null ; 
	    	try {
	    		valeurDate_Heure = validationDate_Heure(  Annee,  Mois,  Jour,  Heure,  Minute );
	        } 	catch ( FormValidationException e ) {
	            setErreur( CHAMP_DATEHEURE, e.getMessage() );
	        }
	        vol.setDate_heure( valeurDate_Heure);
	    }
	    
	    private void traiterFlight_Hours( String Flight_Hours, vol vol ) {
	    	float valeurFlight_Hours = -1 ; 
	    	try {
	            valeurFlight_Hours = validationFlight_Hours( Flight_Hours );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_FH, e.getMessage() );
	        }
	        vol.setFH( valeurFlight_Hours);
	    }
	    
	    private void traiterFlight_Cycle( String Flight_Cycle, vol vol ) {
	        int valeurFlight_Cycle = -1;
	    	try {
	            valeurFlight_Cycle = validationFlight_Cycle( Flight_Cycle );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_FC, e.getMessage() );
	        }
	        vol.setFC( valeurFlight_Cycle);
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
	        int valeurHuile = 0; 
	    	try {
	            valeurHuile = validationHuile( Huile );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_HUILE, e.getMessage() );
	        }
	        vol.setHuile( valeurHuile);
	    }
	    
	    private void traiterCarburant( String Carburant, vol vol ) {
	        int valeurCarburant = 0;
	    	try {
	            valeurCarburant = validationCarburant( Carburant );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_CARBURANT, e.getMessage() );
	        }
	        vol.setCarburant( valeurCarburant);
	    }
	    
	    private Long validationImmat_avion( String Immat_avion ) throws FormValidationException {
	    	
	    	  
	    	List<aircraft> liste_avion = aircraftDAO.trouver(Immat_avion);
	    	
	        if ( Immat_avion != null ) {
	            if ( Immat_avion.length() < 6 && liste_avion.isEmpty()) {
	                throw new FormValidationException( "L'immatriculation de l'avion doit contenir au moins 6 caractères." );
	            }
	           
	        } else {
	        	
	            throw new FormValidationException( "Merci d'entrer l'immatriculation de l'avion." );
	        }
	        
	        return liste_avion.get(0).getAc_id();  
	    }
	    
	    
	    private DateTime validationDate_Heure( String Annee, String Mois, String Jour, String Heure, String Minute ) throws FormValidationException {
	      
	    	int year_tmp;
	    	int month_tmp;
	    	int day_tmp;
	    	int hour_tmp;
	    	int minute_tmp;
	    	
	    	if ( Annee != null && Mois != null && Jour != null && Heure != null && Minute != null) {
	    		
	    		
	            if ( Annee.length() < 4 || Mois.length() < 2 || Jour.length() < 2 || Heure.length() < 2 || Minute.length() < 2 ) {
	                throw new FormValidationException( "Le format ne correspond pas (JJ/MM/AAAA hh:mm)." );
	            }
	            try { 
		    		year_tmp = Integer.parseInt(Annee); 
		    		month_tmp = Integer.parseInt(Mois); 
		    		day_tmp = Integer.parseInt(Jour); 
		    		hour_tmp = Integer.parseInt(Heure); 
		    		minute_tmp = Integer.parseInt(Minute); 
		    	}
		    	
		    	catch ( NumberFormatException e ) {
		    		year_tmp = -1; 
		    		month_tmp = -1; 
		    		day_tmp = -1; 
		    		hour_tmp = -1; 
		    		minute_tmp = -1; 
		    		throw new FormValidationException ("Merci d'entrer la date et l'heure de votre vol sous le format suivant : JJ/MM/AAAA hh:mm ");
		    	}
		    	
	        } else {
	        	year_tmp = -1; 
	    		month_tmp = -1; 
	    		day_tmp = -1; 
	    		hour_tmp = -1; 
	    		minute_tmp = -1; 
	            throw new FormValidationException( "Merci d'entrer la date et l'heure de votre vol." );
	        }
	    	return new DateTime( year_tmp , month_tmp , day_tmp, hour_tmp, minute_tmp);
	    		    	
	    }
	       
	       

	    private float validationFlight_Hours( String Flight_Hours ) throws FormValidationException {
	    	
	    	float FH_tmp;
	    	
	        if ( Flight_Hours != null ) {
	            if ( Flight_Hours.length() < 2 ) {
	                throw new FormValidationException( "La durée de votre vol doit contenir au moins 2 caractères." );
	            }
	                try { 
			    		FH_tmp = Float.parseFloat(Flight_Hours); 
			    	}
	                catch ( NumberFormatException e ) {
			    		FH_tmp = -1; 
			    		throw new FormValidationException ("Merci d'entrer la durée de votre vol.");
	            }
	            } else {
	            	FH_tmp = -1;
	            	throw new FormValidationException( "Merci d'entrer la durée de votre vol." );
	        }
	        return FH_tmp;
	    }
	        
	    private int validationFlight_Cycle( String Flight_Cycle ) throws FormValidationException {
	    	int FC_tmp;
	        if ( Flight_Cycle != null ) {
	            if ( Flight_Cycle.length() < 1 ) {
	                throw new FormValidationException( "Le nombre de cycle effectué durant votre vol doit contenir au moins 1 caractère." );
	            }
	            try { 
		    		FC_tmp = Integer.parseInt(Flight_Cycle); 
		    	}
                catch ( NumberFormatException e ) {
		    		FC_tmp = -1; 
		    		throw new FormValidationException ("Merci d'entrer la durée de votre vol.");
                }
                } else {
                	FC_tmp = -1;
                	throw new FormValidationException( "Merci d'entrer la durée de votre vol." );
	        }
	        return FC_tmp;
	    }
	    

	    private void validationRemarque( String Remarque ) throws FormValidationException {
	        if ( Remarque != null && Remarque.length() < 1 ) {
	            throw new FormValidationException( "Vos remarques doivent contenir au moins 1 caractère." );
	        }
	    }
	    
	    private int validationHuile( String Huile) throws FormValidationException {
	        int Huile_tmp;
	    	if ( Huile != null ) {
	            if ( Huile.length() < 2 ) {
	                throw new FormValidationException( "La quantité d'huile ajoutée doit contenir au moins 2 caractères." );
	            }
	            try { 
		    		Huile_tmp = Integer.parseInt(Huile); 
		    	}
	            catch ( NumberFormatException e ) {
		    		Huile_tmp = -1; 
		    		throw new FormValidationException ("Merci d'entrer la quantité d'huile ajoutée.");
                }
	        } else {
	        	Huile_tmp = -1;
	            throw new FormValidationException( "Merci d'entrer la quantité d'huile ajoutée." );
	        }
	    	return Huile_tmp;
	    }
	    
	    private int validationCarburant( String Carburant ) throws FormValidationException {
	    	int Carburant_tmp;
	        if ( Carburant != null ) {
	            if ( Carburant.length() < 2 ) {
	                throw new FormValidationException( "La quantité de carburant ajouté doit contenir au moins 2 caractères." );
	            }
	            try { 
		    		Carburant_tmp = Integer.parseInt(Carburant); 
		    	}
	            catch ( NumberFormatException e ) {
		    		Carburant_tmp = -1; 
		    		throw new FormValidationException ("Merci d'entrer la quantité de carburant ajouté.");
                }
	        } else {
	        	Carburant_tmp = -1;
	            throw new FormValidationException( "Merci d'entrer la quantité de carburant ajouté." );
	        }
	        return Carburant_tmp;
	    }
	    
	    /*
	     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	     */
	    private void setErreur( String champ, String message ) {
	        erreurs.put( champ, message );
	    }

	    /*
	     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	     * sinon.
	     */
	    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
	        String valeur = request.getParameter( nomChamp );
	        if ( valeur == null || valeur.trim().length() == 0 ) {
	            return null;
	        } else {
	            return valeur;
	        }
	    }

	    

	    
	    
	    
	    
}
