package com.example.sierrebluesappv1.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.database.entity.StageEntity;
import com.example.sierrebluesappv1.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

/**
 * Adapter class for recyclerviews. Uses act_item.xml to arrange items
 * in viewholders.
 * @param <T>
 */
public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<T> mData;
    private RecyclerViewItemClickListener mListener;
    private static int pos; //get the position of the click (used for long clics)

    /**
     * Get the position of click
     * @return click position
     */
    public static int getPos() {
        return pos;
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        //listens for changes
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.act_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        //set the simple click listener
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = mData.get(position);
        //binds text from db to recycler holder according to class
        if (item.getClass().equals(ActEntity.class)) {
            //View for acts
            holder.textViewName.setText(((ActEntity) item).getArtistName()
                    + " - " + ((ActEntity) item).getArtistCountry());
            holder.textViewDateAddress.setText(((ActEntity) item).getDate()
                    + " - " + ((ActEntity) item).getStartTime());
            String places = String.valueOf(((ActEntity) item).getIdStage());
            holder.textViewScenePlaces.setText(places);
        }


        if (item.getClass().equals(StageEntity.class)) {
            //View for stages
            holder.textViewName.setText(((StageEntity) item).getName());
            holder.textViewDateAddress.setText(((StageEntity) item).getAddress());
            String places;
            places = String.valueOf(((StageEntity) item).getMaxCapacity());
            if (((StageEntity) item).isSeatingPlaces()) {
                places += " seating";
            }
            places += " places";
            holder.textViewScenePlaces.setText(places);
        }

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            //Check if there are differences with database.
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof ActEntity) {
                        return ((ActEntity) mData.get(oldItemPosition)).getIdAct().equals(((ActEntity) data.get(newItemPosition)).getIdAct());
                    }
                    if (mData instanceof StageEntity) {
                        return Objects.equals(((StageEntity) mData.get(oldItemPosition)).getName(), ((StageEntity) data.get(newItemPosition)).getName());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    //checks if Act entity is the same
                    if (mData instanceof ActEntity) {
                        ActEntity newAct = (ActEntity) data.get(newItemPosition);
                        ActEntity oldAct = (ActEntity) mData.get(newItemPosition);
                        return newAct.getIdAct().equals(oldAct.getIdAct())
                                && Objects.equals(newAct.getArtistName(), oldAct.getArtistName())
                                && Objects.equals(newAct.getArtistCountry(), oldAct.getArtistCountry())
                                && Objects.equals(newAct.getDate(), oldAct.getDate())
                                && Objects.equals(newAct.getStartTime(), oldAct.getStartTime())
                                && Objects.equals(newAct.getIdStage(), oldAct.getIdStage())
                                ;
                    }
                    //checks if Stage entity is the same
                    if (mData instanceof StageEntity) {
                        StageEntity newStage = (StageEntity) data.get(newItemPosition);
                        StageEntity oldStage = (StageEntity) mData.get(newItemPosition);
                        return Objects.equals(newStage.getName(), oldStage.getName())
                                && Objects.equals(newStage.getName(), oldStage.getName())
                                && Objects.equals(newStage.getAddress(), oldStage.getAddress())
                                && Objects.equals(newStage.getMaxCapacity(), oldStage.getMaxCapacity())
                                && Objects.equals(newStage.isSeatingPlaces(), oldStage.isSeatingPlaces())
                                ;
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }

    /**
     * View Holder class that places elements in card view.
     * Also sets the view ready fo context menu
     */
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView textViewName;
        private TextView textViewDateAddress;
        private TextView textViewScenePlaces;
        private CardView cardView;
        private int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_title_name);
            textViewDateAddress = itemView.findViewById(R.id.text_view_second_line);
            textViewScenePlaces = itemView.findViewById(R.id.text_view_stage_name_item);
            cardView = itemView.findViewById(R.id.recycler_card_view);
            cardView.setOnCreateContextMenuListener(this);


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //User can edit or delete an entity
            MenuItem edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem delete = menu.add(Menu.NONE, 2,2, "Delete");
            pos = getAdapterPosition();
        }

    }

}
