package taze.ahmet.notdefteri;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static taze.ahmet.notdefteri.MainActivity.popup;
import static taze.ahmet.notdefteri.MainActivity.sharedPreferences;

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
        int pos = holder.getLayoutPosition();
        holder.button.setText(list.get(position));
        holder.button.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), not_yaz.class);
            i.putExtra("notIsim", list.get(pos));
            view.getContext().startActivity(i);
        });
        holder.button.setOnLongClickListener(view -> popup(view.getContext(), list, "notudegistir", list.get(pos), pos));

        holder.sil.setOnClickListener(view -> {
            if (pos == -1) return;
            sharedPreferences.edit().remove(list.get(pos)).apply();
            list.remove(pos);
            notifyItemRemoved(pos);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public ArrayList<String> notdegistir(String notisim, int pos) {
        list.set(pos, notisim);
        notifyItemChanged(pos, notisim);
        return list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Button button;
        ImageButton sil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setMinimumWidth(WRAP_CONTENT);
            button = itemView.findViewById(R.id.button);
            ((View) button.getParent()).setMinimumWidth(WRAP_CONTENT);
            sil = itemView.findViewById(R.id.sil);
        }
    }
}