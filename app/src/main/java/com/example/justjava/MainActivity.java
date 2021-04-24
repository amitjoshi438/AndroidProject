package com.example.justjava;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    private int quantity = 1;
    public void submitOrder(View view) {
//        int quantity = 2;
//        display(quantity);
//        displayPrice(quantity*5);
        CheckBox whippedCream = findViewById(R.id.whipped_cream);
        CheckBox choclate =  findViewById(R.id.choclate_topping);
        boolean hasChoclate = choclate.isChecked();
        boolean hasWhippedCream = whippedCream.isChecked();

        int price = calculatePrice(hasChoclate, hasWhippedCream);

        EditText nameText = findViewById(R.id.name);
        Editable text = nameText.getText();
        String name = text.toString();


        String orderSummary = createOrderSummary(price,name,hasChoclate,hasWhippedCream);
        String emailSubject = "JustJava Order for " + name;
//        String priceMessage = "Total: $"+(price)+"\n"+"Thank you!";

//        displayMessage(orderSummary);

        composeEmail(orderSummary,emailSubject);

    }
    /**
     * calculates total price of the order
     * @return total price
     */
    private int calculatePrice(boolean hasChoclate, boolean hasWhippedCream)
    {
        int basePrice = 5;
        if(hasChoclate)
            basePrice += 2;
        if(hasWhippedCream)
            basePrice += 1;
        return quantity*basePrice;
    }

    /**
     * Creates summary for order
     * @param price price per cup
     * @return order summary
     */
    private String createOrderSummary(int price, String name, boolean hasChoclate, boolean hasWhippedCream) {

        return "Name: "+name+"\n"+getString(R.string.quantity)+": "+price/5+"\n"+"Total: $"+(price)+"\n"+"Add Whipped Cream? "+hasWhippedCream+"\n"+"Add Choclate? "+hasChoclate+"\n"+"Thank you!";

    }

    public  void increament(View view) {
        if(quantity>=100) {
            Context context = getApplicationContext();
            CharSequence text = "You cannot have more than 100 coffee";   // String is an implementation of CharSequence interface
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text , duration);
            toast.show();
            return;
        }
        quantity++;
        display(quantity);
    }

    public void decreament(View view) {
        if(quantity<=1){
            Context context = getApplicationContext();
            CharSequence text = "You cannot have less than 1 coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity--;
        display(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    @SuppressLint("SetTextI18n")
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    private void composeEmail(String text, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}