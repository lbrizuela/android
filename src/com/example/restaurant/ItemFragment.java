package com.example.restaurant;

import java.util.ArrayList;

import com.example.clases.Articulo;
import com.example.clases.Seccion;

import complementos.ItemRecyclerViewAdapter;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ItemFragment extends Fragment {



    private static final String KEY_MODEL = "KEY_MODEL";
    private static Seccion seccion;
    private ArrayList<Articulo> articulos;

///    private DummyModel[] dummyModels;
    private OnListFragmentInteractionListener interactionListener;

    public ItemFragment() {
    }

    /**
     * Receive the model list
     */
    public static ItemFragment newInstance(Seccion seccionLlegada) {
        ItemFragment fragment = new ItemFragment();
        seccion = seccionLlegada;
       
        /*Bundle args = new Bundle();
        args.p;
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ////seccion = (DummyModel[]) getArguments().getParcelableArray(KEY_MODEL);
        articulos = seccion.getArticulos();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_articulo, container, false);

       ///// Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_la_cardList);
        recyclerView.setHasFixedSize(true);
        
        recyclerView.setAdapter(new ItemRecyclerViewAdapter(articulos, interactionListener));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }



	
	  public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		  super.onAttach(activity);
	        try {
	        	interactionListener = (OnListFragmentInteractionListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement OnFragmentInteractionListener");
	        }
	}




	public interface OnListFragmentInteractionListener {
	       void onListFragmentInteraction(Articulo item);
	   }
		

	
    




   @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    
    
    
 
}
