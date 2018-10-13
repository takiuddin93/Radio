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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {


    LinearLayout tapactionlayout;
    ConstraintLayout lol;
    ImageView arrowUp, playpauseIcon;
    View bottomSheet;
    RelativeLayout.LayoutParams  tap_action_layout;

    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                }

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tapactionlayout.setVisibility(View.VISIBLE);
                }

                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    tapactionlayout.setVisibility(View.VISIBLE);
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
}
