package com.venta.ventapps.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.venta.ventapps.Entidades.Categorias;
import com.venta.ventapps.Entidades.Clientes;
import com.venta.ventapps.R;
import com.venta.ventapps.dialogos.DialogoCrearCategoriaFragment;
import com.venta.ventapps.fragmentos.Inventario;

import java.util.ArrayList;

public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolderCategorias>  {

    ArrayList<Categorias> listCategorias;
    private View.OnClickListener listener;
    private RecylerItemCLick itemCLick;

    public AdaptadorCategorias(ArrayList<Categorias> listCategorias, RecylerItemCLick itemCLick) {
        this.listCategorias = listCategorias;
        this.itemCLick = itemCLick;
    }

    @Override
    public ViewHolderCategorias onCreateViewHolder( ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_categoria,null,false);
        return new ViewHolderCategorias(vista);
    }

    @Override
    public void onBindViewHolder( ViewHolderCategorias holder, int position) {
        final Categorias item=listCategorias.get(position);
        holder.id.setText(listCategorias.get(position).getIdcategoria()+"");
        holder.nombre.setText(listCategorias.get(position).getNomCategoria());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCLick.itemClick(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCategorias.size();
    }


    /*public interface RecylerItemCLick{
        void itemClick(Categorias categorias);
    }*/

    public class ViewHolderCategorias extends RecyclerView.ViewHolder {

        TextView id,nombre;
        CardView cardView;

        public ViewHolderCategorias(View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.txtidcat);
            nombre=itemView.findViewById(R.id.txtnomcat);
            cardView=itemView.findViewById(R.id.CardCategorialist);
        }
    }

    public interface RecylerItemCLick{
        void itemClick(Categorias categorias);
    }
}
