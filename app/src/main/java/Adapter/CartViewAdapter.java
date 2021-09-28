package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.shopping_cart;

import Data.CartlistContract;
import Data.CartlistDBHelper;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    int change_count;
    int its_price;

    private shopping_cart cart;
    private SQLiteDatabase mDb;
    CartlistDBHelper dbHelper;

    public CartViewAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mCursor = cursor;
    }


    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else return 0;
    }


    @NonNull
    @Override
    public CartViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.cart_list, parent, false);
        CartViewAdapter.ViewHolder vh = new CartViewAdapter.ViewHolder(view);


        return vh;
    }


    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull CartViewAdapter.ViewHolder holder, int position) {
        // 해당 포지션으로 이동한다.
        // false 가 리턴되면 데이터가 없거나 혹은 범위를 초과했다는 뜻이다.
        if (!mCursor.moveToPosition(position))
            return;
        // 열의 이름으로 열의 번호를 넘겨줌
        @SuppressLint("Range") int id = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.CartlistEntry._ID));
        @SuppressLint("Range") byte[] img = mCursor.getBlob(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_IMG));
        @SuppressLint("Range") String name = mCursor.getString(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_NAME));
        @SuppressLint("Range") int price = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_PRICE));
        @SuppressLint("Range") String size = mCursor.getString(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_SIZE));
        @SuppressLint("Range") String cup = mCursor.getString(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_CUP));
        @SuppressLint("Range") int count = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_COUNT));
        @SuppressLint("Range") int total_price = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_TOTAL_PRICE));

        int img_i = byte2Int(img);


        holder.cart_img.setImageResource(img_i);
        holder.cart_name.setText(name);
        holder.cart_price.setText(Integer.toString(price) + "원");
        holder.cart_size.setText(size);
        holder.cart_cup.setText(cup);
        holder.cart_count.setText(Integer.toString(count));
        holder.cart_total_price.setText(Integer.toString(total_price) + "원");


        //장바구니에서 수량조절
        holder.cart_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_count = (Integer.parseInt(holder.cart_count.getText().toString()));
                its_price = price;
                change_count++;
                holder.cart_count.setText(change_count + "");
                holder.cart_total_price.setText(change_count * its_price + "원");
                int f_count = change_count;
                int f_price = change_count * its_price;
                update(id, f_count, f_price);

            }
        });

        holder.cart_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_count = (Integer.parseInt(holder.cart_count.getText().toString()));
                if (change_count > 1) {
                    change_count = (Integer.parseInt(holder.cart_count.getText().toString()));
                    its_price = price;
                    change_count--;
                    holder.cart_count.setText(change_count + "");
                    holder.cart_total_price.setText(change_count * its_price + "원");
                    int f_count = change_count;
                    int f_price = change_count * its_price;
                    update(id, f_count, f_price);
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

    public void swapCursor(Cursor newCursor) {
        // 항상 이전 커서를 닫는다.
        if (mCursor != null)
            mCursor.close();
        // 새 커서로 업데이트
        mCursor = newCursor;
        // 리사이클러뷰 업데이트
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    public void update(int id, int count, int total_price) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(mContext);
        mDb = dbHelper.getWritableDatabase();
        mDb.execSQL("UPDATE cartlist SET count = " + count + ", total_price = '" + total_price + "'" + " WHERE id = '" + id + "'");
        mDb.close();
    }
//
//        ContentValues values = new ContentValues();
//        values.put("count", count);
//        values.put("total_price", total_price);
//        return mDb.update("cartlist2", values, "id=?" + id,null);



    public static int byte2Int(byte[] src) {
        int s1 = src[0] & 0xFF;
        int s2 = src[1] & 0xFF;
        int s3 = src[2] & 0xFF;
        int s4 = src[3] & 0xFF;

        return ((s1 << 24) + (s2 << 16) + (s3 << 8) + (s4 << 0));
    }


//
//    public long update(int id, int count, int total_price){
//        CartlistDBHelper dbHelper = new CartlistDBHelper(mContext);
//        mDb = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("count", count);
//        values.put("total_price", total_price);
//        return mDb.update("cart_list", values, "id=?" + id,null);
//    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
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

}
