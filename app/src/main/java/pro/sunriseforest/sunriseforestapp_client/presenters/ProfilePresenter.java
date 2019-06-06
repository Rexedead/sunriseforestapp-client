package pro.sunriseforest.sunriseforestapp_client.presenters;


import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.ProfileFragment;


//todo переписать ProfilePresenter и ProfileFragment идейно и стилистически подбно с остальными презентерами и фрагментами в проекте
//todo fragment имеет наружу методы showSomething(), hideSomething() и геттеры
//todo добавить swiperefreshlayout
//todo колечко загрузки должно шоуиться и хайдиться в соответствии с работой с сетью
//todo модель в презентере обнавляется после того как она обнавилась на сервере!
// в байнде мы должны каждый раз тянуть юзера из преференса. (можно при первом байнде обнавляться с сервера (для этого используй метод BasePresenter.isFirstBind()));
// при свайпе тянем из инета, если не получилось - оповещаем об ошибке сети и тянем из преференса
//todo избегай дублирование кода
//todo удалить все ненужное
public class ProfilePresenter extends BasePresenter<ProfileFragment> {

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


    }

  /*  private void updUserData(){
        ApiFactory
                .getSunriseForestService()
                .getUser(
                        Integer.parseInt(mSharedPreferenceHelper.getUser().getId())
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::setProfile, this::handleNetworkError);
    }

    private void setProfile(User user) {
        mSharedPreferenceHelper.saveUser(user);
        mUser = mSharedPreferenceHelper.getUser();
        mView.setProfile(mUser.getId(),
                mUser.getName(),
                mUser.getEmail(),
                mUser.getPhoneNumber(),
                mUser.getRole(),
                mUser.getTasksCount(),
                mUser.getRewardSum());

    }*/


    @Override
    public void bindView(ProfileFragment view) {
        super.bindView(view);

        //update from
        mUser = mSharedPreferenceHelper.getUser();
        mView.setProfile(mUser.getId(),
                mUser.getName(),
                mUser.getEmail(),
                mUser.getPhoneNumber(),
                mUser.getRole(),
                mUser.getTasksCount(),
                mUser.getRewardSum());

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
        mUser.setName(mView.getUserName());
        mUser.setEmail(mView.getUserMail());
        mUser.setPhoneNumber(mView.getUserPhone());
        saveProfileToServer(mUser);
    }

    private void saveProfileToServer(User user) {
        log("saveProfileToServer()"+user);

         ApiFactory
                .getSunriseForestService()
                .updProfile(user.getId(), user)
                .compose(new AsyncNetTransformer<>())
                 .subscribe(this::saveProfileIntoPreference,this::handleNetworkError,this::saveComplete);
    }

    private void saveComplete() {
        mView.showToast("Сохранено");
        mView.saveIsVisible(false);

    }

    private void saveProfileIntoPreference(User user){
        log(String.format("saveProfileIntoPreference(User user = %s)", user));
        mSharedPreferenceHelper.saveUser(user);

    }

    private void exitProfile() {
        log("exitProfile()");
        TaskPresenter.getInstance().clearChangedTasks();
        mSharedPreferenceHelper.removeUser();
        mNavigationManager.fromProfileToLogin();
    }


    private boolean canChangeProfile() {
        log("canChangeProfile()");
        //...этот метод походу уже и не нужен, если все изначально можно редактировать
        return true;
    }

    public void refresh(){
        //todo
    }
    public void updateFromServer(){
        //todo
    }
    public void updateFromPreference(){
        //todo
    }

    public void updateUI(){
        //todo
    }
    //todo мб еще чо

    @Override
    public String createTAG() {
        return "ProfilePresenter";
    }


}

