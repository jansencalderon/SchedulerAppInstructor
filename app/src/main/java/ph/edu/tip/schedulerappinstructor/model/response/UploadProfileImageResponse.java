package ph.edu.tip.schedulerappinstructor.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author pocholomia
 * @since 22/09/2016
 */

public class UploadProfileImageResponse extends BasicResponse {

    @SerializedName("data")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
