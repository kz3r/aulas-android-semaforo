package com.example.cristopher.semaforo;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Constante para tempo de repetição da thread
    private final int LIGHTS_DELAY = 1500;



    //Controle de status do semáforo
    private int lightStatus = 1;

    private Handler handler = new Handler();
    private TextView redLight, yellowLight, greenLight;
    private Button Start, Stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Start = (Button) findViewById(R.id.btnStart);
        Stop = (Button) findViewById(R.id.btnStop);

        redLight = (TextView) findViewById(R.id.txtRed);
        yellowLight = (TextView) findViewById(R.id.txtYellow);
        greenLight = (TextView) findViewById(R.id.txtGreen);


        /* Ação dos dois botões(Start e Stop) criando um listener para sua função de onClick
            Start setta o status do controle do semaforo para 1 e inicializa o handler com a função
            runnable declarada logo mais abaixo

            Stop cancela qualquer chamada enfileira do handler e desliga todas as luzes
         */
        Start.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                lightStatus = 1;
                handler.post(lightsOn);
                Start.setEnabled(false);
                Stop.setEnabled(true);
            }
        });

        Stop.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                lightStatus = 1;
                lightsOff();
                handler.removeCallbacks(lightsOn);
                Start.setEnabled(true);
                Stop.setEnabled(false);
            }
        });

    }

    /*
        Thread que controla o acendimento das luzes de acordo com o status do semáforo
        Cada status corresponde a um estado. A thread começa desligando as luzes que podem ter sido
        previamente acionadas, ligando a luz do estado atual, e settando um novo status para a
        proxima a ser acesa.

        A ultima ação é adicionar uma nova chamada ao handler que acionará a thread após LIGHT_DELAY
        milisegundos (definido na declaração da constante no inicio da classe)
     */
    private Runnable lightsOn = new Runnable() {
        @Override
        public void run() {
            lightsOff();

            switch(lightStatus){
                case 1:
                    redLight.setBackgroundColor(Color.parseColor("#E74C3C"));
                    lightStatus = 2;
                    break;
                case 2:
                    yellowLight.setBackgroundColor(Color.parseColor("#F1C40F"));
                    lightStatus = 3;
                    break;
                case 3:
                    greenLight.setBackgroundColor(Color.parseColor("#2ECC71"));
                    lightStatus = 1;
                    break;
                default:
                    break;

            }
            handler.postDelayed(lightsOn, LIGHTS_DELAY);
        }
    };

    //Função para apagar todas as luzes
    private void lightsOff(){
        redLight.setBackgroundColor(Color.parseColor("#BDC3C7"));
        yellowLight.setBackgroundColor(Color.parseColor("#BDC3C7"));
        greenLight.setBackgroundColor(Color.parseColor("#BDC3C7"));
    }

}
