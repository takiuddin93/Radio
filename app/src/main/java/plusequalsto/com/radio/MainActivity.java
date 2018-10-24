package plusequalsto.com.radio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    LinearLayout tapactionlayout;
    ConstraintLayout lol;
    ImageView arrowUp, playpauseIcon;
    View bottomSheet;
    RelativeLayout.LayoutParams  tap_action_layout;

    private BottomSheetBehavior mBottomSheetBehavior;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager mLayoutManager;

    private ArrayList<Model> list;
    private RecyclerViewAdapter adapter;

    private String baseURL = "http://www.plusequalsto.com/radio/";
    public static List<Test> mSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrowUp = (ImageView) findViewById(R.id.arrowUp);
        playpauseIcon = (ImageView) findViewById(R.id.playpause);
        tapactionlayout = (LinearLayout) findViewById(R.id.tap_action_layout);
        bottomSheet = (View) findViewById(R.id.bottom_sheet);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        lol = (ConstraintLayout) findViewById(R.id.lol);

        ViewTreeObserver vto = lol.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                lol.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalHeight = lol.getMeasuredHeight();
                int finalWidth = lol.getMeasuredWidth();

                Toast.makeText(MainActivity.this, finalHeight + " " + finalWidth, LENGTH_LONG ).show();
                return true;
            }
        });

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(158);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    tapactionlayout.setVisibility(View.VISIBLE);
                    arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    playpauseIcon.setVisibility(View.VISIBLE);

                }

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tapactionlayout.setVisibility(View.VISIBLE);
                    arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    playpauseIcon.setVisibility(View.GONE);
                }

                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    tapactionlayout.setVisibility(View.VISIBLE);
                    arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    playpauseIcon.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        list = new ArrayList<Model>();

        getRetrofit();

        adapter = new RecyclerViewAdapter(list, MainActivity.this);
        recyclerView.setAdapter(adapter);

        tapactionlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED)
                {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    playpauseIcon.setVisibility(View.GONE);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    playpauseIcon.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api service = retrofit.create(Api.class);
        Call<List<Test>> call = service.getTest();

        call.enqueue(new Callback<List<Test>>() {
            @Override
            public void onResponse(Call<List<Test>> call, Response<List<Test>> response) {
                mSchedule = response.body();
                progressBar.setVisibility(View.GONE);
                Log.d("hagu", String.valueOf(mSchedule.size()));

                for (int i = 0; i < mSchedule.size(); i++) {
                    Log.d("hagu", String.valueOf(mSchedule.get(i).getDay()));
                    list.add(new Model(Model.IMAGE_TYPE, mSchedule.get(i).getDay(), mSchedule.get(i).getShow(), mSchedule.get(i).getTime()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Test>> call, Throwable t) {
            }
        });
    }

}
