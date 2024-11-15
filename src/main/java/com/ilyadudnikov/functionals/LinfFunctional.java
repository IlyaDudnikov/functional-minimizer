package com.ilyadudnikov.functionals;

import com.ilyadudnikov.functions.IFunction;
import com.ilyadudnikov.math.IVector;

import java.util.List;

public class LinfFunctional implements IFunctional {
    private final List<IVector> points;
    private final List<Double> funcPoints;

    public LinfFunctional(List<IVector> points, List<Double> funcPoints) {
        this.points = points;
        this.funcPoints = funcPoints;
    }

    @Override
    public double value(IFunction function) {
        double sup = Math.abs(function.value(points.get(0)) - funcPoints.get(0));
        for (int i = 1; i < points.size(); i++) {
            double curDifference = Math.abs(function.value(points.get(i)) - funcPoints.get(i));
            if (curDifference > sup) {
                sup = curDifference;
            }
        }
        return sup;
    }
}
