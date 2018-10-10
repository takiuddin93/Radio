package plusequalsto.com.radio;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    private BottomSheetBehavior mBottomSheetBehavior;
    ConstraintLayout tapactionlayout;
    ImageView arrowUp, playpauseIcon;
    View bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomSheet = findViewById(R.id.bottom_sheet);
        tapactionlayout = (ConstraintLayout) findViewById(R.id.tap_action_layout);
        arrowUp = (ImageView) findViewById(R.id.arrowUp);
        playpauseIcon = (ImageView) findViewById(R.id.playpauseIcon);
        final ConstraintLayout.LayoutParams  tap_action_layout = (ConstraintLayout.LayoutParams) playpauseIcon.getLayoutParams();
//        View view_instance = (View)findViewById(R.id.playpauseIcon);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(250);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    tapactionlayout.setVisibility(View.VISIBLE);
                    arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    playpauseIcon.requestLayout();
                    playpauseIcon.getLayoutParams().height  = 200;
                    playpauseIcon.getLayoutParams().width  = 200;
                    tap_action_layout.setMargins(83, 8, 8, 0);
                    playpauseIcon.setScaleType(ImageView.ScaleType.FIT_XY);
                }

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tapactionlayout.setVisibility(View.VISIBLE);
                    playpauseIcon.requestLayout();
                    playpauseIcon.getLayoutParams().height  = 500;
                    playpauseIcon.getLayoutParams().width  = 500;
                    tap_action_layout.setMargins(20, 500, 20, 20);
                    playpauseIcon.setScaleType(ImageView.ScaleType.FIT_XY);
                }

                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    tapactionlayout.setVisibility(View.VISIBLE);
                    arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
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
                    playpauseIcon.getLayoutParams().height  = 500;
                    playpauseIcon.getLayoutParams().width  = 500;
                    tap_action_layout.setMargins(20, 500, 20, 20);
                    playpauseIcon.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    arrowUp.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    playpauseIcon.getLayoutParams().height  = 200;
                    playpauseIcon.getLayoutParams().width  = 200;
                    tap_action_layout.setMargins(83, 8, 8, 0);
                    playpauseIcon.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });

    }
}
