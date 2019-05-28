package pro.sunriseforest.sunriseforestapp_client.net;

import pro.sunriseforest.sunriseforestapp_client.presenters.DeskPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.LoginPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.ProfilePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.RegistrationPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.TaskPresenter;



public class ErrorMassageManager {


    public static String WhatIsMyError(int code, String tag) {
        switch (code) {
            case 400:
                return "Неверный запрос или запрещенные символы";
            case 401:

                if(LoginPresenter.getInstance().TAG.equals(tag))
                    return "Неверный логин или пароль";
                else if(RegistrationPresenter.getInstance().TAG.equals(tag))
                    return "401 при регистрации";
                else return "Неавторизованный запрос, попробуйте перезайти";


            case 403:

                if (RegistrationPresenter.getInstance().TAG.equals(tag)) {
                    return "403 при регистрации";
                }else if (TaskPresenter.getInstance().TAG.equals(tag)) {
                    return "Задача уже забронирована другим пользователем";
                }
                return "Пользователь заблокирован/Нет доступа";


            case 404:


                if(LoginPresenter.getInstance().TAG.equals(tag))
                    return "Запрос на авторизацию по неверной ссылке";
                else if(RegistrationPresenter.getInstance().TAG.equals(tag))
                    return "Запрос на регистрацию по неверной ссылке";
                else if(DeskPresenter.getInstance().TAG.equals(tag))
                    return "Запрос на задачи по неверной ссылке";
                else if(ProfilePresenter.getInstance().TAG.equals(tag))
                    return "Запрос на профиль по неверной ссылке";
                else return "Запрос по неверной ссылке";


            case 408:
                return "Сервер перегружен, попробуйте войти через 5 минут";
            case 409:
                return "Пользователь уже существует";
            case 429:

                if (LoginPresenter.getInstance().TAG.equals(tag)) {
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
