package com.example.restaurant;





import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
	
	
	private int numTabs;
    private DummyModel[] dummyModels0;
    private DummyModel[] dummyModels1;
    private DummyModel[] dummyModels2;

    public PagerAdapter(FragmentManager fragmentManager, int numTabs, DummyModel[] dummyModels0,
                        DummyModel[] dummyModels1, DummyModel[] dummyModels2) {
        super(fragmentManager);
        this.numTabs = numTabs;
        this.dummyModels0 = dummyModels0;
        this.dummyModels1 = dummyModels1;
        this.dummyModels2 = dummyModels2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ItemFragment tab1 = ItemFragment.newInstance(dummyModels0);
                return tab1;
            case 1:
                ItemFragment tab2 = ItemFragment.newInstance(dummyModels1);
                return tab2;
            case 2:
                ItemFragment tab3 = ItemFragment.newInstance(dummyModels2);
                return tab3;
            default:
                throw new RuntimeException("Tab position invalid " + position);
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
    
    
	

}
