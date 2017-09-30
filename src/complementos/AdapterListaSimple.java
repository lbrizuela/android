package complementos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.example.clases.ListaSimple;
import com.example.restaurant.R;


public class AdapterListaSimple extends BaseAdapter {
    private static List<ListaSimple> searchArrayList;
    private ArrayList<ListaSimple> arraylist = new ArrayList();
    private LayoutInflater mInflater;

    static class ViewHolder {
        TextView txtTitulo;

        ViewHolder() {
        }
    }

    public AdapterListaSimple(Context context, List<ListaSimple> results) {
        searchArrayList = results;
        ///this.mInflater = LayoutInflater.from(context);
        this.arraylist.addAll(results);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public ListaSimple getItem(int position) {
        return (ListaSimple) searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.item_listado, null);
            holder = new ViewHolder();
            holder.txtTitulo = (TextView) convertView.findViewById(R.id.txt_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtTitulo.setText(((ListaSimple) searchArrayList.get(position)).getDescripcion());
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
                ListaSimple ls = (ListaSimple) it.next();
                if (ls.getDescripcion().toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchArrayList.add(ls);
                }
            }
        }
        notifyDataSetChanged();
    }
}

