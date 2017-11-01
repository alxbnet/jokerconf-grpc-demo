package ru.jokerconf.grpcdemo.dependencies;

import ru.jokerconf.grpcdemo.Coordinates;
import ru.jokerconf.grpcdemo.Temperature;
import ru.jokerconf.grpcdemo.dependencies.TemperatureServiceGrpc.TemperatureServiceImplBase;
import ru.jokerconf.grpcdemo.providers.RandomTemperatureProvider;
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
