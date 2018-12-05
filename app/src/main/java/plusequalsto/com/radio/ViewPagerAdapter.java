package plusequalsto.com.radio;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    String[] schedule = new String[]{"Sunday", "Monday", "Tuesday", "Wednesdayt", "Thursday", "Friday" , "Saturday"};

    private int numOfTabs;

    ViewPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        Date today = new Date();
        String todayDayOfWeek = dayFormat.format(today);

        for (int i = 0; i < 7; i++) {
            if (schedule[i].equals(todayDayOfWeek)) {
                Log.d("ViewPagerAdapter", String.valueOf(i));
            }
        }

        switch (position) {
            case 0:
                return new SundayFragment();
            case 1:
                return new MondayFragment();
            case 2:
                return new TuesdayFragment();
            case 3:
                return new WednesdayFragment();
            case 4:
                return new ThursdayFragment();
            case 5:
                return new FridayFragment();
            case 6:
                return new SaturdayFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
