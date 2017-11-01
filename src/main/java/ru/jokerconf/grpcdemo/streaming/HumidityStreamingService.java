package ru.jokerconf.grpcdemo.streaming;

import ru.jokerconf.grpcdemo.Coordinates;
import ru.jokerconf.grpcdemo.Humidity;
import ru.jokerconf.grpcdemo.providers.RandomHumidityProvider;
import ru.jokerconf.grpcdemo.streaming.HumidityStreamingServiceGrpc.HumidityStreamingServiceImplBase;
import io.grpc.stub.StreamObserver;

/**
 * Periodically streams random {@link Humidity} values.
 */
public class HumidityStreamingService extends HumidityStreamingServiceImplBase {

    private final RandomValuesStreamer<Humidity> valuesStreamer =
            new RandomValuesStreamer<>(new RandomHumidityProvider());

    @Override
    public StreamObserver<Coordinates> observe(StreamObserver<Humidity> responseObserver) {
        return valuesStreamer.stream(responseObserver);
    }
}
