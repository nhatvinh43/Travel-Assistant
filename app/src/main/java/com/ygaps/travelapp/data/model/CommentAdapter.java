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


//this is a mixable of Review and Comment
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private ArrayList<Review> dataSet;
    private Context context;
    private LayoutInflater inflater;

    public CommentAdapter(Context context, ArrayList<Review> obj){
        this.context = context;
        this.dataSet = obj;
        this.inflater = LayoutInflater.from(context);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        public ImageView avatar;
        public TextView userName, content, date;
        public RatingBar rate;
        public Button report;
        public RelativeLayout ReviewItemParent;
        public androidx.cardview.widget.CardView UserInfor;

        public CommentViewHolder(View view){
            super(view);
            avatar = view.findViewById(R.id.tourInfoRatingAvatar);
            userName = view.findViewById(R.id.tourInfoRatingUsername);
            content = view.findViewById(R.id.tourInfoRatingComment);
            date = view.findViewById(R.id.tourInfoCommentDate);
            rate = view.findViewById(R.id.tourInfoRatingStars);
            report = view.findViewById(R.id.tourInfoRatingReport);
            ReviewItemParent = view.findViewById(R.id.ReviewItemParent);
            UserInfor = view.findViewById(R.id.tourInfoRatingUserId);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        final Review rv = dataSet.get(position);
        holder.userName.setText(rv.getName());
        holder.content.setText(rv.getReview());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Long DateL = Long.valueOf(rv.getCreatedOn());
        String DateF = sdf.format(DateL);
        holder.date.setText(DateF);
        holder.rate.setRating(rv.getPoint());
        holder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ID of User
                Toast.makeText(context.getApplicationContext(),"Report Cmt of User iD: "+ rv.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.UserInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),"See Infor Of UserId " + rv.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.ReviewItemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),"Click On Comment", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }
}