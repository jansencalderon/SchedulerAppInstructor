package ph.edu.tip.schedulerappinstructor.ui.events.detail;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import ph.edu.tip.schedulerappinstructor.model.data.Calendar;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import ph.edu.tip.schedulerappinstructor.model.data.SlotCategory;

/**
 * Created by itsodeveloper on 04/09/2017.
 */
public interface EventDetailView extends MvpView {


    void showError(String message);


    void onAddSlotType();

    void showAlert(String s);

    void stopLoading();

    void startLoading();

    void onSuccess(String type);

    //slot crud
    void onSlotCategoryDelete(SlotCategory slot);

    void onSlotCategoryEdit(SlotCategory slot);

    //calendar crud
    void onAddSchedule();

    //calendar crud
    //slot crud
    void onCalendarDelete(Calendar cal);

    void onCalendarEdit(Calendar cal);
}
