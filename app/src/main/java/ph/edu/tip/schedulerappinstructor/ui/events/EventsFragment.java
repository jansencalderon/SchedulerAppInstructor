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

package ph.edu.tip.schedulerappinstructor.ui.events;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.List;

import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.databinding.FragmentEventsBinding;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import ph.edu.tip.schedulerappinstructor.ui.events.add.EventAddActivity;
import ph.edu.tip.schedulerappinstructor.ui.events.detail.EventDetailActivity;

/**
 * Created by Iiro Krankka (http://github.com/roughike)
 */

public class EventsFragment extends MvpViewStateFragment<EventsView, EventsPresenter> implements EventsView{

    private FragmentEventsBinding binding;
    private EventsListAdapter adapter;
    private String TAG = EventsFragment.class.getSimpleName();


    public EventsFragment() {
    }


    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false);
        binding.setView(getMvpView());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new EventsListAdapter(getMvpView());
        binding.recyclerView.setAdapter(adapter);

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
        presenter.load();
    }


    @NonNull
    @Override
    public EventsPresenter createPresenter() {
        return new EventsPresenter();
    }

    @Override
    public void setData(List<Event> events) {
        adapter.setData(events);
    }

    @Override
    public void startLoad() {
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopLoad() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventClicked(Event event) {
        Log.d(TAG, event.getName());
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        intent.putExtra(Constants.ID, event.getScheduledEventId());
        startActivity(intent);
    }

    @Override
    public void onAddEvent() {
        startActivity(new Intent(getActivity(), EventAddActivity.class));
    }


    @NonNull
    @Override
    public ViewState<EventsView> createViewState() {
        setRetainInstance(true);
        return new EventsViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }

}
