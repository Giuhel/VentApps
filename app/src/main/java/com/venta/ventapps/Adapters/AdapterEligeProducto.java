package com.venta.ventapps.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.venta.ventapps.Entidades.Productos;
import com.venta.ventapps.R;

import java.util.ArrayList;

public class AdapterEligeProducto extends RecyclerView.Adapter<AdapterEligeProducto.AdartadorViewHolder>{

    ArrayList<Productos> listproductos;
    RecylerItemCLick itemCLick;

    public AdapterEligeProducto(ArrayList<Productos> listproductos, RecylerItemCLick itemCLick) {
        this.listproductos = listproductos;
        this.itemCLick=itemCLick;
    }

    @Override
    public AdartadorViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_elegirproductos,null,false);
        return new AdartadorViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdartadorViewHolder holder, int i) {
        final Productos item = listproductos.get(i);
        holder.id.setText(listproductos.get(i).getId()+"");
        holder.nombre.setText(listproductos.get(i).getNombre());
        holder.stock.setText(listproductos.get(i).getCantidad()+"");
        holder.img.setImageBitmap(listproductos.get(i).getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCLick.itemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listproductos.size();
    }


    public static class AdartadorViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView id,nombre,stock;
        CardView cardView;

        public AdartadorViewHolder(View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.eligeimgproducto);
            id=itemView.findViewById(R.id.eligeIdprod);
            nombre=itemView.findViewById(R.id.eligenomProd);
            stock=itemView.findViewById(R.id.eligeStokProd);
            cardView=itemView.findViewById(R.id.idCardEligeProd);
        }
    }

    public interface RecylerItemCLick{
        void itemClick(Productos productos);
    }
}
