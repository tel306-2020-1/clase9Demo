package com.example.clase9demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference();
/*
        databaseReference.child("cursos").child("inf200").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Curso curso = dataSnapshot.getValue(Curso.class);
                String texto = "Codigo: " + curso.getCodigo()
                        + " | Nombre: " + curso.getNombre();
                Log.d("infoApp",texto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


       /* databaseReference.child("cursos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Curso curso = child.getValue(Curso.class);
                    String texto = "Codigo: " + curso.getCodigo()
                            + " | Nombre: " + curso.getNombre()
                            + " | key: " + child.getKey();
                    Log.d("infoApp", texto);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

       databaseReference.child("cursosAutoInc").addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               Curso curso = dataSnapshot.getValue(Curso.class);

               String texto = "Codigo: " + curso.getCodigo()
                       + " | Nombre: " + curso.getNombre();
               Log.d("infoApp", "nuevo nodo: " + texto);
           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               Curso curso = dataSnapshot.getValue(Curso.class);
               String texto = "Codigo: " + curso.getCodigo()
                       + " | Nombre: " + curso.getNombre();
               Log.d("infoApp", "nodo editado: " + texto);
           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


    }

    public void guardarCurso(View view) {

        String nombreCurso = ((EditText) findViewById(R.id.editTextNombreCurso)).getText().toString();
        String codigoCurso = ((EditText) findViewById(R.id.editTextCodigoCurso)).getText().toString();

        Curso curso = new Curso();
        curso.setNombre(nombreCurso);
        curso.setCodigo(codigoCurso);

        //databaseReference.child("cursos/"+codigoCurso).setValue(curso)
        databaseReference.child("cursos").child(codigoCurso).setValue(curso)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("infoApp", "guardado exitosamente");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("infoApp", "onFailure", e.getCause());
                    }
                });
    }

    public void guardarCursoConAutoInc(View view) {

        String nombreCurso = ((EditText) findViewById(R.id.editTextNombreCurso)).getText().toString();
        String codigoCurso = ((EditText) findViewById(R.id.editTextCodigoCurso)).getText().toString();

        Curso curso = new Curso();
        curso.setNombre(nombreCurso);
        curso.setCodigo(codigoCurso);

        DatabaseReference dbRef = databaseReference.child("cursosAutoInc").push();
        String id = dbRef.getKey();
        Log.d("infoAppKey", id);

        dbRef.setValue(curso)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("infoApp", "guardado exitosamente");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("infoApp", "onFailure", e.getCause());
                    }
                });
    }

}