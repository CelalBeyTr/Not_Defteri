package taze.ahmet.notdefteri;

import android.content.Context;
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
    public static SharedPreferences sharedPreferences;
    Adapter adapter;

    public static boolean popup(Context context, ArrayList<String> list, String methodisim, String text, int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.popup, null);
        builder.setView(v);
        AlertDialog ad = builder.create();
        ad.setCanceledOnTouchOutside(false);
        ad.show();
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText isimYaz = v.findViewById(R.id.isim);
        Button iptal = v.findViewById(R.id.iptal);
        Button onayla = v.findViewById(R.id.onayla);
        isimYaz.setText(text);
        iptal.setOnClickListener(view -> ad.dismiss());
        onayla.setOnClickListener(view -> {
            String s = isimYaz.getText().toString();
            if (!s.isEmpty() || list.contains(s)) {
                try {
                    Class.forName(context.getClass().toString().substring(6)).getMethod(methodisim, String.class, Integer.class).invoke(context, s, pos);
                } catch (IllegalAccessException | InvocationTargetException |
                         ClassNotFoundException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                ad.dismiss();
            } else Toast.makeText(context, "isim bos veya onceki notlarla ayni olamaz!", Toast.LENGTH_LONG).show();
        });
        return true;
    }

    public void notudegistir(String notisim, Integer pos) { // Not ekleme butonuna tıklandığında çalışacak fonksiyon
        String not = sharedPreferences.getString(adapter.list.get(pos), "");
        sharedPreferences.edit().putString("notlarım", new Gson().toJson(adapter.notdegistir(notisim, pos))).apply(); // Adapter'a yeni not eklenir ve notlar Shared Preferences'a yazılır.
        Log.e("testtest", adapter.list.get(pos) + "\n" + not);
        sharedPreferences.edit().remove(adapter.list.get(pos)).apply();
        sharedPreferences.edit().putString(notisim, not).apply();

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
        popup(this, adapter.list, "notuEkle", "", 0);

    }

    public void notuEkle(String notisim, Integer ignored) { // Not ekleme butonuna tıklandığında çalışacak fonksiyon
        sharedPreferences.edit().putString("notlarım", new Gson().toJson(adapter.notekle(notisim))).apply(); // Adapter'a yeni not eklenir ve notlar Shared Preferences'a yazılır.

    }

}