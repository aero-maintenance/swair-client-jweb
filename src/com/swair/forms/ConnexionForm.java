package com.swair.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.swair.dao.UtilisateurDAO;
import com.swair.entities.Utilisateur;

public final class ConnexionForm {
    private static final String CHAMP_EMAIL  = "email";
    private static final String CHAMP_PASS   = "password";
    private static final String USER   = "user";
    
    private UtilisateurDAO utilisateurDAO;

    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();
    
    public ConnexionForm(UtilisateurDAO utilisateurDao) {
    	this.utilisateurDAO = utilisateurDao;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /**
     * 
     * @param request
     * @return Utilisateur
     * permet de vérifier si l'utilisateur est enregistre dans la base de donnée
     */
    public Utilisateur verifier_utilisateur( HttpServletRequest request ) {
    	/* Récupération des champs du formulaire */
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String password = getValeurChamp( request, CHAMP_PASS );
        
                
        /* Validation du champ email. */
        try {
            validationEmail( email );
        } catch ( Exception e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        
        try {
            validationMotDePasse( password );
        } catch ( Exception e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
        }
       Utilisateur user = utilisateurDAO.trouver(email);
       try {
    	   validationUser(user,password);
       } catch( Exception e ){
    	   setErreur( USER, e.getMessage() );
    	   user=null;
       }
       
       return user;
    }

    private void validationUser(Utilisateur user, String password) throws Exception {
    	ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( "SHA-256" );
        passwordEncryptor.setPlainDigest( false );
        if(user != null) {
        	if( !passwordEncryptor.checkPassword(password, user.getPassword())) {
        		throw new Exception( "Les identifiants sont incorrects" );
        	}
        }else {
        	throw new Exception( "Les identifiants sont incorrects" );
        } 
    }
    	
    
    /**
     * Valide l'adresse email saisie.
     */
    private void validationEmail( String email ) throws Exception {
        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    }

    /**
     * Valide le mot de passe saisi.
     */
    private void validationMotDePasse( String motDePasse ) throws Exception {
        if ( motDePasse != null ) {
            if ( motDePasse.length() < 3 ) {
                throw new Exception( "Le mot de passe doit contenir au moins 3 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir votre mot de passe." );
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /**
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
