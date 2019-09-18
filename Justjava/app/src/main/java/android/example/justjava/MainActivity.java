package android.example.justjava;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int numberOfCoffees = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    public void display(int number) {
        TextView quantityTextView = findViewById(R.id.tv_quantity);
            quantityTextView.setText("" + number);

    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(String number) {
        TextView priceTextView = findViewById(R.id.tv_price);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    private void displayMessage(String message){
        TextView priceTextView = findViewById(R.id.tv_price);
        priceTextView.setText(message);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String priceMessage = "Total: "+numberOfCoffees*5+"$\n";
        String thank = "Thank you!";
        displayMessage(priceMessage+thank);
    }
    public void increment(View view){
        numberOfCoffees=numberOfCoffees+1;
        display(numberOfCoffees);
    }
    public void decrement(View view){
        numberOfCoffees=numberOfCoffees-1;
        display(numberOfCoffees<=0?numberOfCoffees=0:numberOfCoffees);

       }
}
