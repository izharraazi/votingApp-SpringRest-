package demo;
/*
 * CMPE-273 - SMSVoting Application -March 4 2015
 * Created By Izhar Raazi 
 * Please add Headers while testing - Accept - application/json
 * 									Content-Type - application/json
 */
import org.apache.tomcat.util.codec.binary.Base64;
public class AuthEngine {

	public AuthEngine() {
		// TODO Auto-generated constructor stub
	}

	 public static Boolean authenticate(String authorization){
	        if (authorization != null && authorization.startsWith("Basic") ){

	            String base64Credentials = authorization.substring("Basic".length()).trim();
	            String credentials = new String(Base64.decodeBase64(base64Credentials.getBytes()));
	            String[] values = credentials.split(":", 2);
	            if(values[0].equals("foo") && values[1].equals("bar")){
	                  return true;
	            }
	        }
	        return false;
	    }
}
