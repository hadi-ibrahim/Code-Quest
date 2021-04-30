package com.example.codequestapp.ui.quests;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.codequestapp.R;
import com.example.codequestapp.models.Quest;

public class QuestCardAdapter extends RecyclerView.Adapter<QuestCardAdapter.ViewHolder> {

    private Quest[] localDataSet;
    private Context context;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView categoryBanner;
        public final ImageView trophyImage;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.questTitle);
            categoryBanner =view.findViewById(R.id.categoryBanner);
            trophyImage = view.findViewById(R.id.trophyImage);
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public QuestCardAdapter(Quest[] dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.quest_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.title.setText(localDataSet[position].getTitle());
        int color = Color.parseColor(localDataSet[position].getCategory().getColor());
        viewHolder.categoryBanner.setBackgroundColor(color);
        switch(localDataSet[position].getTrophy()) {
            case "bronze":
                viewHolder.trophyImage.setImageDrawable(context.getDrawable(R.drawable.bronze));
                break;
            case "silver":
                viewHolder.trophyImage.setImageDrawable(context.getDrawable(R.drawable.silver));
                break;
            case "gold":
                viewHolder.trophyImage.setImageDrawable(context.getDrawable(R.drawable.gold));
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}