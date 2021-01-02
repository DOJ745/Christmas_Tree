package by.bstu.faa.christmas_tree.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import by.bstu.faa.christmas_tree.R;
import by.bstu.faa.christmas_tree.model.query.TableAnswerContainer;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<TableAnswerContainer> answers;

    public AnswerAdapter(Context context, ArrayList<TableAnswerContainer> _answers) {
        this.answers = _answers;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.answer_list_item, parent, false);
        return new AnswerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerAdapter.ViewHolder holder, int position) {

        TableAnswerContainer answerContainer = answers.get(position);

        holder.answerText.setText("Текст - " + answerContainer.getAnswerText());
        holder.answerId.setText("ID - " + answerContainer.getAnswerId());
        holder.answerQuestionID.setText("ID вопроса - " + answerContainer.getQuestionId());
        holder.answerTrueness.setText("Верность ответа - " + answerContainer.getTrueness());
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView answerId, answerText, answerQuestionID, answerTrueness;

        ViewHolder(View view){
            super(view);
            answerId = view.findViewById(R.id.answer_id_item);
            answerText = view.findViewById(R.id.answer_text_item);
            answerQuestionID = view.findViewById(R.id.answer_question_id_item);
            answerTrueness = view.findViewById(R.id.answer_trueness_item);
        }
    }
}
