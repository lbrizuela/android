package com.example.restaurant;





import java.util.ArrayList;

import com.example.clases.Oferta;
import com.example.clases.Seccion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
	
	
	private int numTabs;
    private ArrayList<Seccion> secciones;
    private ArrayList<Oferta> ofertas;
  

    public PagerAdapter(FragmentManager fragmentManager, int numero , ArrayList<Oferta> oferta, ArrayList<Seccion> secciones ) {
        super(fragmentManager);
        this.numTabs = numero ;
        this.secciones = secciones;
        this.ofertas= oferta;
      
    }

    @Override
    public Fragment getItem(int position) {
    	if(ofertas !=null && ofertas.size() > 0){
    		if(position == 0){
    			ItemFragmentOferta tab = ItemFragmentOferta.newInstance(ofertas);
    			return tab;
    			
    		}else {
    			
    			ItemFragment tab = ItemFragment.newInstance(secciones.get(position - 1));
    	         return tab;
    		}
    		
    		
    	}else{
        
           ItemFragment tab = ItemFragment.newInstance(secciones.get(position));
           return tab;
    	}
        
        
    }

    @Override
    public int getCount() {
        return numTabs ;
    }
    
    
	

}
