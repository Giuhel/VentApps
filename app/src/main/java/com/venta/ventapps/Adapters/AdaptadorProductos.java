package com.venta.ventapps.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.venta.ventapps.Entidades.Productos;
import com.venta.ventapps.R;

import java.util.ArrayList;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.AdartadorViewHolder> {

    ArrayList<Productos> listproductos;
    private RecylerItemCLick itemCLick;

    public AdaptadorProductos(ArrayList<Productos> listproductos, RecylerItemCLick itemCLick) {
        this.listproductos = listproductos;
        this.itemCLick=itemCLick;
    }

    @NonNull
    @Override
    public AdartadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_productos,null,false);
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

        public AdartadorViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.listImgproducto);
            id=itemView.findViewById(R.id.listIdProducto);
            nombre=itemView.findViewById(R.id.listNomProducto);
            stock=itemView.findViewById(R.id.listStockProducto);
            cardView=itemView.findViewById(R.id.idCardproductolist);
        }
    }

    public interface RecylerItemCLick{
        void itemClick(Productos productos);
    }
}
