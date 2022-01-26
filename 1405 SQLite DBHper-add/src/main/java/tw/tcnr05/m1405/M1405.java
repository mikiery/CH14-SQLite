package tw.tcnr05.m1405;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class M1405 extends AppCompatActivity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405);

        setupViewComponent();
    }

    private void setupViewComponent() {






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


                Toast.makeText(getApplicationContext(), "施工中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.m_update:

                break;
            case R.id.m_delete:

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