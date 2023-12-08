/*Author: Kwadwo Addai Poku,ID Number: 221987*/
package Poku0474.com;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editName, editAddress, editAge, editDays, editMedication, editSurgical, editLab, editRehab;
    private RadioGroup radioGender;

    private EditText editEmail;
    private TextView viewReport;
    private Button btnCalculate, btnClear, btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This section calls all the ids from the xml file
        editName = findViewById(R.id.editName);
        editAddress = findViewById(R.id.editAddress);
        editEmail = findViewById(R.id.editEmail);
        editAge = findViewById(R.id.editAge);
        radioGender = findViewById(R.id.radioGender);

        editDays = findViewById(R.id.editDays);
        editMedication = findViewById(R.id.editMedication);
        editSurgical = findViewById(R.id.editSurgical);
        editLab = findViewById(R.id.editLab);
        editRehab = findViewById(R.id.editRehab);

        viewReport = findViewById(R.id.viewReport);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        btnSend = findViewById(R.id.btnSend);
    }

    // This method calculates the total stay at the hospital by calling various methods
    public void calculateTotal(View view) {
        double stayCharges = calcStayCharges();
        double miscCharges = calcMiscCharges();
        double totalCharges = calcTotalCharges(stayCharges, miscCharges);

        // The Results are displayed in the viewReport TextView
        viewReport.setText(generateReceipt(stayCharges, miscCharges, totalCharges));
    }

    //This method is for calculating the total charges for the number of days spent at the hospital
    private double calcStayCharges() {
        // Variable "days" holds the value entered into the editDays then it's converted into an integer
        int days = Integer.parseInt(editDays.getText().toString());

        // Calculate and return the charges for the hospital stay
        return 200.0 * days;
    }

    // This method is for calculating the total charges of all extra services used during the patients stay at VVU HOSPITAL
    private double calcMiscCharges() {
        // The Variables holds the values entered into the editTextbox then their converted into a double
        double medication = Double.parseDouble(editMedication.getText().toString());
        double surgical = Double.parseDouble(editSurgical.getText().toString());
        double lab = Double.parseDouble(editLab.getText().toString());
        double rehab = Double.parseDouble(editRehab.getText().toString());

        // Calculate and return the total of medication, surgical, lab, and rehab charges
        return medication + surgical + lab + rehab;
    }

    // This method calculates the total charges for both staying and using other services and it does this by calling both the stayCharges method and the miscCharges method
    private double calcTotalCharges(double stayCharges, double miscCharges) {
        // Calculate and return the total charges
        return stayCharges + miscCharges;
    }

    // This method is responsible for provide the receipt of the charges
    private String generateReceipt(double stayCharges, double miscCharges, double totalCharges) {

        // StringBuilder is used in concatenating plenty strings and it will be useful here
        StringBuilder receiptBuilder = new StringBuilder();
        String patient = editName.getText().toString();
        String email = editEmail.getText().toString();

        // This section is resposible for the contents of the receipt
        receiptBuilder.append("Valley View University Hospital\n\n");
        receiptBuilder.append("Patient Name: ").append(patient).append("\n");
        receiptBuilder.append("Patient Email: ").append(email).append("\n");
        receiptBuilder.append("Charges Breakdown:\n");
        receiptBuilder.append("Minimum charge for one day stay is 200.0:\n");
        receiptBuilder.append("Base Stay Charges: GHC ").append(stayCharges).append("\n");
        receiptBuilder.append("Medication Charges: GHC ").append(miscCharges).append("\n");
        receiptBuilder.append("Total Charges: GHC ").append(totalCharges);

        return receiptBuilder.toString();
    }

    // This method is to help clear all inputs so the user can start again
    public void clearAll(View view) {
        editName.getText().clear();
        editAddress.getText().clear();
        editAge.getText().clear();
        radioGender.clearCheck();
        editDays.getText().clear();
        editMedication.getText().clear();
        editSurgical.getText().clear();
        editLab.getText().clear();
        editRehab.getText().clear();
        viewReport.setText("");
    }

    // This method is to send the receipt to the user's provided email address
    public void sendReceipt(View view) {

        // Here we place the contents of the viewReport into a String variable "recieptText"
        String receiptText = viewReport.getText().toString();

        // Then we store the user's email
        String userEmail = editEmail.getText().toString().trim();

        if (!userEmail.isEmpty()) {
            // Intent.ACTION.SENDTO is responsible for the email
            // mailto helps launch an email client
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"));

            // This adds contents to the emailIntent and in this case the Strings of the "userEmail" variable
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{userEmail});



            // This adds contents to the subject area of the email
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "HOSPITAL BILL");

            // This adds contents to the body in this case the contents of the variable "receiptText"
            emailIntent.putExtra(Intent.EXTRA_TEXT, receiptText);

            // This start the emailIntent activity we have defined
            startActivity(emailIntent);

            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            }
            }
        else {
            // This displays a log message if the user doesnt input any email address
            Log.e("MainActivity", "User email is empty. Cannot send receipt.");
            return;

        }
        }

}
