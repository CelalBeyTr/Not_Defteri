package taze.ahmet.notdefteri;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("amet", MODE_PRIVATE);
        recyclerView = findViewById(R.id.liste);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ArrayList<String> list = new Gson().fromJson(sharedPreferences.getString("notlar", ""), new TypeToken<ArrayList<String>>() {
        }.getType());
        adapter = new Adapter(list);
        recyclerView.setAdapter(adapter);
    }

    public void notEkle(View view) {
        sharedPreferences.edit().putString("notlar", new Gson().toJson(adapter.notekle("testt"))).apply();
    }

    public static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


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
            return new ViewHolder(((LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.not, null));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.button.setText(list.get(position));
            holder.button.setOnClickListener(view -> view.getContext().startActivity(new Intent(view.getContext(), not_yaz.class)));
            holder.isim.setOnClickListener(view -> {

            });
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
            ImageButton isim;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                button = itemView.findViewById(R.id.button);
                sil = itemView.findViewById(R.id.sil);
                isim = itemView.findViewById(R.id.isim);
            }
        }
    }
}