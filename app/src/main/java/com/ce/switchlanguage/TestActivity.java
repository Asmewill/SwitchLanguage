package com.ce.switchlanguage;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class TestActivity extends FragmentActivity {
    TextView tv_one,tv_two,tv_three,tv_four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        tv_one= (TextView) findViewById(R.id.tv_one);
        tv_two= (TextView) findViewById(R.id.tv_two);
        tv_three= (TextView) findViewById(R.id.tv_three);
        tv_four= (TextView) findViewById(R.id.tv_four);

        tv_one.setText(getString(R.string.language));
        tv_two.setText(getResources().getString(R.string.helloworld));
        tv_three.setText(getApplication().getResources().getString(R.string.helloworld));

    }
}
