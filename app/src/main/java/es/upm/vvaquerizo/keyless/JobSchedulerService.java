package es.upm.vvaquerizo.keyless;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class JobSchedulerService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        List<DoorData> doorList = DataBase.getDoorsList(JobSchedulerService.this);
        int randomId = new Random().nextInt(doorList.size());
        NotificationsManager.getInstance(JobSchedulerService.this).sendNotificationUpdatedCode(doorList.get(randomId));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
