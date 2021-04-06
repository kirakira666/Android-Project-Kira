package com.example.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int mCount = 0;
    private TextView mShowCount;
    private Button mZero;
    private Button Count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        mZero = (Button) findViewById(R.id.button_zero);
        Count = (Button) findViewById(R.id.button_count);
    }

    public void countUp(View view) {
        ++mCount;
        mZero.setBackgroundColor(Color.rgb(25, 25, 180));
        if (mShowCount != null) {

            mShowCount.setText(Integer.toString(mCount));
            if(mCount % 2 == 0 ){
                view.setBackgroundColor(Color.rgb(155, 0, 100));
            }else{
                view.setBackgroundColor(Color.rgb(0, 95, 120));
            }

        }
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void toZero(View view) {
        mCount=0;
        mZero.setBackgroundColor(Color.rgb(191, 186, 186));
        Count.setBackgroundColor(Color.rgb(155, 0, 100));
        if (mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));
    }
}