package ph.edu.tip.schedulerappinstructor.model.response;

import com.google.gson.annotations.SerializedName;

import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.model.data.Promo;


/**
 * @author pocholomia
 * @since 12/4/2016
 */

public class PromoResponse {

    @SerializedName(Constants.DATA)
    private Promo promo;

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }
}
