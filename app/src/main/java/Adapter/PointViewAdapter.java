package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

import Data.CartlistContract;

public class PointViewAdapter extends RecyclerView.Adapter<PointViewAdapter.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;


    public PointViewAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mCursor = cursor;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView point_name;
        TextView point_type;
        TextView point_date;
        TextView pay_point;
        TextView remain_point;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            point_name = (TextView) itemView.findViewById(R.id.point_name);
            point_type = (TextView) itemView.findViewById(R.id.point_type);
            point_date = (TextView) itemView.findViewById(R.id.point_date);
            pay_point = (TextView) itemView.findViewById(R.id.pay_point);
            remain_point = (TextView) itemView.findViewById(R.id.remain_point);

        }
    }


    @NonNull
    @Override
    public PointViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.point_list, parent, false);
        PointViewAdapter.ViewHolder vh = new PointViewAdapter.ViewHolder(view);


        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull PointViewAdapter.ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;
        @SuppressLint("Range") int id = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.PointuseEntry._ID));
        @SuppressLint("Range") String name = mCursor.getString(mCursor.getColumnIndex(CartlistContract.PointuseEntry.COLUMN_NAME));
        @SuppressLint("Range") int point = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.PointuseEntry.COLUMN_POINT));
        @SuppressLint("Range") int usepoint = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.PointuseEntry.COLUMN_POINTUSE));
        @SuppressLint("Range") String type = mCursor.getString(mCursor.getColumnIndex(CartlistContract.PointuseEntry.COLUMN_TYPE));
        @SuppressLint("Range") String time = mCursor.getString(mCursor.getColumnIndex(CartlistContract.PointuseEntry.COLUMN_TIMESTAMP));

        holder.point_name.setText(name);
        holder.remain_point.setText(point+"원");
        holder.point_type.setText(type);

        time = time.substring(0, time.indexOf(" "));

        holder.point_date.setText(time);

        if(type.equals("충전")) {
            holder.pay_point.setText(usepoint + "원");
            holder.pay_point.setTextColor(Color.parseColor("#754fa0"));
        }else{
            holder.pay_point.setText("-" + usepoint + "원");
        }
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
