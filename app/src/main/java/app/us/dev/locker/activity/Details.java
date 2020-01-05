package app.us.dev.locker.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.us.dev.locker.DataModel;
import app.us.dev.locker.DatabaseHelper;
import app.us.dev.locker.R;

public class Details extends AppCompatActivity {
    TextView titleTV, descriptionTV;
    Button editBTN, deleteBTN;

    int id;
    DataModel diaryModel;
    DatabaseHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        /*MobileAds.initialize(getApplicationContext(), getString(R.string.appID));
        adView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);*/


        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        diaryModel = new DataModel();
        database = new DatabaseHelper(Details.this);
        diaryModel = database.selectWithId(String.valueOf(id));

        init();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;

            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Install now");
                String app_url = "https://play.google.com/store/apps/details?id=app.cave.diarywithloker";
                shareIntent.putExtra(Intent.EXTRA_TEXT, app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                break;
            case R.id.rate:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=app.cave.diarywithloker"));
                startActivity(intent);
                break;
            case R.id.review:
                Intent inter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=app.cave.diarywithloker"));
                startActivity(inter);
                break;
            case R.id.moreapp:
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Cave+of+app"));
                startActivity(i);
                break;
            case R.id.lockID:
                startActivity(new Intent(this, RegistrationF.class));
                finish();
                break;
            case R.id.exit:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
*/
    private void init() {

        titleTV = (TextView) findViewById(R.id.title_TV);
        descriptionTV = (TextView) findViewById(R.id.description_TV);

        titleTV.setText(diaryModel.getTitle());
        descriptionTV.setText(diaryModel.getDescription());

        editBTN = (Button) findViewById(R.id.btn_edit);
        deleteBTN = (Button) findViewById(R.id.btn_delete);

        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Details.this, Edit.class);
                intent.putExtra("id", id);
                intent.putExtra("flag", 1);
                startActivity(intent);
                finish();

            }
        });


        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(Details.this, android.R.style.Theme_Material_Light_Dialog);
                } else {
                    builder = new AlertDialog.Builder(Details.this);
                }
                builder.setTitle("Continue with deletion")
                        .setMessage("Are you sure you want to really delete this data?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                deleteFile(diaryModel);


                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();

            }
        });
    }

    private void deleteFile(DataModel diaryModel) {
        database.deletelement(diaryModel);
        super.onBackPressed();
    }


}
