package ph.edu.tip.schedulerappinstructor.model.data;



import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class EventDaily extends RealmObject {

    @PrimaryKey
    @SerializedName("daily_event_id")
    private String id;
    @SerializedName("name")
    private String eventName;
    @SerializedName("description")
    private String eventDescription;
    @SerializedName("slot")
    private String eventSlot;
    @SerializedName("price")
    private String eventPrice;
    @SerializedName("type")
    private String eventType;
    @SerializedName("time_start")
    private String eventTimeFrom;
    @SerializedName("time_end")
    private String eventTimeTo;
    @SerializedName("daily_event_image")
    private String imageDirectory;
    @SerializedName("location")
    private String location;
    @SerializedName("address")
    private String address;
    @SerializedName("lat")
    private double latitude;
    @SerializedName("long")
    private double longitude;
    @SerializedName("company_id")
    private String companyId;
    @SerializedName("calendar_desc")
    private String calendar;
    @SerializedName("hours_per_session")
    private String hoursperSession;
    @SerializedName("slot_image")
    private String slotImage;

    @SerializedName("days")
    private String days;
    @SerializedName("categories")
    private RealmList<SlotCategory> slotCategory;
    @SerializedName("tags")
    private String tags;





    public RealmList<SlotCategory> getSlotCategory() {
        return slotCategory;
    }

    public void setSlotCategory(RealmList<SlotCategory> slotCategory) {
        this.slotCategory = slotCategory;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }


    public String getSlotImage() {
        return slotImage;
    }

    public void setSlotImage(String slotImage) {
        this.slotImage = slotImage;
    }


    public String getHoursperSession() {
        return hoursperSession;
    }

    public void setHoursperSession(String hoursperSession) {
        this.hoursperSession = hoursperSession;
    }




    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getAddress() {
        return address;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public void setAddress(String address) {
        this.address = address;
    }


    public String getEventSlot() {
        return eventSlot;
    }

    public void setEventSlot(String eventSlot) {
        this.eventSlot = eventSlot;
    }

    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTimeFrom() {
        return eventTimeFrom;
    }

    public void setEventTimeFrom(String eventTimeFrom) {
        this.eventTimeFrom = eventTimeFrom;
    }

    public String getEventTimeTo() {
        return eventTimeTo;
    }

    public void setEventTimeTo(String eventTimeTo) {
        this.eventTimeTo = eventTimeTo;
    }


    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventId() {
        return id;
    }

    public void setEventId(String eventId) {
        this.id = eventId;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }



    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }



}
