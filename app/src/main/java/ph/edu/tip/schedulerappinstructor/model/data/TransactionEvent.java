package ph.edu.tip.schedulerappinstructor.model.data;



import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Mark Jansen Calderon on 1/27/2017.
 */

public class TransactionEvent extends RealmObject {

    @PrimaryKey
    @SerializedName("reservation_id")
    private String id;
    @SerializedName("name")
    private String eventName;
    @SerializedName("description")
    private String eventDescription;
    @SerializedName("price")
    private String eventPrice;
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
    @SerializedName("calendar_desc")
    private String calendar;
    @SerializedName("company_name")
    private String companyname;
    @SerializedName("payment_status")
    private String payment_status;
    @SerializedName("reservation_status")
    private String reservation_status;
    @SerializedName("seat_number")
    private String event_slot;
    @SerializedName("date_start")
    private String eventDateFrom;
    @SerializedName("tags")
    private String tags;
    @SerializedName("created_at")
    private String created;



    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }

    public String getAddress() {
        return address;
    }

    public String getEvent_slot() {
        return event_slot;
    }

    public void setEvent_slot(String event_slot) {
        this.event_slot = event_slot;
    }

    public String getEventDateFrom() {
        return eventDateFrom;
    }

    public void setEventDateFrom(String eventDateFrom) {
        this.eventDateFrom = eventDateFrom;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getReservation_status() {
        return reservation_status;
    }

    public void setReservation_status(String reservation_status) {
        this.reservation_status = reservation_status;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public void setAddress(String address) {
        this.address = address;
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
