package Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Data.CartlistContract.CartlistEntry;
import Data.CartlistContract.CouponlistEntry;
import Data.CartlistContract.MycoulistEntry;
import Data.CartlistContract.MyfavlistEntry;
import Data.CartlistContract.StorelistEntry;
import Data.CartlistContract.PointEntry;
import Data.CartlistContract.PointuseEntry;

public class CartlistDBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "cart2222.db";
        private static final int DATABASE_VERSION = 1;
        private	static final String TABLE_CONTACTS = "contacts";

        public CartlistDBHelper(Context context) {
            super(context,  DATABASE_NAME, null, DATABASE_VERSION);
        }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_CARTLIST_TABLE = "CREATE TABLE " + CartlistEntry.TABLE_NAME + " (" +
                CartlistContract.CartlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CartlistContract.CartlistEntry.COLUMN_IMG +" BLOB NOT NULL, " +
                CartlistContract.CartlistEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                CartlistContract.CartlistEntry.COLUMN_PRICE + " INTEGER NOT NULL, " +
                CartlistContract.CartlistEntry.COLUMN_SIZE +" TEXT, " +
                CartlistContract.CartlistEntry.COLUMN_CUP + " TEXT, " +
                CartlistContract.CartlistEntry.COLUMN_CREAM + " TEXT, " +
                CartlistContract.CartlistEntry.COLUMN_COUNT +" INTEGER NOT NULL, " +
                CartlistContract.CartlistEntry.COLUMN_TOTAL_PRICE + " INTEGER NOT NULL, " +
                CartlistContract.CartlistEntry.COLUMN_TIMESTAMP +" TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        // 쿼리 실행
        sqLiteDatabase.execSQL(SQL_CREATE_CARTLIST_TABLE);


        final String SQL_CREATE_STORELIST_TABLE = "CREATE TABLE " + CartlistContract.StorelistEntry.TABLE_NAME + " (" +
                CartlistContract.StorelistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CartlistContract.StorelistEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                CartlistContract.StorelistEntry.COLUMN_TEL + " TEXT NOT NULL, " +
                CartlistContract.StorelistEntry.COLUMN_ADDRESS +" TEXT NOT NULL, " +
                CartlistContract.StorelistEntry.COLUMN_OPEN + " TEXT NOT NULL, " +
                CartlistContract.StorelistEntry.COLUMN_LAT + " REAL NOT NULL, " +
                CartlistContract.StorelistEntry.COLUMN_LNG +" REAL NOT NULL, " +
                CartlistContract.StorelistEntry.COLUMN_IMG +" BLOB NOT NULL, " +
                CartlistContract.StorelistEntry.COLUMN_DISTANCE +" REAL NOT NULL, " +
                CartlistContract.StorelistEntry.COLUMN_TIMESTAMP +" TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    "); ";


        // 쿼리 실행
        sqLiteDatabase.execSQL(SQL_CREATE_STORELIST_TABLE);


        final String SQL_CREATE_MYFAVLIST_TABLE = "CREATE TABLE " + CartlistContract.MyfavlistEntry.TABLE_NAME + " (" +
                CartlistContract.MyfavlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CartlistContract.MyfavlistEntry.COLUMN_IMG +" BLOB NOT NULL, " +
                CartlistContract.MyfavlistEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                CartlistContract.MyfavlistEntry.COLUMN_PRICE + " INTEGER NOT NULL, " +
                CartlistContract.MyfavlistEntry.COLUMN_TIMESTAMP +" TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        // 쿼리 실행
        sqLiteDatabase.execSQL(SQL_CREATE_MYFAVLIST_TABLE);


        final String SQL_CREATE_COUPONLIST_TABLE = "CREATE TABLE " + CartlistContract.CouponlistEntry.TABLE_NAME + " (" +
                CartlistContract.CouponlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CartlistContract.CouponlistEntry.COLUMN_IMG +" BLOB NOT NULL, " +
                CartlistContract.CouponlistEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                CartlistContract.CouponlistEntry.COLUMN_PRICE + " INTEGER NOT NULL, " +
                CartlistContract.CouponlistEntry.COLUMN_COUPONNUM + " INTEGER NOT NULL, " +
                CartlistContract.CouponlistEntry.COLUMN_TIMESTAMP +" TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        // 쿼리 실행
        sqLiteDatabase.execSQL(SQL_CREATE_COUPONLIST_TABLE);


        final String SQL_CREATE_MYCOULIST_TABLE = "CREATE TABLE " + CartlistContract.MycoulistEntry.TABLE_NAME + " (" +
                CartlistContract.MycoulistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CartlistContract.MycoulistEntry.COLUMN_IMG +" BLOB NOT NULL, " +
                CartlistContract.MycoulistEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                CartlistContract.MycoulistEntry.COLUMN_COUPONNUM + " INTEGER NOT NULL, " +
                CartlistContract.MycoulistEntry.COLUMN_TIMESTAMP +" DATETIME DEFAULT (date('now','localtime'))" +
                "); ";

        // 쿼리 실행
        sqLiteDatabase.execSQL(SQL_CREATE_MYCOULIST_TABLE);


        final String SQL_CREATE_MYPOINT_TABLE = "CREATE TABLE " + CartlistContract.PointEntry.TABLE_NAME + " (" +
                CartlistContract.PointEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CartlistContract.PointEntry.COLUMN_USERID + " TEXT NOT NULL, " +
                CartlistContract.PointEntry.COLUMN_POINT + " INTEGER NOT NULL, " +
                CartlistContract.PointEntry.COLUMN_TIMESTAMP +" TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        // 쿼리 실행
        sqLiteDatabase.execSQL(SQL_CREATE_MYPOINT_TABLE);


        final String SQL_CREATE_POINTUSE_TABLE = "CREATE TABLE " + CartlistContract.PointuseEntry.TABLE_NAME + " (" +
                CartlistContract.PointuseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CartlistContract.PointuseEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                CartlistContract.PointuseEntry.COLUMN_POINT + " INTEGER NOT NULL, " +
                CartlistContract.PointuseEntry.COLUMN_POINTUSE + " INTEGER NOT NULL, " +
                CartlistContract.PointuseEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                CartlistContract.PointuseEntry.COLUMN_TIMESTAMP +" DATETIME DEFAULT (datetime('now','localtime'))" +
                "); ";

        // 쿼리 실행
        sqLiteDatabase.execSQL(SQL_CREATE_POINTUSE_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CartlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StorelistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyfavlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CouponlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MycoulistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PointEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PointuseEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void removePlace(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CartlistEntry.TABLE_NAME, CartlistEntry._ID + "=\"" + id+"\"", null) ;
    }


}
