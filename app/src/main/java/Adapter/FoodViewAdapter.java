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

import Data.FoodData;

public class FoodViewAdapter extends RecyclerView.Adapter<FoodViewAdapter.ViewHolder>{
    public interface ItemClickListener { void onItemClick(int position);}
    FoodViewAdapter.ItemClickListener itemClickListener;
    public void  setItemClickListener(FoodViewAdapter.ItemClickListener itemClickListener){ this.itemClickListener = itemClickListener; }

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


    private ArrayList<FoodData> foodlist;

    public FoodViewAdapter(ArrayList<FoodData> foodlist) {
        this.foodlist = foodlist;
    }


    public void setmList2(ArrayList<FoodData> foodlist) {
        this.foodlist = foodlist;
    }


    @NonNull
    @Override
    public FoodViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_list, parent, false);
        FoodViewAdapter.ViewHolder vh = new FoodViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewAdapter.ViewHolder holder, int position) {
        FoodData item = foodlist.get(position);


        holder.img_item.setImageResource(item.getImg());
        holder.tv_name.setText(item.getName());
        holder.tv_price.setText(Integer.toString(item.getprice())+"원");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), position+"", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), select_menu_cake.class);


                intent.putExtra("img", foodlist.get(position).getImg());
                intent.putExtra("name", foodlist.get(position).getName());
                intent.putExtra("price", foodlist.get(position).getprice());

                intent.putExtra("code",1);

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
        return foodlist.size();
    }

}
