package io.jdk.grpcdemo.dependencies;

import io.jdk.grpcdemo.Coordinates;
import io.jdk.grpcdemo.Wind;
import io.jdk.grpcdemo.dependencies.WindServiceGrpc.WindServiceImplBase;
import io.jdk.grpcdemo.providers.RandomWindProvider;
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