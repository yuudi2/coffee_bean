package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

import Data.CartlistContract;

public class MyfavViewAdapter extends RecyclerView.Adapter<MyfavViewAdapter.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public MyfavViewAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mCursor = cursor;
    }


    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView myfav_img;
        TextView myfav_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            myfav_img = (ImageView) itemView.findViewById(R.id.myfav_img);
            myfav_name = (TextView) itemView.findViewById(R.id.myfav_name);
        }

    }

    @NonNull
    @Override
    public MyfavViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.myfav_list, parent, false);
        MyfavViewAdapter.ViewHolder vh = new MyfavViewAdapter.ViewHolder(view);


        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyfavViewAdapter.ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;
        // 열의 이름으로 열의 번호를 넘겨줌
        @SuppressLint("Range") int id = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.CartlistEntry._ID));
        @SuppressLint("Range") byte[] img = mCursor.getBlob(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_IMG));
        @SuppressLint("Range") String name = mCursor.getString(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_NAME));


        int img_i = byte2Int(img);

        holder.myfav_img.setImageResource(img_i);
        holder.myfav_name.setText(name);

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

}
