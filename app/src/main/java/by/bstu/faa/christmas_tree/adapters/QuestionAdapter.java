package by.bstu.faa.christmas_tree.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import by.bstu.faa.christmas_tree.R;
import by.bstu.faa.christmas_tree.model.query.TableQuestionContainer;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<TableQuestionContainer> questions;

    public QuestionAdapter(Context context, ArrayList<TableQuestionContainer> _questions) {
        this.questions = _questions;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.question_list_item, parent, false);
        return new QuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder holder, int position) {

        TableQuestionContainer questionContainer = questions.get(position);

        holder.questionText.setText("Текст - " + questionContainer.getQuestionText());
        holder.questionThemeId.setText("ID темы - " + questionContainer.getThemeId());
        holder.questionId.setText("ID - " + questionContainer.getQuestionId());

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView questionText, questionThemeId, questionId;

        ViewHolder(View view){
            super(view);
            questionText = view.findViewById(R.id.question_text_item);
            questionThemeId = view.findViewById(R.id.question_theme_id_item);
            questionId = view.findViewById(R.id.question_id_item);
        }
    }
}
