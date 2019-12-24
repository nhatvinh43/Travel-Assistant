package com.ygaps.travelapp.data.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FeedbackSPAdapter extends RecyclerView.Adapter<FeedbackSPAdapter.FeedbackSPViewHolder> {
    private Context context;
    private ArrayList<FeedbackSP> listFeedback;
    private LayoutInflater inflater;

    public FeedbackSPAdapter(Context context, ArrayList<FeedbackSP> obj){
        this.context = context;
        this.listFeedback = obj;
        this.inflater = LayoutInflater.from(context);
    }

    public class FeedbackSPViewHolder extends RecyclerView.ViewHolder{
        public ImageView avatar;
        public TextView userName, date, comment;
        public RatingBar rate;
        public androidx.cardview.widget.CardView parent;
        public Button report;
        public RelativeLayout parentLayout;
        public FeedbackSPViewHolder(View view){
            super(view);
            avatar = view.findViewById(R.id.stopPointInfoRatingAvatar);
            userName = view.findViewById(R.id.stopPointInfoRatingUsername);
            date = view.findViewById(R.id.stopPointInfoCommentDate);
            comment = view.findViewById(R.id.stopPointInfoRatingComment);
            rate = view.findViewById(R.id.stopPointInfoRatingStars);
            report = view.findViewById(R.id.stopPointInfoCommentReport);
            parent = view.findViewById(R.id.stopPointInfoCommentParent);
            parentLayout = view.findViewById(R.id.CommentItemSPParent);
        }
    }

    @Override
    public int getItemCount() {
        return listFeedback.size();
    }

    @NonNull
    @Override
    public FeedbackSPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.comment_item_stop_points, parent, false);
        return new FeedbackSPViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackSPViewHolder holder, int position) {
        final FeedbackSP fbsp = listFeedback.get(position);
        holder.userName.setText(fbsp.getName());
        holder.comment.setText(fbsp.getFeedback());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Long DateL = Long.valueOf(fbsp.getCreatedOn());
        String DateF = sdf.format(DateL);
        holder.date.setText(DateF);
        holder.rate.setRating(Float.valueOf(fbsp.getPoint()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),"See UserInfor Of User " + fbsp.getUserId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),"Report Comt Id " + fbsp.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),"Click On Comment" + fbsp.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
