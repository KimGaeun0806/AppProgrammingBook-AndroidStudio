package org.techtown.cal2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    public String fname = null;
    public String str = null;
    public CalendarView calendarView;
    public Button 저장;
    public Button 수정;
    public EditText 입력;
    public TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calenderView);
        저장 = findViewById(R.id.저장);
        입력 = findViewById(R.id.입력);
        수정 = findViewById(R.id.수정);
        textView2 = findViewById(R.id.textView2);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                저장.setVisibility(View.VISIBLE);
                입력.setVisibility(View.VISIBLE);
                수정.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                입력.setText("");
                checkDay(year, month, dayOfMonth);
            }
        });

        저장.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                savememo(fname);
                str = 입력.getText().toString();
                저장.setVisibility(View.INVISIBLE);
                입력.setVisibility(View.INVISIBLE);
                수정.setVisibility(View.VISIBLE);
                textView2.setText(str);
                textView2.setVisibility(View.VISIBLE);
            }
        });
    }

    public void checkDay(int cYear, int cMonth, int cDay){
        fname = cYear+cMonth+cDay+".txt";
        FileInputStream fis = null;

        try{
            fis = openFileInput(fname);

            byte[]fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str = new String(fileData);

            입력.setVisibility(View.INVISIBLE);
            저장.setVisibility(View.INVISIBLE);
            수정.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(str);

            수정.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    입력.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    입력.setText(str);
                    저장.setVisibility(View.VISIBLE);
                    수정.setVisibility(View.INVISIBLE);
                    textView2.setText(입력.getText());
                }

            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void savememo(String readn){
        FileOutputStream fos = null;

        try{
            fos = openFileOutput(readn, MODE_NO_LOCALIZED_COLLATORS);
            String content = 입력.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}