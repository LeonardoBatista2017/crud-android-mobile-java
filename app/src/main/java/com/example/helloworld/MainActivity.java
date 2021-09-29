package com.example.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private AlunoDAO dao;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.editTextNome);
        cpf = findViewById(R.id.editTextCpf);
        telefone = findViewById(R.id.editTextTelefone);
        dao = new AlunoDAO(this);
        Intent it = getIntent();
        if(it.hasExtra("aluno")){
            aluno = (Aluno) it.getSerializableExtra("aluno");
            nome.setText(aluno.getNome());
            cpf.setText(aluno.getCpf());
            telefone.setText(aluno.getTelefone());
        };
    }

    public void salvar(View view){
        if(aluno==null){
          aluno = new Aluno();
          aluno.setNome(nome.getText().toString());
          aluno.setCpf(cpf.getText().toString());
          aluno.setTelefone(telefone.getText().toString());
          long id = dao.inserir(aluno);
        Toast.makeText(this, "Aluno inserido com id"+id, Toast.LENGTH_SHORT).show();
    }else{
            aluno.setNome(nome.getText().toString());
            aluno.setCpf(cpf.getText().toString());
            aluno.setTelefone(telefone.getText().toString());
            dao.atualizar(aluno);
            Toast.makeText(this, "Aluno atualizado com sucesso", Toast.LENGTH_SHORT).show();
        }
    }

}