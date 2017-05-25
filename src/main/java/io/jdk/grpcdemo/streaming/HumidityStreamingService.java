package io.jdk.grpcdemo.streaming;

import io.jdk.grpcdemo.Coordinates;
import io.jdk.grpcdemo.Humidity;
import io.jdk.grpcdemo.providers.RandomHumidityProvider;
import io.jdk.grpcdemo.streaming.HumidityStreamingServiceGrpc.HumidityStreamingServiceImplBase;
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
