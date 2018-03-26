package com.swair.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.swair.dao.AircraftDAO;
import com.swair.dao.VolDAO;
import com.swair.entities.Vol;
import com.swair.forms.CreationVolForm;


@WebServlet( name="Enregistrer", urlPatterns = "/account/enregistrementVol" )
public class CreationVol extends HttpServlet {
	
	 public static final String VUE 							= "/account/creationVol.jsp";
	 public static final String ATT_FORM        				= "volform";
	 
	 @EJB
	 private AircraftDAO				aircraftDao;
	 @EJB
	 private VolDAO 					volDao;

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /**
         * Affichage de la page d'enristrement d'un vol
         */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		CreationVolForm volform = new CreationVolForm(volDao,aircraftDao);
		request.setAttribute( ATT_FORM, volform );
		
		Vol vol = volform.creerVol(request);
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
	
}
