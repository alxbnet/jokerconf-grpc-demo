package ru.jokerconf.grpcdemo.dependencies;

import ru.jokerconf.grpcdemo.Coordinates;
import ru.jokerconf.grpcdemo.Humidity;
import ru.jokerconf.grpcdemo.dependencies.HumidityServiceGrpc.HumidityServiceImplBase;
import ru.jokerconf.grpcdemo.providers.RandomHumidityProvider;
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
