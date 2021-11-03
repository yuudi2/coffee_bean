package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

import Data.CartlistContract;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.ViewHolder> {


    private Context mContext;
    private Cursor mCursor;


    public OrderViewAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mCursor = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView order_name;
        TextView order_size;
        TextView order_cup;
        TextView order_cream;
        TextView order_count;
        TextView order_price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            order_name = (TextView) itemView.findViewById(R.id.order_name);
            order_size = (TextView) itemView.findViewById(R.id.order_size);
            order_cup = (TextView) itemView.findViewById(R.id.order_cup);
            order_cream = (TextView) itemView.findViewById(R.id.order_cream);
            order_count = (TextView) itemView.findViewById(R.id.order_count);
            order_price = (TextView) itemView.findViewById(R.id.order_price);

        }
    }

    @NonNull
    @Override
    public OrderViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.order_list, parent, false);
        OrderViewAdapter.ViewHolder vh = new OrderViewAdapter.ViewHolder(view);


        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewAdapter.ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;
        // 열의 이름으로 열의 번호를 넘겨줌
        @SuppressLint("Range") int id = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.CartlistEntry._ID));
        @SuppressLint("Range") String name = mCursor.getString(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_NAME));
        @SuppressLint("Range") String size = mCursor.getString(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_SIZE));
        @SuppressLint("Range") String cup = mCursor.getString(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_CUP));
        @SuppressLint("Range") String cream = mCursor.getString(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_CREAM));
        @SuppressLint("Range") int count = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_COUNT));
        @SuppressLint("Range") int total_price = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_TOTAL_PRICE));

        holder.order_name.setText(name);
        holder.order_size.setText(size);
        holder.order_cup.setText(cup);
        holder.order_cream.setText(cream);
        holder.order_count.setText(Integer.toString(count) + "개");
        holder.order_price.setText(Integer.toString(total_price) + "원");
    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else return 0;
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

}
