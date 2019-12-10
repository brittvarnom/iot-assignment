package lock;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.phidget22.*;
import publisher.PhidgetPublisher;
import reader.RFIDdata;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.google.gson.Gson;

import com.phidget22.PhidgetException;

public class LockMover {

    public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";

    public static String sensorServerURL = "http://localhost:8081/IOT_Web_Server/ValidateCard";

    public static final String userid = "16062790"; // change this to be your student-id

    String clientId = userid + "-sub";

    Gson gson = new Gson();

    private MqttClient mqttClient;

    public LockMover() {

        try {
            mqttClient = new MqttClient(BROKER_URL, clientId);
            // mqttClient = new MqttClient(BROKER_URL, userid);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start() {
        RFIDdata rfidData = new RFIDdata(null, null, null, null);
        String doorid = "";
        String stringJson = "";
        try {
            doorid = Integer.toString(PhidgetMotorMover.getInstance().getDeviceSerialNumber());

//            rfidData.setDoorid(doorid);
            stringJson = gson.toJson(rfidData);
            System.out.println("sending data: " + stringJson);
            //sendToServer(stringJson);
        } catch (PhidgetException e1) {
            e1.printStackTrace();
        }

        try {
            mqttClient.setCallback(new MotorSubscribeCallback());
            mqttClient.connect();
            System.out.println("mqtt connected as " + mqttClient.getClientId());

            final String topic = userid + "/B101";
            mqttClient.subscribe(topic);
            System.out.println("motor moving...");

            System.out.println("Subscriber is now listening to " + topic);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String sendToServer(String stringJson) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;

        try {
            stringJson = URLEncoder.encode(stringJson, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String fullURL = sensorServerURL + "?stringJson=" + stringJson;
        System.out.println("Sending data to: " + fullURL); // DEBUG confirmation message
        String line;
        String result = "";

        try {
            url = new URL(fullURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            // Request response from server to enable URL to be opened
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("RESULT: " + result);
        return result;
    }

    public static void main(String... args) {
        final LockMover subscriber = new LockMover();
        subscriber.start();
    }

}
