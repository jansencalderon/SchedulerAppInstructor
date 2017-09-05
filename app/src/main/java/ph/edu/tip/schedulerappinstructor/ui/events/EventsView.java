package ph.edu.tip.schedulerappinstructor.ui.events;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import ph.edu.tip.schedulerappinstructor.model.data.Event;

/**
 * Created by itsodeveloper on 04/09/2017.
 */
public interface EventsView extends MvpView {
    void setData(List<Event> event);

    void startLoad();

    void stopLoad();

    void showError(String message);

    void onEventClicked(Event event);

    void onAddEvent();

}
