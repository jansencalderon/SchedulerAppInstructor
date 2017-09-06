package ph.edu.tip.schedulerappinstructor.model.response;


import com.google.gson.annotations.SerializedName;

import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.model.data.Admin;
import ph.edu.tip.schedulerappinstructor.model.data.Event;


/**
 * Created by Cholo Mia on 12/4/2016.
 */

public class ScheduledEventResponse extends BasicResponse {

    @SerializedName(Constants.DATA)
    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
