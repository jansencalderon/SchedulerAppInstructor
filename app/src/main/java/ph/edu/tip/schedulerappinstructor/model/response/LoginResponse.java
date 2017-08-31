package ph.edu.tip.schedulerappinstructor.model.response;


import com.google.gson.annotations.SerializedName;

import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.model.data.User;


/**
 * Created by Cholo Mia on 12/4/2016.
 */

public class LoginResponse extends BasicResponse {

    @SerializedName(Constants.DATA)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
