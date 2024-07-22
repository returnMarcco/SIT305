package com.example.intellivert_unit_converter;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate and initialise widgetIDs (from layout) to the Widget objects
        Spinner sourceUnitSpinner = findViewById(R.id.idSourceUnitSpinner);
        Spinner targetUnitSpinner = findViewById(R.id.idTargetUnitSpinner);
        EditText editText = findViewById(R.id.idNumberSourceInput);
        Button convertBtn = findViewById(R.id.idConvertButton);
        ImageButton swapUnitValueButton = findViewById(R.id.idSwapUnitValues);

        // Define ArrayAdapters for populating spinners from a hard-coded String array
        ArrayAdapter<CharSequence> sourceUnitAdapter = ArrayAdapter.createFromResource(
                this, R.array.unitArray, android.R.layout.simple_spinner_item
        );

        sourceUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnitSpinner.setAdapter(sourceUnitAdapter);
        sourceUnitSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> targetUnitAdapter = ArrayAdapter.createFromResource(
                this, R.array.unitArray, android.R.layout.simple_spinner_item
        );

        targetUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        targetUnitSpinner.setAdapter(targetUnitAdapter);
        targetUnitSpinner.setOnItemSelectedListener(this);

        // Set Event Listener
        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // Event Handler Callback

                // onClick convert logic here
                int sourceUnitSelection = sourceUnitSpinner.getSelectedItemPosition();
                int targetUnitSelection = targetUnitSpinner.getSelectedItemPosition();

                CharSequence sourceUnitLabel = sourceUnitAdapter.getItem(sourceUnitSelection);
                CharSequence targetUnitLabel = targetUnitAdapter.getItem(targetUnitSelection);

                String inputVal = "inputVal";
                Double inputValDbl = 1.0;
                int durationLong = Toast.LENGTH_LONG;

                try {
                    if (editText != null) {
                        inputVal = editText.getText().toString();
                        inputValDbl = Double.parseDouble(inputVal);
                        System.out.println(sourceUnitSelection);
                        System.out.println(targetUnitSelection);

                        if (sourceUnitSelection < 4 && targetUnitSelection >= 4 || sourceUnitSelection > 3 && targetUnitSelection < 4 ||
                            sourceUnitSelection >= 7 && targetUnitSelection < 7) {
                                String invalidConversionMessage = "Invalid Conversion";
                                Toast invalidConversion = Toast.makeText(getApplicationContext(), invalidConversionMessage, durationLong);
                                invalidConversion.show();
                        } else {
                            double convertedLength = conversionFormulas(inputValDbl, sourceUnitSelection, targetUnitSelection);
                            String validConversionMessage = inputVal + " " + sourceUnitLabel + " = " + convertedLength + " " + targetUnitLabel;
                            Toast validConversionToast = Toast.makeText(getApplicationContext(), validConversionMessage, durationLong);
                            validConversionToast.show();
                        }
                    }

                } catch (Exception e) {
                    String catchAllExceptionMessage = "You must enter a value before tapping the `Convert` button.";
                    Toast catchAllExceptionToast = Toast.makeText(getApplicationContext(), catchAllExceptionMessage, durationLong);
                    catchAllExceptionToast.show();
                }
            }
        });

        swapUnitValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempSourceSpinnerVal = sourceUnitSpinner.getSelectedItemPosition();
                int tempTargetSpinnerVal = targetUnitSpinner.getSelectedItemPosition();

                targetUnitSpinner.setSelection(tempSourceSpinnerVal);
                sourceUnitSpinner.setSelection(tempTargetSpinnerVal);
            }
        });
    }

    public double conversionFormulas(double input, int sourceUnitPos, int targetUnitPos) {
        // I would have used a Switch statement if it allowed multiple expressions
        if (sourceUnitPos == targetUnitPos) { // Length Formulas
            return input;
        // Inch -> Foot
        } else if (sourceUnitPos == 0 && targetUnitPos == 1) {
            return (input / 12);
        // Foot -> Inch
        } else if (sourceUnitPos == 1 && targetUnitPos == 0) {
            return (input * 12);
        // Inch -> Yard
        } else if (sourceUnitPos == 0 && targetUnitPos == 2) {
            return (input / 36);
        // Yard -> Inch
        } else if (sourceUnitPos == 2 && targetUnitPos == 0) {
            return (input * 36);
        // Inch -> Mile
        } else if (sourceUnitPos == 0 && targetUnitPos == 3) {
            return (input / 63360);
        // Mile -> Inch
        } else if (sourceUnitPos == 3 && targetUnitPos == 0) {
            return (input * 63360);
         // Foot -> Yard
        } else if (sourceUnitPos == 1 && targetUnitPos == 2) {
            return (input / 3);
        // Yard -> Foot
        } else if (sourceUnitPos == 2 && targetUnitPos == 1) {
            return (input * 3);
        // Foot -> Mile
        } else if (sourceUnitPos == 1 && targetUnitPos == 3) {
            return (input / 5280);
        // Mile -> Foot
        } else if (sourceUnitPos == 3 && targetUnitPos == 1) {
            return (input * 5280);
        // Yard -> Mile
        } else if (sourceUnitPos == 2 && targetUnitPos == 3) {
            return (input / 1760);
        // Mile -> Yard
        } else if (sourceUnitPos == 3 && targetUnitPos == 2) {
            return (input * 1760);

        // Weight Formulas
        // Pound -> Ounce
        } else if (sourceUnitPos == 4 && targetUnitPos == 5) {
            return (input * 16);
        // Ounce -> Pound
        } else if (sourceUnitPos == 5 && targetUnitPos == 4) {
            return (input / 16);
        // Ounce -> Tonne
        } else if (sourceUnitPos == 5 && targetUnitPos == 6) {
            return (input / 35270);
        // Tonne -> Ounce
        } else if (sourceUnitPos == 6 && targetUnitPos == 5) {
            return (input * 35270);
        // Tonne -> Pound
        } else if (sourceUnitPos == 6 && targetUnitPos == 4) {
            return (input * 2205);
        // Pound -> Tonne
        } else if (sourceUnitPos == 4 && targetUnitPos == 6) {
            return (input / 2205);

        // Temperature Formulas
        // Celsius -> Fahrenheit
        } else if (sourceUnitPos == 7 && targetUnitPos == 8) {
            return (input * 1.8) + 32;
        // Fahrenheit -> Celsius
        } else if (sourceUnitPos == 8 && targetUnitPos == 7) {
            return (input - 32) / 1.8;
        // Celsius -> Kelvin
        } else if (sourceUnitPos == 7 && targetUnitPos == 9) {
            return (input + 273.15);
        // Kelvin -> Celsius
        } else if (sourceUnitPos == 9 && targetUnitPos == 7) {
            return (input - 273.15);
        // Kelvin -> Fahrenheit
        } else if (sourceUnitPos == 9 && targetUnitPos == 8) {
            return (input - 273.15) * 9 / 5 + 32;
        // Fahrenheit -> Kelvin
        } else if (sourceUnitPos == 8 && targetUnitPos == 9) {
            return ((input - 32) * 5 / 9 + 273.15);
        // Celsius -> Kelvin
        } else if (sourceUnitPos == 7 && targetUnitPos == 9) {
            return (input + 273.15);
        }

        return -1;
    }

    // Event-handler declarations required for implementing the Listener Interface
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}