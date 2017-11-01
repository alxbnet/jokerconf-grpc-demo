package ru.jokerconf.grpcdemo.streaming;

import ru.jokerconf.grpcdemo.Coordinates;
import ru.jokerconf.grpcdemo.Wind;
import ru.jokerconf.grpcdemo.providers.RandomWindProvider;
import ru.jokerconf.grpcdemo.streaming.WindStreamingServiceGrpc.WindStreamingServiceImplBase;
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
