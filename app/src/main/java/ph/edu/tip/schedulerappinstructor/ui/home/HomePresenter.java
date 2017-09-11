package ph.edu.tip.schedulerappinstructor.ui.home;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.App;
import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.app.RealmQuery;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import ph.edu.tip.schedulerappinstructor.util.DateTimeUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by itsodeveloper on 04/09/2017.
 */

public class HomePresenter extends MvpBasePresenter<HomeView> {

    private Realm realm;
    private RealmResults<Event> eventRealmResults;

    void onStart() {
        realm = Realm.getDefaultInstance();

        eventRealmResults = realm.where(Event.class).findAll();
        eventRealmResults.addChangeListener(new RealmChangeListener<RealmResults<Event>>() {
            @Override
            public void onChange(RealmResults<Event> events) {
                setList();
            }
        });
    }

    void onStop() {
        realm.close();
        eventRealmResults.removeAllChangeListeners();
    }

    void load() {
        if (isViewAttached()) {
            getView().startLoading();
        }
        App.getInstance().getApiInterface().getEventList(Constants.BEARER + RealmQuery.getUser().getApiToken(), Constants.APPJSON)
                .enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(Call<List<Event>> call, final Response<List<Event>> response) {
                        if (isViewAttached()) {
                            getView().stopLoading();
                        }
                        if (response.isSuccessful()) {
                            final Realm realm = Realm.getDefaultInstance();
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.delete(Event.class);
                                    realm.copyToRealmOrUpdate(response.body());

                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    setList();
                                    realm.close();
                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {
                                    realm.close();
                                    error.printStackTrace();
                                    if (isViewAttached())
                                        getView().showAlert(error.getLocalizedMessage());
                                }
                            });
                        } else {
                            if (isViewAttached())
                                getView().showAlert(String.valueOf(R.string.oops));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Event>> call, Throwable t) {
                        t.printStackTrace();
                        if (isViewAttached()) {
                            getView().stopLoading();
                            getView().showAlert(String.valueOf(R.string.oops));
                        }
                    }
                });
    }


    private void setList() {
        if (isViewAttached()) {
            if (eventRealmResults.isLoaded() && eventRealmResults.isValid()) {
                getView().setDataToday(realm.copyFromRealm(eventRealmResults.where()
                        .equalTo("dateStart", DateTimeUtils.getDateToday())
                        .findAll()
                        .sort(Constants.Realm.EVENT_DATE_START, Sort.ASCENDING)));
                getView().setDataUpcoming(realm.copyFromRealm(eventRealmResults.where()
                        .greaterThan(Constants.Realm.EVENT_DATE_START, DateTimeUtils.getDateToday())
                        .findAll()
                        .sort(Constants.Realm.EVENT_DATE_START, Sort.ASCENDING)));
            }
        }
    }


    public void refreshLocalData() {
        setList();
    }

}
