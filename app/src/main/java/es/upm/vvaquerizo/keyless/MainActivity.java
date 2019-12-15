package es.upm.vvaquerizo.keyless;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setMessage(getResources().getString(R.string.bad_credentials_message));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final TextView username_text = findViewById(R.id.username_text);
        final TextView password_text = findViewById(R.id.password_text);
        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correctCredentials = DataBase.existUserPassword( MainActivity.this,username_text.getText().toString(), password_text.getText().toString());

                if (correctCredentials) {
                    scheduleJob();
                    Intent i = new Intent(MainActivity.this, DoorListActivity.class);
                    MainActivity.this.startActivity(i);
                } else {
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                }
            }
        });
    }

    private void scheduleJob() {
        int renewalIntervalMilis = 30*60*1000;
        ComponentName componentName = new ComponentName(this, JobSchedulerService.class);
        final JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setPersisted(true)
                .setPeriodic(renewalIntervalMilis)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(
                Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
        Toast.makeText(MainActivity.this, "The codes are renewed each " + jobInfo.getIntervalMillis()/60/1000 + " min",
                Toast.LENGTH_SHORT).show();
    }
}
