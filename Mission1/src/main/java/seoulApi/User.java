package seoulApi;

public class User {
	
	private int ID;
	private double LAT;
	private double LNT;
	private String REG_DTTM;
	
	public User(int ID, double LAT, double LNT, String REG_DTTM) {
		super();
		this.ID = ID;
		this.LAT = LAT;
		this.LNT = LNT;
		this.REG_DTTM = REG_DTTM;
	}
	public double getLAT() {
		return LAT;
	}
	public void setLAT(double LAT) {
		this.LAT = LAT;
	}
	public double getLNT() {
		return LNT;
	}
	public void setLNT(double LNT) {
		this.LNT = LNT;
	}
	public String getREG_DTTM() {
		return REG_DTTM;
	}
	public void setREG_DTTM(String REG_DTTM) {
		this.REG_DTTM = REG_DTTM;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	
}
