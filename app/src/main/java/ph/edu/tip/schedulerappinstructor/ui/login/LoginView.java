package ph.edu.tip.schedulerappinstructor.ui.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by pocholomia on 31/08/2016.
 * Interface for Login
 */
public interface LoginView extends MvpView {

    void showAlert(String message);

    void dismissLoad();

    void showLoad();

    void startNewActivity();

    void onLogin();

    void onForgotPassword();

    void showToast(String message);
}
