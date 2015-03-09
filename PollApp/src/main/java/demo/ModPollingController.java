package demo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/*
 * CMPE-273 - SMSVoting Application -March 4 2015
 * Created By Izhar Raazi 
 * Please add Headers while testing - Accept - application/json
 * 									Content-Type - application/json
 */

@RestController
public class ModPollingController extends HttpServlet{
	
	//-------------------   URI----------------------------------------------
	public static final String MODERATOR_VIEW = "api/v1/moderators/{moderator_id}";
	public static final String MODERATOR_CREATE = "api/v1/moderators";
	public static final String MODERATOR_UPDATE = "api/v1/moderators/{moderator_id}";
	public static final String POLL_CREATE  = "api/v1/moderators/{moderator_id}/polls";
	public static final String POLL_VIEW_WITH_RESULTS = "api/v1/moderators/{moderator_id}/polls/{poll_id}";
	public static final String POLL_VIEW_WITHOUT_RESULTS = "api/v1/polls/{poll_id}";
	public static final String POLL_LIST_ALL	= "api/v1/moderators/{moderator_id}/polls";
	
	//-------------------------------------------------------------------------------------------
	private static int modId = 123456;
	private static int pollId = 123456;
	private static HashMap<String, Moderator> adminMap = new HashMap<String, Moderator>(); 
	private static HashMap<String,Poll> pollMap = new HashMap<String, Poll>();
	private static HashMap<String,Poll> modPollMap = new HashMap<String,Poll>();

	//----------------------------------------------------------------------------------------------	
//Add Moderator
	@RequestMapping(value = MODERATOR_CREATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addModerator(@Valid@RequestBody Moderator mod,@RequestHeader(value = "Authorization") String a) throws Exception {
		if(AuthEngine.authenticate(a)){
			if(mod.getEmail().isEmpty()){
				throw new Exception("Email is empty or wrong");
			}
			if(mod.getName().isEmpty()){
				throw new Exception("Name is Empty or wrong");
			}
			if(mod.getPassword().isEmpty()){
				throw new Exception("Password is inconsistent with the policy");
			}
			if (adminMap == null) {
				adminMap = new HashMap<String, Moderator>();
				modId = 123456;
			}
			mod.setId(modId);
			LocalDateTime localTime = LocalDateTime.now(); 	
			String created_at = localTime.toString();
			mod.setCreated_at(created_at);
			Moderator addUser = new Moderator(mod.getId(), mod.getName(),
					mod.getEmail(), mod.getPassword(), mod.getCreated_at());
			adminMap.put(Integer.toString(modId), addUser);
			modId = modId + 1;
			return new ResponseEntity<Moderator>(addUser,HttpStatus.CREATED);
		}else
			return new  ResponseEntity("YOU ARE NOT A MODERATOR SORRY!!",HttpStatus.BAD_REQUEST);
		
	}
//----------------------------------------------------------------------------------------------
//   View Moderator	
	@RequestMapping(value = MODERATOR_VIEW, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Moderator> viewMod(@PathVariable("moderator_id") String mod_id) throws Exception {
		if (adminMap.get(mod_id) == null){
			throw new Exception("INVALID MODERATOR ID / NOT EXISTS");
		}
		
		if (adminMap.get(mod_id) != null){
			Moderator mod1 = new Moderator();
			mod1 = adminMap.get(mod_id);
			return new ResponseEntity<Moderator>(mod1,HttpStatus.OK);
		}
		return null;
		
	}
	
	//----------------------------------------------------------------------------------------------	
// Update Moderator
	@RequestMapping(value = MODERATOR_UPDATE, method = RequestMethod.PUT)
	public ResponseEntity updateModerator(@Valid@PathVariable("moderator_id") String mod_id,@RequestBody Moderator mod,@RequestHeader(value = "Authorization") String a) throws Exception{
		if(AuthEngine.authenticate(a)){
			if (adminMap.get(mod_id) == null){
				return new ResponseEntity("ERROR:: Moderator Does not Exist!!",HttpStatus.NOT_FOUND);
			}
			
			if (adminMap.get(mod_id) != null){
				if(mod.getEmail().isEmpty()){
					throw new Exception("Email is empty or wrong");
				}
				if(mod.getPassword().isEmpty()){
					throw new Exception("Password is inconsistent with the policy");
				}
				Moderator mod1 = new Moderator();
				mod1 = adminMap.get(mod_id);
				LocalDateTime updatedTime = LocalDateTime.now();
				mod1.setCreated_at(updatedTime.toString());
				mod1.setName(mod.getName());
				mod1.setEmail(mod.getEmail());
				mod1.setPassword(mod.getPassword());
				return new ResponseEntity<Moderator>(mod1,HttpStatus.OK);
			}
			return null;
		}else{
			return new  ResponseEntity("YOU ARE NOT A MODERATOR SORRY!!",HttpStatus.BAD_REQUEST);
		}
		
		
	}
	//----------------------------------------------------------------------------------------------	
//Add Poll
	
	@RequestMapping(value = POLL_CREATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addPoll(@RequestBody Poll Poll_body,@PathVariable("moderator_id") String mod_id,@RequestHeader(value = "Authorization") String a) throws Exception {
		if(AuthEngine.authenticate(a)){
			if (adminMap.get(mod_id) == null){
				return new ResponseEntity("ERROR:: Moderator Id Incorrect !!",HttpStatus.NOT_FOUND);
			}
			if (adminMap.get(mod_id) != null){
				if(Poll_body.getQuestion().isEmpty()){
					throw new Exception("Question is empty or wrong");
				}
				if(Poll_body.getStarted_at().isEmpty()){
					throw new Exception("Starting Time is Empty or wrong");
				}
				if(Poll_body.getExpired_at().isEmpty()){
					throw new Exception("Expire at time is wrong and is inconsistent with the policy");
				}
			String i = Integer.toString(pollId, 36);
			
			Poll_body.setPoll_id(i);
			Poll poladd = new Poll(i,Poll_body.getQuestion(),Poll_body.getStarted_at(),Poll_body.getExpired_at(),Poll_body.getchoice(),Poll_body.getResults());
			Moderator mod1  = new Moderator();
			mod1 = adminMap.get(mod_id);
			mod1.addPoll(poladd);
			pollMap.put(i, poladd);
			pollId = pollId+1;
			return new ResponseEntity<Poll>(poladd,HttpStatus.CREATED);
			
		}
			return null;
		}else
			return new  ResponseEntity("ERROR:: You are not a moderator to create a Poll Sorry!!",HttpStatus.BAD_REQUEST);
		
	}
	//----------------------------------------------------------------------------------------------	
//Poll without results
	@RequestMapping(value = POLL_VIEW_WITHOUT_RESULTS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Poll> viewpollwithoutresults(@PathVariable("poll_id") String pol_id) throws Exception {
		if (pollMap.get(pol_id) == null){
			throw new Exception("INVALID POLLING ID");
		}
		
		if (pollMap.get(pol_id) != null){
			Poll poll1 = new Poll();
			poll1 = pollMap.get(pol_id);
			poll1.setResults(null);
			return new ResponseEntity<Poll>(poll1,HttpStatus.OK);
		}
		return null;
		
	}
	//----------------------------------------------------------------------------------------------	
//	Choice implementation

	@RequestMapping(value = POLL_VIEW_WITHOUT_RESULTS, method = RequestMethod.PUT)
	public ResponseEntity updateVote(@PathVariable("poll_id") String pol_id,@RequestParam(value="choice")String choice){
		
		if (pollMap.get(pol_id) == null){
			return new ResponseEntity("NO POLL EXISTS",HttpStatus.NOT_FOUND);
		}
		Poll p = new Poll();
		p = pollMap.get(pol_id);
		if (p!=null){
			 p.getResults().set(Integer.parseInt(choice), p.getResults().get(Integer.parseInt(choice)) +1);
			
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return null;
	}
	//----------------------------------------------------------------------------------------------	
//Poll with results
	@RequestMapping(value = POLL_VIEW_WITH_RESULTS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity viewpollwithresults(@PathVariable("moderator_id") String mod_id,@PathVariable("poll_id") String pol_id,@RequestHeader(value = "Authorization") String a) throws Exception {
		if(AuthEngine.authenticate(a)){
			if (adminMap.get(mod_id) == null){
				throw new Exception("WRONG MODERATOR SORRY NOT AUTHORIZED");
			}
			else if (adminMap.get(mod_id) != null && pollMap.get(pol_id) == null){
					throw new Exception("NO POLL ID EXISTS !!") ;
			}
			if (pollMap.get(pol_id) != null){
					Moderator mod = ModPollingController.adminMap.get(mod_id);
					Poll polling = mod.getPollById(pol_id);
					ArrayList<Integer> res = new ArrayList<Integer>();
					res.add(500);
					res.add(600);
					polling.setResults(res);
					return new ResponseEntity<Poll>(polling,HttpStatus.OK);
			}
			
		
		}else
            return new ResponseEntity("MSG: You are not an authorized moderator to make this transaction",HttpStatus.BAD_REQUEST) ;
		return null;
	}
	//----------------------------------------------------------------------------------------------	
// List All Polls
	@RequestMapping(value=POLL_LIST_ALL, method = RequestMethod.GET)
	    public ResponseEntity listAllPollsGet(@PathVariable("moderator_id") String modid, @RequestHeader(value = "Authorization") String s) {
	        if (AuthEngine.authenticate(s) )
	        {
	            Moderator mod = ModPollingController.adminMap.get(modid);
	            return new ResponseEntity(mod.getPolls(),HttpStatus.OK);
	        }
	        else
	        {
	            return new ResponseEntity(" YOU ARE NOT AUTHORIZED TO MAKE THIS REQUEST",HttpStatus.BAD_REQUEST) ;
	        }

	    }
	
//----------------------------------------------------------------------------------------------
// DELETE POLL
	
	@RequestMapping(value = POLL_VIEW_WITH_RESULTS, method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Poll> delpoll(@PathVariable("moderator_id") String mod_id,@PathVariable("poll_id") String pol_id,@RequestHeader(value = "Authorization") String a) throws Exception {
		if(AuthEngine.authenticate(a)){
			if (adminMap.get(mod_id) == null){
				throw new Exception("Wrong Moderator Sorry not Authorized");
			}
			else if (adminMap.get(mod_id) != null){
				if (pollMap.get(pol_id) == null){
					throw new Exception("No poll id Exists !!") ;
				}
				
				if (pollMap.get(pol_id) != null){
					Moderator mod1 = adminMap.get(mod_id);
					mod1.removePollById(pol_id);
					pollMap.remove(pol_id);
					modPollMap.remove(pol_id);
					return new ResponseEntity<Poll>(HttpStatus.NO_CONTENT);
				}
			}
			
			return null;
		} else 
			return new ResponseEntity("MSG: YOU ARE NOT AN AUTHORIZED MODERATOR TO MAKE THIS TRANSACTION",HttpStatus.BAD_REQUEST) ;
		
		
	}

	
}
	




