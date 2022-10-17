package com.venta.ventapps.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.venta.ventapps.Entidades.Clientes;
import com.venta.ventapps.R;

public class AdaptadorClientes extends RecyclerView.Adapter<AdaptadorClientes.ViewHolderClientes> {

    ArrayList<Clientes> listClientes;
    private View.OnClickListener listener;
    private RecylerItemCLick itemCLick;

    public AdaptadorClientes(ArrayList<Clientes> listClientes, RecylerItemCLick itemCLick) {
        this.listClientes = listClientes;
        this.itemCLick=itemCLick;
    }

    @Override
    public ViewHolderClientes onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_clientes,null,false);
        return new ViewHolderClientes(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolderClientes holder, int position) {
        final Clientes item = listClientes.get(position);
        holder.id.setText(listClientes.get(position).getId()+"");
        holder.nombre.setText(listClientes.get(position).getNombre());
        holder.documento.setText(listClientes.get(position).getDocumento());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCLick.itemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listClientes.size();
    }



    public class ViewHolderClientes extends RecyclerView.ViewHolder {

        TextView id,documento,nombre;

        public ViewHolderClientes(View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.listidcliente);
            nombre=itemView.findViewById(R.id.listnombrecliente);
            documento=itemView.findViewById(R.id.listdocumentocliente);
        }
    }

    public interface RecylerItemCLick{
        void itemClick(Clientes clientes);
    }
}
