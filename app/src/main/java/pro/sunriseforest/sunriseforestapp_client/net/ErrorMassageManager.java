package pro.sunriseforest.sunriseforestapp_client.net;

public class ErrorMassageManager {

    public static String LOGIN(int code){

        switch (code){
            case 401:
                return "неверный логин или пароль";
            case 400:
                return "запрос не ок. код ошибки 400";
        }
       return "";
    }
    public static String REGISTRATION(int code){
        switch (code){
            case 401:
                return "запрос не ок. код ошибки 401";
            case 400:
                return "запрос не ок. код ошибки 400";
        }
        return "";
    }

    public static String GET_TASKS(int code){
        switch (code){
            case 401:
                return "запрос не ок. код ошибки 401";
            case 400:
                return "запрос не ок. код ошибки 400";
        }
        return "";
    }
}
