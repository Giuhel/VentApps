package com.venta.ventapps.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.venta.ventapps.Actividades.ventas;
import com.venta.ventapps.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.venta.ventapps.Entidades.detalleVenta;

import java.util.ArrayList;

public class AdapterProdAgregados extends RecyclerView.Adapter<AdapterProdAgregados.AdapterViewHolder> {

    ArrayList<detalleVenta> listDetalle;
    RecylerItemCLick itemCLick;

    public AdapterProdAgregados(ArrayList<detalleVenta> listDetalle, RecylerItemCLick itemCLick) {
        this.listDetalle = listDetalle;
        this.itemCLick = itemCLick;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_prod_agregados,null,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int i) {
        final detalleVenta item=listDetalle.get(i);
        holder.id.setText(listDetalle.get(i).getIdProd()+"");
        holder.nom.setText(listDetalle.get(i).getNomProd());
        holder.cant.setText(listDetalle.get(i).getCant()+"");
        holder.monto.setText((listDetalle.get(i).getCant()*listDetalle.get(i).getPrecio())+"");

        holder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventas.accion="borrar";
                itemCLick.itemClickProdAgregados(item);
            }
        });
        holder.menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventas.accion="disminuye";
                itemCLick.itemClickProdAgregados(item);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listDetalle.size();
    }


    public static class AdapterViewHolder extends RecyclerView.ViewHolder{

        TextView id,nom,cant,monto;
        ImageButton borrar,menos;

        public AdapterViewHolder(@NonNull View vista) {
            super(vista);

            id=vista.findViewById(R.id.agregaIdprod);
            nom=vista.findViewById(R.id.agreganomProd);
            cant=vista.findViewById(R.id.Agregacant);
            monto=vista.findViewById(R.id.agregaMonto);
            borrar=vista.findViewById(R.id.aregaEliminatodo);
            menos=vista.findViewById(R.id.AgregaQuitar);
        }
    }

    public interface RecylerItemCLick{
        void itemClickProdAgregados(detalleVenta detalle);
    }
}
