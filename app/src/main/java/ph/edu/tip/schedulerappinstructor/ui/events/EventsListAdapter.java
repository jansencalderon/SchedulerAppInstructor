package ph.edu.tip.schedulerappinstructor.ui.events;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.AndroidRuntimeException;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.Endpoints;
import ph.edu.tip.schedulerappinstructor.databinding.ItemEventBinding;
import ph.edu.tip.schedulerappinstructor.model.data.Event;


/**
 * @author pocholomia
 * @since 05/10/2016
 */
public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {
    private List<Event> list;
    private static final int VIEW_TYPE_DEFAULT = 0;
    private EventsView view;
    private ItemEventBinding binding;

    public EventsListAdapter(EventsView view) {
        this.view = view;
        list = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemEventBinding itemEventBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_event,
                parent,
                false);
        return new ViewHolder(itemEventBinding);
    }

    @Override
    public void onBindViewHolder(EventsListAdapter.ViewHolder holder, int position) {
        final Event event = list.get(position);
        holder.binding.setEvent(event);
        holder.binding.setView(view);
        try {
            Glide.with(holder.itemView.getContext())
                    .load(Endpoints.EVENT_URL_IMAGE + event.getScheduledEventImage())
                    .into(holder.binding.eventImage);
        }catch (AndroidRuntimeException e){
            e.printStackTrace();
        }


    }


    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Event> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemEventBinding binding;

        public ViewHolder(ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
