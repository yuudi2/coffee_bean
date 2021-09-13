package Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

import java.util.ArrayList;

import Data.CoffeeData;

public class CoffeeViewAdapter extends RecyclerView.Adapter<CoffeeViewAdapter.ViewHolder> {
    public interface ItemClickListener { void onItemClick(int position);}
    ItemClickListener itemClickListener;
    public void  setItemClickListener(ItemClickListener itemClickListener){ this.itemClickListener = itemClickListener; }
    private Context context;

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

    public CoffeeViewAdapter(ArrayList<CoffeeData> mList2) {
        this.coffeelist = mList2;
    }



    public void setmList2(ArrayList<CoffeeData> mList2) {
        this.coffeelist = mList2;
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


    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull CoffeeViewAdapter.ViewHolder holder, int position) {
        CoffeeData item = coffeelist.get(position);

        holder.img_item.setImageResource(item.getImg());
        holder.tv_name.setText(item.getName());
        holder.tv_price.setText(item.getprice());


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), position+"", Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(v.getContext(), Change_Item_Activity.class);
//
//
//                intent.putExtra("image", coffeelist.get(position).getImage());
//                intent.putExtra("brand", coffeelist.get(position).getMainText());
//                intent.putExtra("item", coffeelist.get(position).getSubText());
//                intent.putExtra("point", coffeelist.get(position).getPoint());
//
//                v.getContext().startActivity(intent);
//
//                if(position != RecyclerView.NO_POSITION){
//                    if(itemClickListener != null){
//                        itemClickListener.onItemClick(position);
//
//                        //intent로 activty에 데이터를 넘겨줌
//
//
//                    }
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return coffeelist.size();
    }

}