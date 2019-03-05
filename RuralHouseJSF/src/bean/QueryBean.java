package bean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;


import businessLogic.*;
import domain.Offer;
import domain.RuralHouse;

public class QueryBean {
	
	private String mensajeAlquiler = "Esperando al alquiler de la oferta";
	private int id;
	private float precio;
	private int number;
	private int noches;
	private Date diaInicioOferta;
	private Date diaFinOferta;
	private Date diaInicio;
	private Date diaFin;
	private String resultado= "";
	private String resultadoAlquiler = "";
	private String telefono;
	private boolean alquiler = true;
	private Offer SelectedOffer;

	private FacadeImplementationWS facade = FacadeBean.getFacade();
	public List<RuralHouse> casas = facade.getAllRuralHouses();
	public RuralHouse casa;
	
	public String getTelefono() {
		return telefono;
	}

	public Date getdiaInicioOferta() {
		if(SelectedOffer != null) {
			diaInicioOferta = SelectedOffer.getFirstDay();
		}
		return diaInicioOferta;
	}
	public Date getdiaFinOferta() {
		if(SelectedOffer != null) {
			diaFinOferta = SelectedOffer.getLastDay();
		}
		return diaFinOferta;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getResultadoAlquiler() {
		return resultadoAlquiler;
	}


	public void setResultadoAlquiler(String resultadoAlquiler) {
		this.resultadoAlquiler = resultadoAlquiler;
	}

	public Offer getSelectedOffer() {
		return SelectedOffer;
	}


	public void setSelectedOffer(Offer selectedOffer) {
		SelectedOffer = selectedOffer;
		
	}


	private List<Offer> offers = new Vector<Offer>();

	public List<Offer> getOffers() {
		return offers;
	}


	public void setOffers(Vector<Offer> offers) {
		this.offers = offers;
	}
	
	/*public void obtenerOffers() {
		try {
			ApplicationFacadeInterfaceWS facade1 = BD.orderBD("Local");
			c.setTime(diaInicio);
			c.add(Calendar.DATE, noches);
			diaFin = c.getTime();
			offers = facade1.getOffers(casa, diaInicio, diaFin);
		} catch (Exception e) {
			System.out.println("No hay ofertas");
		}
	}*/
	
	public int getId() {
		
		return id;
	}

	public float getPrecio() {
		return precio;
	}

	public int getNumber() {
		return number;
	}

	public Date getDiaFin() {
		return diaFin;
	}


	public void setDiaFin(Date diaFin) {
		this.diaFin = diaFin;
	}
	
	public Date getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(Date diaInicio) {
		this.diaInicio = diaInicio;
	}

	public int getNoches() {
		return noches;
	}

	public void setNoches(int noches) {
		this.noches = noches;
	}


	public List<RuralHouse> getCasas() {
		return casas;
	}

	public void setCasas(Vector<RuralHouse> casas) {
		this.casas = casas;
	}

	public RuralHouse getCasa() {
		return casa;
	}

	public void setCasa(RuralHouse nombre) {
		this.casa = nombre;
	}
	
	public Date getdiaFin() {
		return this.diaFin;
	}


	public void action() {
		try {
			Calendar c = Calendar.getInstance();
			//ApplicationFacadeInterfaceWS facade1 = BD.orderBD("Local");
			c.setTime(diaInicio);
			c.add(Calendar.DAY_OF_YEAR, noches);
			diaFin = c.getTime();
			offers = facade.getOffers(casa, diaInicio, diaFin);
			if (offers.isEmpty()){
				resultado = "There are no offers at these dates";
				alquiler = true;
			}
			else{
				resultado = "Select an offer if you want to book";
				alquiler = false;
			}
		} catch (Exception e) {
			System.out.println("No hay ofertas");
		}
		
	}
	
	public String alquilerOferta() {
		if (SelectedOffer==null){
			resultadoAlquiler = "Debes seleccionar una oferta";
			return "";
		}
		else{
			resultadoAlquiler = "";
			return "rent";
		}
	}
	
	public void aceptarAlquilerOferta() {
		boolean a = facade.createBook(SelectedOffer, telefono);
		if (a) {
			this.setAlquiler(false);
			this.setDiaFin(null);
			this.diaInicio=null;
			this.offers = new Vector<Offer>();
			this.setTelefono("");
			this.setNoches(0);
			this.setResultado("");
			this.setResultadoAlquiler("");
			this.setSelectedOffer(null);
			mensajeAlquiler = "Alquilada";
			
		}
		else {
			mensajeAlquiler = "La oferta no ha podido ser creada";
		}
	}

	public String getMensajeAlquiler() {
		return mensajeAlquiler;
	}


	public void setMensajeAlquiler(String mensajeAlquiler) {
		this.mensajeAlquiler = mensajeAlquiler;
	}


	public String getResultado() {
		return resultado;
	}


	public void setResultado(String resultado) {
		this.resultado = resultado;
	}


	public boolean isAlquiler() {
		return alquiler;
	}


	public void setAlquiler(boolean alquiler) {
		this.alquiler = alquiler;
	}

}
