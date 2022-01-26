package tw.tcnr05.m1405;


import static tw.tcnr05.m1405.M1405.BUTTON_NEGATIVE;
import static tw.tcnr05.m1405.M1405.BUTTON_POSITIVE;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
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

public class M1405update extends AppCompatActivity implements View.OnClickListener {
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


    private Button btDel;
    private EditText btEdit;
    protected static final int BUTTON_POSITIVE = -1;
    protected static final int BUTTON_NEGATIVE = -2;

    private String tid;
    private String msg;
    private String taddr;
    private Integer rowsAffected;
    private Button btUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405update);

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
        //------------------------------------
        btUpdate = (Button) findViewById(R.id.btnupdate);
        btDel = (Button) findViewById(R.id.btIdDel);
        btEdit =(EditText)findViewById(R.id.edid);
        //-----------------------------------

        btEdit.setKeyListener(null);  //設定ID 不能修改

        btEnd.setOnClickListener(this);
        btNext.setOnClickListener(this);
        btPrev.setOnClickListener(this);
        btTop.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btEdit.setOnClickListener(this);
        btDel.setOnClickListener(this);
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
            btEdit.setTextColor(ContextCompat.getColor(this, R.color.Red));
            btEdit.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow));
            btEdit.setText(fld[0]);

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
            case R.id.btnupdate:
                // 資料更新
                tid = btEdit.getText().toString().trim();
                tname = e001.getText().toString().trim();
                tgrp = e002.getText().toString().trim();
                taddr = e003.getText().toString().trim();

                rowsAffected = dbHper.updateRec(tid, tname, tgrp, taddr);//傳回修改筆數

                if (rowsAffected == -1) {
                    msg = "資料表已空, 無法修改 !";
                } else if (rowsAffected == 0) {
                    msg = "找不到欲修改的記錄, 無法修改 !";
                } else {
                    msg = "第 " + (index + 1) + " 筆記錄  已修改 ! \n" + "共 " + rowsAffected + " 筆記錄   被修改 !";
                    recSet = dbHper.getRecSet();
                    showRec(index);
                }
                Toast.makeText(M1405update.this, msg, Toast.LENGTH_SHORT).show();


//                ctUpdate();
                break;
            case R.id.btIdDel:
                MyAlertDialog aldDial = new MyAlertDialog(M1405update.this);
                aldDial.getWindow().setBackgroundDrawableResource(R.color.Yellow);
//                aldDial.setTitle("清空所有資料");
                aldDial.setTitle(getString(R.string.setTitle));
                aldDial.setMessage(getString(R.string.Message));
                aldDial.setIcon(android.R.drawable.ic_dialog_info);
                aldDial.setCancelable(false); //返回鍵關閉
                aldDial.setButton(BUTTON_POSITIVE, "確定刪除", aldBtListener);
                aldDial.setButton(BUTTON_NEGATIVE, "取消刪除", aldBtListener);
                aldDial.show();
                break;
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
    private  DialogInterface.OnClickListener aldBtListener =new DialogInterface.OnClickListener() {


        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case BUTTON_POSITIVE:
                                        tid = btEdit.getText().toString().trim();
                    rowsAffected = dbHper.deleteRec(tid);  // delete record
                    if (rowsAffected == -1) {
                        msg = "資料表已空, 無法刪除 !";
                    } else if (rowsAffected == 0) {
                        msg = "找不到欲刪除的記錄, 無法刪除 !";
                    } else {
                        msg = "第 " + (index + 1) + " 筆記錄  已刪除 ! \n" + "共 " + rowsAffected + " 筆記錄   被刪除 !";
                        recSet = dbHper.getRecSet();

                        if (index == dbHper.RecCount()) { //
                            index--; //
                        }
                        showRec(index); //重構
                    }
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                    break;
            }
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();



        }
    };
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