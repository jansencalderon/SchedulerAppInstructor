package ph.edu.tip.schedulerappinstructor.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * @author pocholomia
 * @since 12/4/2016
 */
public class Seat extends RealmObject {



    @SerializedName("id")
    @PrimaryKey
    private int seatID;

    @SerializedName("seat_number")
    private String seatNum;

    public Seat() {
    }




    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }



}
