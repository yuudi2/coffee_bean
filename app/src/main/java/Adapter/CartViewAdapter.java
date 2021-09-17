package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

import java.util.ArrayList;

import Data.CartData;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.ViewHolder> {

    int change_count ;
    int its_price;
    private int position;

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    CoffeeViewAdapter.ItemClickListener itemClickListener;

    public void setItemClickListener(CoffeeViewAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cart_img;
        TextView cart_name;
        TextView cart_price;
        TextView cart_size;
        TextView cart_cup;
        TextView cart_cream;
        TextView cart_count;
        TextView cart_total_price;
        ImageButton cart_minus;
        ImageButton cart_plus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cart_img = (ImageView) itemView.findViewById(R.id.coffe_cart_img);
            cart_name = (TextView) itemView.findViewById(R.id.coffee_cart_name);
            cart_price = (TextView) itemView.findViewById(R.id.coffee_cart_price);
            cart_size = (TextView) itemView.findViewById(R.id.coffee_cart_size);
            cart_cup = (TextView) itemView.findViewById(R.id.coffee_cart_cup);
            //cart_cream = (TextView) itemView.findViewById(R.id.coffee_cart_cr);
            cart_count = (TextView) itemView.findViewById(R.id.coffee_count);
            cart_total_price = (TextView) itemView.findViewById(R.id.coffee_cart_pricetotal);
            cart_minus = (ImageButton) itemView.findViewById(R.id.cart_minus);
            cart_plus = (ImageButton) itemView.findViewById(R.id.cart_plus);


        }
    }

    private ArrayList<CartData> cartlist;

    public CartViewAdapter(ArrayList<CartData> cartlist) {
        this.cartlist = cartlist;
    }

    public void setListData(ArrayList<CartData> cartlist) {
        this.cartlist = cartlist;
    }

    public ArrayList<CartData> getListData() { return cartlist; }

    @NonNull
    @Override
    public CartViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.cart_list, parent, false);
        CartViewAdapter.ViewHolder vh = new CartViewAdapter.ViewHolder(view);


        return vh;
    }


//    //재사용 막음(중복x)
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }


    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull CartViewAdapter.ViewHolder holder, int position) {
        CartData item = cartlist.get(position);

        holder.cart_img.setImageResource(item.getImg());
        holder.cart_name.setText(item.getName());
        holder.cart_price.setText(Integer.toString(item.getprice()) + "원");
        holder.cart_size.setText(item.getsize());
        holder.cart_cup.setText(item.getcup());
        holder.cart_count.setText(Integer.toString(item.getCount()));
        holder.cart_total_price.setText(Integer.toString(item.getTotal_price()) + "원");



        //장바구니에서 수량조절
        change_count = (Integer.parseInt(holder.cart_count.getText().toString()));
        its_price =  item.getprice();

        holder.cart_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_count++;
                holder.cart_count.setText(change_count+"");
                holder.cart_total_price.setText(change_count * its_price + "원");
            }
        });

        holder.cart_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(change_count>1){
                    change_count--;
                    holder.cart_count.setText(change_count+"");
                    holder.cart_total_price.setText(change_count * its_price + "원");
                }
            }
        });


        //holder.tv_name.setText(item.getName());


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), position + "", Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(v.getContext(), select_menu.class);
//
//
//                intent.putExtra("img", cartlist.get(position).getImg());
//                intent.putExtra("name", cartlist.get(position).getName());
//                intent.putExtra("price", cartlist.get(position).getprice());
//
//                v.getContext().startActivity(intent);
//
//                if (position != RecyclerView.NO_POSITION) {
//                    if (itemClickListener != null) {
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


    //아이템 추가 메소드
    public void addItem(CartData c_data){
        cartlist.add(c_data);
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }


    //아이템 삭제 메소드
    public  void removeItem(int position){
        cartlist.remove(position);
        notifyItemRemoved(position);
        //갱신처리
        notifyDataSetChanged();
    }




    @Override
    public int getItemCount() {
        return cartlist.size();
    }

}
