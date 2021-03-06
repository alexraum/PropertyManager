/**
 * 
 */
package edu.ncsu.csc216.business.model.properties;

import java.time.LocalDate;
import java.util.Scanner;

import edu.ncsu.csc216.business.list_utils.SortedLinkedListWithIterator;
import edu.ncsu.csc216.business.list_utils.SortedList;
import edu.ncsu.csc216.business.model.contracts.Lease;
import edu.ncsu.csc216.business.model.stakeholders.Client;
import edu.ncsu.csc216.business.model.stakeholders.PropertyManager;

/**
 * The RentalUnit class provides all necessary state and behavior for child
 * classes that extend the RentalUnit class. RentalUnit also contains abstract
 * methods whose unique implementations are delegated to the child classes
 * and determined by their needs.
 * 
 * @author Alex Raum, Walker Clem
 */
public abstract class RentalUnit implements Comparable<RentalUnit> { // implements Comparable<RentalUnit> ?

	/** the maximum floor a unit can have */
	public static final int MAX_FLOOR = 45;
	/** the minimum floor a unit can have */
	public static final int MIN_FLOOR = 1;
	/** the maximum room a unit can have */
	public static final int MAX_ROOM = 99;
	/** the minimum room a unit can have */
	public static final int MIN_ROOM = 10;
	/** if the unit is in service */
	private boolean inService;
	/** the floor number of the unit */
	private int floor;
	/** the room number of the unit */
	private int room;
	/** the units capacity */
	private int capacity;
	/** a list of all the current Leases for the unit */
	protected SortedLinkedListWithIterator<Lease> myLeases;
	
	/**
	 * Constructor for the Rental unit
	 * 
	 * @param location String that contains the floor and room number
	 *        of the RentalUnit
	 * @param capacity the capacity of the RentalUnit
	 * @throws IllegalArgumentException if the capacity parameter is 
	 *         less than 0, if the floor parameter is greater than the max 
	 *         floor or less than the min floor, or if the room parameter is 
	 *         greater than the max room or less than the min room
	 */
	public RentalUnit(String location, int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException();
		}
		Scanner scan = new Scanner(location);
		scan.useDelimiter("-");
		int f = scan.nextInt();
		int r = scan.nextInt();
		scan.close();
		if (f > MAX_FLOOR || f < MIN_FLOOR) {
			throw new IllegalArgumentException();
		}
		if (r > MAX_ROOM || r < MIN_ROOM) {
			throw new IllegalArgumentException();
		}
		this.inService = true;
		this.floor = f;
		this.room = r;
		this.capacity = capacity;
		this.myLeases = new SortedLinkedListWithIterator<Lease>();
	}
	
	/**
	 * Gets the capacity of the unit
	 * 
	 * @return the capacity
	 */
	public int getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Gets the floor number
	 * 
	 * @return floor number
	 */
	public int getFloor() {
		return this.floor;
	}
	
	/**
	 * Gets the room number
	 * 
	 * @return room number
	 */
	public int getRoom() {
		return this.room;
	}
	
	/**
	 * Compares two units by examining their floor 
	 * and room numbers.
	 * 
	 * @param unit the unit to compare to
	 * @return an integer representing the relative 
	 *         values between the two units based 
	 *         on the comparison criteria
	 */
	public int compareTo(RentalUnit unit) {
		if (getFloor() != unit.getFloor()) {
			return getFloor() - unit.getFloor();
		} else {
			return getRoom() - unit.getRoom();
		}
	}
	
	/**
	 * Returns the unit to service
	 */
	public void returnToService() {
		this.inService = true;
	}
	
	/**
	 * If the unit is in service
	 * 
	 * @return a boolean indicating if the 
	 *         unit is currently in service
	 */
	public boolean isInService() {
		return this.inService;
	}
	
	/**
	 * removes the unit from service
	 */
	public void takeOutOfService() {
		this.inService = false;
	}
	
	/**
	 * The reserve method is used to reserve the rental unit for a new Lease
	 * 
	 * @param client the client creating the lease
	 * @param startDate the start date of the lease
	 * @param duration the duration of the lease
	 * @param occupants the number of occupants for the lease
	 * @return the new Lease that was reserved
	 * @throws RentalCapacityException if the maximum capacity is exceeded
	 * @throws RentalDateException if the date is invalid
	 * @throws RentalOutOfServiceException if the unit is out of service
	 */
	public abstract Lease reserve(Client client, LocalDate startDate, int duration,
			int occupants) throws RentalOutOfServiceException, RentalDateException, RentalCapacityException; 
	
	/**
	 * A method for reserving the rental unit for an existing lease
	 * 
	 * @param confirmationNumber the confirmation number of the lease
	 * @param client the client of the lease
	 * @param startDate the start date of the lease
	 * @param endDate the end date of the lease 
	 * @param numOccupants the number of occupants of the lease 
	 * @return the new Lease that was reserved
	 * @throws RentalCapacityException if the maximum capacity is exceeded
	 * @throws RentalDateException if the date is invalid
	 */
	public abstract Lease recordExistingLease(int confirmationNumber, Client client,
			LocalDate startDate, LocalDate endDate, int numOccupants) throws RentalDateException, RentalCapacityException;
	
	/**
	 * Checks the start and end dates to ensure that they
	 * are valid and that they don't conflict.
	 * 
	 * @param startDate the start date
	 * @param endDate the end date
	 * @throws RentalDateException if the either date is outside 
	 *         of the earliest and latest date range or if the start date
	 *         is earlier than the end date
	 */
	public void checkDates(LocalDate startDate, LocalDate endDate) throws RentalDateException {
		if (startDate.isBefore(PropertyManager.EARLIEST_DATE)) {
			throw new RentalDateException("Lease date cannot start before " + PropertyManager.EARLIEST_DATE);
		}
		if (endDate.isAfter(PropertyManager.LATEST_DATE)) {
			throw new RentalDateException("Lease date cannot end after " + PropertyManager.LATEST_DATE);
		}
		if (startDate.isAfter(endDate)) {
			throw new RentalDateException("Start date for lease cannot be after the end date");
		}
	}
	
	/**
	 * Checks the conditions for a potential Lease to ensure
	 * that they are met.
	 * 
	 * @param client the Client of the Lease
	 * @param startDate the start date of the Lease
	 * @param duration the duration of the Lease
	 * @param numOccupants the number of occupants 
	 * @throws IllegalArgumentException if the client or 
	 *         startDate are null, or if the duration or numOccupants
	 *         parameters are less than 1 
	 * @throws RentalOutOfServiceException if any of the 
	 *         conditions are not met 
	 */
	protected void checkLeaseConditions(Client client, LocalDate startDate, int duration,
			int numOccupants) throws RentalOutOfServiceException {
		if (client == null || startDate == null || duration < 1 || numOccupants < 1) {
			throw new IllegalArgumentException();
		}
		if (!isInService()) {
			throw new RentalOutOfServiceException("Rental unit not in service");
		}
	}
	
	/**
	 * Removes from service on a specified date
	 * 
	 * @param date the cutoff date for the unit to be removed
	 * @return a list of leases with whose start date is after
	 *         the cutoff date
	 */
	public SortedList<Lease> removeFromServiceStarting(LocalDate date) {
		takeOutOfService();
		SortedLinkedListWithIterator<Lease> removed = new SortedLinkedListWithIterator<Lease>();
		int cutoff = cutoffIndex(date);
		if (cutoff >= 0) {
			removed = (SortedLinkedListWithIterator<Lease>) myLeases.truncate(cutoff);
		} else {
			return removed;
		}
		return removed;
	}
	
	/**
	 * Returns the index of the first Lease with a start date on
	 * or after the parameter date.
	 * 
	 * @param date for the Lease dates to be compared to
	 * @return the index of the first date on or after the 
	 *         parameter date
	 */
	protected int cutoffIndex(LocalDate date) {
		for (int i = 0; i < myLeases.size(); i++) {
			if (myLeases.get(i).getStart().compareTo(date) >= 0) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Cancel the Lease in the my Leases list with the corresponding 
	 * confirmation number.
	 * 
	 * @param confirmationNumber the confirmation number of the
	 *        Lease to be canceled
	 * @return the Lease that has been canceled
	 * @throws IllegalArgumentException if confirmationNumber parameter
	 *         does not match any leases in the myLeases list 
	 */
	public Lease cancelLeaseByNumber(int confirmationNumber) {
		for (int i = 0; i < myLeases.size(); i++) {
			if (myLeases.get(i).getConfirmationNumber() == confirmationNumber) {
				return myLeases.remove(i);
			}
		}
		throw new IllegalArgumentException();
	}
	
	/**
	 * Adds a Lease to the myLeases field.
	 *
	 * @param lease the Lease to be add
	 * @throws IllegalArgumentException if the lease is for a different
	 *         rental unit
	 */
	public void addLease(Lease lease) {
		if (!inService) {
			return;
		}
		if (!this.equals(lease.getProperty())) {
			throw new IllegalArgumentException();
		}
		this.myLeases.add(lease);
	}
	
	/**
	 * Returns the list of Leases for this rental unit 
	 * as an array of Strings.
	 * 
	 * @return each Lease for this RentalUnit stored in
	 *         an array
	 */
	public String[] listLeases() {
		String[] leases = new String[myLeases.size()];
		for (int i = 0; i < myLeases.size(); i++) {
			String[] data = myLeases.get(i).leaseData();
			leases[i] = data[0] + " | " + data[1] + " | " 
			+ data[2] + " | " + data[4] + " (" +  data[5] + ")";
		}
		return leases;
	}
	
	/**
	 * Gets the description of the RentalUnit as a String
	 * 
	 * @return the description of the RentalUnit as a String
	 */
	public String getDescription() {
		String f = "" + getFloor();
		String c = "" + getCapacity();
		if (f.length() == 1) {
			f = " " + getFloor();
		} 
		if (c.length() == 1) {
			c = "  " + getCapacity();
		} else if (c.length() == 2) {
			c = " " + getCapacity();
		} 
		if (isInService()) {
			return f + "-" + getRoom() + " | " + c;
		} else {
			return f + "-" + getRoom() + " | " + c + " Unavailable";
		}
	}
	
	/**
	 * Creates a unique hash code for the RentalUnit
	 * 
	 * @return the hash code for the RentalUnit
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + floor;
		result = prime * result + room;
		return result;
	}
	
	/**
	 * Compares this RentalUnit to another object to determine if they are equal.
	 * 
	 * @param obj an object for this RentalUnit to be compared to
	 * @return boolean representing if this RentalUnit is equal to the parameter
	 *         that it was compared to
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RentalUnit))
			return false;
		RentalUnit other = (RentalUnit) obj;
		if (floor != other.floor)
			return false;
		if (room != other.room)
			return false;
		return true;
	}
}
