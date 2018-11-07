package plusequalsto.com.radio;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    ViewPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
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
