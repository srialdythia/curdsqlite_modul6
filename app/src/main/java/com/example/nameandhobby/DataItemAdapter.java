package com.example.nameandhobby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder>{

    private Context mContext;
    private List<DataModel> myDataList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView noTv, namaTv, hobbyTv;
        public ImageView mDeleteImage;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            noTv = itemView.findViewById(R.id.no_tv);
            namaTv = itemView.findViewById(R.id.nama_tv);
            hobbyTv = itemView.findViewById(R.id.hobby_tv);
            mDeleteImage = itemView.findViewById(R.id.delete_ic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    System.out.println("foo" + getAdapterPosition() + listener);
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }


    public DataItemAdapter(Context mContext, List myDataList) {
        this.mContext = mContext;
        this.myDataList = myDataList;
    }

    @NonNull
    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.data_item,parent,false);
        return new DataItemAdapter.ViewHolder(view, this.mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DataItemAdapter.ViewHolder holder, int position) {
        final DataModel data = myDataList.get(position);

        holder.noTv.setText(""+data.getId());
        holder.namaTv.setText(data.getNama());
        holder.hobbyTv.setText(data.getHobby());
    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }


}
