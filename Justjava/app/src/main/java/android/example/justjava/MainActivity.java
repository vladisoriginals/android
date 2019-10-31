package android.example.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    CheckBox checkBox;
    CheckBox checkBox2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.tv_quantity);
            quantityTextView.setText("" + number);

    }
    private int calculatePrice(int quantity){
        checkBox = findViewById(R.id.cb_checkbox);
        checkBox2 = findViewById(R.id.cb_chocolate);
        int price = quantity * 5;
        if (checkBox.isChecked()&&checkBox2.isChecked()){
            price = quantity * 8;
        }else if (checkBox2.isChecked()){
            price = quantity * 7;
        }else if (checkBox.isChecked()){
            price = quantity * 6;
        }

        return price;
    }

    public void submitOrder(View view) {
        checkBox = findViewById(R.id.cb_checkbox);
        checkBox2 = findViewById(R.id.cb_chocolate);
        EditText nameEditText = findViewById(R.id.et_name);

        String order;
        String name = nameEditText.getText() + "\n";
        String cream = "Added whipped cream\n";
        String chocolate = "Added chocolate\n";
        String priceMessage = "Total: " + calculatePrice(quantity) + "$\n";
        String thank = "Thank you!";

        if (checkBox.isChecked() && checkBox2.isChecked()) {
            order = name + cream + chocolate + priceMessage + thank;
        } else if (checkBox.isChecked()) {
            order = name + cream + priceMessage + thank;
        } else if (checkBox2.isChecked()) {
            order = name + chocolate + priceMessage + thank;
        } else {
            order = name + priceMessage + thank;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order for Vlad Stashevsky");
        intent.putExtra(Intent.EXTRA_TEXT, order);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void increment(View view){
        quantity=quantity+1;
        if (quantity>=100){
            displayQuantity(quantity=100);
            Toast toast = Toast.makeText(this,"Max quantity coffees",Toast.LENGTH_LONG);
            toast.show();
        }else {
            displayQuantity(quantity);
        }
    }
    public void decrement(View view){
        quantity=quantity-1;
        if (quantity<=0){
            displayQuantity(quantity=0);
            Toast toast = Toast.makeText(this, "Min quantity coffees", Toast.LENGTH_LONG);
            toast.show();
        }else {
            displayQuantity(quantity);
        }

       }

}
