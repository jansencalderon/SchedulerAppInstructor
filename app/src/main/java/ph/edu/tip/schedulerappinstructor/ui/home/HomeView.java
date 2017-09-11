package ph.edu.tip.schedulerappinstructor.ui.home;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import ph.edu.tip.schedulerappinstructor.model.data.Event;

/**
 * Created by itsodeveloper on 04/09/2017.
 */

public interface HomeView extends MvpView {

    void setDataToday(List<Event> events);

    void setDataUpcoming(List<Event> events);

    void stopLoading();

    void showAlert(String s);

    void startLoading();

    void onEventClicked(Event event);
}
