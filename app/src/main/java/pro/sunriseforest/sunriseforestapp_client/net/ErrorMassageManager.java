package pro.sunriseforest.sunriseforestapp_client.net;

import pro.sunriseforest.sunriseforestapp_client.presenters.LoginPresenter;


//TODO сообщения "френдли юзер"


public class ErrorMassageManager {

/*TODO лучше избавиться от явных строк равных конкретным тегам*/
    public static String WhatIsMyError(int code, String tag) {
        switch (code) {
            case 400:
                return "Неверный запрос или запрещенные символы";
            case 401:

                switch (tag) {
                    case "LoginPresenter":
                        return "Неверное имя пользователя или пароль";
                    case "RegistrationPresenter":
                        return "401 при регистрации";
                    default:
                        return "Неавторизованный запрос, попробуйте перезайти";
                }

            case 403:

                if ("RegistrationPresenter".equals(tag)) {
                    return "403 при регистрации";
                }
                return "Пользователь заблокирован/Нет доступа";


            case 404:

                switch (tag) {
                    case "LoginPresenter":
                        return "Запрос на авторизацию по неверной ссылке";
                    case "RegistrationPresenter":
                        return "Запрос на регистрацию по неверной ссылке";
                    case "DeskPresenter":
                        return "Запрос на задачи по неверной ссылке";
                    case "ProfilePresenter":
                        return "Запрос на профиль по неверной ссылке";
                        default: return "Запрос по неверной ссылке";
                }

            case 408:
                return "Сервер перегружен, попробуйте войти через 5 минут";
            case 429:

                if ("LoginPresenter".equals(tag)) {
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
