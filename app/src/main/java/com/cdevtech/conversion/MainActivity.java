package com.cdevtech.conversion;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Spinner unitTypeSpinner;
    private EditText amountTextView;
    private TextView teaspoonTextView, tablespoonTextView, cupTextView, ounceTextView,
                    pintTextView, quartTextView, gallonTextView, poundTextView,
                    milliliterTextView, literTextView, milligramTextView, kilogramTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        // Get a reference to the spinner
        unitTypeSpinner = (Spinner) findViewById(R.id.unit_type_spinner);

        // Fills the spinner with the unit options
        addItemsToUnitTypeSpinner();

        // Add listener to the spinner
        addListerToUnitTypeSpinner();

        //Set the text color of the Spinner's selected view (not a drop down list view)
        unitTypeSpinner.setSelection(0, true);
        View view = unitTypeSpinner.getSelectedView();
        ((TextView)view).setTextColor(Color.BLUE);
        ((TextView)view).setTextSize(20);

        // Get a reference to the edit text to retrieve the amount of the unit type
        amountTextView = (EditText) findViewById(R.id.amount_text_view);

        // Add a listener to the amount TextView
        AddOnKeyListenerToAmountTextView();

        initializeTextViews();
    }

    public void initializeTextViews() {
        teaspoonTextView = (TextView) findViewById(R.id.tsp_text_view);
        tablespoonTextView  = (TextView) findViewById(R.id.tbs_text_view);
        cupTextView  = (TextView) findViewById(R.id.cup_text_view);
        ounceTextView  = (TextView) findViewById(R.id.oz_text_view);
        pintTextView  = (TextView) findViewById(R.id.pint_text_view);
        quartTextView  = (TextView) findViewById(R.id.quart_text_view);
        gallonTextView  = (TextView) findViewById(R.id.gallon_text_view);
        poundTextView  = (TextView) findViewById(R.id.pound_text_view);
        milliliterTextView  = (TextView) findViewById(R.id.ml_text_view);
        literTextView  = (TextView) findViewById(R.id.liter_text_view);
        milligramTextView  = (TextView) findViewById(R.id.mg_text_view);
        kilogramTextView  = (TextView) findViewById(R.id.kg_text_view);
    }

    public void addItemsToUnitTypeSpinner() {
        // Create the ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> unitTypeSpinnerAdapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.conversion_types,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        unitTypeSpinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        unitTypeSpinner.setAdapter(unitTypeSpinnerAdapter);
    }

    public void addListerToUnitTypeSpinner() {
         unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if (view != null) {
                     // Set the font color and size
                     ((TextView)view).setTextColor(Color.BLUE);
                     ((TextView)view).setTextSize(20);
                 }

                 // Get the item selected in the spinner
                 String itemSelectedInSpinner = parent.getItemAtPosition(position).toString();

                 // Verify if I'm converting from teaspoon so that I use the correct
                 // conversion algorithm
                 checkIfConvertingFromTsp(itemSelectedInSpinner);
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {
                 // TODO: maybe something later
             }
         });
    }

    public void AddListenerToAmountTextView() {
        amountTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIfConvertingFromTsp(unitTypeSpinner.getSelectedItem().toString());
            }
        });
    }

    public void AddOnKeyListenerToAmountTextView() {
        amountTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    checkIfConvertingFromTsp(unitTypeSpinner.getSelectedItem().toString());
                    return true;
                }
                return false;
            }
        });
    }

    public void checkIfConvertingFromTsp(String currentUnit) {
        if(currentUnit.equals("teaspoon")){
            updateUnitTypesUsingTsp();
        } else {
            if(currentUnit.equals("tablespoon")) {
                updateUnitTypesUsingOther(Quantity.Unit.tbs);
            }
            else if(currentUnit.equals("cup")){
                updateUnitTypesUsingOther(Quantity.Unit.cup);
            }
            else if(currentUnit.equals("ounce")){
                updateUnitTypesUsingOther(Quantity.Unit.oz);
            }
            else if(currentUnit.equals("pint")){
                updateUnitTypesUsingOther(Quantity.Unit.pint);
            }
            else if(currentUnit.equals("quart")){
                updateUnitTypesUsingOther(Quantity.Unit.quart);
            }
            else if(currentUnit.equals("gallon")){
                updateUnitTypesUsingOther(Quantity.Unit.gallon);
            }
            else if(currentUnit.equals("pound")){
                updateUnitTypesUsingOther(Quantity.Unit.pound);
            }
            else if(currentUnit.equals("milliliter")){
                updateUnitTypesUsingOther(Quantity.Unit.ml);
            }
            else if(currentUnit.equals("liter")){
                updateUnitTypesUsingOther(Quantity.Unit.liter);
            }
            else if(currentUnit.equals("milligram")){
                updateUnitTypesUsingOther(Quantity.Unit.mg);
            }
            else {
                updateUnitTypesUsingOther(Quantity.Unit.kg);
            }
        }
    }

    public void updateUnitTypesUsingTsp() {
        // Convert the type in the editTextBox to a double
        double doubleToConvert = Double.parseDouble(amountTextView.getText().toString());

        // Combine value to unit
        String teaspoonValueAndUnit = doubleToConvert + " tsp";

        // Change the value for the teaspoon TextView
        teaspoonTextView.setText(teaspoonValueAndUnit);

        // Update all the unit text fields
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.tbs, tablespoonTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.cup, cupTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.oz, ounceTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.pint, pintTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.quart, quartTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.gallon, gallonTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.pound, poundTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.ml, milliliterTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.liter, literTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.mg, milligramTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.kg, kilogramTextView );
    }

    public void updateUnitTextFieldUsingTsp(double doubleToConvert, Quantity.Unit unitConvertingTo,
                                            TextView theTextView) {

        Quantity unitQuantity = new Quantity(doubleToConvert, Quantity.Unit.tsp);
        String tempUnit = unitQuantity.to(unitConvertingTo).toString();
        theTextView.setText(tempUnit);
    }

    public void updateUnitTextFieldUsingTsp(double doubleToConvert, Quantity.Unit currentUnit,
                                            Quantity.Unit unitConvertingTo, TextView theTextView) {

        Quantity currentQuantity = new Quantity(doubleToConvert, currentUnit);
        String tempUnit = (currentQuantity.to(Quantity.Unit.tsp)).to(unitConvertingTo).toString();
        theTextView.setText(tempUnit);
    }

    public void updateUnitTypesUsingOther(Quantity.Unit currentUnit) {
        // Convert the type in the exitTextBox to a double
        double doubleToConvert = Double.parseDouble(amountTextView.getText().toString());

        // Create a quantity based on the passed in unit type
        Quantity currentQuantitySelected = new Quantity(doubleToConvert, currentUnit);

        // Create the string for the teaspoon TextView
        teaspoonTextView.setText(currentQuantitySelected.to(Quantity.Unit.tsp).toString());

        // Update all the unit text fields
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.tbs, tablespoonTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.cup, cupTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.oz, ounceTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.pint, pintTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.quart, quartTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.gallon, gallonTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.pound, poundTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.ml, milliliterTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.liter, literTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.mg, milligramTextView );
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.kg, kilogramTextView );

         // Create the TextView text by taking the value in the EditText and adding
        // on the currently selected unit in the spinner
        String currentUnitTextViewText = doubleToConvert + " " + currentQuantitySelected.unit.name();

        // Create the TextVIew name to change by getting the currently
        // selected quantities unit name and tacking it on _text_view
        String currentTextViewName = currentQuantitySelected.unit.name() + "_text_view";

        // Get the resource id needed for the TextView to use in findVIewById
        int currentId = getResources().getIdentifier(currentTextViewName, "id",
                MainActivity.this.getPackageName());

        // Create an instance of the TextView we want to change
        TextView currentTextView = (TextView) findViewById(currentId);

        // Put the correct data in the TextView
        currentTextView.setText(currentUnitTextViewText);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
