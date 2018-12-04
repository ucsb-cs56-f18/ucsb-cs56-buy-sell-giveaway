package edu.ucsb.cs56.pconrad.springboot.hello;

public class Posting {
	private String title;
	private String description;
	private int id;
	
	private String email;
	private String number;
	private int approved;

	public Posting(String title, String description, String email, String number) {
		this.title = title;
		this.description = description;
		this.email = email;
		this.number = number;
		this.approved = 0;
	}
	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getEmail() {
		return email;
	}

	public String getNumber() {
		return number;
	}

	public int getID() {
                return id;
        }
	public void setID(int i){
		this.id=i;
	}
	public void setApproved() {
		this.approved = approved;
	}
}
