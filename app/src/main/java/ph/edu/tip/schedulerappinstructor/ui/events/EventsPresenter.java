package ph.edu.tip.schedulerappinstructor.ui.events;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.App;
import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.app.RealmQuery;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by itsodeveloper on 04/09/2017.
 */

public class EventsPresenter extends MvpBasePresenter<EventsView> {
    private Realm realm;
    private RealmResults<Event> eventRealmResults;

    void onStart(){
        realm = Realm.getDefaultInstance();

        eventRealmResults = realm.where(Event.class).findAll();
        eventRealmResults.addChangeListener(new RealmChangeListener<RealmResults<Event>>() {
            @Override
            public void onChange(RealmResults<Event> events) {
                setList();
            }
        });
    }

    void onStop(){
        realm.close();
        eventRealmResults.removeAllChangeListeners();
    }
    void load() {
        if (isViewAttached()) {
            getView().startLoad();
        }
        App.getInstance().getApiInterface().getEventList(Constants.BEARER + RealmQuery.getUser().getApiToken(), Constants.APPJSON)
                .enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(Call<List<Event>> call, final Response<List<Event>> response) {
                        if (isViewAttached()) {
                            getView().stopLoad();
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
                                        getView().showError(error.getLocalizedMessage());
                                }
                            });
                        } else {
                            if (isViewAttached())
                                getView().showError(String.valueOf(R.string.oops));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Event>> call, Throwable t) {
                        t.printStackTrace();
                        if (isViewAttached()) {
                            getView().stopLoad();
                            getView().showError(String.valueOf(R.string.oops));
                        }
                    }
                });
    }


    private void setList() {
        if(isViewAttached()){
            if(eventRealmResults.isValid()){
                getView().setData(realm.copyFromRealm(eventRealmResults));
            }
        }
    }


    public void refreshLocalData() {
        setList();
    }
}
