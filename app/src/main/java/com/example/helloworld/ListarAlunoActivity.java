package com.example.helloworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListarAlunoActivity extends AppCompatActivity {
     private ListView listView;
     private AlunoDAO dao;
     private List<Aluno> alunos;
     private List<Aluno> alunosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);
    listView = (ListView)  findViewById(R.id.lista_alunos);
    dao = new AlunoDAO(this);
    alunos = dao.obtertodos();

        alunosFiltrados.addAll(alunos);
        ArrayAdapter<Aluno> adaptador = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1,alunosFiltrados);
        listView.setAdapter(adaptador);

        registerForContextMenu(listView);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal,menu);
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                procuraAluno(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println("Digitou"+s);
                return false;
            }
        });
        return true;
    }

    public void cadastrar(MenuItem item) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoEditar = alunosFiltrados.get(menuInfo.position);
        Intent it = new Intent(this,MainActivity.class);
        it.putExtra("aluno",alunoEditar);
        startActivity(it);
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoExcluir = alunosFiltrados.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente Deseja Excluir o Aluno?")
                .setNegativeButton("Não",null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       alunosFiltrados.remove(alunoExcluir);
                       alunos.remove(alunoExcluir);
                       dao.excluir(alunoExcluir);
                       listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void procuraAluno(String nome){
       alunosFiltrados.clear();
       for(Aluno a : alunos){
           if(a.getNome().toLowerCase().contains(nome.toLowerCase())){
               alunosFiltrados.add(a);
           }
       }

       listView.invalidateViews();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
       super.onCreateContextMenu(menu,v,menuInfo);
       MenuInflater i = getMenuInflater();
       i.inflate(R.menu.menu_contexto,menu);

    }

    @Override
    public void onResume(){
        super.onResume();
        alunos = dao.obtertodos();
        alunosFiltrados.clear();
        alunosFiltrados.addAll(alunos);
        listView.invalidateViews();
    }
}