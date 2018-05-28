package bhumil.test.viewmyshow;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button search;
    private TextView date;
    private DatePicker picker;
    private Calendar calendar;
    int year, month, day;
    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        date = (TextView) findViewById(R.id.date);
        search = (Button)findViewById(R.id.Search);
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        showDate(year, month + 1, day);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID){
            return new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,myDateListener,year,month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    showDate(year,month+1,dayOfMonth);
                }
            };
    private void showDate(int year,int month,int day){
        date.setText(new StringBuilder().append(day).append("/")
                .append(month)
                .append("/").append(year));
    }
    public void Searchactivity(View view){
        Toast.makeText(this,date.getText(),Toast.LENGTH_LONG).show();
        Intent i = new Intent(MainActivity.this,theatre_display.class);
        i.putExtra("date",date.getText());
        startActivity(i);
    }

}

