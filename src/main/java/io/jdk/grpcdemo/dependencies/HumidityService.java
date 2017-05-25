package io.jdk.grpcdemo.dependencies;

import io.jdk.grpcdemo.Coordinates;
import io.jdk.grpcdemo.Humidity;
import io.jdk.grpcdemo.dependencies.HumidityServiceGrpc.HumidityServiceImplBase;
import io.jdk.grpcdemo.providers.RandomHumidityProvider;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

/**
 * Replies with randomly generated {@link Humidity}.
 */
public class HumidityService extends HumidityServiceImplBase {

    private final Supplier<Humidity> humidityProvider = new RandomHumidityProvider();

    @Override
    public void getCurrent(Coordinates request, StreamObserver<Humidity> responseObserver) {
        responseObserver.onNext(humidityProvider.get());
        responseObserver.onCompleted();
    }

}
