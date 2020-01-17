package com.example.lesson1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterDay extends RecyclerView.Adapter<RecyclerAdapterDay.RecyclerViewHolder> {
    private String[] day;
    private int[] weather;

    RecyclerAdapterDay(Parcel data){
        this.day = data.day;
        this.weather = data.weather_image_collection;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_day, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        int tempRandomDay = (new DataHandler()).generateNumber(-30, 30);

        String tempDay = ((tempRandomDay + 3) > 0 ? "+" : "") + (tempRandomDay + 3) + "\u00B0";
        String tempNight = ((tempRandomDay - 3) > 0 ? "+" : "") + (tempRandomDay - 3) + "\u00B0";
        String tempAverage = tempDay + " / " + tempNight;

        holder.setDay(day[position]);
        holder.setTemp(tempAverage);
        holder.setWeather(weather[position]);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView day;
        private ImageView weather;
        private TextView temp;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.recycler_item_day_day);
            weather = itemView.findViewById(R.id.recycler_item_day_weather);
            temp = itemView.findViewById(R.id.recycler_item_day_temp);
        }

        void setDay(String data) {
            day.setText(data);
        }

        void setTemp(String data) {
            temp.setText(data);
        }

        void setWeather(int data) {
            weather.setImageResource(data);
        }
    }
}
