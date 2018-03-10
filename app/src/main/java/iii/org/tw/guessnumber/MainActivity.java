package iii.org.tw.guessnumber;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView log, mesg;
    private EditText input;
    private Button guess, setting, reset;
    private String answer;
    private int guessNumber ;
    private int guessCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = findViewById(R.id.log);
        input = findViewById(R.id.input);
        reset = findViewById(R.id.reset);
        setting = findViewById(R.id.setting);
        mesg = findViewById(R.id.mesg);
        guess = findViewById(R.id.guess);
        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGuess();
            }
        });
        mesg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesg.setVisibility(View.GONE);
            }
        });


        showChoose();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoose();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoose();
            }
        });
    }

    private void initGame(){

        answer = createAnswer(guessNumber);
        guessCount = 1;
        input.setText("");
        log.setText("");
        Log.v("brad", answer);
    }

    private void doGuess(){
        String strInput = input.getText().toString();
        input.setText("");

        String result = checkAB(answer, strInput);
        log.append("猜了" + guessCount + "次" + "\n" + strInput + ":" + result + "\n");
        guessCount++;

        if(guessCount > 10){
            loseDialog();
        }

        if (result.equals(guessNumber+"A0B")){
            winDialog();
        }

    }

    private void showChoose(){
        final String[] chooseNumber = {"3","4","5","6"};

        AlertDialog.Builder numberList = new AlertDialog.Builder(this);
        numberList.setTitle("選擇要玩猜幾位數");
        numberList.setItems(chooseNumber, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "你選的是猜" + chooseNumber[which] + "位數", Toast.LENGTH_SHORT).show();
                guessNumber = Integer.parseInt(chooseNumber[which].toString());
                initGame();
            }
        });
        numberList.show();
    }


    private void winDialog(){
        //mesg.setVisibility(View.VISIBLE);

        AlertDialog alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Winner");
        builder.setMessage("So Good");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoose();
            }
        });
        alert = builder.create();
        alert.show();
    }

    private void loseDialog(){
        AlertDialog alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Loser");
        builder.setMessage("Answer :" + answer);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoose();
            }
        });
        alert = builder.create();
        alert.show();
    }


    static String checkAB(String a, String g) {
        int A, B; A = B = 0;

        for (int i=0 ;i<a.length(); i++) {
            if (g.charAt(i) == a.charAt(i)) {
                A++;
            }else if (a.indexOf(g.charAt(i)) != -1) {
                B++;
            }
        }

        return A + "A" + B +"B";
    }

    private String createAnswer(int d) {

        int[] poker = new int[10];
        for (int i=0; i<poker.length; i++) poker[i] = i;

        for (int i=poker.length; i>0; i--) {
            int rand = (int)(Math.random()*i);
            int temp = poker[rand];
            poker[rand] = poker[i-1];
            poker[i-1] = temp;
        }

        String ret = "";
        for (int i=0; i<d; i++) {
            ret += poker[i];
        }

        return ret;
    }

    public void end(View view) {
        finish();
    }
}