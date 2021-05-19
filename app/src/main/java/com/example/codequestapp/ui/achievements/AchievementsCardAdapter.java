package com.example.codequestapp.ui.achievements;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.codequestapp.R;
import com.example.codequestapp.models.CategoryProgress;
import com.example.codequestapp.models.Quest;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.jetbrains.annotations.NotNull;

public class AchievementsCardAdapter extends RecyclerView.Adapter<AchievementsCardAdapter.ViewHolder> {

    private CategoryProgress[] localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView categoryTitle;
        public final LinearProgressIndicator progressBar;

        public ViewHolder(View view) {
            super(view);

            categoryTitle = view.findViewById(R.id.categoryTitle);
            progressBar = view.findViewById(R.id.progressBar);
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public AchievementsCardAdapter(CategoryProgress[] dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.progress_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.categoryTitle.setText(localDataSet[position].getTitle());
        int progress = Integer.parseInt(localDataSet[position].getProgress());
        int max = Integer.parseInt(localDataSet[position].getTotal());

        viewHolder.progressBar.setMax(max);
        viewHolder.progressBar.setProgress(progress);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}