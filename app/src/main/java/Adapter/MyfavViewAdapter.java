package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.select_menu;
import com.example.project1.select_menu_cake;

import java.util.Arrays;

import Data.CartlistContract;

public class MyfavViewAdapter extends RecyclerView.Adapter<MyfavViewAdapter.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private SQLiteDatabase mDb3;

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
        @SuppressLint("Range") int price = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_PRICE));

        int img_i = byte2Int(img);

        holder.myfav_img.setImageResource(img_i);
        holder.myfav_name.setText(name);


        String [] coffee_name = {"헤이즐넛아메리카노IB","단팥IB","인절미IB","블랙다이몬 아이스커피","블랙다이몬 카페라떼","블랙다이몬 카페수아"};



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Arrays.asList(coffee_name).contains(name) == true) {
                    Intent intent = new Intent(view.getContext(), select_menu.class);

                    intent.putExtra("img", img_i);
                    intent.putExtra("name", name);
                    intent.putExtra("price", price);

                    view.getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(view.getContext(), select_menu_cake.class);

                    intent.putExtra("img", img_i);
                    intent.putExtra("name", name);
                    intent.putExtra("price", price);

                    view.getContext().startActivity(intent);

                }
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

    private Cursor getAllGuests() {
        // 두번째 파라미터 (Projection array)는 여러 열들 중에서 출력하고 싶은 것만 선택해서 출력할 수 있게 한다.
        // 모든 열을 출력하고 싶을 때는 null 을 입력한다.
        return mDb3.query(
                CartlistContract.MyfavlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                CartlistContract.MyfavlistEntry.COLUMN_TIMESTAMP
        );
    }


    public static int byte2Int(byte[] src) {
        int s1 = src[0] & 0xFF;
        int s2 = src[1] & 0xFF;
        int s3 = src[2] & 0xFF;
        int s4 = src[3] & 0xFF;

        return ((s1 << 24) + (s2 << 16) + (s3 << 8) + (s4 << 0));
    }

}
