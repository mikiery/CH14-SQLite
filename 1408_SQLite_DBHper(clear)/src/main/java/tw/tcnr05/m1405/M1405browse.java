package tw.tcnr05.m1405;


import android.app.Service;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
    private float x1,y1x2,y2;
    private float y1;
    private float x2;
    private float range=300;
    private float ran=60;
    private Vibrator myVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405browse);

        setupViewComponent();

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
        // 宣告震動
        myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);

    }

    private void showRec(int index) {

        if (recSet.size() != 0) {
            String stHead = "顯示資料：第 " + (index + 1) + " 筆 / 共 " + recSet.size() + " 筆";
            tvTittle.setTextColor(ContextCompat.getColor(this,R.color.Blue));
            tvTittle.setText(stHead);
            String[] fld = recSet.get(index).split("#");
            e001.setTextColor(ContextCompat.getColor(this,R.color.Red));
            e001.setText(fld[1]);
            e002.setText(fld[2]);
            e003.setText(fld[3]);
        } else   {
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

//                //-------------------------------------------------
    }
    private void ctlFirst() { //首筆
        index =0;
        showRec(index);
    }


    private void ctlPrev() {//上一筆
        index --;
        //****項次*****
        if(index<0){
            index=recSet.size()-1;
        }
        showRec(index);

    }
    private void ctlNext() {//下一筆
        index ++;
        if(index>recSet.size()-1){
            index=0;
        }
        showRec(index);
    }

    private void ctLast() {//最後一筆
    index=recSet.size()-1;
        showRec(index);
    }
//------------------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://按下
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE://移動動畫 音效 震動
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(1000); // 震動五秒
                break;
            case MotionEvent.ACTION_UP://放開
                x2 = event.getX();
                y2 = event.getY();
                //                ========================================
                // 判斷左右的方法，因為屏幕的左上角是：0，0 點右下角是max,max
                // 並且移動距離需大於 > range
                float xbar = Math.abs(x2 - x1); //abs=絕對值
                float ybar = Math.abs(y2 - y1);
                double z = Math.sqrt(xbar * xbar + ybar * ybar); //公式
                int angle = Math.round((float) (Math.asin(ybar / z) / Math.PI * 180));// 角度
                if (x1 != 0 && y1 != 0) {
                    if (x1 - x2 > range) { // 向左滑動 ******設定宣告range移動多少距離初始直*******
                        ctlPrev();//*****連結方法****
                    }
                    if (x1 - x2 <  range) { // 向右滑動
                        ctlNext();
                        // t001.setText("向右滑動\n" + "滑動參值x1=" + x1 + " x2=" + x2 + "
                        // r=" + (x2 - x1)+"\n"+"ang="+angle);
                    }
                    if (y2 - y1 > range && angle > ran) { // 向下滑動 ******設定宣告向上移動移動"角度"多少距離初始直*******
                        // 往下角度需大於50
                        // 最後一筆
                        ctLast();
                    }
                    if (y1 - y2 > range && angle > ran) { // 向上滑動
                        // 往上角度需大於50
                        ctlFirst();// 第一筆
                    }

                }
            break;

        }

        return super.onTouchEvent(event);
    }


    //---生命週期 三個

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
            //震動放開停止
        if (myVibrator != null)
            myVibrator.cancel();
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