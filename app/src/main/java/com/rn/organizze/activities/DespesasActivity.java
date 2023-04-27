package com.rn.organizze.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rn.organizze.R;
import com.rn.organizze.helper.DateCustom;
import com.rn.organizze.model.Movimentacao;

public class DespesasActivity extends AppCompatActivity {
    private TextInputEditText editData, editCategoria, editDescricao;
    private EditText editValor;
    private Movimentacao movimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        editData        = findViewById(R.id.editDataDespesas);
        editCategoria   = findViewById(R.id.editCategoriaDespesas);
        editDescricao   = findViewById(R.id.editDescricaoDespesas);
        editValor       = findViewById(R.id.editValorDespesas);

        //Preenche o campo data com a date atual:
        editData.setText(DateCustom.dataAtual());
    }

    public void salvarDespesa(View view){
        String campoValor = editValor.getText().toString();
        String campoData = editData.getText().toString();
        String campoCategoria = editCategoria.getText().toString();
        String campoDescricao = editDescricao.getText().toString();

        if(validarCampos(campoData, campoCategoria, campoDescricao, campoValor)){
            String data = campoData;
            movimentacao = new Movimentacao();
            movimentacao.setData(campoData);
            movimentacao.setCategoria(campoCategoria);
            movimentacao.setDescricao(campoDescricao);
            movimentacao.setValor(Double.parseDouble(campoValor));
            movimentacao.setTipo("d");

            movimentacao.salvar(data);
        }

    }


    public boolean validarCampos(String campoData, String campoCategoria, String campoDescricao, String campoValor){
        boolean camposValidados = false;

        if(!campoData.isEmpty() && campoData != null){
            if(!campoCategoria.isEmpty() && campoCategoria != null){
                if(!campoDescricao.isEmpty() && campoDescricao != null){
                    if(!campoValor.isEmpty() && campoValor != null){
                        camposValidados = true;
                    }else{
                        mostrarToast("Problema com o valor:/");
                    }
                }else{
                    mostrarToast("Bote a descrição:)");
                }
            }else{
                mostrarToast("Bote a categoria:)");
            }
        }else{
            mostrarToast("Problema com a data:/");
        }

        return camposValidados;
    }

    public void mostrarToast(String mensagem){
        Toast.makeText(getApplicationContext(),
                mensagem, Toast.LENGTH_LONG).show();
    }


}