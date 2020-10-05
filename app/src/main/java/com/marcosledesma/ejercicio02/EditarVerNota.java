package com.marcosledesma.ejercicio02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marcosledesma.ejercicio02.modelos.Nota;

public class EditarVerNota extends AppCompatActivity {
    private EditText txtTitulo, txtContenido;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ver_nota);

        txtTitulo = findViewById(R.id.txtTituloEditarVerNota);
        txtContenido = findViewById(R.id.txtContenidoEditarVerNota);
        btnGuardar = findViewById(R.id.btnGuardarEditarVerNota);

        // Recojo la nota del bundle de la otra actividad
        final Nota nota = getIntent().getExtras().getParcelable("NOTA");
        if (nota != null) {
            final int posicion = getIntent().getExtras().getInt("POSICION");
            txtTitulo.setText(nota.getTitulo());
            txtContenido.setText(nota.getContenido());
            // Poner "título" a la actividad
            setTitle(nota.getTitulo());

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!txtTitulo.getText().toString().isEmpty() &&
                            !txtContenido.getText().toString().isEmpty()) {
                        nota.setTitulo(txtTitulo.getText().toString());
                        nota.setContenido(txtContenido.getText().toString());
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("NOTA", nota);
                        bundle.putInt("POSICION", posicion);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }
    }
}