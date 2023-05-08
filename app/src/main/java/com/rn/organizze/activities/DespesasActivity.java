package com.rn.organizze.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.rn.organizze.R;
import com.rn.organizze.config.ConfiguracaoFirebase;
import com.rn.organizze.helper.Base64Custom;
import com.rn.organizze.helper.DateCustom;
import com.rn.organizze.model.Movimentacao;
import com.rn.organizze.model.Usuario;

public class DespesasActivity extends AppCompatActivity {
    private TextInputEditText editData, editCategoria, editDescricao;
    private EditText editValor;
    private Movimentacao movimentacao;

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double despesaTotal;

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

        recuperarDespesaTotal();
    }


    public void salvarDespesa(View view){
        String campoValor = editValor.getText().toString();
        String campoData = editData.getText().toString();
        String campoCategoria = editCategoria.getText().toString();
        String campoDescricao = editDescricao.getText().toString();

        if(validarCampos(campoData, campoCategoria, campoDescricao, campoValor)){
            String data = campoData;
            Double valorRecuperado = Double.parseDouble(campoValor);
            movimentacao = new Movimentacao();
            movimentacao.setData(campoData);
            movimentacao.setCategoria(campoCategoria);
            movimentacao.setDescricao(campoDescricao);
            movimentacao.setValor(valorRecuperado);
            movimentacao.setTipo("d");

            Double despesaAtualizada = despesaTotal + valorRecuperado;
            atualizarDespesa(despesaAtualizada);
            movimentacao.salvar(data);

            finish();
        }

    }

    private void recuperarDespesaTotal() {

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios")
                .child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

   public void atualizarDespesa(Double despesa){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("despesaTotal").setValue(despesa);

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