package ph.edu.tip.schedulerappinstructor.ui.events.detail;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.App;
import ph.edu.tip.schedulerappinstructor.app.Endpoints;
import ph.edu.tip.schedulerappinstructor.app.GlideApp;
import ph.edu.tip.schedulerappinstructor.databinding.ItemInstructorBinding;
import ph.edu.tip.schedulerappinstructor.databinding.ItemInstructorBinding;
import ph.edu.tip.schedulerappinstructor.model.data.ScheduleEventAdmin;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author pocholomia
 * @since 05/10/2016
 */
public class InstructorListAdapter extends RecyclerView.Adapter<InstructorListAdapter.ViewHolder> {

    private final EventDetailView view;
    private List<ScheduleEventAdmin> list;

    public InstructorListAdapter(EventDetailView view) {
        this.view = view;
        list = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setList(List<ScheduleEventAdmin> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemInstructorBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_instructor,
                parent,
                false);
        return new InstructorListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScheduleEventAdmin item = list.get(position);
        holder.binding.setInstructor(item);
        holder.binding.setView(view);

        GlideApp.with(holder.itemView.getContext())
                .load(Endpoints.STATIC_IMAGE_URL+item.getImage())
                .centerCrop()
                .into(holder.binding.imageView);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemInstructorBinding binding;

        public ViewHolder(ItemInstructorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
