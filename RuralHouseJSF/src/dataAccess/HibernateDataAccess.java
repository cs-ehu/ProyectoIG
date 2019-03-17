package dataAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.persistence.TypedQuery;

import domain.Offer;
import domain.RuralHouse;
import exceptions.OverlappingOfferExists;
import modelo.HibernateUtil;

import org.hibernate.Session;

public class HibernateDataAccess {

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) {
		System.out.println(">> DataAccess: hibernateCreateOffer=> ruralHouse= " + ruralHouse + " firstDay= " + firstDay
				+ " lastDay=" + lastDay + " price=" + price);

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		RuralHouse result = (RuralHouse) session.get(RuralHouse.class, ruralHouse.getHouseNumber());
		try {
			Offer o = result.createOffer(firstDay, lastDay, price);
			session.getTransaction().commit();
			return o;
		}catch (Exception e) {
			System.out.println("Ha petado en create Offer");
			return null;
		}

	}

	public List<RuralHouse> getAllRuralHouses() {
		System.out.println(">> DataAccess: getAllRuralHouses");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<RuralHouse> result = session.createQuery("from RuralHouse").list();
		return result;
	}

	public List<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay) {
		System.out.println(">> hibernateDataAccess: getOffers");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		RuralHouse result = (RuralHouse) session.get(RuralHouse.class, rh.getHouseNumber());
		try {
			List<Offer> l = result.getOffers(firstDay, lastDay);
			session.getTransaction().commit();
			return l;
		} catch (Exception e) {
			System.out.println("Ha peta en getOffers");
			return null;
		}

	}
	/*public static void deleteOffer() {
		System.out.println(">> hibernateDataAccess: deleteOffer");
		 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		 session.beginTransaction();
		 //cargo la casa de la sesion con el house number de la casa que hemos pasado
		//RuralHouse rh1= (RuralHouse) session.load(RuralHouse.class, rh.getHouseNumber());
		//hago el query exactamente con ese numero para no perder la referencia y obtengo las ofertas
		 List<Offer> result = session.createQuery("from Offer").list();
		 
		for (Offer o : result) {				
			//elimino la oferta de la BD
			session.delete(o);	
		}
		//guardo cambios
		session.getTransaction().commit();
		
	 }*/
	public boolean existsOverlappingOffer(RuralHouse rh,Date firstDay, Date lastDay) throws  OverlappingOfferExists{
		 try {

			 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			 session.beginTransaction();
			 
			List<RuralHouse> result = session.createQuery("from RuralHouse where houseNumber='" + rh.getHouseNumber()+ "'").list();
			if(((RuralHouse) result.get(0)) != null){
				RuralHouse rh1 = (RuralHouse) result.get(0);
				if (rh1.overlapsWith(firstDay, lastDay)!=null) { 
					return true;
				}else { 
					return false;
				}
			}else {
				System.out.println("No ha encontrado la casa");
				return false;
			}
	     } catch (Exception e) {
	    	 System.out.println("Error: "+e.toString());
			 return true;	    
		   }
	}
	
	public boolean createBook(Offer off, String telephoneNumber) {
		System.out.println(">> DataAccess: createBook");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from Offer where offernumber='" + off.getOfferNumber() + "'").list();
		if ((Offer) result.get(0) != null) {
			Offer oferta = (Offer) result.get(0);
			if (oferta.getReservedPhone() == null) {
				oferta.setReservedPhone(telephoneNumber);
				session.save(oferta);
				session.getTransaction().commit();
				return true;
			} else
				return false;
		} else
			return false;

	}
	
	public static void main(String[] args) {
		initializeDB();
		//deleteOffer();
		//System.out.println("Ofertas borradas");
	}

	private static void initializeDB() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Offer> resultO = session.createQuery("from Offer").list();
		for (Offer o : resultO) {
			session.delete(o);
		}
		List<RuralHouse> resultRH = session.createQuery("from RuralHouse").list();
		for (RuralHouse rh : resultRH) {
			session.delete(rh);
			session.getTransaction().commit();
			System.out.println("BD borrada");
		}
		
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		RuralHouse rh1 = new RuralHouse("Ezkioko etxea", "Ezkio");
		session.save(rh1);
		RuralHouse rh2 = new RuralHouse("Eskiatzeko etxea", "Jaca");
		session.save(rh2);
		RuralHouse rh3 = new RuralHouse("Udaletxea", "Bilbo");
		session.save(rh3);
		RuralHouse rh4 = new RuralHouse("Gaztetxea", "Renteria");
		session.save(rh4);
		session.getTransaction().commit();
		System.out.println("BD inicializada");

	}
}
