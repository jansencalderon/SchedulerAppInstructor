package ph.edu.tip.schedulerappinstructor.model.response;


import com.google.gson.annotations.SerializedName;

import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import ph.edu.tip.schedulerappinstructor.model.data.Schedule;


/**
 * Created by Cholo Mia on 12/4/2016.
 */

public class ScheduleResponse extends BasicResponse {

    @SerializedName(Constants.DATA)
    private Schedule sched;

    public Schedule getSched() {
        return sched;
    }

    public void setSched(Schedule sched) {
        this.sched = sched;
    }
}
