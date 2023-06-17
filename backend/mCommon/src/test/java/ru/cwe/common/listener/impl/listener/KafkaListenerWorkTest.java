package ru.cwe.common.listener.impl.listener;

import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

// TODO: 17.06.2023 impl
public class KafkaListenerWorkTest {
	private static final String KAFKA_SERVER = "localhost:9092";
	private static final Class<?> PRODUCER_KEY_SERIALIZER = StringSerializer.class;
//	private static final Class<?> PRODUCER_VALUE_SERIALIZER = JsonSerializer.class;

	@Test
	void start() {

	}

// TODO: 17.06.2023 del
//	    public static void main(String[] args) {
//        Properties properties = new Properties();
//        properties.put("bootstrap.servers", "localhost:9092,localhost:9093");
//        properties.put("key.serializer", AlertKeySerde.class.getName());
//        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        properties.put("partitioner.class", AlertLevelPartitioner.class.getName());
//
//        try(KafkaProducer<Alert, String> producer = new KafkaProducer<>(properties)){
//            Alert alert = new Alert(1, "Stage 1", "CRITICAL", "Stage 1 stopped");
//            ProducerRecord<Alert, String> record = new ProducerRecord<>("kinaction_alert", alert, alert.getAlertMessage());
//            producer.send(record, new AlertCallback());
//        }
//    }


// TODO: 17.06.2023 del 
//	    private final String kafkaServer = ;
//
//    @Bean
//    public Map<String, Object> producerConfigs(){
//        return new HashMap<>(){{
//            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
//            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
//            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        }};
//    }
//
//    @Bean
//    public ProducerFactory<Long, UserDto> producerFactory(){
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    public KafkaTemplate<Long, UserDto> kafkaTemplate(){
//        return new KafkaTemplate<>(producerFactory());
//    }


}
