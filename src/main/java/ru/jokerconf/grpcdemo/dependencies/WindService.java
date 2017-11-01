package ru.jokerconf.grpcdemo.dependencies;

import ru.jokerconf.grpcdemo.Coordinates;
import ru.jokerconf.grpcdemo.Wind;
import ru.jokerconf.grpcdemo.dependencies.WindServiceGrpc.WindServiceImplBase;
import ru.jokerconf.grpcdemo.providers.RandomWindProvider;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

/**
 * Replies with randomly generated {@link Wind}.
 */
public class WindService extends WindServiceImplBase {

    private final Supplier<Wind> windProvider = new RandomWindProvider();

    @Override
    public void getCurrent(Coordinates request, StreamObserver<Wind> responseObserver) {
        responseObserver.onNext(windProvider.get());
        responseObserver.onCompleted();
    }
}