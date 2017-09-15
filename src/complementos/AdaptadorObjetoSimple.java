package complementos;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import android.widget.TextView;



 
public class AdaptadorObjetoSimple extends Adapter<AdaptadorObjetoSimple.ViewHolder> {
 
    private ArrayList<Mesa> datos;
    private Context context;
    private int resource;
    private boolean modoSeleccion;
    private SparseBooleanArray seleccionados;
 
    public AdaptadorObjetoSimple(Context context, ArrayList<Mesa> datos) {
        this.context = context;
        this.datos = datos;
        seleccionados = new SparseBooleanArray();
    }
 
   
 
 
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mesa os = datos.get(position);
        holder.bindView(os);
    }
 
 
    /**VIEWHOLDER*/
    class ViewHolder extends RecyclerView.ViewHolder{
 
        private TextView tv_texto;
        private View item;
 
        public ViewHolder(View itemView) {
            super(itemView);
            this.item = itemView;
        }
 
        public void bindView(Mesa os){
 
            tv_texto = (TextView) item.findViewById(R.id.txt_item_mesas);
            tv_texto.setText("Numero Mesa: "+String.valueOf(os.getNroMesa()));
 
            //Selecciona el objeto si estaba seleccionado
            if (seleccionados.get(getAdapterPosition())){
                item.setSelected(true);
            } else{
                item.setSelected(false);
            }
 
 
            /**Selecciona/deselecciona un ítem si está activado el modo selección*/
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   if (modoSeleccion) {
                        if (!v.isSelected()) {
                        	v.setSelected(true);
                            seleccionados.put(getAdapterPosition(), true);
                            
                        } else {
                        	v.setSelected(false);
                            seleccionados.put(getAdapterPosition(), false);
                          //  if (!haySeleccionados())
                               /// modoSeleccion = false;
                        }
                  ////  }
                }
            });
        }
    }
 
    public boolean haySeleccionados() {
        for (int i = 0; i <= datos.size(); i++) {
            if (seleccionados.get(i))
                return true;
        }
        return false;
    }
 
    /**Devuelve aquellos objetos marcados.*/
    public LinkedList<Mesa> obtenerSeleccionados(){
        LinkedList<Mesa> marcados = new LinkedList<Mesa>();
        for (int i = 0; i < datos.size(); i++) {
            if (seleccionados.get(i)){
                marcados.add(datos.get(i));
            }
        }
        return marcados;
    }

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return datos.size();
	}




	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_listado_mesas, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
	
	
	
}