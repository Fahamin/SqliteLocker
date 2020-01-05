package app.us.dev.locker.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import app.us.dev.locker.DataModel;
import app.us.dev.locker.DatabaseHelper;
import app.us.dev.locker.R;

public class DataAdd extends AppCompatActivity {
    EditText titleET, descriptionET;
    Button saveBTN;
    String title, description, times, date;
    Calendar calendar;
    ImageView imageView;

    private View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_add);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        init();




    }



    private void init() {

        titleET = findViewById(R.id.title_ET);
        descriptionET = findViewById(R.id.description_ET);
        saveBTN =  findViewById(R.id.save_BTN);
        rootView = findViewById(R.id.root_view);
        saveBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (checkValidity()) {

                    title = titleET.getText().toString();
                    description = descriptionET.getText().toString();

                    Calendar cal = Calendar.getInstance();
                    Date chosenDate = cal.getTime();

                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                    date = dateFormat.format(chosenDate);

                    calendar = Calendar.getInstance();

                    SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");
                    String strTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

                    Date time = null;
                    try {
                        time = sdf24.parse(strTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    times = sdf12.format(time);


                    DataModel diaryModel = new DataModel();
                    diaryModel.setId(0);
                    diaryModel.setTitle(title);
                    diaryModel.setDescription(description);
                    diaryModel.setDate(date);
                    diaryModel.setTime(times);

                    DatabaseHelper database = new DatabaseHelper(DataAdd.this);
                    database.insertelement(diaryModel);
                    DataAdd.super.onBackPressed();


                }
            }
        });
    }


    private boolean checkValidity() {

        if (titleET.getText().toString().equals("")) {

            titleET.setError("This field is required !!!");
            return false;

        } else if (descriptionET.getText().toString().equals("")) {

            descriptionET.setError("This field is required !!!");
            return false;

        } else {

            return true;
        }
    }

}
