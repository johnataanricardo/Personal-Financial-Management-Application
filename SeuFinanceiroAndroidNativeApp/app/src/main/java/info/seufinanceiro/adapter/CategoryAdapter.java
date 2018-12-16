package info.seufinanceiro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import info.seufinanceiro.R;
import info.seufinanceiro.model.Category;

public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Category category = categories.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_category, parent, false);
        }

        TextView description = (TextView) view.findViewById(R.id.description);
        description.setText(category.getDescription());

        return view;
    }

}
