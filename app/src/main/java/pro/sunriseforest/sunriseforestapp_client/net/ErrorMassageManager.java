package pro.sunriseforest.sunriseforestapp_client.net;

import pro.sunriseforest.sunriseforestapp_client.presenters.DeskPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.LoginPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.ProfilePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.RegistrationPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.TaskPresenter;


//TODO сообщения "френдли юзер"


public class ErrorMassageManager {

    private final static String LoginPresenterTag = "%%%/presenter/" + LoginPresenter.TAG;
    private final static String RegistrationPresenterTag = "%%%/presenter/" + RegistrationPresenter.TAG;
    private final static String DeskPresenterTag = "%%%/presenter/" + DeskPresenter.TAG;
    private final static String ProfilePresenterTag = "%%%/presenter/" + ProfilePresenter.TAG;
    private final static String TaskPresenterTag = "%%%/presenter/" + TaskPresenter.TAG;

    public static String WhatIsMyError(int code, String tag) {
        switch (code) {
            case 400:
                return "Неверный запрос или запрещенные символы";
            case 401:

                switch (tag) {
                    case LoginPresenterTag:
                        return "Неверное имя пользователя или пароль";
                    case RegistrationPresenterTag:
                        return "401 при регистрации";
                    default:
                        return "Неавторизованный запрос, попробуйте перезайти";
                }

            case 403:

                if (RegistrationPresenterTag.equals(tag)) {
                    return "403 при регистрации";
                }
                if (TaskPresenterTag.equals(tag)) {
                    return "Задача уже забронирована другим пользователем";
                }
                return "Пользователь заблокирован/Нет доступа";


            case 404:

                switch (tag) {
                    case LoginPresenterTag:
                        return "Запрос на авторизацию по неверной ссылке";
                    case RegistrationPresenterTag:
                        return "Запрос на регистрацию по неверной ссылке";
                    case DeskPresenterTag:
                        return "Запрос на задачи по неверной ссылке";
                    case ProfilePresenterTag:
                        return "Запрос на профиль по неверной ссылке";
                    default:
                        return "Запрос по неверной ссылке";
                }

            case 408:
                return "Сервер перегружен, попробуйте войти через 5 минут";
            case 409:
                return "Пользователь уже существует";
            case 429:

                if (LoginPresenterTag.equals(tag)) {
                    return "Слишком много попыток входа. Попробуйте еще раз через 5 минут";
                }
                return "Слишком много запросов. Попробуйте еще раз через 5 минут";

            case 500:
                return "Ошибка сервера 500. Попробуйте еще раз через 5 минут";
            case 502:
                return "Ошибочный ответ от базы. Попробуйте еще раз через 5 минут";
            case 504:
                return "База перегружена. Попробуйте еще раз через 5 минут";
            default:
                return "Передать админам:";
        }
    }

}
