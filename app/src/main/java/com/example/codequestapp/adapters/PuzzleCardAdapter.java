package com.example.codequestapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.codequestapp.R;

import com.example.codequestapp.models.Puzzle;
import com.example.codequestapp.models.PuzzleOption;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;

public class PuzzleCardAdapter extends RecyclerView.Adapter<PuzzleCardAdapter.ViewHolder> {

    private Puzzle[] localDataSet;
    private Context context;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView prompt;
        public final ChipGroup options;


        public ViewHolder(View view) {
            super(view);
            prompt  = view.findViewById(R.id.puzzlePrompt);
            options = view.findViewById(R.id.puzzlesChipOptions);
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public PuzzleCardAdapter(Puzzle[] dataSet, Context context) {
        this.localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.puzzle_card_item, viewGroup, false);

//        questionAnswer.setListener to change the thing

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.prompt.setText(localDataSet[position].getPrompt());
        for(PuzzleOption option: localDataSet[position].getOptions()) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Chip chip = (Chip) li.inflate(R.layout.choice_chip_layout, viewHolder.options, false);

            chip.setText(option.getOption());

            viewHolder.options.addView(chip);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}