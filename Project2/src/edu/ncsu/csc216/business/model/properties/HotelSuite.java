/**
 * 
 */
package edu.ncsu.csc216.business.model.properties;

import java.time.LocalDate;

import edu.ncsu.csc216.business.list_utils.SortedList;
import edu.ncsu.csc216.business.model.contracts.Lease;
import edu.ncsu.csc216.business.model.stakeholders.Client;

/**
 * The hotel suite object
 * @author Alex Raum, Walker Clem
 *
 */
public class HotelSuite extends RentalUnit {
	
	/** the max capacity of the room */
	public static final int MAX_CAPACITY = 2;
	
	/**
	 * Constructor for the hotel room
	 * @param s the name of the room
	 */
	public HotelSuite(String s) {
		this(s, 0);
	}
	
	/**
	 * Constructor for the hotel room
	 * @param s the name of the room
	 * @param i the id of the room
	 */
	public HotelSuite(String s, int i) {
		super(s, i);
	}
	
	/**
	 * Reserves the hotel room
	 * @param client the client to lease to
	 * @param date the date to lease to
	 * @param i the first number
	 * @param j the second number
	 * @throws RentalCapacityException if the capacity is too high
	 * @throws RentalDateException if the date is wrong
	 * @return a lease
	 */
	@Override
	public Lease reserve(Client client, LocalDate date, int i, int j) throws RentalCapacityException, RentalDateException {
		return null;
	}
	
	/**
	 * Records the previous lease for the hotel room
	 * @param i the first number
	 * @param client the client to lease to
	 * @param endDate the date to start
 	 * @param startDate the date to lease to
	 * @param j the second number
	 * @throws RentalCapacityException if the capacity is too high
	 * @throws RentalDateException if the date is wrong
	 * @return a lease
	 */
	@Override
	public Lease recordExistingLease(int i, Client client, LocalDate startDate, LocalDate endDate, int j) throws RentalCapacityException, RentalDateException {
		return null;
	}
	
	/**
	 * Removes the service
	 * @param date the date to start
	 * @return the lease info
	 */
	@Override
	public SortedList<Lease> removeFromServiceStarting(LocalDate date) {
		return null;
	}
	
	/**
	 * Gets the description of the room
	 * @param the description of the room
	 */
	@Override
	public String getDescription() {
		return null;
	}
	
	/**
	 * Checks the dates
	 * @param startDate the date to start
	 * @param endDate the date to end
	 * @throws RentalDateException if the date is wrong
	 */
	@Override
	public void checkDates(LocalDate startDate, LocalDate endDate) throws RentalDateException {
		
	}
}
