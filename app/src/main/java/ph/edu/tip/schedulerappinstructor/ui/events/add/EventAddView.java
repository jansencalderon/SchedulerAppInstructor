package ph.edu.tip.schedulerappinstructor.ui.events.add;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Mark Jansen Calderon on 1/26/2017.
 */

public interface EventAddView extends MvpView{

    void showAlert(String message);
    void onPhotoClicked();

    void startLoading();

    void stopLoading();

    void onPickLocation();
    void onRemoveLocation();

    void onSuccess();
}
