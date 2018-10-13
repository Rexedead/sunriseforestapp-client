package pro.sunriseforest.sunriseforestapp_client.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.models.WaitTemp;
import pro.sunriseforest.sunriseforestapp_client.server.ServerHelper;


public class BulletinBoardFragment extends Fragment implements ItemClickListener {
    private List<Task> mTaskList = new ArrayList<>();


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BulletinBoardFragment() {
        // Required empty public constructor
    }

    public static BulletinBoardFragment newInstance() {
        return new BulletinBoardFragment();
    }


    public static BulletinBoardFragment newInstance(String param1, String param2) {
        BulletinBoardFragment fragment = new BulletinBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bulletin_board_fragment, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_view_bulletin_fragment);
        RecycleTaskAdapter mRecycleTaskAdapter = new RecycleTaskAdapter(mTaskList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecycleTaskAdapter);
        mRecycleTaskAdapter.notifyDataSetChanged();
        prepareTaskData();
        return view;
    }


    private void prepareTaskData() {

        ServerHelper s = new ServerHelper();

        Task miniTask = new Task(s.getTasks().get(0).getId(), s.getTasks().get(0).getTextTask());
        mTaskList.add(miniTask);

        miniTask = new Task(s.getTasks().get(1).getId(), s.getTasks().get(1).getTextTask());
        mTaskList.add(miniTask);

        miniTask = new Task(s.getTasks().get(2).getId(), s.getTasks().get(2).getTextTask());
        mTaskList.add(miniTask);




    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view, int position) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}




