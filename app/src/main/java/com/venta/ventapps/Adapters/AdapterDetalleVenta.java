package com.venta.ventapps.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.venta.ventapps.Entidades.detalleVenta;
import com.venta.ventapps.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterDetalleVenta extends RecyclerView.Adapter<AdapterDetalleVenta.ViewHolderDetalleVenta>{

    ArrayList<detalleVenta> listaDetalle;

    public AdapterDetalleVenta(ArrayList<detalleVenta> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    @NonNull
    @Override
    public ViewHolderDetalleVenta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_proddetalleventa,null,false);
        return new ViewHolderDetalleVenta(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDetalleVenta holder, int position) {
        final detalleVenta item=listaDetalle.get(position);
        holder.producto.setText(listaDetalle.get(position).getNomProd());
        holder.cantidad.setText(listaDetalle.get(position).getCant()+"");
        holder.precio.setText(listaDetalle.get(position).getPrecio()+"");
        double total=listaDetalle.get(position).getCant()*listaDetalle.get(position).getPrecio();
        holder.costo.setText(total+"");
    }

    @Override
    public int getItemCount() {
        return listaDetalle.size();
    }

    public class ViewHolderDetalleVenta extends RecyclerView.ViewHolder{

        TextView producto,cantidad,precio,costo;

        public ViewHolderDetalleVenta(View itemView) {
            super(itemView);
            producto=itemView.findViewById(R.id.prodDetalleVenta);
            cantidad=itemView.findViewById(R.id.cantidadDetalleVenta);
            precio=itemView.findViewById(R.id.precioUDetalleVenta);
            costo=itemView.findViewById(R.id.totalDetalleVenta);
        }
    }
}
