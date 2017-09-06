package ph.edu.tip.schedulerappinstructor.ui.events.detail;

import com.hannesdorfmann.mosby.mvp.MvpView;

import ph.edu.tip.schedulerappinstructor.model.data.Admin;
import ph.edu.tip.schedulerappinstructor.model.data.Schedule;
import ph.edu.tip.schedulerappinstructor.model.data.ScheduleEventAdmin;
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

    void onSchedTimeStart();

    void onSchedTimeEnd();

    void onSchedDelete(Schedule sched);

    void onSchedEdit(Schedule sched);

    void onAddInstructor();

    void onEditInstructor(ScheduleEventAdmin eventAdmin);

    void onDeleteInstructor(ScheduleEventAdmin eventAdmin);

    void onChooseInstructor(ScheduleEventAdmin eventAdmin);
}
