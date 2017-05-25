package io.jdk.grpcdemo.providers;

import io.jdk.grpcdemo.Temperature;

import java.util.Random;
import java.util.function.Supplier;

import static io.jdk.grpcdemo.Temperature.Units.CELSUIS;

/**
 * Randomly generates {@link Temperature}.
 */
public class RandomTemperatureProvider implements Supplier<Temperature> {

    private final Random random = new Random();

    @Override
    public Temperature get() {
        float degrees = 10 + random.nextInt(20) + random.nextFloat();
        return Temperature.newBuilder().setDegrees(degrees).setUnits(CELSUIS).build();
    }
}
