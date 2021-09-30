package Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Data.CartlistContract.CartlistEntry;

public class CartlistDBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "cartlist4.db";
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
                CartlistContract.CartlistEntry.COLUMN_COUNT +" INTEGER NOT NULL, " +
                CartlistContract.CartlistEntry.COLUMN_TOTAL_PRICE + " INTEGER NOT NULL, " +
                CartlistContract.CartlistEntry.COLUMN_TIMESTAMP +" TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        // 쿼리 실행
        sqLiteDatabase.execSQL(SQL_CREATE_CARTLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CartlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void removePlace(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CartlistEntry.TABLE_NAME, CartlistEntry._ID + "=\"" + id+"\"", null) ;
    }


}
