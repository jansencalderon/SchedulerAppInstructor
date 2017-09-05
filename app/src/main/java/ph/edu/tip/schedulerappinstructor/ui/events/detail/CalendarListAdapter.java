package ph.edu.tip.schedulerappinstructor.ui.events.detail;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.Endpoints;
import ph.edu.tip.schedulerappinstructor.databinding.ItemCalendarBinding;
import ph.edu.tip.schedulerappinstructor.model.data.Calendar;

/**
 * @author pocholomia
 * @since 05/10/2016
 */
public class CalendarListAdapter extends RecyclerView.Adapter<CalendarListAdapter.ViewHolder> {

    private final EventDetailView view;
    private List<Calendar> list;

    public CalendarListAdapter(EventDetailView view) {
        this.view = view;
        list = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setCalendarList(List<Calendar> list) {
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCalendarBinding itemCalendarBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_calendar,
                parent,
                false);
        return new CalendarListAdapter.ViewHolder(itemCalendarBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Calendar item = list.get(position);
        holder.itemCalendarBinding.setCalendar(item);
        holder.itemCalendarBinding.setView(view);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCalendarBinding itemCalendarBinding;

        public ViewHolder(ItemCalendarBinding itemCalendarBinding) {
            super(itemCalendarBinding.getRoot());
            this.itemCalendarBinding = itemCalendarBinding;
        }
    }
}
