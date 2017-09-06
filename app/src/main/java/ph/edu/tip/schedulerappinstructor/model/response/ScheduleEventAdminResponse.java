package ph.edu.tip.schedulerappinstructor.model.response;


import com.google.gson.annotations.SerializedName;

import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.model.data.Admin;
import ph.edu.tip.schedulerappinstructor.model.data.ScheduleEventAdmin;


/**
 * Created by Cholo Mia on 12/4/2016.
 */

public class ScheduleEventAdminResponse extends BasicResponse {

    @SerializedName(Constants.DATA)
    private ScheduleEventAdmin scheduleEventAdmin;

    public ScheduleEventAdmin getScheduleEventAdmin() {
        return scheduleEventAdmin;
    }

    public void setScheduleEventAdmin(ScheduleEventAdmin scheduleEventAdmin) {
        this.scheduleEventAdmin = scheduleEventAdmin;
    }
}
