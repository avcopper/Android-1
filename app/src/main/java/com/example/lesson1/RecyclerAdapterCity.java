package com.example.lesson1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterCity extends RecyclerView.Adapter<RecyclerAdapterCity.RecyclerViewHolder> {
    private String[] cities;
    private RecyclerItemClickListener clickListener = null;

    RecyclerAdapterCity(Parcel data){
        cities = data.cities;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_city, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.setCity(cities[position]);
        Parcel parcel = new Parcel();
        parcel.city = cities[position];
        holder.setClicker(parcel);
    }

    @Override
    public int getItemCount() {
        return cities.length;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cityItem;
        private TextView citiesContainer;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            cityItem = itemView.findViewById(R.id.recycler_item__city_item);
            citiesContainer = itemView.findViewById(R.id.recycler_item_city_city);
        }

        void setClicker(Parcel parcel) {
            cityItem.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onItemClick(parcel);
                    }
                }
            });
        }

        void setCity(String data) {
            citiesContainer.setText(data);
        }
    }

    void setClickListener(RecyclerItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface RecyclerItemClickListener {
        void onItemClick(Parcel parcel);
    }
}
