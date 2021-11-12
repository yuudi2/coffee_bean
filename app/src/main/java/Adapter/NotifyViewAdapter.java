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

public class NotifyViewAdapter extends RecyclerView.Adapter<NotifyViewAdapter.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;


    public NotifyViewAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mCursor = cursor;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noti_name;
        TextView noti_detail;
        TextView noti_date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noti_name = (TextView) itemView.findViewById(R.id.noti_name);
            noti_detail = (TextView) itemView.findViewById(R.id.noti_detail);
            noti_date = (TextView) itemView.findViewById(R.id.noti_date);

        }
    }


    @NonNull
    @Override
    public NotifyViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.notification_list, parent, false);
        NotifyViewAdapter.ViewHolder vh = new NotifyViewAdapter.ViewHolder(view);

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull NotifyViewAdapter.ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;
        @SuppressLint("Range") int id = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.NotifyEntry._ID));
        @SuppressLint("Range") String user = mCursor.getString(mCursor.getColumnIndex(CartlistContract.NotifyEntry.COLUMN_USERID));
        @SuppressLint("Range") String name = mCursor.getString(mCursor.getColumnIndex(CartlistContract.NotifyEntry.COLUMN_NAME));
        @SuppressLint("Range") String detail = mCursor.getString(mCursor.getColumnIndex(CartlistContract.NotifyEntry.COLUMN_DETAIL));
        @SuppressLint("Range") String time = mCursor.getString(mCursor.getColumnIndex(CartlistContract.NotifyEntry.COLUMN_TIMESTAMP));

        holder.noti_name.setText("[" + name + "]");
        holder.noti_detail.setText(detail);
        holder.noti_date.setText(time);


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


    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else return 0;
    }
}
