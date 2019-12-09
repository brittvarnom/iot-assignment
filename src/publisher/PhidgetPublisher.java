package publisher;

import org.eclipse.paho.client.mqttv3.*;


public class PhidgetPublisher {

//    public static final String BROKER_URL = "tcp://iot.eclipse.org:1883";
     public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";

    public static final String userid = "16062790"; // change this to be your student-id
    
    public static final String TOPIC_ROOM_ID	= userid + "/B101";
    public static final String TOPIC_RFID       = userid + "/rfid";
    public static final String TOPIC_GENERIC    = userid + "/";

    private MqttClient client;


    public PhidgetPublisher() {

        try {
            client = new MqttClient(BROKER_URL, userid);
            // create mqtt session
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setWill(client.getTopic(userid + "/LWT"), "I'm gone :(".getBytes(), 0, false);
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Specific publishing methods for particular phidgets
    public void publishRoomID() throws MqttException {
    	final MqttTopic motorTopic = client.getTopic(TOPIC_ROOM_ID);
    	final String roomid = "roomid";
        System.out.println("Published data. Topic: " + motorTopic.getName() + "   Message: " + roomid);
    }
    
    // More generic publishing methods - avoids having to name every one
    public void publishSensor(String sensorValue, String sensorName) throws MqttException {
        final MqttTopic mqttTopic = client.getTopic(TOPIC_GENERIC + sensorName);
        final String sensor = sensorValue + "";
        mqttTopic.publish(new MqttMessage(sensor.getBytes()));
        System.out.println("Published data. Topic: " + mqttTopic.getName() + "   Message: " + sensor);
    }
    public void publishSensor(int sensorValue, String sensorName) throws MqttException {
    // same as string publisher, just convert int to String
      publishSensor(String.valueOf(sensorValue), sensorName);
    }
    public void publishSensor(float sensorValue, String sensorName) throws MqttException {
    // same as string publisher, just convert float to String
      publishSensor(String.valueOf(sensorValue), sensorName);
    }
    
    public void publishRfid(String rfidTag) throws MqttException {
        final MqttTopic rfidTopic = client.getTopic(TOPIC_RFID);
        final String rfid = rfidTag + "";
        rfidTopic.publish(new MqttMessage(rfid.getBytes()));
        System.out.println("Published data. Topic: " + rfidTopic.getName() + "   Message: " + rfid);
    }

    
    
}
