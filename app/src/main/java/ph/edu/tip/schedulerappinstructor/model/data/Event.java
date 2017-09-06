package ph.edu.tip.schedulerappinstructor.model.data;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Mark Jansen Calderon on 1/27/2017.
 */

public class Event extends RealmObject {


    @SerializedName("scheduled_event_id")
    @Expose
    @PrimaryKey
    private Integer scheduledEventId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("admin_id")
    @Expose
    private Integer adminId;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("scheduled_event_image")
    @Expose
    private String scheduledEventImage;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("calendar")
    @Expose
    private RealmList<Schedule> calendar = null;
    @SerializedName("calendar_desc")
    @Expose
    private String calendarDesc;
    @SerializedName("date_start")
    @Expose
    private String dateStart;
    @SerializedName("categories")
    @Expose
    private RealmList<SlotCategory> categories = null;

    @SerializedName("scheduled_event_admin")
    @Expose
    private RealmList<ScheduleEventAdmin> admins = null;



    public Integer getScheduledEventId() {
        return scheduledEventId;
    }

    public void setScheduledEventId(Integer scheduledEventId) {
        this.scheduledEventId = scheduledEventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getScheduledEventImage() {
        return scheduledEventImage;
    }

    public void setScheduledEventImage(String scheduledEventImage) {
        this.scheduledEventImage = scheduledEventImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public RealmList<Schedule> getCalendar() {
        return calendar;
    }

    public void setCalendar(RealmList<Schedule> calendar) {
        this.calendar = calendar;
    }

    public String getCalendarDesc() {
        return calendarDesc;
    }

    public void setCalendarDesc(String calendarDesc) {
        this.calendarDesc = calendarDesc;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public RealmList<SlotCategory> getCategories() {
        return categories;
    }

    public void setCategories(RealmList<SlotCategory> categories) {
        this.categories = categories;
    }

    public RealmList<ScheduleEventAdmin> getAdmins() {
        return admins;
    }

    public void setAdmins(RealmList<ScheduleEventAdmin> admins) {
        this.admins = admins;
    }
}
