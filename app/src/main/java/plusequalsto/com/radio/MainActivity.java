package plusequalsto.com.radio;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    ViewPager viewPage;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout menuTabs;

    TabItem tabSunday;
    TabItem tabMonday;
    TabItem tabTuesday;
    TabItem tabWednesday;
    TabItem tabThursday;
    TabItem tabFriday;
    TabItem tabSaturday;

    private BottomSheetBehavior mBottomSheetBehavior;

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
        lol = (ConstraintLayout) findViewById(R.id.lol);

        menuTabs = (TabLayout) findViewById(R.id.menuTabs);

        tabSunday = findViewById(R.id.tabSunday);
        tabMonday = findViewById(R.id.tabMonday);
        tabTuesday = findViewById(R.id.tabTuesday);
        tabWednesday = findViewById(R.id.tabWednesday);
        tabThursday = findViewById(R.id.tabThursday);
        tabFriday = findViewById(R.id.tabFriday);
        tabSaturday = findViewById(R.id.tabSaturday);

        viewPage = (ViewPager) findViewById(R.id.viewPage);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), menuTabs.getTabCount());
        viewPage.setAdapter(viewPagerAdapter);

//        setupTabIcons();

        menuTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPage.setCurrentItem(tab.getPosition());
//                if (tab.getPosition() == 1) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
//                    menuTabs.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
//                    }
//                } else if (tab.getPosition() == 2) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray));
//                    menuTabs.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray));
//                    }
//                } else if (tab.getPosition() == 3) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
//                    menuTabs.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
//                    }
//                } else if (tab.getPosition() == 4) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
//                    menuTabs.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
//                    }
//                } else if (tab.getPosition() == 5) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray));
//                    menuTabs.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray));
//                    }
//                } else if (tab.getPosition() == 6) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
//                    menuTabs.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
//                    }
//                } else {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
//                    menuTabs.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
//                    }
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(menuTabs));

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
        mBottomSheetBehavior.setPeekHeight(168);
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
