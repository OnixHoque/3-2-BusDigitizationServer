
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
    /**
     * When the command is read, the response is in this order this:
     * <br>
     * <br>ROUTE_RESULTS
     * <br>No. of Results
     * <br>bus_service_1
     * <br>rating_1
     * <br>time_1
     * <br>distance_1
     * <br>price_1
     * <br>hazard_count_1
     * <br>.
     * <br>.
     * <br>locationN
     * 
     **/
    public static String ROUTE_RESULT = "ROUTE_RESULTS";
    
}

