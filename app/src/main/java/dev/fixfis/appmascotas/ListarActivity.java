package dev.fixfis.appmascotas;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import dev.fixfis.appmascotas.entidad.Metrics;

public class ListarActivity extends AppCompatActivity {
    ListView lv;

    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lv = findViewById(R.id.listview);
        queue = Volley.newRequestQueue(this);

        dataget();

    }

    private void dataget() {
        JsonArrayRequest js = new JsonArrayRequest(
                Request.Method.GET,
                Metrics.ip+"/mascotas",
                null,
                this::loadView,
                err -> {

                }
        );
        queue.add(js);
    }

    private void loadView(JSONArray rs) {


    }


}