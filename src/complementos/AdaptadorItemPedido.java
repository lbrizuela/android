package complementos;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.clases.ItemPedido;
import com.example.clases.ListaSimple;
import com.example.clases.Mesa;
import com.example.restaurant.R;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;



 
public class AdaptadorItemPedido extends Adapter<AdaptadorItemPedido.ViewHolder> {
 
    private ArrayList<ItemPedido> datos;
    private Context context;
    private int resource;
    private AdapterCallback mAdapterCallback;
 
    public AdaptadorItemPedido(Context context, ArrayList<ItemPedido> datos) {
        this.context = context;
        this.datos = datos;
        try {
            this.mAdapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }
 
    public static interface AdapterCallback {
        void onMethodCallback();
    }
 
 
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    	ItemPedido os = datos.get(position);
        holder.bindView(os);
    }
 
 
    /**VIEWHOLDER*/
    class ViewHolder extends RecyclerView.ViewHolder{
 
        private TextView tv_texto, tv_cantidad, tv_precio;
        private ImageButton img_borrar_item;
        private ProgressBar progress_borrar;
        private View item;
 
        public ViewHolder(View itemView) {
            super(itemView);
            this.item = itemView;
        }
 
        public void bindView(ItemPedido os){
 
            tv_texto = (TextView) item.findViewById(R.id.item_compra);
            tv_cantidad = (TextView) item.findViewById(R.id.item_compra_cantidad);
            tv_precio = (TextView) item.findViewById(R.id.item_compra_precio);
            img_borrar_item = (ImageButton) item.findViewById(R.id.borrar_item);
            progress_borrar =(ProgressBar) item.findViewById(R.id.progress_borrar_item);
            
            
            if(os.getArticulo() !=null){
                tv_texto.setText(String.valueOf(os.getArticulo().getNombre()));
             }else{
             	 tv_texto.setText(String.valueOf(os.getOferta().getNombre()));
             }
            
            tv_cantidad.setText("Cantidad: " +" " + String.valueOf(os.getCantidad()));
            tv_precio.setText("Precio: $ " + String.valueOf(os.getPrecioUnitario()));
 
            
 
 
            /**Selecciona/deselecciona un ítem si está activado el modo selección*/
            img_borrar_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	img_borrar_item.setVisibility(View.GONE);
                	progress_borrar.setVisibility(View.VISIBLE);
                	borrarItem(getAdapterPosition());
                }
            });
        }
    }
 
 
    /**Devuelve aquellos objetos marcados.*/
    public void borrarItem(int posicion){
    	
       ItemPedido itemPedidoBorrado = datos.get(posicion);
       datos.remove(itemPedidoBorrado);
       notifyItemRemoved(posicion);
       notifyItemRangeChanged(posicion, getItemCount() - posicion);
       try {
           mAdapterCallback.onMethodCallback();
       } catch (ClassCastException exception) {
          // do something
       }
    }
    
    

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return datos.size();
	}




	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_lista_compra, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
	
	
	
	
	
}