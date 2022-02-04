package Adapter;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.find_store;
import com.example.project1.store_info;

import Data.CartlistContract;

public class StoreViewAdapter extends RecyclerView.Adapter<StoreViewAdapter.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    Location myLocation;
    double latitude = 0;
    double longitude = 0;

    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION = 2;

    public StoreViewAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mCursor = cursor;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView store_img;
        TextView store_name;
        TextView store_address;
        TextView store_distance;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            store_img = (ImageView) itemView.findViewById(R.id.store_img);
            store_name = (TextView) itemView.findViewById(R.id.store_name);
            store_address = (TextView) itemView.findViewById(R.id.store_address);
            store_distance = (TextView) itemView.findViewById(R.id.distance);

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



        //현재 내 좌표(위도, 경도) 구하기
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        ((find_store)find_store.context2).onLocationChanged(myLocation);

        //((select_store)select_store.context4).onLocationChanged(myLocation);

        //내 좌표
        if(myLocation!= null){
            latitude = myLocation.getLatitude();
            longitude = myLocation.getLongitude();
        }

        double sLat = Double.valueOf(latitude);
        double sLng = Double.valueOf(longitude);

        //목표 좌표
        double eLat = Double.valueOf(lat);
        double eLng = Double.valueOf(lng);

        String distance = calcDistance(sLat, sLng, eLat, eLng);

        holder.store_distance.setText(distance);


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



    //좌표 간 거리 계산산
   public static String calcDistance(double lat1, double lon1, double lat2, double lon2){
        double EARTH_R, Rad, radLat1, radLat2, radDist;
        double distance, ret;

        EARTH_R = 6371000.0;
        Rad = Math.PI/180;
        radLat1 = Rad * lat1;
        radLat2 = Rad * lat2;
        radDist = Rad * (lon1 - lon2);

        distance = Math.sin(radLat1) * Math.sin(radLat2);
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist);
        ret = EARTH_R * Math.acos(distance);

        double rslt = Math.round(Math.round(ret) / 1000);
        String result = rslt + " km";
        if(rslt == 0) result = Math.round(ret) +" m";

        return result;
    }


}
