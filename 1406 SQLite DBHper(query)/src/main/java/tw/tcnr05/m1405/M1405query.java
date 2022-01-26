package tw.tcnr05.m1405;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class M1405query extends AppCompatActivity implements View.OnClickListener {
    //SQLite函式庫
    private FriendDbHelper dbHper;
    //資料庫檔案名稱
    private static final String DB_FILE = "friends.db";
    //資料表名稱
    private static final String DB_TABLE = "member";
    //資料庫版本
    private static final int DBversion = 1;

    private Button b001;
    private EditText e001, e002, e003;
    private TextView count_t;
    private String tname;
    private String tgrp;
    private String taddress;
    private Button b002;
    private TextView t001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405query);

        setupViewComponent();
        initDB();
        count_t.setText("共計:" + Integer.toString(dbHper.RecCount()) + "筆");
    }

    private void setupViewComponent() {
        e001 = (EditText) findViewById(R.id.edtName);
        e002 = (EditText) findViewById(R.id.edtGrp);
        e003 = (EditText) findViewById(R.id.edtAddr);
        b002 = (Button) findViewById(R.id.btnquery);
        t001 = (TextView) findViewById(R.id.m_ans);
        count_t =(TextView)findViewById(R.id.count_t) ;


        b002.setOnClickListener(this);
    }

    private void initDB() { //生資料庫
        if (dbHper == null) {
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
        }
    }

    @Override
    public void onClick(View v) {
        String result = null;
        // 查詢name跟在e001上打得是否有有此筆資料
        String tname = e001.getText().toString().trim();
        if (tname.length() != 0)  {
            String rec = dbHper.FindRec(tname); //run sql
            if (rec != null)     {
                result = "找到的資料為 ：\n" + rec;
            } else     {
                result = "找不到指定的編號：" + tname;
            }
//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
//                    .show();
            t001.setText(result);
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
//                // isOpen若返回true，則表示輸入法打開
        if (isOpen == true) {// 如果輸入法打開則關閉，如果沒打開則打開
//                    // InputMethodManager m=(InputMethodManager)
//                    // getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
//                //-------------------------------------------------
    }



    //---生命週期

    @Override
    public void onBackPressed() {
//            super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dbHper != null) {
            dbHper.close();
            dbHper = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dbHper == null) {
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dbHper != null) {
            dbHper.close();
            dbHper = null;
        }
    }

    //---權限
    //---MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1405b, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.m_return:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}