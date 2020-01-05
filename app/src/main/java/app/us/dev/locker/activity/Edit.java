package app.us.dev.locker.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import app.us.dev.locker.DataModel;
import app.us.dev.locker.DatabaseHelper;
import app.us.dev.locker.R;

public class Edit extends AppCompatActivity {
    Button saveBTN;

    String title, description, times, date;
    Calendar calendar;
    int id, flag;
    EditText titleET, descriptionET;
    DatabaseHelper diaryDatabase;
    DataModel diaryModel;

    private View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
        }




        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        flag = intent.getIntExtra("flag", -1);

        diaryModel = new DataModel();
        diaryDatabase = new DatabaseHelper(this);

        diaryModel = diaryDatabase.selectWithId(String.valueOf(id));

        init();
        setData(diaryModel);


    }

    private void setData(DataModel diaryModel) {

        titleET.setText(diaryModel.getTitle());
        descriptionET.setText(diaryModel.getDescription());
    }


    private void init() {

        titleET = findViewById(R.id.title_TV);
        descriptionET = findViewById(R.id.description_TV);
        rootView = findViewById(R.id.root_view);
        saveBTN = (Button) findViewById(R.id.save_BTN);


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


                    DataModel model = new DataModel();
                    model.setId(id);
                    model.setTitle(title);
                    model.setDescription(description);
                    model.setDate(date);
                    model.setTime(times);


                    diaryDatabase.editelement(model);
                    if (flag == 1) {

                        startActivity(new Intent(Edit.this, MainActivity.class));
                        Toast.makeText(Edit.this, "Successfully edited", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {

                        Edit.super.onBackPressed();
                        Toast.makeText(Edit.this, "Successfully edited", Toast.LENGTH_SHORT).show();
                    }


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
