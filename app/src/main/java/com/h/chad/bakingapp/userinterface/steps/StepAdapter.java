package com.h.chad.bakingapp.userinterface.steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;
import com.h.chad.bakingapp.model.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chad on 7/10/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder>{
    private final static String TAG = StepAdapter.class.getName();
    private Context mContext;
    private ArrayList<Ingredients> mIngredients;
    private ArrayList<Steps> mSteps;
    private boolean mTwoPane;

    public StepAdapter(Context context, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps
            , boolean twoPane){
        this.mContext = context;
        this.mIngredients = ingredients;
        this.mSteps = steps;
        this.mTwoPane = twoPane;
    }

    @Override
    public StepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        int layoutIdForStep = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean attachToParentImmediatly = false;
        View view = inflater.inflate(layoutIdForStep, parent, attachToParentImmediatly);
        return new StepAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(StepAdapterViewHolder holder, final int position) {
        holder.bind(position);
        //When a step gets clicked on, it opens up the step details
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {

                } else {
                    Context context = v.getContext();
                    int stepID = mSteps.get(position).getId();
                    Intent stepDetailIntent = new Intent(context, StepDetailActivity.class);
                    Bundle args = new Bundle();
                    args.putParcelableArrayList(StepDetailFragment.GET_STEP_ARRAYLIST, mSteps);
                    stepDetailIntent.putExtras(args);
                    stepDetailIntent.putExtra(StepDetailFragment.GET_STEP_ID, stepID);
                    context.startActivity(stepDetailIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_step_number) TextView stepNumber;
        @BindView(R.id.tv_step_short_description) TextView shortDescription;
        public final View mView;

        public Steps mItem;

        public StepAdapterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            int stepint = mSteps.get(position).getId() + 1;
            stepNumber.setText(Integer.toString(stepint) +mContext.getString(R.string.step_paren));
            String shortDescriptionString = mSteps.get(position).getShortDescription();
            if (!TextUtils.isEmpty(shortDescriptionString))
                shortDescription.setText(shortDescriptionString);
            else
                shortDescription.setText(mContext.getString(R.string.no_description));
        }
    }
}