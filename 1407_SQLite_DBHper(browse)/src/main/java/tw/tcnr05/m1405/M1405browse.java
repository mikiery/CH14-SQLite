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
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class M1405browse extends AppCompatActivity implements View.OnClickListener {
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
    private Button btPrev,btEnd;
    private Button btNext;
    private Button btTop;
    private TextView tvTittle;
    private int   index =0; //陣列第一筆
    private ArrayList<String> recSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405browse);

        setupViewComponent();
//        initDB();
//        count_t.setText("共計:" + Integer.toString(dbHper.RecCount()) + "筆");
    }

    private void setupViewComponent() {
        tvTittle=(TextView)findViewById(R.id.tvIdTitle);
        e001 = (EditText) findViewById(R.id.edtName);
        e002 = (EditText) findViewById(R.id.edtGrp);
        e003 = (EditText) findViewById(R.id.edtAddr);

        count_t =(TextView)findViewById(R.id.count_t) ;

        btNext = (Button) findViewById(R.id.btIdNext);
        btPrev = (Button) findViewById(R.id.btIdPrev);
        btTop = (Button) findViewById(R.id.btIdtop);
        btEnd = (Button) findViewById(R.id.btIdend);

        btNext.setOnClickListener(this);
        btPrev.setOnClickListener(this);
        btTop.setOnClickListener(this);
        btEnd.setOnClickListener(this);
       initDB();
    count_t.setText("共計:" + Integer.toString(dbHper.RecCount()) + "筆");
        //設定公有變數
    showRec(index);

    }

    private void showRec(int index) {
        if (recSet.size() != 0)   { //因為是Array是size
            String stHead = "顯示資料：第 " + (index + 1) + " 筆 / 共 " + recSet.size() + " 筆";
            tvTittle.setTextColor(ContextCompat.getColor(this, R.color.Blue));
            tvTittle.setText(stHead);
            //--------------------------
            String[] fld = recSet.get(index).split("#");  //fld=欄位簡寫//****自訂產生一維有幾個*****
            e001.setTextColor(ContextCompat.getColor(this, R.color.Red));
            //setText(fld[0]); 是ID
            e001.setText(fld[1]);
            e002.setText(fld[2]);
            e003.setText(fld[3]);
            //---------------------
        } else        {
            e001.setText("");
            e002.setText("");
            e003.setText("");
        }


    }


    private void initDB() { //生資料庫
        if (dbHper == null) {
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
        }
        recSet=dbHper.getRecSet();
        int a =0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btIdtop: //首筆
                ctlFirst();
                break;
            case R.id.btIdPrev://上一筆
                ctlPrev();
                break;
            case R.id.btIdNext://下一筆
                ctlNext();
                break;
            case R.id.btIdend://最後一筆
                ctLast();
                break;

        }








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
    private void ctlFirst() {
        index =0;
        showRec(index);
    }


    private void ctlPrev() {
        index --;
        //****項次*****
        if(index<0){
            index=recSet.size()-1;
        }
        showRec(index);

    }
    private void ctlNext() {
        index ++;
        if(index>recSet.size()-1){
            index=0;
        }
        showRec(index);
    }

    private void ctLast() {
    index=recSet.size()-1;
        showRec(index);
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
            case R.id.action_settings:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}