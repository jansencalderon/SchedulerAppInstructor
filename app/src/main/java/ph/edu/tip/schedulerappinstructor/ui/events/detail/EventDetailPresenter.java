package ph.edu.tip.schedulerappinstructor.ui.events.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;

import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.App;
import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.app.RealmQuery;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import ph.edu.tip.schedulerappinstructor.model.response.BasicResponse;
import ph.edu.tip.schedulerappinstructor.util.RequestBodyCreate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailPresenter extends MvpNullObjectBasePresenter<EventDetailView>{
    private Realm realm;

    public void onStart(){
        realm = Realm.getDefaultInstance();
    }

    public void onStop(){
        realm.close();
    }

    public Event event(int id){
        return realm.where(Event.class).equalTo(Constants.Realm.EVENT_ID, id).findFirst();
    }

    public void onAddSlotCategory(int eventId, String slotName, String numberOfSlot, String price, boolean selectSeatNumber, File image) {
        String select = "off";
        if(selectSeatNumber){
            select="on";
        }
        if (image == null) {
            getView().showAlert("Choose Image First");
        } else if (slotName.equals("") || numberOfSlot.equals("") || price.equals("")) {
            getView().showAlert("Please complete all details");
        } else {
            getView().startLoading();
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image);
            MultipartBody.Part body = MultipartBody.Part.createFormData(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_IMAGE
                    , image.getName(), requestFile);
            App.getInstance().getApiInterface().createEventSlotCategory(
                    Constants.BEARER + RealmQuery.getUser().getApiToken(),
                    RequestBodyCreate.createPartFromString(eventId+""),
                    RequestBodyCreate.createPartFromString(slotName),
                    RequestBodyCreate.createPartFromString(numberOfSlot),
                    RequestBodyCreate.createPartFromString(price),
                    RequestBodyCreate.createPartFromString(select),
                    body,
                    Constants.APPJSON).enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                    getView().stopLoading();
                    if (response.isSuccessful()) {
                        if (response.body().isSuccess()) {
                            getView().onSuccess("SlotType");
                            getView().showAlert(response.body().getMessage());
                        } else {
                            getView().showAlert(response.body().getMessage());
                        }
                    } else {
                        getView().showAlert("Server Error");
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    t.printStackTrace();
                    getView().stopLoading();
                    getView().showAlert(t.getLocalizedMessage());
                }
            });
        }

    }
}
