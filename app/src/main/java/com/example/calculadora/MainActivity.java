package com.example.calculadora;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText resultado;
    private EditText novoNumero;
    private TextView op;
    private String operacaoPendente;
    private Double operando1 = null;
    private TextView erro;
    private static final String ESTADO_OP_PENDENTE = "OperacaoPendente";
    private static final String ESTADO_OP = "operando1";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ESTADO_OP_PENDENTE,operacaoPendente);
        if(operando1 != null){
            outState.putDouble(ESTADO_OP,operando1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        operacaoPendente = savedInstanceState.getString(ESTADO_OP_PENDENTE);
        operando1 = savedInstanceState.getDouble(ESTADO_OP);
        op.setText(operacaoPendente);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = findViewById(R.id.button1);
        Button b2 = findViewById(R.id.button2);
        Button b3 = findViewById(R.id.button3);
        Button b4 = findViewById(R.id.button4);
        Button b5 = findViewById(R.id.button5);
        Button b6 = findViewById(R.id.button6);
        Button b7 = findViewById(R.id.button7);
        Button b8 = findViewById(R.id.button8);
        Button b9 = findViewById(R.id.button9);
        Button b0 = findViewById(R.id.button0);
        Button bmais = findViewById(R.id.buttonmais);
        Button bmenos = findViewById(R.id.buttonmenos);
        Button bponto = findViewById(R.id.buttonf);
        Button bvez = findViewById(R.id.buttonx);
        Button bdiv = findViewById(R.id.buttondiv);
        Button bigual = findViewById(R.id.buttonigual);
        Button delete = findViewById(R.id.buttondelet);
        Button ac = findViewById(R.id.ac);
        novoNumero = findViewById(R.id.novonumero1);
        erro = findViewById(R.id.erro);
        erro.setText("");


        View.OnClickListener ListinerNumericos = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                novoNumero.append(b.getText().toString());
            }
        };

        View.OnClickListener ListinerAc = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText("");
                op.setText("");
                novoNumero.setText("");
                operacaoPendente = String.valueOf(0.00);
                operando1 = null;
            }
        };
        ac.setOnClickListener(ListinerAc);


        Button buttonNegar = findViewById(R.id.buttonmm);
        buttonNegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = novoNumero.getText().toString();
                if (n.length() == 0) {
                    novoNumero.setText("-");
                } else {
                    try {
                        Double valorDouble = Double.valueOf(n);
                        valorDouble *= -1;
                        novoNumero.setText(valorDouble.toString());
                    } catch (NumberFormatException e) {
                        novoNumero.setText("");
                    }
                }
            }
        });



        View.OnClickListener ListinerDelete = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String s = novoNumero.getText().toString();
               int t = s.length();
              if(t>0){
                   novoNumero.setText("");
                   novoNumero.setText(s.substring(0,t-1));
               }else{
                  novoNumero.setText("");
                  novoNumero.setText(s);
                  //erro.setText("Nada a ser deletado");
                  Toast.makeText(MainActivity.this, "Nada a ser deletado.", Toast.LENGTH_SHORT).show();
              }
            }
        };

        View.OnClickListener ListenerOperacao = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String operacao = b.getText().toString();
                String numero = novoNumero.getText().toString();


                try {
                    Double valorDouble = Double.valueOf(numero);
                    executaOperacao(valorDouble, operacao);
                    mostraResultado();

                } catch (NumberFormatException e) {
                    novoNumero.setText("");
                }
                operacaoPendente = operacao;
                op.setText(operacaoPendente);
            }
        };


        bdiv.setOnClickListener(ListenerOperacao);
        bvez.setOnClickListener(ListenerOperacao);
        bmais.setOnClickListener(ListenerOperacao);
        bmenos.setOnClickListener(ListenerOperacao);
        bigual.setOnClickListener(ListenerOperacao);
        delete.setOnClickListener(ListinerDelete);
        b0.setOnClickListener(ListinerNumericos);
        b1.setOnClickListener(ListinerNumericos);
        b2.setOnClickListener(ListinerNumericos);
        b3.setOnClickListener(ListinerNumericos);
        b4.setOnClickListener(ListinerNumericos);
        b5.setOnClickListener(ListinerNumericos);
        b6.setOnClickListener(ListinerNumericos);
        b7.setOnClickListener(ListinerNumericos);
        b8.setOnClickListener(ListinerNumericos);
        b9.setOnClickListener(ListinerNumericos);
        bponto.setOnClickListener(ListinerNumericos);
        resultado = findViewById(R.id.resultado);
        novoNumero = findViewById(R.id.novonumero1);
        op = findViewById(R.id.operacao);
    }
    private void executaOperacao(Double numero, String operacao){
        if(operando1 ==null){
            operando1 = numero;
        }else{
            if(operacaoPendente.equals("=")){
                operacaoPendente = operacao;
            }

            switch (operacaoPendente){
                case "=":
                        operando1 = numero;
                    break;
                case "/":
                    if(numero ==0) {
                        Toast.makeText(MainActivity.this, "Impossivel dividir por Zero", Toast.LENGTH_SHORT).show();
                    }else{
                        operando1/= numero;
                        break;
                    }
                case "*":
                    operando1 *= numero;
                    break;
                case "+":
                        operando1 += numero;

                    break;
                case "-":
                        operando1 -= numero;
                    break;

            }
        }
    }
    @SuppressLint("SetTextI18n")
    private void mostraResultado(){
        resultado.setText(operando1.toString());
        novoNumero.setText("");
    }




}
