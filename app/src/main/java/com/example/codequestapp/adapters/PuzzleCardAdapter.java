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

import com.example.codequestapp.models.Question;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class PuzzleCardAdapter extends RecyclerView.Adapter<PuzzleCardAdapter.ViewHolder> {

    private Puzzle[] localDataSet;
    private Context context;
    private RecyclerView cards;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView prompt;
        public final ChipGroup options;

        public ViewHolder(View view) {
            super(view);
            prompt = view.findViewById(R.id.puzzlePrompt);
            options = view.findViewById(R.id.puzzlesChipOptions);
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public PuzzleCardAdapter(Puzzle[] dataSet, Context context, RecyclerView cards) {
        this.localDataSet = dataSet;
        this.context = context;
        this.cards = cards;
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
        for (PuzzleOption option : localDataSet[position].getOptions()) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Chip chip = (Chip) li.inflate(R.layout.choice_chip_layout, viewHolder.options, false);

            chip.setText(option.getOption());

            viewHolder.options.addView(chip);
        }

    }

    public ArrayList<Puzzle> generatePuzzleAnswers() {

        ArrayList<Puzzle> puzzles = new ArrayList<Puzzle>();

        for (int childCount = cards.getChildCount(), i = 0; i < childCount; ++i) {
            final PuzzleCardAdapter.ViewHolder holder = (PuzzleCardAdapter.ViewHolder) cards.getChildViewHolder(cards.getChildAt(i));
            Puzzle puzzle = localDataSet[i];
            ArrayList<PuzzleOption> options = new ArrayList<PuzzleOption>();

            for( int optionsCount = holder.options.getChildCount(),j = 0 ; j < optionsCount ; j++) {
                Chip chip = (Chip) (holder.options.getChildAt(j));
                boolean isSelected = chip.isChecked();

                String option = chip.getText().toString();
                options.add(new PuzzleOption(option, isSelected));
            }
            puzzle.setOptions(options);

            puzzles.add(puzzle);
        }
        return puzzles;

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}