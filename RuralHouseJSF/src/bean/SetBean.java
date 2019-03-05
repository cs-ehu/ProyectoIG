package bean;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import businessLogic.*;
import domain.Offer;
import domain.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;

public class SetBean {

	private FacadeImplementationWS facade = FacadeBean.getFacade();
	public List<RuralHouse> casas = facade.getAllRuralHouses();
	public RuralHouse casa;
	private float precio;
	private Date diaInicio;
	private Date diaFin;
	private String resultado= "";
	
	public String getResultado() {
		return resultado;
	}
	
	public List<RuralHouse> getCasas() {
		return casas;
	}
	public RuralHouse getCasa() {
		return casa;
	}

	public void setCasa(RuralHouse nombre) {
		this.casa = nombre;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public float getPrecio() {
		return this.precio;
	}
	public Date getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(Date diaInicio) {
		this.diaInicio = diaInicio;
	}
	public Date getDiaFin() {
		return diaFin;
	}
	public void setDiaFin(Date diaFin) {
		this.diaFin = diaFin;
	}
	public void action() {
	
		try {
			Offer o = facade.createOffer(casa, diaInicio, diaFin, precio);
			if (o==null)
	  			resultado = (ResourceBundle.getBundle("Etiquetas").getString("BadDatesOverlap"));
	  		else resultado = (ResourceBundle.getBundle("Etiquetas").getString("OfferCreated"));


		} catch (java.lang.NumberFormatException e1) {
			//resultado = (jTextField3.getText()+ " "+ ResourceBundle.getBundle("Etiquetas").getString("PriceNotValid"));
	  	} catch (OverlappingOfferExists e1) {
	  		resultado =  (ResourceBundle.getBundle("Etiquetas").getString("OverlappingOffer"));
	  	}
	  	catch (BadDates e1) {
	  		resultado = (ResourceBundle.getBundle("Etiquetas").getString("LastDayBefore"));
	  	} catch (Exception e1) {

	  		e1.printStackTrace();
	  	}
	}

}
