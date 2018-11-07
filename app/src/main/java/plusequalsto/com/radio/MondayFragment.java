package plusequalsto.com.radio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MondayFragment extends Fragment {

    RecyclerView recyclerView;
    private ProgressBar progressBar;
    LinearLayoutManager mLayoutManager;
    ArrayList<Model> list;
    RecyclerViewAdapter adapter;

    String baseURL = "http://www.plusequalsto.com/radio/";
    List<Schedule> mSchedule;

    public MondayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View menu = inflater.inflate(R.layout.fragment_monday, container, false);

        recyclerView = (RecyclerView) menu.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) menu.findViewById(R.id.progressBar);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        list = new ArrayList<Model>();

        getRetrofit();

        adapter = new RecyclerViewAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);

        return menu;
    }

    private void getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api service = retrofit.create(Api.class);
        Call<List<Schedule>> call = service.getSchedules();

        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                mSchedule = response.body();
                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < mSchedule.size(); i++) {
                    if (mSchedule.get(i).getDay().equals("Monday")){
                        list.add(new Model(Model.IMAGE_TYPE, mSchedule.get(i).getDay(), mSchedule.get(i).getShow(), mSchedule.get(i).getTime()));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
            }
        });
    }

}
