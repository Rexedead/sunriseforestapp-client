package pro.sunriseforest.sunriseforestapp_client.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_START_AND_DEADLINE_DATE, Locale.getDefault());
        Date date = null;
        try {
            date = df.parse(task.getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        return date.getTime();
    }

    public static int getTaskId(Task task){
        return Integer.parseInt(task.getTaskID());
    }
}
