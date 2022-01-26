package tw.tcnr05.m1405;
////----------------------------------------------------------
//建構式參數說明：
//context 可以操作資料庫的內容本文，一般可直接傳入Activity物件。
//name 要操作資料庫名稱，如果資料庫不存在，會自動被建立出來並呼叫onCreate()方法。
//factory 用來做深入查詢用，入門時用不到。
//version 版本號碼。
////-----------------------
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FriendDbHelper extends SQLiteOpenHelper {

    String TAG = "tcnr05=>";
    public String sCreateTableCommand;
    // 資料庫名稱
    private static final String DB_FILE = "friends.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;    // 資料表名稱 版本
    private static final String DB_TABLE = "member";    // 資料庫物件，固定的欄位變數

    private static final String crTBsql = "CREATE     TABLE   " + DB_TABLE + "   ( "
            + "id    INTEGER   PRIMARY KEY," + "name TEXT NOT NULL," + "grp TEXT,"
            + "address TEXT);";

    private static SQLiteDatabase database;

    public FriendDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
        super(context, DB_FILE, null, VERSION);
        sCreateTableCommand = "";
    }


    //----------------------------------------------

    @Override
    public void onCreate(SQLiteDatabase db) {
        //----------------db執行sq語言
        db.execSQL(crTBsql);


    }


    //----------------------------------------------
    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new FriendDbHelper(context, DB_FILE, null, VERSION)
                    .getWritableDatabase();
        }
        return database;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //        Log.d(TAG, "onUpgrade()");
        db.execSQL("DROP     TABLE     IF    EXISTS    " + DB_TABLE);
        onCreate(db);


    }

    public long insertRec(String b_name, String b_grp, String b_address) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues rec = new ContentValues();
        rec.put("name", b_name);
        rec.put("grp", b_grp);
        rec.put("address", b_address);
        long rowID = db.insert(DB_TABLE, null, rec);  //SQLite 新增語法
        db.close();
        return rowID;
    }

    public int RecCount() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT    *   FROM   " + DB_TABLE;
        Cursor recSet = db.rawQuery(sql, null); //select
        return recSet.getCount();
    }
}

