package demo;
/*
 * CMPE-273 - SMSVoting Application -March 4 2015
 * Created By Izhar Raazi 
 * Please add Headers while testing - Accept - application/json
 * 									Content-Type - application/json
 */
import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Poll {

	@NotEmpty
	@NotNull
	private String poll_id;
	@NotEmpty
	@NotNull
	private String question;
	@NotEmpty
	@NotNull
	private ArrayList<String> choice;
	@NotEmpty
	@NotNull
	private String started_at;
	@NotEmpty
	@NotNull
	private String expired_at;
	private ArrayList<Integer> results;
	
	  public Poll() {
	        choice = new ArrayList<String>();
	        results = new ArrayList<Integer>() ;
	    }
	
	  public Poll(String poll_id,String question, String started_at,String expired_at, ArrayList<String> choice,ArrayList<Integer> results ) {
	        this.poll_id = poll_id;
	        this.question = question;
	        this.started_at = started_at;
	        this.expired_at = expired_at;
	        this.choice = new ArrayList<String>(choice) ;
	        results = new ArrayList<Integer>() ;
	        for (int i=0; i<choice.size();i++ )
	        {
	                   results.add(new Integer(0));
	        }
	    }
	public String getPoll_id() {
		return poll_id;
	}
	public void setPoll_id(String poll_id) {
		this.poll_id = poll_id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	 public ArrayList<String> getchoice() {
	        return choice;
	    }

	    public void setChoice(ArrayList<String> choice) {

	        this.choice = choice;
	        for (int i=0; i<choice.size();i++ )
	        {
	            results.add(new Integer(0));
	        }
	    }
	    
	public String getStarted_at() {
		return started_at;
	}
	public void setStarted_at(String started_at) {
		this.started_at = started_at;
	}
	public String getExpired_at() {
		return expired_at;
	}
	public void setExpired_at(String expired_at) {
		this.expired_at = expired_at;
	}
	 public ArrayList<Integer> getResults() {
	        return results;
	    }
	 public void  setResults(ArrayList<Integer> results) {
	        this.results = results;
	    }
}