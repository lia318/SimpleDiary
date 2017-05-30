package kr.hs.lia318e_mirim.simplediary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatePicker date;
    EditText edit;
    Button but;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = (DatePicker)findViewById(R.id.datePicker);
        edit = (EditText)findViewById(R.id.edit);
        but = (Button)findViewById(R.id.but);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DATE);

        date.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fileName = year + "_" +(month+1)+ "_" +day+ ".txt";
                String readDate = readDiary(fileName);
                edit.setText(readDate);
                but.setEnabled(true); // 새로 작성이나 수정이 되도록 활성화

            }
        });
    } // end of onCreate

    public String readDiary(String fileName){
        return null;
    }

} // end of MainActivity

//  DatePicker에서 Month는 1부터 시작