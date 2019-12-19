package com.example.doan.data.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.TourInfo_Main;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    private ArrayList<Member> memberList, filterList;
    private Context context;
    private LayoutInflater inflater;

    public MemberAdapter(Context context, ArrayList<Member> object){
        this.context = context;
        this.memberList = object;
        this.inflater = LayoutInflater.from(context);
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder{
        public ImageView avatar;
        public TextView name;
        public androidx.cardview.widget.CardView parent;
        public androidx.cardview.widget.CardView avatarParent;

        public MemberViewHolder(View view){
            super(view);

            avatar = view.findViewById(R.id.tourInfoMemberAvatar);
            name = view.findViewById(R.id.tourInfoMemberUsername);
            parent = view.findViewById(R.id.memberParent);
            avatarParent = view.findViewById(R.id.memberAvatarParent);

        }
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.member_item,parent,false );
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        final Member member = memberList.get(position);
        holder.name.setText(member.getName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click on member: " + member.getId(),Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, TourInfo_Main.class);
//                Log.d("SEND", member.getId()+"");
//                intent.putExtra("TourIdForInfo", tour.getId().toString());
//                context.startActivity(intent);
            }
        });
    }
    public void clear(){
        memberList.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Member> listItems)
    {
        memberList.addAll(listItems);
        notifyDataSetChanged();
    }
}
