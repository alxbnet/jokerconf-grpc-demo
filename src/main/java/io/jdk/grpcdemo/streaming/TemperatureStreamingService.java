package io.jdk.grpcdemo.streaming;

import io.jdk.grpcdemo.Coordinates;
import io.jdk.grpcdemo.Temperature;
import io.jdk.grpcdemo.providers.RandomTemperatureProvider;
import io.jdk.grpcdemo.streaming.TemperatureStreamingServiceGrpc.TemperatureStreamingServiceImplBase;
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
