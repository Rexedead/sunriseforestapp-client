package pro.sunriseforest.sunriseforestapp_client.presenters.old;


//import pro.sunriseforest.sunriseforestapp_client.ui.fragments.LoginFragment;

//
//public class LoginPresenter extends AppPresenter<LoginFragment> {
//
//    public static String TAG = "LOGIN_PRESENTER";
//
//    private SharedPreferenceHelper mPreferenceHelper;
//
//    LoginPresenter() {
//        mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
//    }
//
//    @Override
//    public void update() {
//        if (mPreferenceHelper.getMyUser() != null) {
//            mView.showTaskDeskActivity();
//        }
//    }
//
//    public void selectedGoToRegistration() {
//        mView.showRegistrationActivity();
//    }
//
//    public void selectedLogin(String email, String password) {
//
//        Call<User> call = ApiFactory.getSunriseForestService().userLoginByEmail(email, password);
//        call.enqueue(new Callback<User>() {
//
//            @Override
//            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
//                int code = response.code();
//
//                User user = response.body();
//                if (user != null) {
//                    mPreferenceHelper.saveUser(user);
//                    mView.showTaskDeskActivity();
//                }
//                showErrorByCode(code);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
//                Log.e("LoginPresenter", t.getMessage());
//            }
//        });
//    }
//
//
//    private void showErrorByCode(int code) {
//        if (code == 401) {
//            mView.showError("неверный логин или пароль");
//        } else if (code == 400) {
//            mView.showError("запрос не ок. код ошибки 400");
//        }
//    }
//
//
//    @Override
//    public String getTAG() {
//        return TAG;
//    }
//
//
//}
