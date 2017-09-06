package complementos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.example.clases.ItemPedido;
import com.example.clases.ListaSimple;
import com.example.restaurant.R;

public class AdapterListaConIconos extends BaseAdapter {
    private static List<ItemPedido> searchArrayList;
    private ArrayList<ItemPedido> arraylist = new ArrayList();
    private LayoutInflater mInflater;
    

    static class ViewHolder {
        TextView txtTitulo;
        NumberPicker txtNumber;
        ImageButton btnEliminar;

        ViewHolder() {
        	
        }
    }

    public AdapterListaConIconos(Context context, List<ItemPedido> results) {
        searchArrayList = results;
        this.mInflater = LayoutInflater.from(context);
        this.arraylist.addAll(results);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public ItemPedido getItem(int position) {
        return (ItemPedido) searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.item_lista_compra, null);
            holder = new ViewHolder();
            holder.txtTitulo = (TextView) convertView.findViewById(R.id.item_compra);
            holder.txtNumber = (NumberPicker) convertView.findViewById(R.id.numberPicker1);
            holder.btnEliminar = (ImageButton) convertView.findViewById(R.id.borrar_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtTitulo.setText(((ItemPedido) searchArrayList.get(position)).getDescripcionItem());
        holder.txtNumber.setValue(((ItemPedido) searchArrayList.get(position)).getCantidadItem());
        
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchArrayList.clear();
        if (charText.length() == 0) {
            searchArrayList.addAll(this.arraylist);
        } else {
            Iterator it = this.arraylist.iterator();
            while (it.hasNext()) {
            	ItemPedido ls = (ItemPedido) it.next();
                if (ls.getDescripcionItem().toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchArrayList.add(ls);
                }
            }
        }
        notifyDataSetChanged();
    }
}

