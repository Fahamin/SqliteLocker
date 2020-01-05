package app.us.dev.locker.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import app.us.dev.locker.DataAdapter;
import app.us.dev.locker.DataModel;
import app.us.dev.locker.DatabaseHelper;
import app.us.dev.locker.ItemDecorate;
import app.us.dev.locker.R;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView messageTV;
    FloatingActionButton fab;
    List<DataModel> diaryModelList;
    DataModel diaryModel;
    DataAdapter adapter;
    DatabaseHelper diaryDatabase;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
        }




        init();
        diaryModelList = diaryDatabase.getAllitem();

        if (diaryModelList.size() > 0) {

            prepareForView();


        } else {

            recyclerView.setVisibility(View.GONE);
            messageTV.setVisibility(View.VISIBLE);

        }


    }


    private void init() {

        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        messageTV = (TextView) findViewById(R.id.messageTV);

        diaryModelList = new ArrayList<>();
        diaryModel = new DataModel();
        diaryDatabase = new DatabaseHelper(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, DataAdd.class));
            }
        });

    }

    private void prepareForView() {


        messageTV.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        diaryModelList.clear();
        diaryModelList = diaryDatabase.getAllitem();


        adapter = new DataAdapter(this, diaryModelList, recyclerView, adapter, "", messageTV, this);

        RecyclerView.LayoutManager layoutManagerBeforeMeal = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManagerBeforeMeal);
        recyclerView.addItemDecoration(new ItemDecorate(1, dpToPx(2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }




    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        diaryModelList.clear();
        diaryModelList = diaryDatabase.getAllitem();

        if (diaryModelList.size() > 0) {

            prepareForView();


        } else {

            recyclerView.setVisibility(View.GONE);
            messageTV.setVisibility(View.VISIBLE);

        }
    }
}