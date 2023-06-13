package ru.cwe.common.listener;

public class KafkaListenerThread extends Thread implements ListenerThread {
	// TODO: 13.06.2023 message-buffer
	// TODO: 13.06.2023 properties

	@Override
	public synchronized void start() {
		super.start();
	}

	@Override
	public synchronized void shutdown() {

	}

	@Override
	public void run() {
// TODO: 13.06.2023 del
	/*
@Slf4j
public class Consumer {

    private static final String TOPIC_NAME = "msg";

    private final AtomicBoolean keepConsuming = new AtomicBoolean(true);
    private final Map<String, Object> properties;

    public Consumer(final Map<String, Object> properties) {
        this.properties = properties;
    }

    public void start() {
        Runnable r = () -> {
            try(KafkaConsumer<Long, UserDto> consumer = new KafkaConsumer<>(properties)){
                consumer.subscribe(List.of(TOPIC_NAME));

                while (keepConsuming.get()){
                    ConsumerRecords<Long, UserDto> records = consumer.poll(Duration.ofMillis(250));
                    for (ConsumerRecord<Long, UserDto> record : records) {
                        log.info(" +-+ {}", record);
                    }
                }
            }
        };

        // TODO: 10.06.2023 !!!
        System.out.println("---------------");
        new Thread(r).start();
        System.out.println("++++++++++++++++++");
    }

    @PreDestroy
    public void shutdown() {
        keepConsuming.set(false);
    }
}



	 */
	}
}
