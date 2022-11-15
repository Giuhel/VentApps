package com.venta.ventapps.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.venta.ventapps.Entidades.Ventaa;
import com.venta.ventapps.R;

import java.util.ArrayList;

public class AdapterListaVentas extends RecyclerView.Adapter<AdapterListaVentas.ViewHolderlistVentas> {

    ArrayList<Ventaa> listaVentas;
    private RecylerItemCLick itemCLick;

    public AdapterListaVentas(ArrayList<Ventaa> listaVentas, RecylerItemCLick itemCLick) {
        this.listaVentas = listaVentas;
        this.itemCLick = itemCLick;
    }

    @NonNull
    @Override
    public ViewHolderlistVentas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ventaas,null,false);
        return new ViewHolderlistVentas(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderlistVentas holder, int position) {
        final Ventaa item=listaVentas.get(position);
        holder.numerov.setText(listaVentas.get(position).getNumV());
        holder.fecha.setText(listaVentas.get(position).getFecha());
        holder.monto.setText(listaVentas.get(position).getMonto()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCLick.itemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaVentas.size();
    }

    public class ViewHolderlistVentas extends RecyclerView.ViewHolder {

        TextView numerov,fecha,monto;

        public ViewHolderlistVentas(View itemView) {
            super(itemView);
            numerov=itemView.findViewById(R.id.listventanum);
            fecha=itemView.findViewById(R.id.listventaFecha);
            monto=itemView.findViewById(R.id.listVentaTotal);
        }
    }

    public interface RecylerItemCLick{
        void itemClick(Ventaa ventas);
    }
}
