package taze.ahmet.notdefteri;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         sharedPreferences = getSharedPreferences("hıyararda_33" , MODE_PRIVATE);// Shared Preferences oluşturuluyor

        RecyclerView recyclerView = findViewById(R.id.liste); // RecyclerView oluşturuluyor ve layout belirleniyor
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));  // RecyclerView oluşturuluyor ve layout belirleniyor



       ArrayList<String>  list =  new Gson().fromJson(sharedPreferences.getString("notlarım" , ""), new TypeToken<ArrayList<String>>(){}.getType());   // Shared Preferences'tan notlar okunuyor

             // Adapter oluşturuluyor ve RecyclerView'a set ediliyor

          // Adapter oluşturuluyor ve RecyclerView'a set ediliyor
    }



    public void notEkle(View view) { // Not ekleme butonuna tıklandığında çalışacak fonksiyon
        EditText editText = findViewById(R.id.edit_text12); // XML dosyasında tanımladığınız EditText nesnesinin referansı alınır.
        String yeniNot = editText.getText().toString(); // Kullanıcının girdiği not alınır.
        adapter.notekle(yeniNot); // Adapter'a yeni not eklenir.
        sharedPreferences.edit().putString("notlarım", new Gson().toJson(adapter.list)).apply(); // Eklenen notlar Shared Preferences'a yazılır.
    }


    public static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements taze.ahmet.notdefteri.Adapter {


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