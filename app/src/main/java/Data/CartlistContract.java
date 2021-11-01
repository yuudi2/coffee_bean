package Data;

import android.provider.BaseColumns;

public class CartlistContract {

    //장바구니 db
    public static final class CartlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "cartlist";
        public static final String _ID = "id";
        public static final String COLUMN_IMG = "img";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_SIZE = "partySize";
        public static final String COLUMN_CUP = "cup";
        public static final String COLUMN_CREAM = "cream";
        public static final String COLUMN_COUNT = "count";
        public static final String COLUMN_TOTAL_PRICE = "total_price";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

    //매장리스트 db
    public static final class StorelistEntry implements BaseColumns {
        public static final String TABLE_NAME = "storelist";
        public static final String _ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TEL = "tel";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_OPEN = "open";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LNG = "lng";
        public static final String COLUMN_IMG = "img";
        public static final String COLUMN_DISTANCE = "distance";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

    //찜 목록 db
    public static final class MyfavlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "myfavlist";
        public static final String _ID = "id";
        public static final String COLUMN_IMG = "img";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

    //쿠폰 등록 db
    public static final class CouponlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "couponlist";
        public static final String _ID = "id";
        public static final String COLUMN_IMG = "img";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_COUPONNUM = "coupon";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

    //쿠폰 등록 db
    public static final class MycoulistEntry implements BaseColumns {
        public static final String TABLE_NAME = "mycoulist";
        public static final String _ID = "id";
        public static final String COLUMN_IMG = "img";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COUPONNUM = "coupon";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

    //포인트 db
    public static final class PointEntry implements BaseColumns {
        public static final String TABLE_NAME = "mypoint";
        public static final String _ID = "id";
        public static final String COLUMN_USERID = "user";
        public static final String COLUMN_POINT = "point";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
