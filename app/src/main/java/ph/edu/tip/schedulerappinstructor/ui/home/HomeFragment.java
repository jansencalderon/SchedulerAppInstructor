/*
 * BottomBar library for Android
 * Copyright (c) 2016 Iiro Krankka (http://github.com/roughike).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ph.edu.tip.schedulerappinstructor.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.List;

import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.databinding.FragmentHomeBinding;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import ph.edu.tip.schedulerappinstructor.ui.events.detail.EventDetailActivity;

/**
 * Created by Iiro Krankka (http://github.com/roughike)
 */
public class HomeFragment extends MvpFragment<HomeView,HomePresenter> implements HomeView {

    private FragmentHomeBinding binding;
    private String TAG = HomeFragment.class.getSimpleName();

    public HomeFragment() {
    }


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.load();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onStart();
        presenter.load();
    }

    @NonNull
    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void setDataToday(List<Event> events) {
       // binding.schedEventCount.setText(events.size()+"");
        if(events.size()<=0){
            binding.eventsTodayLabel.setText("You have no events today");
            return;
        }
        HomeListAdapter homeListAdapter = new HomeListAdapter(getMvpView());
        binding.recyclerViewToday.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerViewToday.setAdapter(homeListAdapter);
        homeListAdapter.setList(events);
        binding.eventsTodayCount.setText(events.size()+"");
    }

    @Override
    public void setDataUpcoming(List<Event> events) {
        // binding.schedEventCount.setText(events.size()+"");
        showAlert(events.size()+"");
        if(events.size()<=0){
            binding.eventsUpcomingLabel.setText("You have no events today");
            return;
        }
        HomeListAdapter homeListAdapter = new HomeListAdapter(getMvpView());
        binding.recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerViewUpcoming.setAdapter(homeListAdapter);
        homeListAdapter.setList(events);
        binding.eventsUpcomingCount.setText(events.size()+"");
    }

    @Override
    public void stopLoading() {
        binding.swipeRefreshLayout.setRefreshing(false);

    }


    @Override
    public void onEventClicked(Event event) {
        Log.d(TAG, event.getName());
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        intent.putExtra(Constants.ID, event.getScheduledEventId());
        startActivity(intent);
    }


    @Override
    public void showAlert(String s) {

    }

    @Override
    public void startLoading() {
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onStop();
    }
}
