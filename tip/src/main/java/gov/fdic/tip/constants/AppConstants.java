package gov.fdic.tip.constants;

/**
 * @author Prasad Ravva
 * @Project TIP
 * @Module Review Cycle Group
 * @Date 9/28/2025
 * Application-wide constants. 
 */
public class AppConstants {
    
    private AppConstants() {
		// Private constructor to prevent instantiation
	}
    
    public static final String API_BASE_PATH = "/api/v1";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    
    public static class ErrorMessages {
        public static final String RESOURCE_NOT_FOUND = "Resource not found with id: ";
        public static final String INVALID_INPUT = "Invalid input provided";
        public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";
    }
    
 }	