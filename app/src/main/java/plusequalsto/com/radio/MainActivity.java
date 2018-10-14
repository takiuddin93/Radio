package plusequalsto.com.radio;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

    ListView listView;

    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSchedule();
        tapactionlayout = (LinearLayout) findViewById(R.id.tap_action_layout);
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

        arrowUp = (ImageView) findViewById(R.id.arrowUp);
        playpauseIcon = (ImageView) findViewById(R.id.playpause);
        bottomSheet = findViewById(R.id.bottom_sheet);
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

    private void getSchedule() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Schedule>> call = api.getSchedules();

        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                List<Schedule> scheduleList = response.body();

                //Creating an String array for the ListView
                String[] schedules = new String[scheduleList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < scheduleList.size(); i++) {
                    schedules[i] = scheduleList.get(i).getDay();
                }


                //displaying the string array into listview
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, schedules));

            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
