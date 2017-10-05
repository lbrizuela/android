package com.example.restaurant;





import java.util.ArrayList;

import com.example.clases.Seccion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
	
	
	private int numTabs;
    private ArrayList<Seccion> secciones;
  

    public PagerAdapter(FragmentManager fragmentManager, int numero , ArrayList<Seccion> secciones ) {
        super(fragmentManager);
        this.numTabs = numero ;
        this.secciones = secciones;
      
    }

    @Override
    public Fragment getItem(int position) {
        
        ItemFragment tab = ItemFragment.newInstance(secciones.get(position));
        return tab;
        
    }

    @Override
    public int getCount() {
        return numTabs ;
    }
    
    
	

}
