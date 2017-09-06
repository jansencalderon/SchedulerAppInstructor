package ph.edu.tip.schedulerappinstructor.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * @author pocholomia
 * @since 12/4/2016
 */
public class SlotCategory extends RealmObject {



    @SerializedName("slot_category_id")
    @PrimaryKey
    private int slotCategoryId;
    @SerializedName("slot_name")
    private String slotName;
    @SerializedName("no_slot_allotted")
    private String slotAlloted;
    @SerializedName("no_slot_available")
    private String slotAvail;
    @SerializedName("price")
    private String slotPrice;
    @SerializedName("slot_image")
    private String slotImage;
     @SerializedName("select_seat_number")
    private String isSeatTrue;




    public SlotCategory() {
    }

    public String getIsSeatTrue() {
        return isSeatTrue;
    }

    public void setIsSeatTrue(String isSeatTrue) {
        this.isSeatTrue = isSeatTrue;
    }


    public String getSlotAvail() {
        return slotAvail;
    }

    public void setSlotAvail(String slotAvail) {
        this.slotAvail = slotAvail;
    }


    public int getSlotCategoryId() {
        return slotCategoryId;
    }

    public void setSlotCategoryId(int slotCategoryId) {
        this.slotCategoryId = slotCategoryId;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getSlotAlloted() {
        return slotAlloted;
    }

    public void setSlotAlloted(String slotAlloted) {
        this.slotAlloted = slotAlloted;
    }

    public String getSlotPrice() {
        return slotPrice;
    }

    public void setSlotPrice(String slotPrice) {
        this.slotPrice = slotPrice;
    }


    public String getSlotImage() {
        return slotImage;
    }

    public void setSlotImage(String slotImage) {
        this.slotImage = slotImage;
    }
}
