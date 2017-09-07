package ph.edu.tip.schedulerappinstructor.app;

/**
 * @author pocholomia
 * @since 24/01/2017
 */

public class Constants {


    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String APPJSON = "application/json";
    public static final String ID = "id";
    public static final String ACCEPT = "Accept";

    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";


    public static final String EMAIL_ADD = "admin_email";
    public static final String PASSWORD = "password";

    public static final String OLD_PASSWORD = "old_password";
    public static final String SLOT_IMAGE = "slotimage";


    //transaction
    public static final String USER_ID = "user_id";
    public static final String EVENT_ID = "scheduled_event_id";
    public static final String SEAT_NUM = "seat_number";

    //slot cateogry
    public static final String CATEGORY_ID = "slot_category_id";

    public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";

    public class Realm {
        public static final String EVENT_ID = "scheduledEventId";
        public static final String SLOT_CATEGORY_ID = "slotCategoryId";
        public static final String CALENDAR_ID = "calendarId";
        public static final String SCHEDULED_EVENT_ADMIN_ID = "adminId";
    }

    public class ApiParameters {

        public class ScheduledEvents {
            public static final String SCHEDULED_EVENT_NAME = "name";
            public static final String SCHEDULED_EVENT_DESC = "description";
            public static final String SCHEDULED_EVENT_LOC = "location";
            public static final String SCHEDULED_EVENT_ADD = "address";
            public static final String SCHEDULED_EVENT_LAT = "lat";
            public static final String SCHEDULED_EVENT_LNG = "lng";
            public static final String SCHEDULED_EVENT_TYPE = "type";
            public static final String SCHEDULED_EVENT_TAGS = "tags";
            public static final String SCHEDULED_EVENT_IMAGE = "image";
            public static final String SCHEDULED_EVENT_ID = "scheduled_event_id";
        }

        public class ScheduledEventSlotCategory {
            public static final String NAME = "slot_name";
            public static final String SLOT_ALOTTED = "no_slot_allotted";
            public static final String PRICE = "price";
            public static final String SELECT_SEAT_NUMBER = "select_seat_number";
            public static final String IMAGE = "image";
            public static final String SCHEDULED_EVENT_ID= "scheduled_event_id";
            public static final String SLOT_CATEGORY_ID = "slot_category_id";
        }

        public class ScheduledEventSchedule{
            public static final String DATE = "date";
            public static final String TIME_START = "time_start";
            public static final String TIME_END = "time_end";
            public static final String SCHEDULED_EVENT_ID = "scheduled_event_id";
            public static final String SCHED_ID = "calendar_id";
        }

        public class ScheduledEventAdmin{
            public static final String POSITION = "position";
            public static final String DESCRIPTION = "description";
            public static final String ADMIN_ID = "admin_id";
            public static final String ID = "id";
            public static final String SCHEDULED_EVENT_ID = "scheduled_event_id";
        }



    }

}
