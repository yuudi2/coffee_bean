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
import com.example.project1.select_menu;
import java.util.ArrayList;

import Data.CoffeeData;

public class CoffeeViewAdapter extends RecyclerView.Adapter<CoffeeViewAdapter.ViewHolder> {
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


    private ArrayList<CoffeeData> coffeelist = null;

    public CoffeeViewAdapter(ArrayList<CoffeeData> coffeelist) {
        this.coffeelist = coffeelist;
    }


    public void setmList2(ArrayList<CoffeeData> coffeelist) {
        this.coffeelist = coffeelist;
    }


    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_list, parent, false);
        CoffeeViewAdapter.ViewHolder vh = new CoffeeViewAdapter.ViewHolder(view);
        return vh;
    }


    //재사용 막음(중복x)
    @Override
    public int getItemViewType(int position) {
        return position;
    }


    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull CoffeeViewAdapter.ViewHolder holder, int position) {
        CoffeeData item = coffeelist.get(position);

        holder.img_item.setImageResource(item.getImg());
        holder.tv_name.setText(item.getName());
        holder.tv_price.setText(Integer.toString(item.getprice())+"원");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), position+"", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), select_menu.class);


                intent.putExtra("img", coffeelist.get(position).getImg());
                intent.putExtra("name", coffeelist.get(position).getName());
                intent.putExtra("price", coffeelist.get(position).getprice());

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
    public int getItemCount() {
        return coffeelist.size();
    }

}
