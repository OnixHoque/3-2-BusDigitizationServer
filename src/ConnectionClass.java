
public class ConnectionClass {
    public static String LOGIN_SQ = "LOGIN";		//followed by 'usr' and 'password'. Send 0 : Unsuccessful login; 	1 : Successful login
    public static String SIGNUP_SQ = "SIGNUP";		//followed by 'usr' and 'password'. Send 0 : Unsuccessful Signup; 	1 : Successful Signup
    public static String TEST_CONN = "PING";		//say hi
    /**
     * When the command is read, the response is like this:
     * <br>
     * <br>LOCATIONS
     * <br>No. of locations
     * <br>location1
     * <br>location2
     * <br>.
     * <br>.
     * <br>locationN
     * 
     **/
    public static String LOCATION_LIST = "LOCATIONS";
    
}

