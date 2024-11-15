package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.Vector;
import com.ilyadudnikov.math.IVector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PolynomialFunctionTests {

    @Test
    public void testValueConstantFunction() {
        IVector parameters = new Vector();
        parameters.add(5.0); // Полином: f(x) = 5
        PolynomialFunction function = new PolynomialFunction(parameters);

        IVector point = new Vector();
        point.add(3.0); // Точка, в которой вычисляем

        assertEquals(5.0, function.value(point), "Constant function should always return the constant value.");
    }

    @Test
    public void testValueLinearFunction() {
        IVector parameters = new Vector();
        parameters.add(2.0); // Полином: f(x) = 2x + 1
        parameters.add(1.0);
        PolynomialFunction function = new PolynomialFunction(parameters);

        IVector point = new Vector();
        point.add(3.0);

        assertEquals(7.0, function.value(point), "Linear function f(x) = 2x + 1 should return 7 when x = 3.");
    }

    @Test
    public void testValueQuadraticFunction() {
        IVector parameters = new Vector();
        parameters.add(1.0); // Полином: f(x) = x^2 + 2x + 1
        parameters.add(2.0);
        parameters.add(1.0);
        PolynomialFunction function = new PolynomialFunction(parameters);

        IVector point = new Vector();
        point.add(2.0);

        assertEquals(9.0, function.value(point), "Quadratic function f(x) = x^2 + 2x + 1 should return 9 when x = 2.");
    }

    @Test
    public void testValueZeroFunction() {
        IVector parameters = new Vector();
        parameters.add(0.0); // Полином: f(x) = 0
        PolynomialFunction function = new PolynomialFunction(parameters);

        IVector point = new Vector();
        point.add(4.0);

        assertEquals(0.0, function.value(point), "Zero function should always return 0.");
    }

    @Test
    public void testValueHighDegreePolynomial() {
        IVector parameters = new Vector();
        parameters.add(1.0); // Полином: f(x) = x^3 + x^2 + x + 1
        parameters.add(1.0);
        parameters.add(1.0);
        parameters.add(1.0);
        PolynomialFunction function = new PolynomialFunction(parameters);

        IVector point = new Vector();
        point.add(2.0);

        assertEquals(15.0, function.value(point), "High-degree polynomial should return 15 when x = 2.");
    }

    @Test
    public void testValueNegativeInput() {
        IVector parameters = new Vector();
        parameters.add(3.0); // Полином: f(x) = 3x^2 + 2x + 1
        parameters.add(2.0);
        parameters.add(1.0);
        PolynomialFunction function = new PolynomialFunction(parameters);

        IVector point = new Vector();
        point.add(-2.0);

        assertEquals(9, function.value(point), "Polynomial f(x) = 3x^2 + 2x + 1 should return 9 when x = -2.");
    }

    @Test
    public void testValueWithEmptyParameters() {
        IVector parameters = new Vector(); // Пустой полином
        PolynomialFunction function = new PolynomialFunction(parameters);

        IVector point = new Vector();
        point.add(1.0);

        assertEquals(0.0, function.value(point), "Polynomial with no parameters should return 0.");
    }

    @Test
    public void testBindUpdatesParameters() {
        IVector initialParameters = new Vector();
        initialParameters.add(1.0); // Полином: f(x) = x + 1
        initialParameters.add(1.0);

        PolynomialFunction function = new PolynomialFunction(initialParameters);

        IVector point = new Vector();
        point.add(2.0);

        assertEquals(3.0, function.value(point), "Initial parameters should calculate correctly.");

        IVector newParameters = new Vector();
        newParameters.add(2.0); // Новый полином: f(x) = 2x + 3
        newParameters.add(3.0);

        function.bind(newParameters);

        assertEquals(7.0, function.value(point), "After rebinding, the function should use new parameters.");
    }
}
