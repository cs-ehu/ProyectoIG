package businessLogic;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Date;


//import domain.Booking;
import domain.Offer;
import domain.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ApplicationFacadeInterfaceWS  {
	

	/**
	 * This method creates an offer with a house number, first day, last day and price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */


	@WebMethod Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay,
			float price) throws  OverlappingOfferExists, BadDates;
	/**
	 * This method creates a book with a corresponding parameters
	 * 
	 * @param First
	 *            day, last day, house number and telephone
	 * @return a book
	 */
	
	
	
	/**
	 * This method retrieves the existing  rural houses 
	 * 
	 * @return a Set of rural houses
	 */
	@WebMethod public List<RuralHouse> getAllRuralHouses();
	
	/**
	 * This method obtains the  offers of a ruralHouse in the provided dates 
	 * 
	 * @param ruralHouse, the ruralHouse to inspect 
	 * @param firstDay, first day in a period range 
	 * @param lastDay, last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there is no overlapping offer
	 */

	@WebMethod public List<Offer> getOffers( RuralHouse rh, Date firstDay,  Date lastDay) ;
	//@WebMethod public Offer getOffer( RuralHouse rh, Date firstDay,  Date lastDay) ;
	//@WebMethod public RuralHouse getMiRuralHouse();
	
	
	
	/**
	 * This method creates a book for a client telephoneNumber 
	 * 
	 * @param offer, the the offer to be booked 
	 * @param telephoneNumber, the telephone of the person who book the offer 
	 * @return true if the offer has been booked, false in other case
	 */

	@WebMethod public boolean createBook( Offer off, String telephoneNumber);
	//@WebMethod public void initializeBD();

	
}
