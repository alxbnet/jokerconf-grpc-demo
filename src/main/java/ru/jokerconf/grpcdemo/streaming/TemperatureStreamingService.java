package ru.jokerconf.grpcdemo.streaming;

import ru.jokerconf.grpcdemo.Coordinates;
import ru.jokerconf.grpcdemo.Temperature;
import ru.jokerconf.grpcdemo.providers.RandomTemperatureProvider;
import ru.jokerconf.grpcdemo.streaming.TemperatureStreamingServiceGrpc.TemperatureStreamingServiceImplBase;
import io.grpc.stub.StreamObserver;

/**
 * Periodically streams random {@link Temperature} values.
 */
public class TemperatureStreamingService extends TemperatureStreamingServiceImplBase {

    private final RandomValuesStreamer<Temperature> valuesStreamer =
            new RandomValuesStreamer<>(new RandomTemperatureProvider());

    @Override
    public StreamObserver<Coordinates> observe(StreamObserver<Temperature> responseObserver) {
        return valuesStreamer.stream(responseObserver);
    }
}
