package complementos;

import com.example.restaurant.DummyModel;
import com.example.restaurant.ItemFragment;
import com.example.restaurant.R;

import android.content.DialogInterface.OnClickListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private final DummyModel[] dummyModels;
    private final ItemFragment.OnListFragmentInteractionListener interactionListener;
    

    public ItemRecyclerViewAdapter(DummyModel[] items, ItemFragment.OnListFragmentInteractionListener listener) {
        dummyModels = items;
        interactionListener = listener;
      
    
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listado, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position ) {
        DummyModel dm = dummyModels[position];
        holder.dummyModelItem = dm;
        holder.titleView.setText(dm.getTitle());
        
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != interactionListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    interactionListener.onListFragmentInteraction(holder.dummyModelItem);
                }
            }
        }); 

              
    }
    
 
    
    
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }


    @Override
    public int getItemCount() {
        return dummyModels.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        
        public final TextView titleView;
        
        public DummyModel dummyModelItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
           /// idView = (TextView) view.findViewById(R.id.id);
            titleView = (TextView) view.findViewById(R.id.txt_item);
            
          
           
        }


		
        
        
       
    }




}
