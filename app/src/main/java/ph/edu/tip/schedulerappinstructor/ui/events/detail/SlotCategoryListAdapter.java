package ph.edu.tip.schedulerappinstructor.ui.events.detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.Endpoints;
import ph.edu.tip.schedulerappinstructor.databinding.ItemSlotCategoryBinding;
import ph.edu.tip.schedulerappinstructor.model.data.SlotCategory;

/**
 * @author pocholomia
 * @since 05/10/2016
 */
public class SlotCategoryListAdapter extends RecyclerView.Adapter<SlotCategoryListAdapter.ViewHolder> {

    private final EventDetailView view;
    private List<SlotCategory> list;

    public SlotCategoryListAdapter(EventDetailView view) {
        this.view = view;
        list = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setSlotCategoryList(List<SlotCategory> list) {
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSlotCategoryBinding itemSlotCategoryBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_slot_category,
                parent,
                false);
        return new SlotCategoryListAdapter.ViewHolder(itemSlotCategoryBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SlotCategory slot = list.get(position);
        holder.itemSlotCategoryBinding.setSlot(slot);
        holder.itemSlotCategoryBinding.setView(view);
        Glide.with(holder.itemView.getContext()).load(Endpoints.EVENT_URL_IMAGE+slot.getSlotImage());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSlotCategoryBinding itemSlotCategoryBinding;

        public ViewHolder(ItemSlotCategoryBinding itemSlotCategoryBinding) {
            super(itemSlotCategoryBinding.getRoot());
            this.itemSlotCategoryBinding = itemSlotCategoryBinding;
        }
    }
}
