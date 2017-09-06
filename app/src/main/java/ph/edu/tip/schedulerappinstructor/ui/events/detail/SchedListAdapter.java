package ph.edu.tip.schedulerappinstructor.ui.events.detail;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.databinding.ItemSchedBinding;
import ph.edu.tip.schedulerappinstructor.model.data.Schedule;

/**
 * @author pocholomia
 * @since 05/10/2016
 */
public class SchedListAdapter extends RecyclerView.Adapter<SchedListAdapter.ViewHolder> {

    private final EventDetailView view;
    private List<Schedule> list;

    public SchedListAdapter(EventDetailView view) {
        this.view = view;
        list = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setList(List<Schedule> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSchedBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_sched,
                parent,
                false);
        return new SchedListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Schedule item = list.get(position);
        holder.binding.setSched(item);
        holder.binding.setView(view);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSchedBinding binding;

        public ViewHolder(ItemSchedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
