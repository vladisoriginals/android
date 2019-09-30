package android.example.justjava;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.tv_quantity);
            quantityTextView.setText("" + number);

    }
    private int calculatePrice(){
        int price = quantity * 5;
        return price;
    }
    private void displayMessage(String message){
        TextView orderSummary = findViewById(R.id.tv_order_summary);
        orderSummary.setText(message);
    }

    public void submitOrder(View view) {
        String name = "Vlad Stashevsky\n";
        String priceMessage = "Total: "+calculatePrice()+"$\n";
        String thank = "Thank you!";
        displayMessage(name+priceMessage+thank);
    }
    public void increment(View view){
        quantity=quantity+1;
        displayQuantity(quantity);
    }
    public void decrement(View view){
        quantity=quantity-1;
        displayQuantity(quantity<=0?quantity=0:quantity);

       }
}
