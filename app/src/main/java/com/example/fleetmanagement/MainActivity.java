package com.example.fleetmanagement;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import android.widget.TextView;
import android.widget.Button;

import com.example.fleetmanagement.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TextView txtAnswer;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDiv, btnSub, btnAdd, btnMul, btnAns, btnReset;
    private double val1=0.0, val2=0.0;
    private boolean Add,Sub,Mul,Div;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setViewIDs();

        btn0.setOnClickListener(view -> {
            txtAnswer.setText(txtAnswer.getText() + "0");
        });

        btn1.setOnClickListener(view -> {
            txtAnswer.setText(txtAnswer.getText() + "1");
        });

        btn2.setOnClickListener(view -> {
            txtAnswer.setText(txtAnswer.getText() + "2");
        });

        btn3.setOnClickListener(view -> {
            txtAnswer.setText(txtAnswer.getText() + "3");
        });

        btn4.setOnClickListener(view -> {
            txtAnswer.setText(txtAnswer.getText() + "4");
        });

        btn5.setOnClickListener(view -> {
            txtAnswer.setText(txtAnswer.getText() + "5");
        });

        btn6.setOnClickListener(view -> {
            txtAnswer.setText(txtAnswer.getText() + "6");
        });

        btn7.setOnClickListener(view -> {
            txtAnswer.setText(txtAnswer.getText() + "7");
        });

        btn8.setOnClickListener(view -> {
            txtAnswer.setText(txtAnswer.getText() + "8");
        });

        btn9.setOnClickListener(view -> {
            txtAnswer.setText(txtAnswer.getText() + "9");
        });

        btnAdd.setOnClickListener(view -> {
            if(txtAnswer.getText().length() != 0)
            {
                val1=Float.parseFloat(txtAnswer.getText()+ "");
                Add=true;
                txtAnswer.setText(null);
            }
        });

        btnSub.setOnClickListener(view -> {
            if(txtAnswer.getText().length() != 0)
            {
                val1=Float.parseFloat(txtAnswer.getText()+ "");
                Sub=true;
                txtAnswer.setText(null);
            }
        });

        btnMul.setOnClickListener(view -> {
            if(txtAnswer.getText().length() != 0)
            {
                val1=Float.parseFloat(txtAnswer.getText()+ "");
                Mul=true;
                txtAnswer.setText(null);
            }
        });

        btnDiv.setOnClickListener(view -> {
            if(txtAnswer.getText().length() != 0)
            {
                val1=Float.parseFloat(txtAnswer.getText()+ "");
                Div=true;
                txtAnswer.setText(null);
            }
        });

        btnAns.setOnClickListener(view -> {
            if(Add|Sub|Mul|Div) {
                val2=Float.parseFloat(txtAnswer.getText() + "");
            }

            if(Add) {
                txtAnswer.setText(val1+val2+"");
                Add=false;
            }

            if(Sub) {
                txtAnswer.setText(val1-val2+"");
                Sub=false;
            }

            if(Div) {
                txtAnswer.setText(val1/val2+"");
                Div=false;
            }

            if(Mul) {
                txtAnswer.setText(val1*val2+"");
                Mul=false;
            }
        });

        btnReset.setOnClickListener(view -> {
            txtAnswer.setText("");
            val1=0.0;
            val2=0.0;
            Add=false;
            Sub=false;
            Div=false;
            Mul=false;
        });

    }

    private void setViewIDs()
    {
        btn0=(Button) findViewById(R.id.button0);
        btn1=(Button) findViewById(R.id.button1);
        btn2=(Button) findViewById(R.id.button2);
        btn3=(Button) findViewById(R.id.button3);
        btn4=(Button) findViewById(R.id.button4);
        btn5=(Button) findViewById(R.id.button5);
        btn6=(Button) findViewById(R.id.button6);
        btn7=(Button) findViewById(R.id.button7);
        btn8=(Button) findViewById(R.id.button8);
        btn9=(Button) findViewById(R.id.button9);

        btnDiv=(Button) findViewById(R.id.buttonDivide);
        btnMul=(Button) findViewById(R.id.buttonMultiply);
        btnSub=(Button) findViewById(R.id.buttonSubtract);
        btnAdd=(Button) findViewById(R.id.buttonAdd);
        btnAns=(Button) findViewById(R.id.buttonAnswer);
        btnReset=(Button) findViewById(R.id.buttonReset);

        txtAnswer=(TextView) findViewById(R.id.Answer);
    }
}