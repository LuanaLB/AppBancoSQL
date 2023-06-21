package com.example.appbancosql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity {

    private EditText txtNome, txtIdade;
    private Button btSalvar;
    private BdSqlite bd;
    Pessoa p = new Pessoa();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        bd = new BdSqlite(this);

        txtNome = findViewById(R.id.nome);
        txtIdade = findViewById(R.id.idade);
        btSalvar = findViewById(R.id.salvar);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = criaPessoa();
                bd.inserirDados(p.getNome(),p.getIdade());
                Intent it = new Intent(getActivity(), ListActivity.class);
                startActivity(it);

            }
        });
    }

    public Context getActivity(){ return this; };

    @Override
    protected void onDestroy() {
        bd.close();
        super.onDestroy();
    }

    private Pessoa criaPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(txtNome.getText().toString());
        pessoa.setIdade(Integer.parseInt(txtIdade.getText().toString()));
        return pessoa;
    }
}