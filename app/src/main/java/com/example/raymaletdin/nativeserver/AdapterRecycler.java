package com.example.raymaletdin.nativeserver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mFirstName;
    private List<String> mSecondName;

    public AdapterRecycler(List<String> mLastStr, List<String> mSecondStr) {
        this.mFirstName = mLastStr;
        this.mSecondName = mSecondStr;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        viewHolder = getViewHolder(parent, inflater);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.firstName.setText(mFirstName.get(position));
        vh.lastName.setText(mSecondName.get(position));
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.recycler_list, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }

    public void add(ArrayList<String> firstList, ArrayList<String> secondList) {
        this.mSecondName = secondList;
        this.mFirstName = firstList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFirstName == null ? 0 : mFirstName.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.firstName)
        TextView firstName;

        @BindView(R.id.lastName)
        TextView lastName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
