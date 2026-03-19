package dev.fixfis.appmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dev.fixfis.appmascotas.entidad.Mascota;
import dev.fixfis.appmascotas.entidad.Metrics;
import dev.fixfis.appmascotas.lista.MascotasAdapter;

public class ListarCustom extends AppCompatActivity implements MascotasAdapter.OnActionListener {
    RecyclerView v;

    RequestQueue q;

    MascotasAdapter adapter;
    ArrayList<Mascota> listammascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_custom);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        q = Volley.newRequestQueue(this);
        v = findViewById(R.id.ryc);
        listammascotas = new ArrayList<>();
        adapter = new MascotasAdapter(listammascotas, this);
        v.setLayoutManager(new LinearLayoutManager(this));
        v.setAdapter(adapter);

        Metrics.valores.put("listar_custom_refresh", (Runnable) () -> {
            Toast.makeText(this,"Testeando!",Toast.LENGTH_SHORT).show();
        });
        drawData();

    }

    private void drawData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Metrics.ip + "/mascotas",
                null,
                this::renderizar,
                Throwable::printStackTrace
        );
        q.add(jsonArrayRequest);
    }

    private void renderizar(JSONArray j) {
        listammascotas.clear();
        try {
            for (int i = 0; i < j.length(); i++) {
                JSONObject jsonObject = j.getJSONObject(i);
                Mascota m = new Mascota();
                m.setId(jsonObject.getInt("id"));
                m.setNombre(jsonObject.getString("nombre"));
                m.setTipo(jsonObject.getString("tipo"));
                m.setColor(jsonObject.getString("color"));
                m.setPeso(jsonObject.getString("peso"));
                listammascotas.add(m);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDelete(int position, Mascota m) {
        adapter.eliminarItem(position);
    }

    @Override
    public void onEdit(int position, Mascota m) {
        Metrics.valores.put("mascotas_valor_seleccionado_editar",m);
        startActivity(new Intent(this, Actualizar.class));
    }
}