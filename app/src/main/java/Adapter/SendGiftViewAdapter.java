package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.select_gift;

import java.util.ArrayList;

import Data.GiftData;

public class SendGiftViewAdapter extends RecyclerView.Adapter<SendGiftViewAdapter.ViewHolder> {


    private ArrayList<GiftData> giftlist;

    public SendGiftViewAdapter(ArrayList<GiftData> giftlist) {
        this.giftlist = giftlist;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_item;
        TextView tv_name;
        TextView tv_price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_item = (ImageView) itemView.findViewById(R.id.sendgift_img);
            tv_name = (TextView) itemView.findViewById(R.id.sendgift_name);
            tv_price = (TextView) itemView.findViewById(R.id.sendgift_price);

        }
    }


    @NonNull
    @Override
    public SendGiftViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.sendgift_list, parent, false);
        SendGiftViewAdapter.ViewHolder vh = new SendGiftViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SendGiftViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GiftData item = giftlist.get(position);


        holder.img_item.setImageResource(item.getImg());
        holder.tv_name.setText(item.getName());
        holder.tv_price.setText(Integer.toString(item.getprice())+"원");

        holder.img_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), position+"", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), select_gift.class);


                intent.putExtra("img", giftlist.get(position).getImg());
                intent.putExtra("name", giftlist.get(position).getName());
                intent.putExtra("price", giftlist.get(position).getprice());

                v.getContext().startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return giftlist.size();
    }
}
