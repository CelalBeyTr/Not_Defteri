package taze.ahmet.notdefteri;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    Adapter adapter;

    public static void popup(Activity context, String methodisim) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.popup, null);
        builder.setView(v);
        AlertDialog ad = builder.create();
        ad.setCanceledOnTouchOutside(true);
        ad.show();
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText isimYaz = v.findViewById(R.id.isim);
        Button iptal = v.findViewById(R.id.iptal);
        Button onayla = v.findViewById(R.id.onayla);
        iptal.setOnClickListener(view -> ad.dismiss());
        onayla.setOnClickListener(view -> {
            String s = isimYaz.getText().toString();
            if (!s.isEmpty()) {
                try {
                    Class.forName(context.getPackageName() + "." + context.getLocalClassName()).getMethod(methodisim, String.class).invoke(context, s);
                } catch (IllegalAccessException | InvocationTargetException |
                         ClassNotFoundException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                ad.dismiss();
            } else Toast.makeText(context, "eaikf", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("hıyararda_33", MODE_PRIVATE);// Shared Preferences oluşturuluyor
        recyclerView = findViewById(R.id.liste); // RecyclerView oluşturuluyor ve layout belirleniyor
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));  // RecyclerView oluşturuluyor ve layout belirleniyor
        ArrayList<String> list = new Gson().fromJson(  //notlari stringden ArrayListe ceviriyor
                sharedPreferences.getString("notlarım", "") // Shared Preferences'tan notlar okunuyor
                , new TypeToken<ArrayList<String>>() {
                }.getType());  //Gson stringi hangi tip nesneye cevirmesini istedgimizi belirtiyor
        Log.e("testtest", String.valueOf(list));
        adapter = new Adapter(list);// Adapter oluşturuluyor ve RecyclerView'a set ediliyor
        recyclerView.setAdapter(adapter); // Adapter oluşturuluyor ve RecyclerView'a takılıyor ediliyor


    }

    public void notEkle(View view) { // Not ekleme butonuna tıklandığında çalışacak fonksiyon
        popup(this, "notuEkle");

    }

    public void notuEkle(String notisim) { // Not ekleme butonuna tıklandığında çalışacak fonksiyon
        sharedPreferences.edit().putString("notlarım", new Gson().toJson(adapter.notekle(notisim))).apply(); // Adapter'a yeni not eklenir ve notlar Shared Preferences'a yazılır.

    }

}