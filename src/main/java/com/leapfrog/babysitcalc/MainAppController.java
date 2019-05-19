/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.babysitcalc;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class MainAppController implements Initializable {

    @FXML
    private JFXTimePicker jt_starttime;

    @FXML
    private JFXTimePicker jt_endtime;

    @FXML
    private ChoiceBox chboxFamily;
    @FXML
    private JFXTextField jtf_payrate;
    @FXML
    private Button btn_calculate;

    @FXML
    private JFXTextField jtf_totalpay;

    @FXML
    private Label label;
    @FXML
    private JFXTextField jtf_workedhrs;

    @FXML
    private ComboBox<String> cb_family;

    @FXML
    private DatePicker currentdatepick;

    @FXML
    private JFXTimePicker jfx_starttime;
    @FXML
    private JFXTimePicker jfx_endtime;
    @FXML
    private Label label1;
    @FXML
    private Label labeltop;

    private double payrate = 0;
    private double totalpay = 0;
    private int hoursworked = 0;
    private int hoursworkedcnt = 0;
    private String date = "", name = "";
    String beforedate = "";
    ArrayList<BabySitter> arrayBaby = new ArrayList<BabySitter>();
    private String starttime;
    private String endtime;
    private Calendar calstarttime = Calendar.getInstance();
    private Calendar calendtime = Calendar.getInstance();

    @FXML
    void onDateChange(ActionEvent event) {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd ");
        //  Date date = dateFormat.parse(currentdatepick.getValue().)

    }

//starts no earlier than 5:00PM
    @FXML
    public void StartTimeTextChange(ActionEvent event) throws ParseException {
        starttime = (jfx_starttime.getValue()).toString();
        calstarttime = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        /* SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US); //M/d/yy 
       LocalDateTime dt = LocalDateTime.parse(starttime, 
            DateTimeFormatter.ofPattern("hh:mm", Locale.ENGLISH));*/

        calstarttime.setTime(dateFormat.parse(starttime));
        int starttimehour = calstarttime.get(Calendar.HOUR_OF_DAY);
        if (starttimehour < 17) {
            Alert dg = new Alert(Alert.AlertType.INFORMATION);
            dg.setTitle("Starting Time");
            dg.setContentText("Start Time should not be earlier than 5 pm.");
            dg.show();
        }
        //  label.setText(String.valueOf((starttimehour)));

    }
//leaves no later than 4:00AM

    @FXML
    void EndTimeTextChange(ActionEvent event) throws ParseException {
        endtime = (jfx_endtime.getValue()).toString();
        //       calendtime = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        /* SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US); //M/d/yy 
       LocalDateTime dt = LocalDateTime.parse(starttime, 
            DateTimeFormatter.ofPattern("hh:mm", Locale.ENGLISH));*/

        calendtime.setTime(dateFormat.parse(endtime));
        int endtimehour = calendtime.get(Calendar.HOUR_OF_DAY);
        if (endtimehour >= 4) {
            Alert dgend = new Alert(Alert.AlertType.INFORMATION);
            dgend.setTitle("End Time");
            dgend.setContentText("End Time should not be later than 4 am.");
            dgend.show();
        }
        //label.setText(String.valueOf((endtimehour)));
    }

    @FXML
    void btn_CalulateClick(ActionEvent event) {
        jtf_totalpay.setText(String.valueOf(totalpay));

    }

    @FXML
    void ChoiceButtonPushed(ActionEvent event) {
        hoursworkedcnt = 0;
        String nameOfFamily = cb_family.getValue();
        try {
            BabySitter b = new BabySitter();
            b.setDate(date);
            b.setFamily(cb_family.getValue());
            b.setName(name);
            if (!(arrayBaby.contains(b))) {
                     totalpay = CalculatePay(nameOfFamily);
                arrayBaby.add(b);
            } else {
                Alert dg1 = new Alert(Alert.AlertType.INFORMATION);
                dg1.setTitle("Value Check");
                dg1.setContentText(name + "already worked for" + cb_family.getValue() + "on" + date);
                dg1.show();
            }
           
        } catch (ParseException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    double CalculatePay(String nameOfFamily) throws ParseException {
        int beforepayrate = 0, afterpayrate = 0, middlepayrate = 0;
        starttime = (jfx_starttime.getValue()).toString();
        endtime = (jfx_endtime.getValue()).toString();

        String eleven = "23:00";
        String nine = "21:00";
        String ten = "22:00";
        String twelve = "00:00";
        int hrs = 0;
        int endhrs = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date startdate = formatter.parse(starttime);
        Calendar c = Calendar.getInstance();
        c.setTime(startdate);

        hrs = c.get(Calendar.HOUR_OF_DAY);
        //  Date actualstarttime=formatter.parse(String.valueOf(hrs+1));
        //    String actualstarttime1= actualstarttime.toString();
        if (nameOfFamily == "Family A") { //$15 per hour before 11pm, and $20 per hour the rest of the night
            // payrate = 20;
            if (hrs < 23) {
                beforepayrate = 15 * CalculatePeriod(starttime, eleven);
                afterpayrate = 20 * CalculatePeriod(eleven, endtime);
            } else {
                afterpayrate = 20 * (CalculatePeriod(starttime, endtime) - 1);
            }
            payrate = beforepayrate + afterpayrate;
        } else if (nameOfFamily == "Family B") {// $12 per hour before 10pm, $8 between 10 and 12, and $16 the rest of the night
            // payrate = 16;
            if (hrs < 22) {
                beforepayrate = 12 * CalculatePeriod(starttime, ten);
                middlepayrate = 8 * CalculatePeriod(ten, twelve);
                afterpayrate = 16 * CalculatePeriod(twelve, endtime);
            } else if (((c.get(Calendar.HOUR_OF_DAY)) >= 22) || (((c.get(Calendar.HOUR_OF_DAY)) < 00))) {
                beforepayrate = 0;
                middlepayrate = 8 * CalculatePeriod(starttime, twelve);
                afterpayrate = 16 * CalculatePeriod(twelve, endtime);

            } else {
                afterpayrate = 16 * (CalculatePeriod(starttime, endtime) - 1);
            }
            payrate = beforepayrate + middlepayrate + afterpayrate;

        } else if (nameOfFamily == "Family C") { //$21 per hour before 9pm, then $15 the rest of the night
            // payrate = 15;
            if (hrs < 21) {
                beforepayrate = 21 * CalculatePeriod(starttime, nine);
                afterpayrate = 15 * CalculatePeriod(nine, endtime);
            } else {

                afterpayrate = 15 * (CalculatePeriod(starttime, endtime) - 1);
            }
            payrate = beforepayrate + afterpayrate;
        } else {
        }
        return payrate;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //     labeltop.setText("Pay Rate Calculation for" +logincom.getName());
        String st[] = {"Family A", "Family B", "Family C"};
        //     chboxFamily.getItems().add(FXCollections.observableArrayList(st));//("Family A");
        for (String s : st) {
            cb_family.getItems().add(s);
        }
        // currentdate= getCurrentDate();
        //currentdate.setPromptText(getCurrentdate());//setValue(getCurrentdate());
        //    jfx_endtime.setPromptText(getCurrenttime());
        //   jfx_starttime.setPromptText(getCurrenttime());
        // CalculatePeriod();
    }

    public int CalculatePeriod(String sttime, String edtime) throws ParseException {
        // Calendar x = Calendar.getInstance();
        //Calendar y = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date startdate = formatter.parse(sttime);
        Date enddate = formatter.parse(edtime);
        calstarttime.setTime(startdate);
        if ((calstarttime.get(Calendar.MINUTE)) > 0) {
            calstarttime.set(Calendar.MINUTE, 0);
            calstarttime.set(Calendar.HOUR_OF_DAY, ((calstarttime.get(Calendar.HOUR_OF_DAY) + 1)));
        }
        calendtime.setTime(enddate);
        hoursworked = getPeriodHours(calstarttime, calendtime);// gethours.get(Calendar.HOUR_OF_DAY);
        hoursworkedcnt = hoursworked + hoursworkedcnt;
        jtf_workedhrs.setText(String.valueOf(hoursworkedcnt));
        return hoursworked;
        /*    calstarttime.set(Calendar.HOUR_OF_DAY, startdate);
        calstarttime.set(Calendar.MINUTE, 11);
        calstarttime.set(Calendar.SECOND, 2);
        calendtime.set(Calendar.HOUR_OF_DAY, 12);
        calendtime.set(Calendar.MINUTE, 12);
        calendtime.set(Calendar.SECOND, 12);*/
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        // Date date = getPeriod(calstarttime, calendtime).getTime();
        //Calendar gethours = Calendar.getInstance();
        //gethours.setTime(date);
        //jtf_payrate.setText(getPeriod(x, y).getTime().toString());
        //  jtf_payrate.setText(dateFormat.format(date));

    }

    public static Calendar getPeriod(Calendar starttime, Calendar endtime) {
        Calendar result = Calendar.getInstance();

        result.set(Calendar.HOUR_OF_DAY, endtime.get(Calendar.HOUR_OF_DAY)
                - starttime.get(Calendar.HOUR_OF_DAY));
        result.set(Calendar.MINUTE, endtime.get(Calendar.MINUTE)
                - starttime.get(Calendar.MINUTE));
        result.set(Calendar.SECOND, endtime.get(Calendar.SECOND)
                - starttime.get(Calendar.SECOND));
        return result;

    }

    public static int getPeriodHours(Calendar starttime, Calendar endtime) {

        Calendar result = Calendar.getInstance();
        result.set(Calendar.HOUR_OF_DAY, endtime.get(Calendar.HOUR_OF_DAY)
                - starttime.get(Calendar.HOUR_OF_DAY));
        result.set(Calendar.MINUTE, endtime.get(Calendar.MINUTE)
                - starttime.get(Calendar.MINUTE));
        result.set(Calendar.SECOND, endtime.get(Calendar.SECOND)
                - starttime.get(Calendar.SECOND));
        return result.get(Calendar.HOUR_OF_DAY);

    }

    public LocalDate fromString(String text) //String to locale date
    {
        String pattern = "MM/dd/yyyy";
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate date = null;

        if (text != null && !text.trim().isEmpty()) {
            date = LocalDate.parse(text, dtFormatter);
        }

        return date;
    }

    public String getCurrenttime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a");
        LocalDateTime timenow = LocalDateTime.now();
        return dtf.format(timenow);
    }

}
