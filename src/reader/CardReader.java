package reader;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.phidget22.*;
import publisher.PhidgetPublisher;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.google.gson.Gson;

public class CardReader {

    PhidgetPublisher publisher = new PhidgetPublisher();
    RFID rfid = new RFID();
    RFIDdata rfidData = new RFIDdata(null, null, null, null);
    String stringJson = new String();
    Gson gson = new Gson();

    // address of server which will receive sensor data
    public static String sensorServerURL = "http://localhost:8081/IOT_Web_Server/ValidateCard";

    public static void main(String[] args) throws PhidgetException {
        new CardReader();
    }

    public CardReader() throws PhidgetException {
        // Make the RFID Phidget able to detect loss or gain of an rfid card
        rfid.addTagListener(new RFIDTagListener() {
            // What to do when a tag is found

            public void onTag(RFIDTagEvent e) {
                try {
                    String reader = Integer.toString(e.getSource().getDeviceSerialNumber());
                    String tagRead = e.getTag();
                    // optional print, used as debug here
                    System.out.println("DEBUG: Tag read: " + tagRead);
                    System.out.println("DEBUG: Sending new rfid value : " + tagRead);

                    rfidData.setReaderid(reader);
                    rfidData.setTagid(tagRead);

                    stringJson = gson.toJson(rfidData);
                    System.out.println("sending data: " + stringJson);

                } catch (PhidgetException e1) {
                    e1.printStackTrace();
                }
                String jsonResponse = sendToServer(stringJson);
                System.out.println("JSON: " + jsonResponse);

                try {
                    System.out.println("trying to publish response");
                    publisher.publishResponse(jsonResponse);
                } catch (MqttException mqtte) {
                    mqtte.printStackTrace();
                }
            }

        });

        rfid.addTagLostListener(new RFIDTagLostListener() {
            // What to do when a tag is lost
            public void onTagLost(RFIDTagLostEvent e) {
                // optional print, used as debug here
                System.out.println("DEBUG: Tag lost: " + e.getTag());
            }
        });

        // Open and start detecting rfid cards
        rfid.open(5000); // wait 5 seconds for device to respond

        // attach to the sensor and start reading
        try {

            System.out.println("\n\nGathering data for 15 seconds\n\n");
            pause(15);
            rfid.close();
            System.out.println("\nClosed RFID Reader");

        } catch (PhidgetException ex) {
            System.out.println(ex.getDescription());
        }

    }

    public String sendToServer(String stringJson) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        // String fullURL = sensorServerURL + "?tagid=" + cardRead + "&readerid=" +
        // readerId;

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

    private void pause(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
}
