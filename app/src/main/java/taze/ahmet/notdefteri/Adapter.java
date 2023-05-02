package taze.ahmet.notdefteri;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    ArrayList<String> list = new ArrayList<>();

    Adapter(ArrayList<String> list) {
        if (list != null) this.list = list;
    }

    public ArrayList<String> notekle(String s) {
        list.add(s);
        notifyItemInserted(list.size());
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(((LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.not, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.button.setText(list.get(position));
        holder.button.setOnClickListener(view -> view.getContext().startActivity(new Intent(view.getContext(), not_yaz.class)));
        holder.button.setOnLongClickListener(view -> true);
        holder.sil.setOnClickListener(view -> {
            int pos = holder.getLayoutPosition();
            if (pos == -1) return;
            list.remove(pos);
            notifyItemRemoved(pos);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Button button;
        ImageButton sil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setMinimumWidth(WRAP_CONTENT);
            button = itemView.findViewById(R.id.button);
            ((View)button.getParent()).setMinimumWidth(WRAP_CONTENT);
            sil = itemView.findViewById(R.id.sil);
        }
    }
}