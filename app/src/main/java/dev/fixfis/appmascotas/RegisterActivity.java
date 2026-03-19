package dev.fixfis.appmascotas;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.function.Consumer;

import dev.fixfis.appmascotas.entidad.Metrics;

public class RegisterActivity extends AppCompatActivity {
    EditText et_tipo, et_nombre, et_color, et_peso;
    Button et_save;
    private RequestQueue queue;

    private void loadUI() {
        et_tipo = findViewById(R.id.registrar_tipo);
        et_nombre = findViewById(R.id.registrar_nombre);
        et_color = findViewById(R.id.registrar_color);
        et_peso = findViewById(R.id.registrar_peso);

        et_save = findViewById(R.id.registrar_save);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadUI();
        queue = Volley.newRequestQueue(this);
        et_save.setOnClickListener(v -> {
            guardar();
        });
    }
    boolean isEmpty = false;

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    private void guardar() {
        String tipo = valueIfNotNull(et_tipo,this::setEmpty);
        if (!tipo.equalsIgnoreCase("perro") && !tipo.equalsIgnoreCase("gato")) {
            et_tipo.setError("No es perro ni gato");
            et_tipo.requestFocus();
            return;
        }
        String nombre = valueIfNotNull(et_nombre,this::setEmpty);
        String color = valueIfNotNull(et_color,this::setEmpty);
        int peso = Integer.parseInt(valueIfNotNull(et_peso,this::setEmpty));

        if (isEmpty) {
            isEmpty=false;
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tipo",tipo);
            jsonObject.put("nombre",nombre);
            jsonObject.put("color",color);
            jsonObject.put("peso",peso);
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Metrics.ip+"/mascotas",
                        jsonObject
                        ,
                        response -> {
                            try {
                                Toast.makeText(this,response.getString("msg"),Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        error -> {
                            // TODO: Handle error
                            Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                );
        queue.add(jsonObjectRequest);
    }

    String valueIfNotNull(EditText text, Consumer<Boolean> b){
        Editable editable = text.getText();
        if (editable == null) {
            b.accept(true);
            return "0";
        }
        String string = editable.toString();
        if (string.isEmpty()) {
            b.accept(true);
            text.setError("Falta completar este campo");
            text.requestFocus();
            return "0";
        }

        return string;
    }
}