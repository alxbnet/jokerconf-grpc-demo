package ru.jokerconf.grpcdemo.dependencies;

import ru.jokerconf.grpcdemo.*;
import ru.jokerconf.grpcdemo.WeatherServiceGrpc.WeatherServiceImplBase;
import ru.jokerconf.grpcdemo.dependencies.HumidityServiceGrpc.HumidityServiceBlockingStub;
import ru.jokerconf.grpcdemo.dependencies.TemperatureServiceGrpc.TemperatureServiceBlockingStub;
import ru.jokerconf.grpcdemo.dependencies.WindServiceGrpc.WindServiceBlockingStub;
import io.grpc.stub.StreamObserver;

/**
 * Sends sync (blocking) requests to temperature, humidity and wind services,
 * combines their responses and sends it back to a client.
 */
public class WeatherBlockingService extends WeatherServiceImplBase {

    private final TemperatureServiceBlockingStub temperatureService;
    private final HumidityServiceBlockingStub humidityService;
    private final WindServiceBlockingStub windService;

    public WeatherBlockingService(TemperatureServiceBlockingStub temperatureService,
                                  HumidityServiceBlockingStub humidityService,
                                  WindServiceBlockingStub windService) {
        this.temperatureService = temperatureService;
        this.humidityService = humidityService;
        this.windService = windService;
    }

    @Override
    public void getCurrent(WeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {

        Coordinates coordinates = request.getCoordinates();

        Temperature temperature = temperatureService.getCurrent(coordinates);
        Wind wind = windService.getCurrent(coordinates);
        Humidity humidity = humidityService.getCurrent(coordinates);

        WeatherResponse response = WeatherResponse.newBuilder()
                .setTemperature(temperature).setHumidity(humidity).setWind(wind)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
