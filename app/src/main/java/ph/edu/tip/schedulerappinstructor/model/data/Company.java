package ph.edu.tip.schedulerappinstructor.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author pocholomia
 * @since 04/07/2016
 */
public class Company extends RealmObject {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("company_id")
    @PrimaryKey
    private int id;
    @SerializedName("company_name")
    private String name;
    @SerializedName("company_address")
    private String address;
    @SerializedName("company_contact")
    private String contact;
    @SerializedName("company_email")
    private String emailAdd;
    @SerializedName("company_image")
    private String logo;
    @SerializedName("company_lat")
    private double latitude;
    @SerializedName("company_lng")
    private double longitude;
    @SerializedName("company_description")
    private String description;
    @SerializedName("company_services")
    private String services;

    @SerializedName("company_opening")
    private String opening;
    @SerializedName("company_closing")
    private String closing;

    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }


    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }





}
