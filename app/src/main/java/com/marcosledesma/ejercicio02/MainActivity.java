package com.marcosledesma.ejercicio02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marcosledesma.ejercicio02.modelos.Nota;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int CREAR_NOTA = 1;
    private final int EDITAR_VER_NOTA = 2;
    private LinearLayout contenedor;
    private Button btnCrear;
    private ArrayList<Nota> notas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contenedor = findViewById(R.id.contenedorMain);
        btnCrear = findViewById(R.id.btnCrearMain);

        notas = new ArrayList<>();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrearNota.class);
                startActivityForResult(intent, CREAR_NOTA);
            }
        });
    }

    // Creamos on activityResult después de añadir el intent al bundle en crear nota (btnGuardar)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        if (requestCode == CREAR_NOTA && resultCode == RESULT_OK){
            if (data != null){
                // Monto una nota con la recibida en el parcelable
                final Nota nota = data.getExtras().getParcelable("NOTA");
                // Guardo la nota en el ArrayList
                notas.add(nota);
                final int posicion = notas.size()-1;

                // PINTAR LOS ELEMENTOS DE LA VISTA CUANDO HAY NOTAS
                if (nota != null){
                    // CREAR LINEARLAYOUT HORIZONTAL (Y DENTRO TEXTVIEW Y BOTON) DESDE CODIGO
                    LinearLayout contenedorHorizontal = new LinearLayout(this);
                    contenedorHorizontal.setOrientation(LinearLayout.HORIZONTAL);
                    // TextView
                    final TextView txtNota = new TextView(this);
                    txtNota.setText(nota.getTitulo());
                    txtNota.setTextSize(20);
                    txtNota.setTextColor(Color.BLUE);

                    // Parametros layoutHorizontal (textView ocupa 3/4)
                    LinearLayout.LayoutParams paramsTXT = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3);
                    paramsTXT.setMargins(10, 10, 10, 10);
                    txtNota.setLayoutParams(paramsTXT);

                    //Boton
                    Button btnEliminar = new Button(this);
                    btnEliminar.setText("BORRAR");

                    // setOnClickListener para btnEliminar
                    btnEliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            notas.remove(posicion);
                            repintarElementos();
                        }
                    });

                    // Parámetros layoutHorizontal (btnEliminar ocupa 1/4)
                    LinearLayout.LayoutParams paramsBTN = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    paramsBTN.setMargins(10, 10, 10, 10);
                    btnEliminar.setLayoutParams(paramsBTN);

                    // CREADO UN INTENT CON LA INFO (al clicar en titulo nota me lleva a la act_EDITAR_VER_NOTA)
                    txtNota.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, EditarVerNota.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("NOTA", nota);
                            bundle.putInt("POSICION", posicion);
                            intent.putExtras(bundle);
                            //
                            startActivityForResult(intent, EDITAR_VER_NOTA);

                        }
                    });
                    // AGREGAR AL CONTENEDOR LOS ELEMENTOS CREADOS POR CÓDIGO
                    // Hacer txt y btn "hijos" del contenedorHorizontal
                    contenedorHorizontal.addView(txtNota);
                    contenedorHorizontal.addView(btnEliminar);
                    contenedor.addView(contenedorHorizontal);
                }
            }
        }

        // Actualizar o borrar la nota del ArrayList (btnGuardar y btnBorrar)
        if(requestCode == EDITAR_VER_NOTA && resultCode == RESULT_OK){
            if (data != null){
                int posicion = data.getExtras().getInt("POSICION");
                Nota nota = data.getExtras().getParcelable("NOTA");
                // Si nota está vacío lo elimino
                if (nota == null){
                    notas.remove(posicion);
                }else{
                    // Actualizamos el alumno en el ArrayList
                    notas.get(posicion).setTitulo(nota.getTitulo());
                    notas.get(posicion).setContenido(nota.getContenido());
                }
                repintarElementos();
            }
        }
    }

    // Ver todas las actualizaciones que hemos hecho
    private void repintarElementos() {
        contenedor.removeAllViews();

        for (int i = 0; i < notas.size(); i++) {
            // CREAR ELEMENTOS DE LA VISTA DESDE CODIGO
            final Nota nota = notas.get(i);

            // CREAR LINEARLAYOUT HORIZONTAL (Y DENTRO TEXTVIEW Y BOTON) DESDE CODIGO
            LinearLayout contenedorHorizontal = new LinearLayout(this);
            contenedorHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            // TextView
            TextView txtNota = new TextView(this);
            txtNota.setText(nota.getTitulo());
            txtNota.setTextSize(20);
            txtNota.setTextColor(Color.BLUE);

            // Parametros layoutHorizontal (textView ocupa 3/4)
            LinearLayout.LayoutParams paramsTXT = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3);
            paramsTXT.setMargins(10, 10, 10, 10);
            txtNota.setLayoutParams(paramsTXT);

            //Boton
            Button btnEliminar = new Button(this);
            btnEliminar.setText("BORRAR");

            // Parámetros layoutHorizontal (btnEliminar ocupa 1/4)
            LinearLayout.LayoutParams paramsBTN = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            paramsBTN.setMargins(10, 10, 10, 10);
            btnEliminar.setLayoutParams(paramsBTN);

            // CREADO UN INTENT CON LA INFO
            final int posicion = i;
            txtNota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("POSICION", posicion);
                    bundle.putParcelable("NOTA", nota);
                    Intent intent = new Intent(MainActivity.this, EditarVerNota.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, EDITAR_VER_NOTA);
                }
            });
            // AGREGAR TOD0 AL CONTENEDOR
            contenedorHorizontal.addView(txtNota);
            contenedorHorizontal.addView(btnEliminar);
            contenedor.addView(contenedorHorizontal);
        }
    }
}