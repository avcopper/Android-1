package com.example.lesson1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterTime extends RecyclerView.Adapter<RecyclerAdapterTime.RecyclerViewHolder> {
    private String[] time;
    private int[] weather;

    RecyclerAdapterTime(Parcel data){
        this.time = data.time;
        this.weather = data.weather_image_collection;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_time, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        int tempRandomTime;
        String[] tempCollection = new String[6];
        DataHandler dataHandler = new DataHandler();

        for (int i = 0; i < 6; i++) {
            tempRandomTime = dataHandler.generateNumber(-2, 2);
            tempCollection[i] = (-11 + tempRandomTime) + "\u00B0";
        }

        holder.setTime(time[position]);
        holder.setTemp(tempCollection[position]);
        holder.setImage(weather[position + 6]);

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView time;
        private ImageView weather;
        private TextView temp;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.recycler_item_time_time);
            weather = itemView.findViewById(R.id.recycler_item_time_weather);
            temp = itemView.findViewById(R.id.recycler_item_time_temp);
        }

        void setTime(String data) {
            time.setText(data);
        }

        void setTemp(String data) {
            temp.setText(data);
        }

        void setImage(int data) {
            weather.setImageResource(data);
        }
    }
}
