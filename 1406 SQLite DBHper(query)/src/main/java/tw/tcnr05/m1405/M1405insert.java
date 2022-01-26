package tw.tcnr05.m1405;

import android.content.Context;
import android.os.Bundle;
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

public class M1405insert extends AppCompatActivity implements View.OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405insert);

        setupViewComponent();
        initDB();
    }

    private void setupViewComponent() {
        e001 = (EditText) findViewById(R.id.edtName);
        e002 = (EditText) findViewById(R.id.edtGrp);
        e003 = (EditText) findViewById(R.id.edtAddr);
        count_t = (TextView) findViewById(R.id.count_t);
        b001 = (Button) findViewById(R.id.btnAdd);

        b001.setOnClickListener(this);
    }

    private void initDB() { //生資料庫
        if (dbHper == null) {
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
        }
    }

    @Override
    public void onClick(View v) {

        // 檢查name跟在e001上打得是否有有此筆資料
        tname = e001.getText().toString().trim();
        tgrp = e002.getText().toString().trim();
        taddress = e003.getText().toString().trim();
        if (tname.equals("") || tgrp.equals("") || taddress.equals("")) {
            Toast.makeText(getApplicationContext(), "資料空白無法新增 !", Toast.LENGTH_SHORT).show();
            return;
        }
        String msg = null;



        //------------------------------------------
        long rowID = dbHper.insertRec(tname, tgrp, taddress);
//------------------------------------------------------------------------------------
        if (rowID != -1) {
            e001.setHint("請繼續輸入");
            e001.setText("");
            e002.setText("");
            e003.setText("");

            msg = "新增記錄  成功 ! \n" + "目前資料表共有 " + dbHper.RecCount() + " 筆記錄 !";
        } else {
            msg = "新增記錄  失敗 !";
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        count_t.setText("共計:" + Integer.toString(dbHper.RecCount()) + "筆");
//                // 獲取輸入法打開的狀態-------------------------------
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