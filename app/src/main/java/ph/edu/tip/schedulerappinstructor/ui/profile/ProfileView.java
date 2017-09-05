package ph.edu.tip.schedulerappinstructor.ui.profile;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by itsodeveloper on 04/09/2017.
 */

public interface ProfileView extends MvpView {
    void onLogOut();

    void showAlert(String s);
}
