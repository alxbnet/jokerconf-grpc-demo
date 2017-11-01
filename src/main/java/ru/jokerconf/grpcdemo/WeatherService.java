package ru.jokerconf.grpcdemo;

import ru.jokerconf.grpcdemo.Humidity;
import ru.jokerconf.grpcdemo.Temperature;
import ru.jokerconf.grpcdemo.WeatherRequest;
import ru.jokerconf.grpcdemo.WeatherResponse;
import ru.jokerconf.grpcdemo.WeatherServiceGrpc.WeatherServiceImplBase;
import io.grpc.stub.StreamObserver;

import static ru.jokerconf.grpcdemo.Temperature.Units.CELSUIS;

/**
 * Returns hard-coded weather response.
 */
public class WeatherService extends WeatherServiceImplBase {

    @Override
    public void getCurrent(WeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {
        WeatherResponse response = WeatherResponse.newBuilder()
                .setTemperature(Temperature.newBuilder().setUnits(CELSUIS).setDegrees(20.f))
                .setHumidity(Humidity.newBuilder().setValue(.65f))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
