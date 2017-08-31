package ph.edu.tip.schedulerappinstructor.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.roughike.bottombar.OnTabSelectListener;

import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.databinding.ActivityLoginBinding;
import ph.edu.tip.schedulerappinstructor.databinding.ActivityMainBinding;
import ph.edu.tip.schedulerappinstructor.ui.events.EventsFragment;
import ph.edu.tip.schedulerappinstructor.ui.home.HomeFragment;
import ph.edu.tip.schedulerappinstructor.ui.profile.ProfileFragment;

public class MainActivity extends MvpActivity<MainView,MainPresenter> implements MainView {

    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment selectedFragment = null;
                switch (tabId) {
                    case R.id.tab_home:
                        selectedFragment = HomeFragment.newInstance();
                        break;
                    case R.id.tab_events:
                        selectedFragment = EventsFragment.newInstance();
                        break;
                    case R.id.tab_profile:
                        selectedFragment = ProfileFragment.newInstance();
                        break;
                    default:
                        selectedFragment = HomeFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(binding.frameLayout.getId(), selectedFragment);
                transaction.commit();
            }
        });

    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

}
