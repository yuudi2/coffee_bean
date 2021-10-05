package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.store_info;

import Data.CartlistContract;

public class StoreViewAdapter extends RecyclerView.Adapter<StoreViewAdapter.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public StoreViewAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mCursor = cursor;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView store_img;
        TextView store_name;
        TextView store_address;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            store_img = (ImageView) itemView.findViewById(R.id.store_img);
            store_name = (TextView) itemView.findViewById(R.id.store_name);
            store_address = (TextView) itemView.findViewById(R.id.store_address);
        }

    }


    @NonNull
    @Override
    public StoreViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.store_list, parent, false);
        StoreViewAdapter.ViewHolder vh = new StoreViewAdapter.ViewHolder(view);

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull StoreViewAdapter.ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;
        @SuppressLint("Range") int id = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.StorelistEntry._ID));
        @SuppressLint("Range") String name = mCursor.getString(mCursor.getColumnIndex(CartlistContract.StorelistEntry.COLUMN_NAME));
        @SuppressLint("Range") String address = mCursor.getString(mCursor.getColumnIndex(CartlistContract.StorelistEntry.COLUMN_ADDRESS));
        @SuppressLint("Range") byte[] img = mCursor.getBlob(mCursor.getColumnIndex(CartlistContract.StorelistEntry.COLUMN_IMG));
        @SuppressLint("Range") String tel = mCursor.getString(mCursor.getColumnIndex(CartlistContract.StorelistEntry.COLUMN_TEL));
        @SuppressLint("Range") Double lat = mCursor.getDouble(mCursor.getColumnIndex(CartlistContract.StorelistEntry.COLUMN_LAT));
        @SuppressLint("Range") Double lng = mCursor.getDouble(mCursor.getColumnIndex(CartlistContract.StorelistEntry.COLUMN_LNG));
        @SuppressLint("Range") String open = mCursor.getString(mCursor.getColumnIndex(CartlistContract.StorelistEntry.COLUMN_OPEN));


        int img_i = byte2Int(img);

        holder.store_name.setText(name);
        holder.store_address.setText(address);
        holder.store_img.setImageResource(img_i);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), store_info.class);

                intent.putExtra("name", name);
                intent.putExtra("address", address);
                intent.putExtra("img", img);
                intent.putExtra("tel", tel);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("open", open);

                v.getContext().startActivity(intent);
            }
        });


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

    public static int byte2Int(byte[] src) {
        int s1 = src[0] & 0xFF;
        int s2 = src[1] & 0xFF;
        int s3 = src[2] & 0xFF;
        int s4 = src[3] & 0xFF;

        return ((s1 << 24) + (s2 << 16) + (s3 << 8) + (s4 << 0));
    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else return 0;
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


}
