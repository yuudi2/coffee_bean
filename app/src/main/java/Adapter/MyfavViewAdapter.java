package Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.project1.myfav_menu;
import com.example.project1.select_menu;
import com.example.project1.select_menu_cake;

import java.util.Arrays;

import Data.CartlistContract;
import Data.CartlistDBHelper;

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
        ImageButton myfav_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            myfav_img = (ImageView) itemView.findViewById(R.id.myfav_img);
            myfav_name = (TextView) itemView.findViewById(R.id.myfav_name);
            myfav_delete = (ImageButton) itemView.findViewById(R.id.delete_favmenu);
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
        // ?????? ???????????? ?????? ????????? ?????????
        @SuppressLint("Range") int id = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.CartlistEntry._ID));
        @SuppressLint("Range") byte[] img = mCursor.getBlob(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_IMG));
        @SuppressLint("Range") String name = mCursor.getString(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_NAME));
        @SuppressLint("Range") int price = mCursor.getInt(mCursor.getColumnIndex(CartlistContract.CartlistEntry.COLUMN_PRICE));

        int img_i = byte2Int(img);


        holder.myfav_img.setImageResource(img_i);
        holder.myfav_name.setText(name);


        String [] coffee_name = {"???????????????????????????IB","??????IB","?????????IB","??????????????? ???????????????","??????????????? ????????????","??????????????? ????????????"};

        //??? ?????? ??????
        holder.myfav_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("????????????");
                builder.setMessage("?????? ????????? ?????????????????????????");


                //  setPositiveButton -> "OK"??????
                builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(id);
                        swapCursor(getAllGuests());

                        //???????????? ????????? ???????????? ???????????? ??????
                        notifyDataSetChanged();

                        if(getItemCount()==0){
                            ((myfav_menu)myfav_menu.con).visible2();
                        }
                    }
                });

                //  setNegativeButton -> "Cancel" ??????  //
                builder.setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                builder.show();

            }
        });

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
        // ?????? ?????? ????????? ?????????.
        if (mCursor != null)
            mCursor.close();
        // ??? ????????? ????????????
        mCursor = newCursor;
        // ?????????????????? ????????????
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    private Cursor getAllGuests() {
        // ????????? ???????????? (Projection array)??? ?????? ?????? ????????? ???????????? ?????? ?????? ???????????? ????????? ??? ?????? ??????.
        // ?????? ?????? ???????????? ?????? ?????? null ??? ????????????.
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


    public void delete(int id) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(mContext);
        mDb3 = dbHelper.getWritableDatabase();
        mDb3.execSQL("DELETE FROM myfavlist WHERE id = '" + id + "'");
        //mDb.close();
    }


    public static int byte2Int(byte[] src) {
        int s1 = src[0] & 0xFF;
        int s2 = src[1] & 0xFF;
        int s3 = src[2] & 0xFF;
        int s4 = src[3] & 0xFF;

        return ((s1 << 24) + (s2 << 16) + (s3 << 8) + (s4 << 0));
    }

}
