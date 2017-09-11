package ph.edu.tip.schedulerappinstructor.ui.home;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.Endpoints;
import ph.edu.tip.schedulerappinstructor.app.GlideApp;
import ph.edu.tip.schedulerappinstructor.databinding.ItemEventsTodayBinding;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import ph.edu.tip.schedulerappinstructor.model.data.ScheduleEventAdmin;
import ph.edu.tip.schedulerappinstructor.ui.events.detail.EventDetailView;

/**
 * @author pocholomia
 * @since 05/10/2016
 */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {

    private final HomeView view;
    private List<Event> list;

    public HomeListAdapter(HomeView view) {
        this.view = view;
        list = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setList(List<Event> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemEventsTodayBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_events_today,
                parent,
                false);
        return new HomeListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event item = list.get(position);
        holder.binding.setEvent(item);
        holder.binding.setView(view);
        GlideApp.with(holder.itemView.getContext())
                .load(Endpoints.EVENT_URL_IMAGE+item.getScheduledEventImage())
                .into(holder.binding.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemEventsTodayBinding binding;

        public ViewHolder(ItemEventsTodayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
