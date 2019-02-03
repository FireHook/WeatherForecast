package com.example.vladyslav.weatherforecast.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.vladyslav.weatherforecast.R;
import com.example.vladyslav.weatherforecast.mvp.adapter.ForecastDetailAdapter;
import com.example.vladyslav.weatherforecast.mvp.model.ForecastToAdapter;
import com.example.vladyslav.weatherforecast.mvp.presenter.ForecastDetailPresenter;
import com.example.vladyslav.weatherforecast.network.model.Root;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastDetailFragment extends MvpAppCompatFragment implements ForecastDetailView {

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @InjectPresenter ForecastDetailPresenter mPresenter;

    private Root mRoot;
    private ForecastDetailAdapter mAdapter;

    public static Fragment newInstance(Root root) {
        ForecastDetailFragment instance = new ForecastDetailFragment();
        instance.mRoot = root;
        return instance;
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setRoot(mRoot);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override public void initRecyclerAdapter(List<ForecastToAdapter> list) {
        mAdapter = new ForecastDetailAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
    }
}
