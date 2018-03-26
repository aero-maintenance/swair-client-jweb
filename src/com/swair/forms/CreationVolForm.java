package com.swair.forms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.swair.dao.AircraftDAO;
import com.swair.dao.DAOException;
import com.swair.dao.VolDAO;
import com.swair.entities.Aircraft;
import com.swair.entities.Vol;

public class CreationVolForm {
	
		private static final String ATT_SESSION_AC_LIST	= "sessionAircraftList";
		private static final String CHAMP_AVION     	= "listAircrafts";
	    private static final String CHAMP_FH   			= "Flight_Hours";
	    private static final String CHAMP_FC			= "Flight_Cycle";
	    private static final String CHAMP_REMARQUE      = "Remarque";
	    private static final String CHAMP_HUILE   		= "huile";
	    private static final String CHAMP_CARBURANT 	= "carburant";
	    private static final String CHAMP_DATE			= "date";
	    private static final String CHAMP_TIME			= "time";

	    private AircraftDAO 		aircraftDao ;
	    private VolDAO          	volDao;
	    private String              resultat;
	    private Map<String, String> erreurs= new HashMap<String, String>();
	    

	    public CreationVolForm( VolDAO volDAO, AircraftDAO aircraftDao ) {
	        this.volDao = volDAO;
	        this.aircraftDao = aircraftDao;
	    }

	    public Map<String, String> getErreurs() {
	        return erreurs;
	    }

	    public String getResultat() {
	        return resultat;
	    }
	    
	    public Vol creerVol( HttpServletRequest request) {
	    	/**
	    	 * récupération des informations de la page creationVol.jsp
	    	 */
	    	String date = getValeurChamp( request, CHAMP_DATE );
	    	String time = getValeurChamp( request, CHAMP_TIME );
	    	String key_avion = getValeurChamp( request, CHAMP_AVION );	    	
	        String Flight_Hours = getValeurChamp( request, CHAMP_FH );
	        String Flight_Cycle = getValeurChamp( request, CHAMP_FC );
	        String Remarque = getValeurChamp( request, CHAMP_REMARQUE );
	        String Huile = getValeurChamp( request, CHAMP_HUILE );
	        String Carburant = getValeurChamp( request, CHAMP_CARBURANT );
	        
	        
	        HttpSession session = request.getSession();
	        Map<Long, Aircraft> mapAvions = (Map<Long, Aircraft>) session.getAttribute(ATT_SESSION_AC_LIST);
	        
	        Aircraft aircraft = aircraftDao.trouver(mapAvions.get(Long.parseLong(key_avion)).getImmatriculation());
	        
	        Vol vol = new Vol();
	        
	        traiterDateTime(date, time, vol);
	        traiterAvion(aircraft, vol);
	        traiterFlight_Hours( Flight_Hours, vol );
	        traiterFlight_Cycle( Flight_Cycle, vol );
	        traiterRemarque( Remarque, vol );
	        traiterHuile( Huile, vol );
	        traiterCarburant( Carburant, vol );
	        
	        try {
	            if ( erreurs.isEmpty() ) {
	                volDao.creer( vol );
	                resultat = "Succès de la création du vol.";
	                System.out.println("Pas d'erreurs");
	                
	            } else {
	                resultat = "Echec de la création du vol.";
	                System.out.println("Erreurs !!!\n\n*******************************************************\n");
	                for (Entry<String, String> entry : erreurs.entrySet()) {
	                    System.out.println(entry.getKey() + "=" + entry.getValue());
	                }
	                System.out.println();
	            }
	        } catch ( DAOException e ) {
	            setErreur( "imprévu", "Erreur imprévue lors de la création." );
	            resultat = "Echec de la création du vol : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	            e.printStackTrace();
	        }

	        return vol;
	    }
	    
	    private void traiterAvion(Aircraft aircraft, Vol vol) {
	    	if(aircraft == null) {
	    		setErreur( CHAMP_AVION, "Aéronef inconnu" );
	    	}
	    	vol.setAircraft(aircraft);
	    }
	    
	    private void traiterDateTime(String strDate, String strTime, Vol vol) {
	    	LocalDate date = null;
	    	LocalTime time = null;
	    	
	    	try {
	    		date = validationDate(strDate);
	    		time = validationTime(strTime);
	        } catch ( FormValidationException | ParseException e ) {
	            setErreur( CHAMP_FH, e.getMessage() );
	            System.out.println(e.getMessage());
	        }
	    	DateTime datetime = new DateTime(date.getYear(),
	    									date.getMonthOfYear(), 
							    			date.getDayOfMonth(),
							    			time.getHourOfDay(), 
							    			time.getMinuteOfHour());
	        vol.setDate_heure(datetime);
	    }

	    private void traiterFlight_Hours( String Flight_Hours, Vol vol ) {
	    	float valeurFlight_Hours = -1 ; 
	    	try {
	            valeurFlight_Hours = validationFlight_Hours( Flight_Hours );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_FH, e.getMessage() );
	        }
	        vol.setFH( valeurFlight_Hours);
	    }
	    
	    private void traiterFlight_Cycle( String Flight_Cycle, Vol vol ) {
	        int valeurFlight_Cycle = -1;
	    	try {
	            valeurFlight_Cycle = validationFlight_Cycle( Flight_Cycle );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_FC, e.getMessage() );
	        }
	        vol.setFC( valeurFlight_Cycle);
	    }
	    
	    private void traiterRemarque( String Remarque, Vol vol ) {
	        try {
	            validationRemarque( Remarque );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_REMARQUE, e.getMessage() );
	        }
	        vol.setRemarque( Remarque);
	    }
	    
	    private void traiterHuile( String Huile, Vol vol ) {
	        int valeurHuile = 0; 
	    	try {
	            valeurHuile = validationHuile( Huile );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_HUILE, e.getMessage() );
	        }
	        vol.setHuile( valeurHuile);
	    }
	    
	    private void traiterCarburant( String Carburant, Vol vol ) {
	        int valeurCarburant = 0;
	    	try {
	            valeurCarburant = validationCarburant( Carburant );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_CARBURANT, e.getMessage() );
	        }
	        vol.setCarburant( valeurCarburant);
	    }
	    
	    
	    private LocalDate validationDate(String strDate) throws FormValidationException, ParseException {
	    	
	    	if ( strDate != null ) {
	    		if(!strDate.matches("^\\s*((?:19|20)\\d{2})\\-(1[012]|0?[1-9])\\-(3[01]|[12][0-9]|0?[1-9])\\s*$")) {
	    			System.out.println("format date incorrect");
	    			throw new FormValidationException( "Merci d'entrer une date valide jj/mm/aaaa " );
	    		} 
	    	}
	    	else {
	    	
	            throw new FormValidationException( "Merci d'entrer une date valide jj/mm/aaaa " );
	    	}    
	    	DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
	    	return LocalDate.parse(strDate, format);
	    }
	    
	    private LocalTime validationTime(String strTime) throws FormValidationException {
	    	if(strTime != null) {
	    		if(!strTime.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]?$")){
	    			throw new FormValidationException( "Merci d'entrer une heure valide");	
	    		}
	    	}else {
	    		throw new FormValidationException( "Merci d'entrer une heure valide");	
	    	}
	    	DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm");	
	    	return LocalTime.parse(strTime,format);
	    }
	    
	    private Aircraft validation_avion( String Immat_avion ) throws FormValidationException {
	    	Aircraft avion = aircraftDao.trouver(Immat_avion);
	    	
	        if ( Immat_avion != null ) {
	            if ( Immat_avion.length() < 6 && avion==null) {
	                throw new FormValidationException( "L'immatriculation de l'avion doit contenir au moins 6 caractères." );
	            }
	           
	        } else {
	        	
	            throw new FormValidationException( "Merci d'entrer l'immatriculation de l'avion." );
	        }
	        
	        return avion; 
	    }
	    
	    private float validationFlight_Hours( String Flight_Hours ) throws FormValidationException {
	    	
	    	float FH_tmp;
	    	
	        if ( Flight_Hours != null ) {
	            if ( Flight_Hours.length() < 1 ) {
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
	            if ( Huile.length() < 1 ) {
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
	            if ( Carburant.length() < 1 ) {
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
