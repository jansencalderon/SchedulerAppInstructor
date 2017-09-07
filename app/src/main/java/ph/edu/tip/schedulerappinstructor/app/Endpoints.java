package ph.edu.tip.schedulerappinstructor.app;

/**
 * @author pocholomia
 * @since 24/01/2017
 */

public class Endpoints {


   // public static final String BASE_URL = "http://grabchores.pythonanywhere.com";
   // public static final String BASE_URL = "http://web.tip.edu.ph/tap";
    //public static final String BASE_URL = "http://10.3.32.201/scheduler";
    private static final String BASE_URL = "http://scheduler.dgts.ph";


    static final String API_URL = BASE_URL + "/api/";

    public static final String ID = "{id}";


    //Credentials
    public static final String LOGIN = "admin/login";
    public static final String REGISTER = "user";
    public static final String CHANGE_PASSWORD = "user/changepass";
    public static final String UPDATE_ACCOUNT = "user/updateacct";
    public static final String FORGOT_PASSWORD = "user/forgotpass/{email_address}";
    public static final String LOGOUT = "user/logout";



    //Profile
    public static final String IMG_HOLDER = "{img}";
    public static final String IMAGE_URL = BASE_URL+"/storage/app/" + IMG_HOLDER;
    public static final String IMAGE_URL_FB = "http://graph.facebook.com/" + IMG_HOLDER + "/picture?type=large";
    public static final String PROVINCE = "province";
    public static final String CITIES = "cities/{province_id}";
    public static final String SECURITY_QUESTION = "security-question";
    public static final String SECURITY_QUESTION2 = "security-question";
    public static final String UPLOAD_IMG = "user/updateimage";
    public static final String STATIC_IMAGE_URL = BASE_URL+"/storage/app/";



    //Promo
    public static final String PROMO_TYPE = "S";
    public static final String PROMO_LIST = "promo/"+ PROMO_TYPE;
    public static final String PROMO_DETAIL = PROMO_LIST + "/" + ID;

    //Company
    public static final String COMPANY_LIST = "company";
    public static final String COMPANY_DETAIL = COMPANY_LIST + "/" + ID;

    //Events
    public static final String EVENT_URL_IMAGE = BASE_URL + "/storage/app/";
    public static final String EVENT_LIST = "admin_scheduled_events";
    public static final String EVENT_DETAIL = "admin_scheduled_events" + "/" + ID;
    public static final String EVENT_SEAT = "admin_scheduled_events/slot"+ "/" + ID;

    //Daily
    public static final String DAILY_URL_IMAGE = BASE_URL + "/storage/app/";
    public static final String DAILY_LIST = "daily_events";
    public static final String DAILY_DETAIL = "daily_events" + "/" + ID;
    public static final String DAILY_SEAT = "daily_events/slot"+ "/" + ID;


     //Admin
     public static final String ADMIN_LIST = "scheduled_events/admin"+"/"+ ID;
     public static final String ADMIN_DETAIL = ADMIN_LIST + "/" + ID;


     //transaction
     public static final String TRANSACTIONS = "scheduled_event_reservation";
     public static final String TRANSACTIONS_DETAIL = TRANSACTIONS + "/" + ID;
     public static final String TRANSACTIONS_RESERVE = TRANSACTIONS;



    //scheduled events
    public static final String CREATE_SCHEDULED_EVENT = "admin_scheduled_events";
    public static final String CHANGE_EVENT_STATUS = "admin_scheduled_events/change_status/{scheduled_event_id}";


    //slot category
    public static final String CREATE_SCHEDULED_EVENT_SLOT_CATEGORY = "scheduled_event_slot_category";
    public static final String DELETE_OR_UPDATE_SLOT_CATEGORY = "scheduled_event_slot_category/{slot_category_id}";


    //calendar
    public static final String CREATE_CALENDAR = "scheduled_event_calendar";
    public static final String DELETE_OR_UPDATE_CALENDAR = "scheduled_event_calendar/{calendar_id}";

    //events instructor
    public static final String INSTRUCTOR_LIST = "scheduled_event_instructor/{scheduled_event_id}";

    //
    public static final String COMPANY_INSTRUCTOR = "admin";
    public static final String ADD_EVENT_INSTRUCTOR = "scheduled_event_admin";


    public static final String DELETE_OR_UPDATE_EVENT_INSTRUCTOR = "scheduled_event_admin/{id}";
}
