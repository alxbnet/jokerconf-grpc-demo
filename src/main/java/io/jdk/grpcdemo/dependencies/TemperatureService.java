package io.jdk.grpcdemo.dependencies;

import io.jdk.grpcdemo.Coordinates;
import io.jdk.grpcdemo.Temperature;
import io.jdk.grpcdemo.dependencies.TemperatureServiceGrpc.TemperatureServiceImplBase;
import io.jdk.grpcdemo.providers.RandomTemperatureProvider;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

/**
 * Replies with randomly generated {@link Temperature}.
 */
public class TemperatureService extends TemperatureServiceImplBase {

    private final Supplier<Temperature> temperatureProvider = new RandomTemperatureProvider();

    @Override
    public void getCurrent(Coordinates request, StreamObserver<Temperature> responseObserver) {
        responseObserver.onNext(temperatureProvider.get());
        responseObserver.onCompleted();
    }

}
