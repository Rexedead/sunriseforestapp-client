package pro.sunriseforest.sunriseforestapp_client.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import pro.sunriseforest.sunriseforestapp_client.models.Task;

public class TasksUtils {

    public static final String FORMAT_START_AND_DEADLINE_DATE = "dd.MM.yyyy";

    public static Comparator<Task> getComparatorTasks(){
        //todo  думаю, что в начале сравнивать по статусу тасков, потом по дате создания таска
        return (t1, t2) -> {
//          int result = (-1)* Byte.compare(t1.getStatus(), t2.getStatus());
//          if(result == 0) ...
//          return result;
            return 0;
        };
    }

    public static long getStartDateInMills(Task task){
        return getDateInMillsFromString(task.getStartDate()).getTime();
    }

    //works with start date and deadline date of task
    public static Date getDateInMillsFromString(String dateString){
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_START_AND_DEADLINE_DATE, Locale.getDefault());
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        return date;
    }


    public static int getTaskId(Task task){
        return Integer.parseInt(task.getTaskID());
    }


    // MinimumLatency численно равно  11:00 за день до старта.
    // возвращаем 0 если сейчас позднее 11:00 в день перед стартом .
    // -1 если дата старта уже в прошлом
    public static long getMinimumLatencyForRemindAboutStartTask(Task task){
        //start date
        Calendar startDate = Calendar.getInstance();
        long dateStartTaskInMills = TasksUtils.getStartDateInMills(task);
        startDate.setTimeInMillis(dateStartTaskInMills);

        Calendar now = Calendar.getInstance();

        //day after start
        Calendar dayAfterStart = Calendar.getInstance();
        dayAfterStart.setTimeInMillis(dateStartTaskInMills);
        dayAfterStart.add(Calendar.DAY_OF_YEAR , 1);
        if(startDate.after(dayAfterStart)) dayAfterStart.add(Calendar.YEAR, 1);

        //remind date
        Calendar remindDate = Calendar.getInstance();
        remindDate.setTimeInMillis(dateStartTaskInMills);
        remindDate.add(Calendar.DAY_OF_YEAR , -1);
        remindDate.set(Calendar.HOUR_OF_DAY, 11);
        if(remindDate.after(startDate)) remindDate.add(Calendar.YEAR, -1);

        //result
        long minimumLatency = remindDate.getTimeInMillis() - now.getTimeInMillis();

        if(now.before(remindDate)) return minimumLatency;
        else if(now.after(remindDate) && dayAfterStart.after(now)) return 0;
        else return -1;

    }
}
