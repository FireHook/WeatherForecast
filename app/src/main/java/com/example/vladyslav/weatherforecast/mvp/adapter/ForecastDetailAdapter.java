package com.example.vladyslav.weatherforecast.mvp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vladyslav.weatherforecast.R;
import com.example.vladyslav.weatherforecast.Utils;
import com.example.vladyslav.weatherforecast.mvp.model.ForecastItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TODAY_FORECAST = 0;
    private static final int FUTURE_FORECAST = 1;
    private static final int MAX_DAYS_FORECAST = 5;

    private List<ForecastItem> mForecastList;

    public ForecastDetailAdapter(List<ForecastItem> forecasts) {
        mForecastList = forecasts;
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TODAY_FORECAST:
                return new ViewHolderTodayForecast(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_today, viewGroup, false));
            case FUTURE_FORECAST:
                return new ViewHolderFutureForecast(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_week, viewGroup, false));
            default:
                return new ViewHolderFutureForecast(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_week, viewGroup, false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case TODAY_FORECAST:
                ViewHolderTodayForecast viewHolderTodayForecast = (ViewHolderTodayForecast) viewHolder;
                bindUpperViewHolder(viewHolderTodayForecast, position);
                break;
            case FUTURE_FORECAST:
                ViewHolderFutureForecast viewHolderFutureForecast = (ViewHolderFutureForecast) viewHolder;
                bindLowerViewHolder(viewHolderFutureForecast, position);
                break;
        }
    }

    private void bindUpperViewHolder(ViewHolderTodayForecast holder, int position) {
        ForecastItem forecast = mForecastList.get(position);
        holder.mDateText.setText(holder.mDateText.getContext().getString(R.string.today, forecast.getDate()));
        String degree = (char) 0x00B0 + "C";
        holder.mDayTempText.setText(new StringBuilder().append(forecast.getDayTemperature()).append(degree));
        holder.mNightTempText.setText(new StringBuilder().append(forecast.getNightTemperature()).append(degree));
        holder.mMainDescText.setText(forecast.getDescription());
        holder.mMainImage.setImageResource(Utils.getWeatherIcon(holder.mMainImage.getContext(), forecast.getIcon()));
        holder.mCityText.setText(forecast.getCity());

    }

    private void bindLowerViewHolder(ViewHolderFutureForecast holder, int position) {
        ForecastItem forecast = mForecastList.get(position);
        holder.mDayNameText.setText(forecast.getDate());
        holder.mSecondaryDayTemp.setText(forecast.getDayTemperature());
        holder.mSecondaryNightTemp.setText(forecast.getNightTemperature());
        holder.mSecondaryDesc.setText(forecast.getDescription());
        holder.mSecondaryImage.setImageResource(Utils.getWeatherIcon(holder.mSecondaryImage.getContext(), forecast.getIcon()));
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TODAY_FORECAST : FUTURE_FORECAST;
    }

    @Override
    public int getItemCount() {
        return MAX_DAYS_FORECAST;
    }

    public class ViewHolderTodayForecast extends RecyclerView.ViewHolder {

        @BindView(R.id.date_text)             TextView mDateText;
        @BindView(R.id.day_temp_text)         TextView mDayTempText;
        @BindView(R.id.night_temp_text)       TextView mNightTempText;
        @BindView(R.id.main_description_text) TextView mMainDescText;
        @BindView(R.id.main_image)            ImageView mMainImage;
        @BindView(R.id.city_name_text)        TextView mCityText;

        ViewHolderTodayForecast(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderFutureForecast extends RecyclerView.ViewHolder {

        @BindView(R.id.secondary_image)            ImageView mSecondaryImage;
        @BindView(R.id.day_name_text)              TextView mDayNameText;
        @BindView(R.id.secondary_description_text) TextView mSecondaryDesc;
        @BindView(R.id.secondary_day_temp)         TextView mSecondaryDayTemp;
        @BindView(R.id.secondary_night_temp)       TextView mSecondaryNightTemp;

        ViewHolderFutureForecast(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
