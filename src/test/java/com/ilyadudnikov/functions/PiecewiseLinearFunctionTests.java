
package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class PiecewiseLinearFunctionTests {

    private PiecewiseLinearFunction function;

    @BeforeEach
    public void setUp() {
        // Создаем параметры для PiecewiseLinearFunction
        IVector parameters = new Vector();
        parameters.add(1.0);  // q1
        parameters.add(3.0);  // q2
        parameters.add(5.0);  // q3

        // Пример x-значений, которые задают интервалы
        function = new PiecewiseLinearFunction(parameters);

        // Заполняем x для функции. Например, интервалы: [0, 1], [1, 2], [2, 3]
        function.x = Arrays.asList(0.0, 1.0, 2.0, 3.0);
    }

    @Test
    public void testValueWithinInterval() {
        IVector point = new Vector();
        point.add(1.5);  // Точка, лежащая между интервалами [1.0, 2.0]

        // Устанавливаем x и q для интервалов
        function.x = Arrays.asList(0.0, 1.0, 2.0, 3.0);  // Интервалы
        function.q = Arrays.asList(1.0, 3.0, 5.0);      // Значения функции

        double result = function.value(point);
        double expected = 4.0;  // Ожидаемое значение: интерполяция между 1.0 и 3.0 для точки 1.5

        assertEquals(expected, result, 0.01, "Value should match the expected value.");
    }


    @Test
    public void testValueAtIntervalBoundary() {
        IVector point = new Vector();
        point.add(1.0);  // Точка на границе интервала [0, 1]

        double result = function.value(point);
        double expected = 3.0;  // Ожидаемое значение: q1 = 1.0

        assertEquals(expected, result, "Value at boundary should be q1.");
    }

    @Test
    public void testGradientWithinInterval() {
        IVector point = new Vector();
        point.add(1.5);  // Точка, лежащая в интервале [1, 2]

        IVector gradient = function.gradient(point);
        assertEquals(3, gradient.size(), "Gradient should have the same size as q.");
        assertTrue(gradient.get(1) > 0, "Second component of gradient should be positive.");
    }

    @Test
    public void testGradientOutsideDefinedIntervals() {
        IVector point = new Vector();
        point.add(4.0);  // Точка за пределами определённых интервалов

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            function.gradient(point);
        });

        assertEquals("Point is outside the defined intervals", thrown.getMessage(), "Exception message should indicate out-of-bounds point.");
    }

    @Test
    public void testBindFunctionWithNewParameters() {
        IVector newParameters = new Vector();
        newParameters.add(2.0);  // Новый параметр
        newParameters.add(4.0);  // Новый параметр
        newParameters.add(6.0);  // Новый параметр

        function.bind(newParameters);
        Vector doubles = new Vector();
        doubles.add(1.5);
        IVector gradient = function.gradient(doubles);  // Тестирование с новым параметром

        assertNotNull(gradient, "The gradient should not be null after binding new parameters.");
    }

    @Test
    public void testValueWithDifferentXIntervals() {
        function.x = Arrays.asList(0.0, 2.0, 4.0);  // Новые интервалы

        IVector point = new Vector();
        point.add(3.0);  // Точка в интервале [2, 4]

        double result = function.value(point);
        double expected = 4.0;  // Ожидаемое значение: q2 = 4.0

        assertEquals(expected, result, "Value should match the expected value for the new intervals.");
    }
}

