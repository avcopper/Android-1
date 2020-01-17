package com.example.lesson1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterCity extends RecyclerView.Adapter<RecyclerAdapterCity.RecyclerViewHolder> {
    private String[] cities;
    private String[] weather;
    private RecyclerItemClickListener clickListener = null;

    RecyclerAdapterCity(Parcel data){
        this.cities = data.cities;
        this.weather = data.weather_collection;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_city, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        DataHandler dataHandler = new DataHandler();
        int tempRandomDay = dataHandler.generateNumber(-30, 30);
        int weatherRnd = dataHandler.generateNumber(0, 3);

        String tempCurrent = (tempRandomDay > 0 ? "+" : "") + tempRandomDay + "\u00B0";
        String tempDay = ((tempRandomDay + 3) > 0 ? "+" : "") + (tempRandomDay + 3) + "\u00B0";
        String tempNight = ((tempRandomDay - 3) > 0 ? "+" : "") + (tempRandomDay - 3) + "\u00B0";

        holder.setCity(cities[position]);
        holder.setWeather(weather[weatherRnd]);
        holder.setTemp(tempCurrent);
        holder.setTempDay(tempDay);
        holder.setTempNight(tempNight);

        Parcel parcel = new Parcel();
        parcel.city = cities[position];
        parcel.weather = weather[weatherRnd];
        parcel.tempCurrent = tempCurrent;
        parcel.tempDay = tempDay;
        parcel.tempNight = tempNight;

        holder.setClicker(parcel);
    }

    @Override
    public int getItemCount() {
        return cities.length;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout cityItem;
        private TextView citiesContainer;
        private TextView weatherContainer;
        private TextView tempContainer;
        private TextView tempDayContainer;
        private TextView tempNightContainer;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            cityItem = itemView.findViewById(R.id.recycler_item__city_item);
            citiesContainer = itemView.findViewById(R.id.recycler_item_city_city);
            weatherContainer = itemView.findViewById(R.id.recycler_item_city_weather);
            tempContainer = itemView.findViewById(R.id.recycler_item_city_temp);
            tempDayContainer = itemView.findViewById(R.id.recycler_item_city_temp_day);
            tempNightContainer = itemView.findViewById(R.id.recycler_item_city_temp_night);
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

        void setWeather(String data) {
            weatherContainer.setText(data);
        }

        void setTemp(String data) {
            tempContainer.setText(data);
        }

        void setTempDay(String data) {
            tempDayContainer.setText(data);
        }

        void setTempNight(String data) {
            tempNightContainer.setText(data);
        }
    }

    void setClickListener(RecyclerItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface RecyclerItemClickListener {
        void onItemClick(Parcel parcel);
    }
}
