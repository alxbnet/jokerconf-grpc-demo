package io.jdk.grpcdemo.streaming;

import io.jdk.grpcdemo.Coordinates;
import io.jdk.grpcdemo.Wind;
import io.jdk.grpcdemo.providers.RandomWindProvider;
import io.jdk.grpcdemo.streaming.WindStreamingServiceGrpc.WindStreamingServiceImplBase;
import io.grpc.stub.StreamObserver;

/**
 * Periodically streams random {@link Wind} values.
 */
public class WindStreamingService extends WindStreamingServiceImplBase {

    private final RandomValuesStreamer<Wind> valuesStreamer = new RandomValuesStreamer<>(new RandomWindProvider());

    @Override
    public StreamObserver<Coordinates> observe(StreamObserver<Wind> responseObserver) {
        return valuesStreamer.stream(responseObserver);
    }

}
