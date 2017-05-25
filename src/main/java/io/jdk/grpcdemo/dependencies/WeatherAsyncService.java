package io.jdk.grpcdemo.dependencies;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.jdk.grpcdemo.*;
import io.jdk.grpcdemo.WeatherServiceGrpc.WeatherServiceImplBase;
import io.jdk.grpcdemo.dependencies.HumidityServiceGrpc.HumidityServiceFutureStub;
import io.jdk.grpcdemo.dependencies.TemperatureServiceGrpc.TemperatureServiceFutureStub;
import io.jdk.grpcdemo.dependencies.WindServiceGrpc.WindServiceFutureStub;
import io.grpc.stub.StreamObserver;

import java.util.List;

/**
 * Sends async requests to temperature, humidity and wind services,
 * combines their responses and sends it back to a client.
 */
public class WeatherAsyncService extends WeatherServiceImplBase {

    private final TemperatureServiceFutureStub tempService;
    private final HumidityServiceFutureStub humidityService;
    private final WindServiceFutureStub windService;

    WeatherAsyncService(TemperatureServiceFutureStub tempService,
                        HumidityServiceFutureStub humidityService,
                        WindServiceFutureStub windService) {
        this.tempService = tempService;
        this.humidityService = humidityService;
        this.windService = windService;
    }

    @Override
    public void getCurrent(WeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {

        Coordinates coordinates = request.getCoordinates();

        ListenableFuture<List<WeatherResponse>> responsesFuture = Futures.allAsList(
                Futures.transform(humidityService.getCurrent(coordinates),
                        (Humidity humidity) -> WeatherResponse.newBuilder().setHumidity(humidity).build()),
                Futures.transform(tempService.getCurrent(coordinates),
                        (Temperature temp) -> WeatherResponse.newBuilder().setTemperature(temp).build()),
                Futures.transform(windService.getCurrent(coordinates),
                        (Wind wind) -> WeatherResponse.newBuilder().setWind(wind).build())
        );

        Futures.addCallback(responsesFuture, new FutureCallback<List<WeatherResponse>>() {
            @Override
            public void onSuccess(List<WeatherResponse> results) {
                WeatherResponse.Builder response = WeatherResponse.newBuilder();
                results.forEach(response::mergeFrom);
                responseObserver.onNext(response.build());
                responseObserver.onCompleted();
            }
            @Override
            public void onFailure(Throwable t) { responseObserver.onError(t); }
        });

    }
}
