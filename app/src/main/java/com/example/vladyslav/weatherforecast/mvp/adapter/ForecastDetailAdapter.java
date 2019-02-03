package com.example.vladyslav.weatherforecast.mvp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vladyslav.weatherforecast.R;
import com.example.vladyslav.weatherforecast.mvp.model.ForecastToAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ForecastToAdapter> mForecastList;

    public ForecastDetailAdapter(List<ForecastToAdapter> forecasts) {
        mForecastList = forecasts;
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case 0:
                return new ViewHolder0(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_upper, viewGroup, false));
            case 2:
                return new ViewHolder2(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_lower, viewGroup, false));
            default:
                return new ViewHolder0(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_upper, viewGroup, false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0) viewHolder;
                bindUpperViewHolder(viewHolder0, position);
                break;
            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2) viewHolder;
                bindLowerViewHolder(viewHolder2, position);
                break;
        }
    }

    private void bindUpperViewHolder(ViewHolder0 holder, int position) {
        ForecastToAdapter forecast = mForecastList.get(position);
        holder.mDateText.setText(new StringBuilder().append("Today, ").append(forecast.getDate()).toString());
        String degree = (char) 0x00B0 + "C";
        holder.mDayTempText.setText(new StringBuilder().append(forecast.getDayTemperature()).append(degree));
        holder.mNightTempText.setText(new StringBuilder().append(forecast.getNightTemperature()).append(degree));
        holder.mMainDescText.setText(forecast.getDescription());
        String drawableName = "d" + forecast.getIcon().substring(0, 2);
        int resID = holder.mMainImage.getResources().getIdentifier(drawableName, "drawable", holder.mMainImage.getContext().getPackageName());
        holder.mMainImage.setImageResource(resID);
        holder.mCityText.setText(forecast.getCity());

    }

    private void bindLowerViewHolder(ViewHolder2 holder, int position) {
        ForecastToAdapter forecast = mForecastList.get(position);
        holder.mDayNameText.setText(forecast.getDate());
        holder.mSecondaryDayTemp.setText(forecast.getDayTemperature());
        holder.mSecondaryNightTemp.setText(forecast.getNightTemperature());
        holder.mSecondaryDesc.setText(forecast.getDescription());
        String drawableName = "d" + forecast.getIcon().substring(0, 2);
        int resID = holder.mSecondaryImage.getResources().getIdentifier(drawableName, "drawable", holder.mSecondaryImage.getContext().getPackageName());
        holder.mSecondaryImage.setImageResource(resID);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else
            return 2;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder0 extends RecyclerView.ViewHolder {

        @BindView(R.id.date_text)             TextView mDateText;
        @BindView(R.id.day_temp_text)         TextView mDayTempText;
        @BindView(R.id.night_temp_text)       TextView mNightTempText;
        @BindView(R.id.main_description_text) TextView mMainDescText;
        @BindView(R.id.main_image)            ImageView mMainImage;
        @BindView(R.id.city_name_text)        TextView mCityText;

        public ViewHolder0(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.secondary_image)            ImageView mSecondaryImage;
        @BindView(R.id.day_name_text)              TextView mDayNameText;
        @BindView(R.id.secondary_description_text) TextView mSecondaryDesc;
        @BindView(R.id.secondary_day_temp)         TextView mSecondaryDayTemp;
        @BindView(R.id.secondary_night_temp)       TextView mSecondaryNightTemp;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
