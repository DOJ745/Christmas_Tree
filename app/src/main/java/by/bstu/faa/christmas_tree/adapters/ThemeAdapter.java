package by.bstu.faa.christmas_tree.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import by.bstu.faa.christmas_tree.R;
import by.bstu.faa.christmas_tree.model.query.TableThemesContainer;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<TableThemesContainer> themes;

    public ThemeAdapter(Context context, ArrayList<TableThemesContainer> _themes) {
        this.themes = _themes;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ThemeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.theme_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ThemeAdapter.ViewHolder holder, int position) {

        TableThemesContainer themeContainer = themes.get(position);

        holder.themeName.setText("Название - " + themeContainer.getThemeName());
        holder.themeId.setText("ID - " + themeContainer.getThemeId());
    }

    @Override
    public int getItemCount() {
        return themes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView themeName, themeId;

        ViewHolder(View view){
            super(view);
            themeName = view.findViewById(R.id.theme_name_item);
            themeId = view.findViewById(R.id.theme_id_item);
        }
    }
}
