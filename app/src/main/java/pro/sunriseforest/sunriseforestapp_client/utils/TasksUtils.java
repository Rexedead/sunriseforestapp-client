package pro.sunriseforest.sunriseforestapp_client.utils;

import java.util.Comparator;

import pro.sunriseforest.sunriseforestapp_client.models.Task;

public class TasksUtils {

    public static Comparator<Task> getComparatorTasks(){
        //todo  думаю, что в начале сравнивать по статусу тасков, потом по дате создания таска
        return (t1, t2) -> {
//          int result = (-1)* Byte.compare(t1.getStatus(), t2.getStatus());
//          if(result == 0) ...
//          return result;
            return 0;
        };
    }
}
