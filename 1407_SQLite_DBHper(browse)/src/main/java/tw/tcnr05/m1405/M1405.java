package tw.tcnr05.m1405;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class M1405 extends AppCompatActivity   {
    //SQLite函式庫//由於這裡地次新增要抓面這四個方法這樣才有功能運作 包含initDB()
    private FriendDbHelper dbHper;
    //資料庫檔案名稱
    private static final String DB_FILE = "friends.db";
    //資料表名稱
    private static final String DB_TABLE = "member";
    //資料庫版本
    private static final int DBversion = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405);

        initDB();
    }

    private void initDB() {
        if (dbHper == null) {
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1405, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent it = new Intent();

        switch (item.getItemId()) {
            case R.id.m_add:
                //u_add();
                Toast.makeText(getApplicationContext(), "施工中", Toast.LENGTH_SHORT).show();
                it.setClass(M1405. this,M1405insert.class);
                startActivity(it);
                break;
            case R.id.m_query://查詢

                it.setClass(M1405. this,M1405query.class);
                startActivity(it);
                Toast.makeText(getApplicationContext(), "施工中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.m_update:
                it.setClass(M1405. this,M1405browse.class);
                startActivity(it);
                Toast.makeText(getApplicationContext(), "修改瀏覽", Toast.LENGTH_SHORT).show();
                break;
            case R.id.m_delete:

                break;
            case R.id.m_batch://批次新增
                dbHper.createTB();  //--- new function
                long totrec=dbHper.RecCount();
                Toast.makeText(getApplicationContext(), "總計:"+totrec, Toast.LENGTH_LONG).show();
                break;
            case R.id.action_settings:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}