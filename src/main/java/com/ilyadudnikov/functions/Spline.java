package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spline implements IParametricFunction, IDifferentiableFunction {

    private List<Double> nodes = new ArrayList<>(Arrays.asList(0.0, 15.0));
    private List<Double> coefficients;

    public Spline() {}

    public Spline(IVector parameters) {
        bind(parameters);
    }

    @Override
    public IVector gradient(IVector point) {
        int intervalIndex = findInterval(point.get(0));
        List<Double> basisFunctions = calculateBasisFunctions(point.get(0), nodes.get(intervalIndex), nodes.get(intervalIndex + 1));

        IVector result = new Vector();
        result.addAll(basisFunctions);
        return result;
    }

    @Override
    public double value(IVector point) {
        double x = point.get(0);

        if (x < nodes.get(0) || x > nodes.get(nodes.size() - 1)) {
            throw new IndexOutOfBoundsException("Index: Point is outside of the defined intervals");
        }

        int intervalIndex = findInterval(x);
        List<Double> basisFunctions = calculateBasisFunctions(x, nodes.get(intervalIndex), nodes.get(intervalIndex + 1));

        double result = 0;
        for (int i = 2 * intervalIndex, j = 0; j < 4; i++, j++) {
            result += coefficients.get(i) * basisFunctions.get(j);
        }
        return result;
    }

    @Override
    public IFunction bind(IVector parameters) {
        coefficients = new ArrayList<>();
        coefficients.addAll(parameters);
        return this;
    }

    private int findInterval(double point) {
        int index = 0;
        while (point > nodes.get(index + 1)) {
            index++;
        }
        return index;
    }

    private List<Double> calculateBasisFunctions(double x, double x0, double x1) {
        List<Double> basisFunctions = new ArrayList<>();
        double h = x1 - x0;
        double ksi = (x - x0) / h;

        basisFunctions.add(1 - 3 * ksi * ksi + 2 * ksi * ksi * ksi);
        basisFunctions.add(h * (ksi - 2 * ksi * ksi + ksi * ksi * ksi));
        basisFunctions.add(3 * ksi * ksi - 2 * ksi * ksi * ksi);
        basisFunctions.add(h * (-ksi * ksi + ksi * ksi * ksi));

        return basisFunctions;
    }

}
