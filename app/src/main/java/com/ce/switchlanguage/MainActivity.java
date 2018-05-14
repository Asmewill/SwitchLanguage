package com.ce.switchlanguage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale _UserLocale=LocaleUtils.getUserLocale(this);
        LocaleUtils.updateLocale(this,_UserLocale);
        setContentView(R.layout.activity_main);
        Toolbar _Toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_Toolbar);
        findViewById(R.id.change).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int _ItemId=item.getItemId();
        switch (_ItemId) {
            case R.id.chinese:
                    LocaleUtils.updateLocale(this, LocaleUtils.LOCALE_CHINESE);
                    LocaleUtils.updateLocale(getApplicationContext(), LocaleUtils.LOCALE_CHINESE);
                    restartAct();
               break;
            case R.id.english:
                    LocaleUtils.updateLocale(this, LocaleUtils.LOCALE_ENGLISH);
                    LocaleUtils.updateLocale(getApplicationContext(), LocaleUtils.LOCALE_ENGLISH);
                    restartAct();

                break;
            case R.id.russian:
                    LocaleUtils.updateLocale(this, LocaleUtils.LOCALE_RUSSIAN);
                    LocaleUtils.updateLocale(getApplicationContext(), LocaleUtils.LOCALE_RUSSIAN);
                    restartAct();

                break;
            case R.id.bb:
                    LocaleUtils.updateLocale(this, LocaleUtils.LOCALE_BB);
                    LocaleUtils.updateLocale(getApplicationContext(), LocaleUtils.LOCALE_BB);
                    restartAct();
                break;
        }
        return true;
    }

    /**
     * 重启当前Activity
     */
    private void restartAct() {
        finish();
        Intent _Intent = new Intent(this, MainActivity.class);
        startActivity(_Intent);
        //清除Activity退出和进入的动画
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change:
                startActivity(new Intent(this,TestActivity.class));
                break;
        }

    }
}
