package kr.hs.lia318e_mirim.simplediary;

        import android.content.Context;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatePicker date;
    EditText edit;
    Button but;
    String fileName;

    // R : 모든 소스를 사용할 수 있게 하는 존재? 모든 정보가 들어있다.
    // 오류가 나면 rebuild 하기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = (DatePicker)findViewById(R.id.datePicker);
        edit = (EditText)findViewById(R.id.edit);
        but = (Button)findViewById(R.id.but);
        //Calendar는 추상클래스. 바로 객체생성 불가. 그렇다고 상속받아서 사용하긴 귀찮. getInstance 메서드가 (현재객체 참조값 반환하도록)만들어져있음.
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE); //DAY OF MONTH랑 같음

        date.init(year, month, day, new DatePicker.OnDateChangedListener() { //네번째 매개변수는 날짜가변경됐을때 동작할 핸들러
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) { //사용자가 날짜를 바꿀때마다 호출됨
                fileName = year + " _ " + (month+1) + " _ " + day + ".txt"; //파일네임에는 /들어가면 안됨
                String readData = readDiary(fileName);
                edit.setText(readData);
                but.setEnabled(true);
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fOut = openFileOutput(fileName, Context.MODE_PRIVATE);//두번째인자는 모드 설정. private는 현재 앱에서만 접근 가능??
                    String str = edit.getText().toString();
                    fOut.write(str.getBytes()); //스트링을 바이트로 변환
                    fOut.close();
                    Toast.makeText(MainActivity.this , "정상적으로 " + fileName + " 파일이 저장되었습니다.", Toast.LENGTH_LONG).show();
                }
                catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                catch (IOException e) { //원래 FilenotFound이것도 해줘야한느데 따로 처리안해줄거면 부모인 IO만 써줘도 FIlenot 처리 가능
                    e.printStackTrace();
                }
            }
        });
    }//onCreate 닫음
    public String readDiary(String fileName){
        String diaryStr = null;
        FileInputStream fIn = null; //참조변수 초기화는 널
        try {
            fIn = openFileInput(fileName); //만약 파일이 없으면 예외처리
            byte[] buf = new byte[500]; // 500바이트 이하의 문자가 저장됐다고 생각하고 설정한값. 만약 500바이트 이상 입력되었으면 짤릴듯. 바이트수 키우면 더 많이
            //읽어올 수 있음ㅎ
            fIn.read(buf);
            diaryStr = new String(buf).trim(); // 500바이트 읽어온걸 string으로 변환 trim은 앞뒤공간만 제거 가능
            but.setText("수정");
            fIn.close();

        } catch (FileNotFoundException e) { //파일못찾을떄
            edit.setHint("일기가 존재하지 않습니다.");
            but.setText("새로 저장");
        } catch (IOException e) { //읽어오다가 문제 생길 경우
        }


        return diaryStr;
    }
}
//  DatePicker에서 Month는 1부터 시작