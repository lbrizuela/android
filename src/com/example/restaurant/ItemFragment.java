package com.example.restaurant;

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

    private DummyModel[] dummyModels;
    private OnListFragmentInteractionListener interactionListener;

    public ItemFragment() {
    }

    /**
     * Receive the model list
     */
    public static ItemFragment newInstance(DummyModel[] dummyModels) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(KEY_MODEL, dummyModels);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            throw new RuntimeException("You must to send a dummyModels ");
        }
        dummyModels = (DummyModel[]) getArguments().getParcelableArray(KEY_MODEL);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_articulo, container, false);

       ///// Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_la_cardList);
        recyclerView.setHasFixedSize(true);
        
        recyclerView.setAdapter(new ItemRecyclerViewAdapter(dummyModels, interactionListener));
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
	       void onListFragmentInteraction(DummyModel item);
	   }
		

	
    



    

   /* @Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		
		Context context  = activity.getApplicationContext();
		super.onAttach(activity);
        // activity must implement OnListFragmentInteractionListener
        if ( context instanceof OnListFragmentInteractionListener) {
            interactionListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    
		
	}*/

/*	@Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * <p/>
     */
    
    
    
   /* public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyModel item);
    }*/
	
}
