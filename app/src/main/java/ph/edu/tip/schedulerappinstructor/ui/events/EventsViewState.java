package ph.edu.tip.schedulerappinstructor.ui.events;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;

/**
 * @author pocholomia
 * @since 05/10/2016
 */

class EventsViewState implements RestorableViewState<EventsView> {
    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<EventsView> restoreInstanceState(Bundle in) {
        return this;
    }

    @Override
    public void apply(EventsView view, boolean retained) {

    }
}
