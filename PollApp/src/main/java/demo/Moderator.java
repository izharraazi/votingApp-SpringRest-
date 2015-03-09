package demo;
/*
 * CMPE-273 - SMSVoting Application -March 4 2015
 * Created By Izhar Raazi 
 * Please add Headers while testing - Accept - application/json
 * 									Content-Type - application/json
 */
import java.util.ArrayList;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import demo.Poll;

public class Moderator {

	
	private int id;
	@NotNull
	private String name;
	@NotNull
	@Email (message="Incorrect email id. Please enter correct email.")
	private String email;
	@NotNull
	@Length(min=4, message="Passowrd must contain minimum 4 characters")
	private String password;
	private String created_at;
	
	private ArrayList<Poll> polls;

	/*--------------  Constructor-------------------*/
	public Moderator() {}

	public Moderator(int id, String name, String email, String password,String created_at) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.created_at=created_at;
		polls = new ArrayList<Poll>();
	}
	public Moderator(String email, String password)
	{
		this.email=email;
		this.password=password;
	}

	
	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@JsonIgnore
	   public ArrayList<Poll> getPolls() {
	        return polls;
	    }

	    public void setPolls(ArrayList<Poll> polls) {this.polls  = polls;
	    }

	    public void addPoll(Poll poll) {this.polls.add(poll) ;
	    }

	    //Get poll created by moderator using poll id
	    public Poll getPollById(String id) {
	        Poll poll = null;
	        for (Poll p : this.polls)
	        {
	            if (p.getPoll_id().equals(id)) {
	                poll = p;
	                break;
	            }
	        }
	        return poll;
	    }

	    //Remove poll created by moderator using poll id
	    public void removePollById(String id) {
	        Poll poll = getPollById(id);
	        this.polls.remove(poll);
	    }


}
