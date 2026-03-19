package dev.fixfis.appmascotas;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.fixfis.appmascotas.entidad.Mascota;
import dev.fixfis.appmascotas.entidad.Metrics;

public class Actualizar extends AppCompatActivity {
    Mascota mascota;
    Runnable listarCustomRefresh;
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
        listarCustomRefresh = (Runnable) Metrics.valores.get("listar_custom_refresh");

        mascota = (Mascota) Metrics.valores.get("mascotas_valor_seleccionado_editar");
        if (mascota==null){
            Toast.makeText(this,"RARO!",Toast.LENGTH_SHORT).show();
        }
    }

}