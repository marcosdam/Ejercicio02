package com.marcosledesma.ejercicio02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marcosledesma.ejercicio02.modelos.Nota;

public class CrearNota extends AppCompatActivity {
    private EditText txtTitulo, txtContenido;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota);
        txtTitulo = findViewById(R.id.txtTituloCrearNota);
        txtContenido = findViewById(R.id.txtContenidoCrearNota);
        btnGuardar = findViewById(R.id.btnGuardarCrearNota);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CREAR Nota, meterlo en un bundle, meterlo en un intent, resultcode
                if(!txtTitulo.getText().toString().isEmpty() && !txtContenido.getText().toString().isEmpty()){
                    Nota nota = new Nota(txtTitulo.getText().toString(), txtContenido.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("NOTA", nota);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}