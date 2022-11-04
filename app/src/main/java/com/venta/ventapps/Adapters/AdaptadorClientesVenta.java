package com.venta.ventapps.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.venta.ventapps.Entidades.Clientes;
import com.venta.ventapps.R;
import java.util.ArrayList;

public class AdaptadorClientesVenta extends RecyclerView.Adapter<AdaptadorClientesVenta.ViewHolderClientes>{

    ArrayList<Clientes> listClientes;
    private View.OnClickListener listener;
    private RecylerItemCLickVentas itemCLick;

    public AdaptadorClientesVenta(ArrayList<Clientes> listClientes, RecylerItemCLickVentas itemCLick) {
        this.listClientes = listClientes;
        this.itemCLick = itemCLick;
    }

    @NonNull
    @Override
    public ViewHolderClientes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listcliente_venta,null,false);
        return new ViewHolderClientes(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClientes holder, int position) {
        final Clientes item=listClientes.get(position);
        holder.id.setText(listClientes.get(position).getId()+"");
        holder.nombre.setText(listClientes.get(position).getNombre());
        holder.documento.setText(listClientes.get(position).getDocumento());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCLick.itemClickClientesVentas(item);
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
            id=itemView.findViewById(R.id.listidclienteventa);
            nombre=itemView.findViewById(R.id.listnomclienteventa);
            documento=itemView.findViewById(R.id.listdocuclienteventa);
        }
    }

    public interface RecylerItemCLickVentas{
        void itemClickClientesVentas(Clientes clientes);
    }
}
