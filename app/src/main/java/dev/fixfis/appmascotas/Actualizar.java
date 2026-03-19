package dev.fixfis.appmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import dev.fixfis.appmascotas.entidad.Mascota;
import dev.fixfis.appmascotas.entidad.Metrics;

public class Actualizar extends AppCompatActivity {
    Mascota mascota;
    Runnable listarCustomRefresh;

    EditText tipo, nombre, color, peso;
    TextView id;
    Button save;

    RequestQueue q;
    private void loadUi() {
        tipo = findViewById(R.id.modificar_tipo);
        nombre = findViewById(R.id.modificar_nombre);
        color = findViewById(R.id.modificar_color);
        peso = findViewById(R.id.modificar_peso);

        id = findViewById(R.id.modificar_id);

        save = findViewById(R.id.modificar_save);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadUi();

        listarCustomRefresh = (Runnable) Metrics.valores.get("listar_custom_refresh");

        mascota = (Mascota) Metrics.valores.get("mascotas_valor_seleccionado_editar");
        if (mascota==null){
            Toast.makeText(this,"RARO!",Toast.LENGTH_SHORT).show();
        }

        initValues();

        save.setOnClickListener(v->{
            modificar();
        });

        q = Volley.newRequestQueue(this);
    }

    private void initValues() {
        id.setText("id: "+mascota.getId());
        nombre.setText(mascota.getNombre());
        color.setText(mascota.getColor());
        peso.setText(String.valueOf(mascota.getPeso()));
        tipo.setText(mascota.getTipo());
    }

    private void modificar() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre",nombre.getText().toString());
            jsonObject.put("color",color.getText().toString());
            jsonObject.put("peso",peso.getText().toString());
            jsonObject.put("tipo",tipo.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = Metrics.ip + "/mascotas/" + mascota.getId();
        Log.i("URLTEST",url);
        Log.i("BODYTEST",jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonObject,
                this::onSucces,
                Throwable::printStackTrace
        );

        q.add(jsonObjectRequest);

    }

    private void onSucces(JSONObject jsonObject) {
        try {
            Toast.makeText(this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
            finish();
            listarCustomRefresh.run();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}