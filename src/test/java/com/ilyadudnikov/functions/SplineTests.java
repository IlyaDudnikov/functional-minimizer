package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.Vector;
import com.ilyadudnikov.math.IVector;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SplineTests {

    @Test
    public void testValueWithinInterval() {
        IVector parameters = new Vector();
        parameters.addAll(Arrays.asList(1.0, 0.0, 2.0, 0.0)); // Коэффициенты для одного интервала
        Spline spline = new Spline(parameters);

        IVector point = new Vector();
        point.add(7.5); // Точка внутри интервала [0.0, 15.0]

        double expectedValue = 1.5; // Значение при x = 7.5 с учетом базисных функций
        assertEquals(expectedValue, spline.value(point), 1e-6, "Value should match the expected value within the interval.");
    }

    @Test
    public void testValueAtIntervalBoundary() {
        IVector parameters = new Vector();
        parameters.addAll(Arrays.asList(1.0, 0.0, 2.0, 0.0)); // Коэффициенты для одного интервала
        Spline spline = new Spline(parameters);

        IVector point = new Vector();
        point.add(0.0); // Граничная точка

        double expectedValue = 1.0; // Значение при x = 0.0
        assertEquals(expectedValue, spline.value(point), 1e-6, "Value should match the expected value at the interval boundary.");
    }

    @Test
    public void testGradientWithinInterval() {
        IVector parameters = new Vector();
        parameters.addAll(Arrays.asList(1.0, 0.0, 2.0, 0.0)); // Коэффициенты для одного интервала
        Spline spline = new Spline(parameters);

        IVector point = new Vector();
        point.add(7.5); // Точка внутри интервала [0.0, 15.0]

        IVector expectedGradient = new Vector();
        expectedGradient.addAll(Arrays.asList(0.5, 1.875, 0.5, -1.875)); // Ожидаемые базисные функции

        assertEquals(expectedGradient, spline.gradient(point), "Gradient should match the expected gradient.");
    }

    @Test
    public void testValueOutsideDefinedNodes() {
        IVector parameters = new Vector();
        parameters.addAll(Arrays.asList(1.0, 0.0, 2.0, 0.0)); // Коэффициенты для одного интервала
        Spline spline = new Spline(parameters);

        IVector point = new Vector();
        point.add(-1.0); // Точка за пределами интервалов

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> spline.value(point));
        assertTrue(exception.getMessage().contains("Index"));
    }
}
