package tw.tcnr15.m1401;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class M1401 extends AppCompatActivity implements

        ViewSwitcher.ViewFactory,
        AdapterView.OnItemClickListener, View.OnClickListener {

    private ImageSwitcher txtComPlay;
    private TextView txtSelect;
    private TextView txtResult;
    private ImageButton btnscissors, btnstone, btnnet;

    private String user_select;
    private String answer;
    private MediaPlayer startmusic, mediawin, medialose, mediavctory, mediaend;

    private ImageSwitcher imgSwi_comp;
    private Button Okbtn;
    private Button Cancebtn;
    private Button ShowResultbtn;

    private int miCountSet = 0,   //總共玩幾局
                         miCountPlayerWin = 0, //玩家玩幾局
                         miCountComWin = 0,//腦贏玩幾局
                        miCountDraw = 0; //平手玩幾局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1401);
        setupViewComponent();
    }

    private void setupViewComponent() {

        txtSelect = (TextView) findViewById(R.id.m1401_s001);           //選擇結果
        txtResult = (TextView) findViewById(R.id.m1401_f000);        //輸贏判斷
        btnscissors = (ImageButton) findViewById(R.id.m1401_b001);       //剪刀
        btnstone = (ImageButton) findViewById(R.id.m1401_b002);            //石頭
        btnnet = (ImageButton) findViewById(R.id.m1401_b003);               //布
        Okbtn = (Button) findViewById(R.id.m1401_btnOk);
        Cancebtn = (Button) findViewById(R.id.m1401_btnCance);
        ShowResultbtn = (Button) findViewById(R.id.btnShowResult);

        //-----------------------電腦出拳
        txtComPlay = (ImageSwitcher) findViewById(R.id.m1401_c001);       //電腦選擇
        txtComPlay.setFactory(this);


        // ---開機動畫---
//        r_layout = (RelativeLayout) findViewById(R.id.m1401_r001);
//        r_layout.setBackgroundResource(R.drawable.back01);
////        r_layout.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_scale_rotate_out));
//        r_layout.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_scale_rotate_in));
//        r_layout.setBackgroundResource(R.drawable.back01);

        //開頭音樂
        // --開啟片頭音樂-----
//        startmusic = MediaPlayer.create(getApplication(), R.raw.guess);
//        startmusic.start();
        startmusic = MediaPlayer.create(getApplication(), R.raw.guess);
        startmusic.start();
        mediawin = MediaPlayer.create(getApplication(), R.raw.haha);
        medialose = MediaPlayer.create(getApplication(), R.raw.lose);
        mediavctory = MediaPlayer.create(getApplication(), R.raw.vctory);
        mediaend = MediaPlayer.create(getApplication(), R.raw.yt1);

        btnscissors.setOnClickListener(this);
        btnstone.setOnClickListener(this);
        btnnet.setOnClickListener(this);

//        Okbtn.setOnClickListener(this);
//        Cancebtn.setOnClickListener(this);
//        ShowResultbtn.setOnClickListener(this);
        u_setAlpha();
        u_Loaddata(); //開啟載入


    }

//private View.OnClickListener btnShowResult=new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//
//
//    }
//}


    @Override
    public void onClick(View v) {
//            imgSwi_comp.clearAnimation();
//            Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_scale_rotate_in);
//            anim.setInterpolator(new BounceInterpolator());
//            imgSwi_comp.setInAnimation(anim);

        miCountSet ++;
        int icomp = (int) (Math.random() * 3 + 1);
        //剪刀,石頭,布 random
        switch (icomp) {
            case 1:
                txtComPlay.setImageResource(R.drawable.scissors);

                break;
            case 2:
                txtComPlay.setImageResource(R.drawable.stone);

                break;
            case 3:
                txtComPlay.setImageResource(R.drawable.net);

                break;

        }
        switch (v.getId()) {

            case R.id.m1401_b001:                                         //玩家選剪刀
                user_select = getString(R.string.m1401_s001) + " " + getString(R.string.m1401_b001);
//                    u_01();
                u_setAlpha();
                btnscissors.getBackground().setAlpha(150);
                switch (icomp) {
                    case 1:
                        txtComPlay.setImageResource(R.drawable.scissors);
//                            answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f003);       //平
//                            txtResult.setTextColor(Color.YELLOW);                                           //平手的黃色

                        music(3);

                        break;
                    case 2:
                        txtComPlay.setImageResource(R.drawable.stone);
//                            answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f002);        //輸
//                            txtResult.setTextColor(Color.RED);                                               //輸的紅色
                        music(2);
                        break;
                    case 3:
                        txtComPlay.setImageResource(R.drawable.net);
//                            answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f001);         //贏
//                            txtResult.setTextColor(Color.GREEN);                                           //贏的綠色
                        music(1);
                        break;
                }


                break;
            case R.id.m1401_b002:                                       //玩家選擇石頭
                user_select = getString(R.string.m1401_s001) + " " + getString(R.string.m1401_b002);
//                    u_01();
                u_setAlpha();
                btnstone.getBackground().setAlpha(150);
                switch (icomp) {
                    case 1:
                        txtComPlay.setImageResource(R.drawable.scissors);
//                            answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f001);       //贏
//                            txtResult.setTextColor(Color.GREEN);
                        music(1);
                        break;
                    case 2:
                        txtComPlay.setImageResource(R.drawable.stone);
//                            answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f003);       //平手
//                            txtResult.setTextColor(Color.YELLOW);
                        music(3);
                        break;
                    case 3:
                        txtComPlay.setImageResource(R.drawable.net);
//                            answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f002);       //輸
//                            txtResult.setTextColor(Color.RED);
                        music(2);
                        break;
                }

                break;
            case R.id.m1401_b003:                                             //玩家選擇布
                user_select = getString(R.string.m1401_s001) + " " + getString(R.string.m1401_b003);
//                    u_01();
                u_setAlpha();
                btnnet.getBackground().setAlpha(150);
                switch (icomp) {
                    case 1:
                        txtComPlay.setImageResource(R.drawable.scissors);
//                            answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f002);      //輸
//                            txtResult.setTextColor(Color.RED);
                        music(2);
                        break;
                    case 2:
                        txtComPlay.setImageResource(R.drawable.stone);
//                            answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f001);      //贏
//                            txtResult.setTextColor(Color.GREEN);
                        music(1);
                        break;
                    case 3:
                        txtComPlay.setImageResource(R.drawable.net);
//                            answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f003);        //平手
//                            txtResult.setTextColor(Color.YELLOW);
                        music(3);
                        break;
                }

                break;

        }
        //-----------------------------------------------------------------電腦出拳增加動畫
        txtComPlay.clearAnimation();
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_trans_bounce);
        anim.setInterpolator(new BounceInterpolator());
        txtComPlay.setInAnimation(anim);

        txtSelect.setText(user_select);                                    //選擇結果
        txtResult.setText(answer);                                      //判斷輸贏

    }

    private void u_setAlpha () {

        //------------------動畫背景

        btnscissors.setBackgroundResource(R.drawable.circle_shape);
        btnscissors.getBackground().setAlpha(0);
        btnstone.setBackgroundResource(R.drawable.circle_shape);
        btnstone.getBackground().setAlpha(0);
        btnnet.setBackgroundResource(R.drawable.ring);
        btnnet.getBackground().setAlpha(0);

    }


    private void u_01() {
//        //imageButton 背景為銀色且為全透明
//        btnScissors.setBackgroundColor(ContextCompat.getColor(this, R.color.Silver));
//        btnScissors.getBackground().setAlpha(0); //0-255
//        btnStone.setBackgroundColor(ContextCompat.getColor(this, R.color.Silver));
//        btnStone.getBackground().setAlpha(0);
//        btnNet.setBackgroundColor(ContextCompat.getColor(this, R.color.Silver));
//        btnNet.getBackground().setAlpha(0);
//        btnscissors.setBackgroundColor(Color.GRAY);
//        btnscissors.getBackground().setAlpha(0);
//        btnstone.setBackgroundColor(Color.GRAY);
//        btnstone.getBackground().setAlpha(0);
//        btnnet.setBackgroundColor(Color.GRAY);
//        btnnet.getBackground().setAlpha(0);
    }

    private void music(int i) {
//        if(startmusic.isPlaying()) startmusic.stop();
//        if(mediawin.isPlaying()) startmusic.pause();
//        if(medialose.isPlaying()) startmusic.pause();
//        if(mediavctory.isPlaying()) startmusic.pause();

//        private int miCountSet = 0,   //總共玩幾局
//                miCountPlayerWin = 0, //玩家玩幾局
//                miCountComWin = 0,//腦贏玩幾局
//                miCountDraw = 0; //平手玩幾局


        switch (i){
            case 1:    mediawin.start();        //贏
                answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f001);       //贏
                txtResult.setTextColor(Color.GREEN);                                           //贏的綠色

                Toast.makeText(getApplicationContext(),getText(R.string.m1401_f001),Toast.LENGTH_SHORT).show();
                miCountPlayerWin ++;
                break;
                case 2: medialose.start();         //輸
                    answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f002);        //輸
                    txtResult.setTextColor(Color.RED);                                               //輸的紅色
                    Toast.makeText(getApplicationContext(),getText(R.string.m1401_f002),Toast.LENGTH_SHORT).show();
                    miCountComWin  ++;
                break;
                case 3:mediavctory.start();      //平手
                    answer=getString(R.string.m1401_f000)+getString(R.string.m1401_f003);         //平手
                    txtResult.setTextColor(Color.YELLOW);                                           //平手的綠色
                    Toast.makeText(getApplicationContext(),getText(R.string.m1401_f003),Toast.LENGTH_SHORT).show();
                    miCountDraw  ++;
                break;
            case 4:mediaend.start();

                break;
        }
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        this.finish();
//    }

    @Override
    protected void onStop() {
        super.onStop();
        if(startmusic.isPlaying()) startmusic.stop();                                         //關機音樂

        music(4);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public View makeView() {
        ImageView v = new ImageView(this);
        // v.setBackgroundColor(0xFF000000);
        v.setScaleType(ImageView.ScaleType.FIT_CENTER);
        v.setLayoutParams(new
                ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return v;

    }




    public void btnok(View view) {
        Intent it = new Intent();

        Bundle bundle = new Bundle();
        bundle.putInt("KEY_COUNT_SET", miCountSet );
        bundle.putInt("KEY_COUNT_PLAYER_WIN", miCountPlayerWin);
        bundle.putInt("KEY_COUNT_COM_WIN", miCountComWin);
        bundle.putInt("KEY_COUNT_DRAW", miCountDraw);
        it.putExtras(bundle);

        setResult(RESULT_OK, it);
        finish();

    }

    public void btnShowResult(View view) {
        Intent it = new Intent();
        it.setClass(M1401.this, GameResult.class);

        Bundle bundle = new Bundle();
        bundle.putInt("KEY_COUNT_SET", miCountSet);
        bundle.putInt("KEY_COUNT_PLAYER_WIN", miCountPlayerWin);
        bundle.putInt("KEY_COUNT_COM_WIN", miCountComWin);
        bundle.putInt("KEY_COUNT_DRAW", miCountDraw);
        it.putExtras(bundle);

        startActivity(it);

    }


    public void Cancebtn(View view) {
        setResult(RESULT_CANCELED);
        finish();

    }
    private void u_savedata() {
        // 儲存SharedPreferences資料
//        SharedPreferences gameResultData =
//                getSharedPreferences("GAME_RESULT", 0);
        SharedPreferences gameResultData=getSharedPreferences("GAME_RESULT",0);
        gameResultData.edit().
        putInt("KEY_COUNT_SET", miCountSet).
        putInt("KEY_COUNT_PLAYER_WIN", miCountPlayerWin).
        putInt("KEY_COUNT_COM_WIN", miCountComWin).
        putInt("KEY_COUNT_DRAW", miCountDraw)

                .commit(); //完成儲存

Toast.makeText(M1401.this,"儲存成功" , Toast.LENGTH_SHORT).show();

    }




    private void u_Loaddata() {
        SharedPreferences gameResultData=getSharedPreferences("GAME_RESULT",0);

                miCountSet=gameResultData.getInt("KEY_COUNT_SET",0);
                miCountPlayerWin=gameResultData.getInt("KEY_COUNT_PLAYER_WIN",0);
                miCountComWin=gameResultData.getInt("KEY_COUNT_COM_WIN",0);
                miCountDraw=gameResultData.getInt("KEY_COUNT_DRAW",0);

        Toast.makeText(M1401.this,"載入成功" , Toast.LENGTH_SHORT).show();

    }
    private void u_cleardata() {

        SharedPreferences gameResultData=getSharedPreferences("GAME_RESULT",0);

        gameResultData
                .edit()
                .clear()
                .commit();

                  miCountSet = 0;   //總共玩幾局
                miCountPlayerWin = 0; //玩家玩幾局
                miCountComWin = 0;//腦贏玩幾局
                miCountDraw = 0;//平手玩幾局


        Toast.makeText(M1401.this,"清除成功" , Toast.LENGTH_SHORT).show();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.m1401, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnShowResult:
                ShowResultbtn.performClick();  //電腦腦按物件performClick()
                break;
            case R.id.btnSaveResult:
                    u_savedata();



                break;
            case R.id.btnLoadResult:
                u_Loaddata();
                break;
            case R.id.btnClearResult:
                u_cleardata();
                Cancebtn.performClick();
                break;
            case R.id.btnaboutme:
                new AlertDialog.Builder(M1401.this)
                        .setTitle("Preferences範例程式")
                        .setMessage("TCNR雲端製作")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.star_big_on)
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                        .show();
                break;

            case R.id.action_settings:
                Okbtn.performClick();
                finish();
                u_savedata(); //結束時自動存取
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}