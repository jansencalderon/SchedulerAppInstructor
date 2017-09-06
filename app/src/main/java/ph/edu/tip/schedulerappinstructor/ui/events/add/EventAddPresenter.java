package ph.edu.tip.schedulerappinstructor.ui.events.add;


import com.google.android.gms.location.places.Place;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;

import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ph.edu.tip.schedulerappinstructor.app.App;
import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.app.RealmQuery;
import ph.edu.tip.schedulerappinstructor.model.data.Admin;
import ph.edu.tip.schedulerappinstructor.model.response.ScheduledEventResponse;
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
                    Constants.APPJSON).enqueue(new Callback<ScheduledEventResponse>() {
                                                   @Override
                                                   public void onResponse(Call<ScheduledEventResponse> call, final Response<ScheduledEventResponse> response) {
                                                       getView().stopLoading();
                                                       if (response.body().isSuccess()) {
                                                           final Realm realm = Realm.getDefaultInstance();
                                                           realm.executeTransactionAsync(new Realm.Transaction() {
                                                               @Override
                                                               public void execute(Realm realm) {
                                                                   realm.copyToRealmOrUpdate(response.body().getEvent());
                                                               }
                                                           }, new Realm.Transaction.OnSuccess() {
                                                               @Override
                                                               public void onSuccess() {
                                                                   realm.close();
                                                                   getView().onSuccess(response.body().getEvent().getScheduledEventId());
                                                                   getView().showAlert(response.body().getMessage());
                                                               }
                                                           }, new Realm.Transaction.OnError() {
                                                               @Override
                                                               public void onError(Throwable error) {
                                                                   realm.close();
                                                                   error.printStackTrace();
                                                                   getView().showAlert(error.getLocalizedMessage());
                                                               }
                                                           });

                                                       } else {
                                                           getView().showAlert(response.body().getMessage());
                                                       }
                                                   }

                                                   @Override
                                                   public void onFailure(Call<ScheduledEventResponse> call, Throwable t) {
                                                       getView().stopLoading();
                                                       t.printStackTrace();
                                                       getView().showAlert(t.getLocalizedMessage());
                                                   }
                                               }
            );
        }


    }


}
