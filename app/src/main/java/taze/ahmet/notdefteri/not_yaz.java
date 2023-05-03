package taze.ahmet.notdefteri;

import static taze.ahmet.notdefteri.MainActivity.sharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class not_yaz extends AppCompatActivity {

    String notIsim;
    EditText notyaz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_yaz);
        notIsim = getIntent().getStringExtra("notIsim");
        notyaz = findViewById(R.id.not);
        String not = sharedPreferences.getString(notIsim, "");
        if (!not.equals("")) notyaz.setText(not);
    }

    public void kaydet(View view) {
        sharedPreferences.edit().putString(notIsim, notyaz.getText().toString()).apply();
        finish();
    }
}

