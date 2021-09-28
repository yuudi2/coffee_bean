package Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.select_menu_cake;

import java.util.ArrayList;

import Data.CakeData;

public class CakeViewAdapter extends RecyclerView.Adapter<CakeViewAdapter.ViewHolder>{
    public interface ItemClickListener { void onItemClick(int position);}
    ItemClickListener itemClickListener;
    public void  setItemClickListener(ItemClickListener itemClickListener){ this.itemClickListener = itemClickListener; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_item;
        TextView tv_name;
        TextView tv_price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_item = (ImageView) itemView.findViewById(R.id.coffe_img);
            tv_name = (TextView) itemView.findViewById(R.id.coffee_name);
            tv_price = (TextView) itemView.findViewById(R.id.coffee_price);

        }
    }


    private ArrayList<CakeData> cakelist;

    public CakeViewAdapter(ArrayList<CakeData> cakelist) {
        this.cakelist = cakelist;
    }


    public void setmList2(ArrayList<CakeData> cakelist) {
        this.cakelist = cakelist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_list, parent, false);
        CakeViewAdapter.ViewHolder vh = new CakeViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CakeViewAdapter.ViewHolder holder, int position) {
        CakeData item = cakelist.get(position);


        holder.img_item.setImageResource(item.getImg());
        holder.tv_name.setText(item.getName());
        holder.tv_price.setText(Integer.toString(item.getprice())+"원");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), position+"", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), select_menu_cake.class);


                intent.putExtra("img", cakelist.get(position).getImg());
                intent.putExtra("name", cakelist.get(position).getName());
                intent.putExtra("price", cakelist.get(position).getprice());

                v.getContext().startActivity(intent);

                if(position != RecyclerView.NO_POSITION){
                    if(itemClickListener != null){
                        itemClickListener.onItemClick(position);

                        //intent로 activty에 데이터를 넘겨줌


                    }
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //재사용 막음
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public int getItemCount() {
        return cakelist.size();
    }

}
