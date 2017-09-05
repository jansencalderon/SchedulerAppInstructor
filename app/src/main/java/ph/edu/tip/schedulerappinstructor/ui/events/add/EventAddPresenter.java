package ph.edu.tip.schedulerappinstructor.ui.events.add;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.places.Place;
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
import ph.edu.tip.schedulerappinstructor.model.data.Admin;
import ph.edu.tip.schedulerappinstructor.model.response.BasicResponse;
import ph.edu.tip.schedulerappinstructor.util.RequestBodyCreate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mark Jansen Calderon on 1/26/2017.
 */

public class EventAddPresenter extends MvpNullObjectBasePresenter<EventAddView> {

    private Realm realm;
    private Admin admin;
    private static final String TAG = EventAddPresenter.class.getSimpleName();

    public void onStart() {
        realm = Realm.getDefaultInstance();
        admin = RealmQuery.getUser();
    }

    public void onStop() {
        realm.close();
    }


    public void onCreateEvent(String name, String description, String type, String tags, Place place, File image) {
        if (place == null) {
            getView().showAlert("Choose Venue First");
        } else if (image == null) {
            getView().showAlert("Choose Image First");
        } else if (name.equals("") || description.equals("") || type.equals("") || tags.equals("")) {
            getView().showAlert("Please complete all details");
        } else {
            getView().startLoading();
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image);
            MultipartBody.Part body = MultipartBody.Part.createFormData(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_IMAGE
                    , image.getName(), requestFile);
            App.getInstance().getApiInterface().createEvent(
                    Constants.BEARER + admin.getApiToken(),
                    RequestBodyCreate.createPartFromString(name),
                    RequestBodyCreate.createPartFromString(description),
                    RequestBodyCreate.createPartFromString(place.getName().toString()),
                    RequestBodyCreate.createPartFromString(place.getAddress().toString()),
                    RequestBodyCreate.createPartFromString(place.getLatLng().latitude + ""),
                    RequestBodyCreate.createPartFromString(place.getLatLng().longitude + ""),
                    RequestBodyCreate.createPartFromString(type),
                    RequestBodyCreate.createPartFromString(tags),
                    body,
                    Constants.APPJSON).enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                    getView().stopLoading();
                    if (response.isSuccessful()) {
                        if (response.body().isSuccess()) {
                            getView().onSuccess();
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
