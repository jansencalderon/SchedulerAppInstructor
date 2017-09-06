package ph.edu.tip.schedulerappinstructor.model.response;


import com.google.gson.annotations.SerializedName;

import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.model.data.Schedule;
import ph.edu.tip.schedulerappinstructor.model.data.SlotCategory;


/**
 * Created by Cholo Mia on 12/4/2016.
 */

public class SlotCategoryResponse extends BasicResponse {

    @SerializedName(Constants.DATA)
    private SlotCategory slotCategory;

    public SlotCategory getSlotCategory() {
        return slotCategory;
    }

    public void setSlotCategory(SlotCategory slotCategory) {
        this.slotCategory = slotCategory;
    }
}
