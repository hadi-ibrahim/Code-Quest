package com.example.codequestapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.codequestapp.R;
import com.example.codequestapp.SolveQuestActivity;
import com.example.codequestapp.models.Quest;
import com.example.codequestapp.models.Question;
import com.example.codequestapp.utils.AppContext;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;

public class QuestionCardAdapter extends RecyclerView.Adapter<QuestionCardAdapter.ViewHolder> {

    private Question[] localDataSet;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView prompt;
        public final TextInputLayout questionField;
        public final TextInputEditText questionAnswer;


        public ViewHolder(View view) {
            super(view);
            prompt  = view.findViewById(R.id.questionPrompt);
            questionField = view.findViewById(R.id.questionField);
            questionAnswer = view.findViewById(R.id.questionAnswer);
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public QuestionCardAdapter(Question[] dataSet, Context context) {
        this.localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_card_item, viewGroup, false);

//        questionAnswer.setListener to change the thing

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.prompt.setText(localDataSet[position].getPrompt());
        viewHolder.questionAnswer.setHint(localDataSet[position].getHint());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}