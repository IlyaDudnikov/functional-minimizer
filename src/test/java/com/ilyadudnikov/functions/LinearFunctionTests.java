package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinearFunctionTests {
    @Test
    void testValue() {
        IVector parameters = new Vector();
        parameters.add(2.0);  // Коэффициент при x1
        parameters.add(3.0);  // Коэффициент при x2
        parameters.add(1.0);  // Свободный член

        LinearFunction linearFunction = new LinearFunction(parameters);

        IVector point = new Vector();
        point.add(1.0);  // x1 = 1
        point.add(2.0);  // x2 = 2

        double result = linearFunction.value(point);

        assertEquals(2.0 * 1.0 + 3.0 * 2.0 + 1.0, result, 0.0001);  // Ожидаемый результат: 2 + 6 + 1 = 9
    }

    @Test
    void testGradient() {
        IVector parameters = new Vector();
        parameters.add(2.0);  // Коэффициент при x1
        parameters.add(3.0);  // Коэффициент при x2
        parameters.add(1.0);  // Свободный член

        LinearFunction linearFunction = new LinearFunction(parameters);

        IVector point = new Vector();
        point.add(1.0);  // x1 = 1
        point.add(2.0);  // x2 = 2

        IVector gradient = linearFunction.gradient(point);

        assertEquals(2.0, gradient.get(0), 0.0001);  // Ожидаемый градиент по x1
        assertEquals(3.0, gradient.get(1), 0.0001);  // Ожидаемый градиент по x2
    }

    @Test
    void testBind() {
        IVector parameters = new Vector();
        parameters.add(2.0);  // Коэффициент при x1
        parameters.add(3.0);  // Коэффициент при x2
        parameters.add(1.0);  // Свободный член

        LinearFunction linearFunction = new LinearFunction(new Vector());
        linearFunction.bind(parameters);

        IVector resultParameters = linearFunction.gradient(new Vector());

        assertEquals(parameters.get(0), resultParameters.get(0), 0.0001);
        assertEquals(parameters.get(1), resultParameters.get(1), 0.0001);
    }

    @Test
    void testGradientWithSingleParameter() {
        IVector parameters = new Vector();
        parameters.add(3.0);  // Свободный член (вместо коэффициента)

        LinearFunction linearFunction = new LinearFunction(parameters);

        IVector point = new Vector();
        point.add(2.0);  // x1 = 2

        IVector gradient = linearFunction.gradient(point);

        assertTrue(gradient.isEmpty());  // Градиент должен быть пустым, так как нет переменных
    }

    @Test
    void testValueWithSingleParameter() {
        IVector parameters = new Vector();
        parameters.add(3.0);  // Свободный член

        LinearFunction linearFunction = new LinearFunction(parameters);

        IVector point = new Vector();
        point.add(2.0);  // x1 = 2

        double result = linearFunction.value(point);

        assertEquals(3.0, result, 0.0001);  // Ожидаемый результат: только свободный член, значение 3
    }

}

