package pro.sunriseforest.sunriseforestapp_client.presenters;


import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.ProfileFragment;

public class ProfilePresenter extends BasePresenter<ProfileFragment> {

    private static final String TAG = "ProfilePresenter";
    private User mUser;
    private SharedPreferenceHelper mSharedPreferenceHelper;
    private NavigationManager mNavigationManager;

    private static final ProfilePresenter ourInstance = new ProfilePresenter();

    public static ProfilePresenter getInstance() {
        return ourInstance;
    }


    private ProfilePresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
        mUser = mSharedPreferenceHelper.getUser();

    }


    @Override
    public void bindView(ProfileFragment view) {
        super.bindView(view);
        view.showProfile(mUser);

        boolean yes = canChangeProfile();
        mView.setEnabledEditTexts(yes);
        mView.saveIsVisible(false);

    }


    public void clickedExitProfile() {
        log("clickedExitProfile()");
        exitProfile();
    }

    public void clickedSaveButton() {
        log("clickedSaveButton()");
        //...
        mView.showToast("*клик по сохранялке*");
        mUser.setName(mView.getUserName());
        mUser.setEmail(mView.getUserMail());
        mUser.setPhoneNumber(mView.getUserPhone());
        saveProfileToServer(mUser);
        mView.saveIsVisible(false);


    }

    private void saveProfileToServer(User user) {
        log("saveProfileToServer()"+user);

         ApiFactory
                .getSunriseForestService()
                .updProfile(user.getId(), user)
                .compose(new AsyncNetTransformer<>())
                 .subscribe(this::saveProfileIntoPreference,this::handleNetworkError);
    }

    private void saveProfileIntoPreference(User user){
        log(String.format("saveProfileIntoPreference(User user = %s)", user));
        mSharedPreferenceHelper.saveUser(user);

    }

    private void exitProfile() {
        log("exitProfile()");
        mSharedPreferenceHelper.removeUser();
        mNavigationManager.fromProfileToLogin();
    }


    private boolean canChangeProfile() {
        log("canChangeProfile()");
        //...этот метод походу уже и не нужен, если все изначально можно редактировать
        return true;
    }


    @Override
    public String getTAG() {
        return TAG;
    }


}

