package by.bstu.faa.christmas_tree.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import by.bstu.faa.christmas_tree.R;
import by.bstu.faa.christmas_tree.model.UserInfo;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<UserInfo> users;

    public UserAdapter(Context context, ArrayList<UserInfo> _users) {
        this.users = _users;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.user_list_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {

        UserInfo userContainer = users.get(position);

        holder.userId.setText("ID - " + userContainer.getId());
        holder.userNickname.setText("Никнейм - " + userContainer.getName());
        holder.userTreeLevel.setText("Уровень дерева - " + userContainer.getTreeLevel());
        holder.userScore.setText("Счёт - " + userContainer.getScore());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView userId, userNickname, userTreeLevel, userScore;

        ViewHolder(View view){
            super(view);
            userId = view.findViewById(R.id.user_id_item);
            userNickname = view.findViewById(R.id.user_nickname_item);
            userTreeLevel = view.findViewById(R.id.user_tree_level_item);
            userScore = view.findViewById(R.id.user_score_item);
        }
    }
}
