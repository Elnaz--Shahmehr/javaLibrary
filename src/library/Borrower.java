package library;

public class Borrower {
	
	private int libraryCardNumber;
	private String name;
	private String streetAddress;
	private String postalCode;
	private String city;
	
	
	
	public Borrower(String city, int libraryCardNumber, String name,
			String postalCode, String streetAddress) {
		super();
		this.city = city;
		this.libraryCardNumber = libraryCardNumber;
		this.name = name;
		this.postalCode = postalCode;
		this.streetAddress = streetAddress;
	}
	
	public Borrower( String name,
			String streetAddress,String postalCode, String city) {
		super();
		this.city = city;
		this.libraryCardNumber = 0;
		this.name = name;
		this.postalCode = postalCode;
		this.streetAddress = streetAddress;
	}
	
	public int getLibraryCardNumber() {
		return libraryCardNumber;
	}
	public void setLibraryCardNumber(int libraryCardNumber) {
		this.libraryCardNumber = libraryCardNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String toString(){
		return name + "\n" +
				streetAddress + "\n" +
				postalCode + " " + city;
		
	}
}
