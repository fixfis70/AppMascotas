package dev.fixfis.appmascotas.lista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.fixfis.appmascotas.R;
import dev.fixfis.appmascotas.entidad.Mascota;

public class MascotasAdapter extends RecyclerView.Adapter<MascotasAdapter.ViewModel> {
    List<Mascota> mascotas;
    private final OnActionListener onActionListener;

    public interface OnActionListener{
        void onDelete(int position,Mascota m);
        void onEdit(int position,Mascota m );
    }

    public MascotasAdapter(List<Mascota> mascotas,OnActionListener onActionListener) {
        this.mascotas = mascotas;
        this.onActionListener = onActionListener;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascota,parent,false);
        return new ViewModel(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        Mascota mascota = mascotas.get(position);
        holder.nombre.setText(mascota.getNombre());
        holder.color.setText(mascota.getColor());
        holder.peso.setText(String.valueOf(mascota.getPeso()));

        holder.edit.setOnClickListener(v-> onActionListener.onEdit(holder.getLayoutPosition(),mascota));
        holder.delete.setOnClickListener(v-> onActionListener.onDelete(holder.getLayoutPosition(),mascota));
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    public static class ViewModel extends RecyclerView.ViewHolder {
        TextView nombre, color, peso;
        Button edit, delete;
        public ViewModel(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtnombre);
            color = itemView.findViewById(R.id.txtcolor);
            peso = itemView.findViewById(R.id.txtpeso);
            edit = itemView.findViewById(R.id.btnedit);
            delete = itemView.findViewById(R.id.btndlt);
        }
    }

    public void eliminarItem(int pos){
        mascotas.remove(pos);
        notifyItemRemoved(pos    );
    }
}
