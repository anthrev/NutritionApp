package e.usf.nutritionapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter <ExampleAdapter.ExampleViewHolder> implements Filterable {
    private List<TestItem> exampleList;
    private List<TestItem> exampleListFull;

    class ExampleViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView1;
        TextView mTextView2;

        ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.text_view1);
            mTextView2 = itemView.findViewById(R.id.text_view2);
        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_item, viewGroup, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return  evh;

    }

    public ExampleAdapter(List<TestItem> exampleList){
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        TestItem currentItem = exampleList.get(i);

        exampleViewHolder.mImageView.setImageResource(currentItem.getImage());
        exampleViewHolder.mTextView1.setText(currentItem.getText());
        exampleViewHolder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    public  Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TestItem> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPatter = constraint.toString().toLowerCase().trim();

                for (TestItem item : exampleListFull) {
                    if(item.getText2().toLowerCase().contains(filterPatter)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
