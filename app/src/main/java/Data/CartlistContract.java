package Data;

import android.provider.BaseColumns;

public class CartlistContract {
    public static final class CartlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "cartlist";
        public static final String _ID = "id";
        public static final String COLUMN_IMG = "img";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_SIZE = "partySize";
        public static final String COLUMN_CUP = "cup";
        public static final String COLUMN_COUNT = "count";
        public static final String COLUMN_TOTAL_PRICE = "total_price";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
