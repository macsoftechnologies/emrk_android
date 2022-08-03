package com.macsoftech.ekart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.model.CompanyName;


import java.util.List;

public class ComapnyNameAdapter extends RecyclerView.Adapter<ComapnyNameAdapter.MyviewHolder> {

    List<CompanyName> companyLists;
    Context mContext;
    View.OnClickListener onItemClickListener;


    public ComapnyNameAdapter(List<CompanyName> list, Context nContext) {
        companyLists = list;
        mContext = nContext;
    }

    public void onItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_company, parent, false);
        return new MyviewHolder(item);
    }


    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        holder.txtcampanyname.setText(companyLists.get(position).getCompanyName());
        holder.txtmobileNo.setText("MobileNO: "+companyLists.get(position).getMobileNo());
        holder.txtqty.setText("QTY"+companyLists.get(position).getQty());
        holder.txtqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return companyLists.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder {


        private TextView txtcampanyname;
        private TextView txtmobileNo;
        private TextView txtqty;
        private TextView txtviewdetails;

        public MyviewHolder(View itemView) {
            super(itemView);
            txtcampanyname = itemView.findViewById(R.id.txtcampanyname);
            txtmobileNo = itemView.findViewById(R.id.txtmobileNo);
            txtqty = itemView.findViewById(R.id.txtqty);
            txtviewdetails = itemView.findViewById(R.id.txtviewdetails);

            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);

        }
    }

}
