package com.casper.testdrivendevelopment.data;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.casper.testdrivendevelopment.R;

public class EditBookActivity extends AppCompatActivity {

    private EditText editTextGoodName,editTextGoodPrice;
    private Button buttonOk,buttonCancel;
    private int editPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);

        editPosition = getIntent().getIntExtra("edit_position",0);

        editTextGoodName = (EditText)findViewById(R.id.edit_text_good_name);

        buttonCancel = (Button)findViewById(R.id.button_cancel);
        buttonOk = (Button)findViewById(R.id.button_ok);

       // double goodPrice = getIntent().getDoubleExtra("good_price",-1);
        String goodName = getIntent().getStringExtra("good_name");

        if(goodName != null){
            editTextGoodName.setText(goodName);
          //  editTextGoodPrice.setText(goodPrice+"");
        }

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("edit_position", editPosition);
                intent.putExtra("good_name", editTextGoodName.getText().toString().trim());
               // intent.putExtra("good_price",Double.parseDouble( editTextGoodPrice.getText().toString()));
                setResult(RESULT_OK, intent);
                EditBookActivity.this.finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBookActivity.this.finish();
            }
        });


    }
}
