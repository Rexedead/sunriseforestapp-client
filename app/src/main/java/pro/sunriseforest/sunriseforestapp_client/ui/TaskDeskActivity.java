package pro.sunriseforest.sunriseforestapp_client.ui;

//import pro.sunriseforest.sunriseforestapp_client.presenter.PresenterManager;
//import pro.sunriseforest.sunriseforestapp_client.presenter.TaskDeskPresenter;


//
//public class TaskDeskActivity extends AppCompatActivity implements DeskFragment.OnClickSingleRowListener {
//
//    private TaskDeskPresenter mPresenter;
//
//    public static void startActivity(Context context) {
//        Intent intent = new Intent(context, TaskDeskActivity.class);
//        context.startActivity(intent);
//    }
//
//
//    View.OnClickListener mOnClickFabButton = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            mPresenter.fabAction();
//        }
//    };
//
//    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//
//                    return true;
//                case R.id.navigation_dashboard:
//                    startActivity(new Intent(TaskDeskActivity.this, ProfileActivity.class));
//                    return true;
//                case R.id.navigation_notifications:
//                    return true;
//            }
//            return false;
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.desk_fragment);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(mOnClickFabButton);
//
//        BottomNavigationView navigation = findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
//        mPresenter = (TaskDeskPresenter) PresenterManager.getInstance()
//                .getPresenter(TaskDeskPresenter.TAG);
//
//        mPresenter.bindView(this);
////        mPresenter.initTaskDeskActivity();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mPresenter.isActivityUnBunding()) {
//            mPresenter.bindView(this);
//
//            setBottomMenuItem();
//        }
//        mPresenter.update();
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mPresenter.unBindActivity();
//    }
//
//    @Override
//    public void onBackPressed() {
//        Log.i(this.getClass().getName(), "getBackStackEntryCount(): " + getSupportFragmentManager().getBackStackEntryCount());
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
//
//
//    }
//
//    public void showListTask(List<Task> tasks) {
//        String jsonListTask = jParser(tasks);
//        initFragment(DeskFragment.newInstance(jsonListTask), null);
//        Log.i(this.getClass().getName(), "getBackStackEntryCount(): " + getSupportFragmentManager().getBackStackEntryCount());
//    }
//
//    public void showSingleTask(Task task) {
//        String jsonTask = jParser(task);
//        initFragment(TaskFragment.newInstance(jsonTask), "SingleTask");
//        Log.i(this.getClass().getName(), "getBackStackEntryCount(): " + getSupportFragmentManager().getBackStackEntryCount());
//    }
//
//    private String jParser(List<Task> unparsedtask) {
//        Moshi moshi = new Moshi.Builder().build();
//        Type type = Types.newParameterizedType(List.class, Task.class);
//        JsonAdapter<List> jsonAdapter = moshi.adapter(type);
//        return jsonAdapter.toJson(unparsedtask);
//    }
//
//    private String jParser(Task unparsedtask) {
//        Moshi moshi = new Moshi.Builder().build();
//        JsonAdapter<Task> jsonAdapter = moshi.adapter(Task.class);
//        return jsonAdapter.toJson(unparsedtask);
//    }
//
//    public void initFragment(Fragment f, String backstack) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.desk_fragment, f);
//        if (backstack == null) {
//            transaction.commit();
//        } else {
//            transaction.addToBackStack(backstack);
//            transaction.commit();
//        }
//
//    }
//
//
//    private void setBottomMenuItem() {
//        BottomNavigationView mNavigation = findViewById(R.id.navigation);
//        Menu menu = mNavigation.getMenu();
//        MenuItem menuItem = menu.getItem(0);
//        menuItem.setChecked(true);
//    }
//
//    public void createNewTask() {
//        NewTaskActivity.startActivity(this);
//    }
//
//
//    @Override
//    public void onClicked(View singleRowView, int position) {
//        mPresenter.clickedSelectedTask(position);
//    }
//}
//
//
