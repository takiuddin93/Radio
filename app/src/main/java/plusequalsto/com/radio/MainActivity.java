package plusequalsto.com.radio;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_SHORT;
import static plusequalsto.com.radio.NetworkState.isOnline;

public class MainActivity extends AppCompatActivity {

    LinearLayout tapactionlayout;
    ConstraintLayout lol;
    ImageView arrowUp, playpause;
    View bottomSheet;
    RelativeLayout.LayoutParams  tap_action_layout;

    private BottomSheetBehavior mBottomSheetBehavior;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager mLayoutManager;

    private ArrayList<Model> list;
    private RecyclerViewAdapter adapter;

    private String baseURL = "http://www.plusequalsto.com/radio/";
    public static List<Schedule> mSchedule;

    private RadioService mRadioService;
    private boolean mBoundToRadioService;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Because we have bound to an explicit
            // service that is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            RadioService.RadioBinder binder = (RadioService.RadioBinder) service;
            mRadioService = binder.getService();
            mBoundToRadioService = true;
            if (mRadioService.isPlaying()) {
                playpause.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
            } else {
                playpause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
//                mRadioService.hideNotification();
            }
        }
        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            mBoundToRadioService = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrowUp = (ImageView) findViewById(R.id.arrowUp);
        tapactionlayout = (LinearLayout) findViewById(R.id.tap_action_layout);
        bottomSheet = (View) findViewById(R.id.bottom_sheet);
        playpause = (ImageView) findViewById(R.id.playpause);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        lol = (ConstraintLayout) findViewById(R.id.lol);

        ViewTreeObserver vto = lol.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                lol.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalHeight = lol.getMeasuredHeight();
                int finalWidth = lol.getMeasuredWidth();
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
                    playpause.setVisibility(View.VISIBLE);

                }

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tapactionlayout.setVisibility(View.VISIBLE);
                    arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    playpause.setVisibility(View.GONE);
                }

                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    tapactionlayout.setVisibility(View.VISIBLE);
                    arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    playpause.setVisibility(View.GONE);
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
                    playpause.setVisibility(View.GONE);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    playpause.setVisibility(View.VISIBLE);
                }
            }
        });
        setupPlaybackToolbar();
    }

    private void getRetrofit() {
        Date today = new Date();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        final String todayDayOfWeek = dayFormat.format(today);
        Log.d("todayDayOfWeek", todayDayOfWeek);

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
                    if (mSchedule.get(i).getDay().equals(todayDayOfWeek)){
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

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, RadioService.class);
        startService(intent);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
//    if (mBackStack.peek() != R.id.visualizer){
//        updateShowNamePlaybackToolbar();
//    }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // if the stream is playing, then stop the notification
        if (mBoundToRadioService && mRadioService.isPlaying()) {
            playpause.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
            Toast.makeText(MainActivity.this, "pause", LENGTH_SHORT).show();
        } else {
            playpause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
            Toast.makeText(MainActivity.this, "play", LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        // if the stream is playing, then start the notification
        if (mBoundToRadioService && mRadioService.isPlaying())
        // unbind the radio service
        unbindService(mConnection);
        mBoundToRadioService = false;
    }

    private void setupPlaybackToolbar() {
        playpause.setOnClickListener(togglePlayPauseClickListener());
    }

    private View.OnClickListener togglePlayPauseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadioService.isPlaying()) {
                    mRadioService.pause();
                    playpause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                    Toast.makeText(MainActivity.this, "play", LENGTH_SHORT).show();
//                    mRadioService.hideNotification();
                } else {
                    if (isOnline(MainActivity.this)) {
                        if (!mRadioService.isLoading()) {
                            if (mRadioService.isLoaded()) {
                                playpause.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                                Toast.makeText(MainActivity.this, "pause", LENGTH_SHORT).show();
                                playpause.clearAnimation();
                            } else {

                            }
                            mRadioService.setRunOnStreamPrepared(new Runnable() {
                                @Override
                                public void run() {
                                    playpause.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                                    Toast.makeText(MainActivity.this, "pause", LENGTH_SHORT).show();
                                    playpause.clearAnimation();
                                }
                            });
                            mRadioService.play();
                        }
                    } else {

                    }
                }
            }
        };
    }

}
