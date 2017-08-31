package ph.edu.tip.schedulerappinstructor.model.data;



import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Mark Jansen Calderon on 1/27/2017.
 */

public class Event extends RealmObject {

    @PrimaryKey
    @SerializedName("scheduled_event_id")
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
    @SerializedName("date_start")
    private String eventDateFrom;
    @SerializedName("date_end")
    private String eventDateTo;
    @SerializedName("time_start")
    private String eventTimeFrom;
    @SerializedName("time_end")
    private String eventTimeTo;
    @SerializedName("scheduled_event_image")
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
    @SerializedName("select_seat_number")
    private String isSeatTrue;
    @SerializedName("slot_image")
    private String slotImage;
    @SerializedName("company_name")
    private String companyname;
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


    public String getIsSeatTrue() {
        return isSeatTrue;
    }

    public void setIsSeatTrue(String isSeatTrue) {
        this.isSeatTrue = isSeatTrue;
    }

    public String getSlotImage() {
        return slotImage;
    }

    public void setSlotImage(String slotImage) {
        this.slotImage = slotImage;
    }


    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
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

    public String getEventDateFrom() {
        return eventDateFrom;
    }

    public void setEventDateFrom(String eventDateFrom) {
        this.eventDateFrom = eventDateFrom;
    }

    public String getEventDateTo() {
        return eventDateTo;
    }

    public void setEventDateTo(String eventDateTo) {
        this.eventDateTo = eventDateTo;
    }


    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }



}
