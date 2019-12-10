package publisher;

import org.eclipse.paho.client.mqttv3.*;
import com.google.gson.Gson;

import reader.RFIDdata;

public class PhidgetPublisher {
    public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";

    public static final String userid = "16062790";

    public static final String TOPIC_RESPONSE = userid + "/";

    RFIDdata rfiDdata = new RFIDdata(null, null, null, null);
    private MqttClient client;
    Gson gson = new Gson();

    public PhidgetPublisher() {

        try {
            client = new MqttClient(BROKER_URL, userid);
            // create mqtt session
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setWill(client.getTopic(userid + "/LWT"), "I'm gone :(".getBytes(), 0, false);
            client.connect(options);
            System.out.println("mqtt connected as " + client.getClientId());
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void publishResponse(String jsonResponse) throws MqttException {
        final RFIDdata response = gson.fromJson(jsonResponse, RFIDdata.class);
        System.out.println("RESPONSE= " + response);
        System.out.println("publisher mqtt connected as " + client.getClientId());
        final MqttTopic mqttTopic = client.getTopic(TOPIC_RESPONSE + response.getRoomid());

        System.out.println("BYTES= " + jsonResponse.getBytes());
        mqttTopic.publish(new MqttMessage(jsonResponse.getBytes()));

        System.out.println("Published data. Topic: " + mqttTopic.getName() + "   Message: " + jsonResponse);
    }
}
