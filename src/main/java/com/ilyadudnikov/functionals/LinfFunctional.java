package com.ilyadudnikov.functionals;

import com.ilyadudnikov.functions.IFunction;
import com.ilyadudnikov.math.IVector;

import java.util.List;

public class LinfFunctional implements IFunctional {
    private List<IVector> points;
    private List<Double> funcPoints;

    public LinfFunctional() {}

    public LinfFunctional(List<IVector> points, List<Double> funcPoints) {
        this.points = points;
        this.funcPoints = funcPoints;
    }

    @Override
    public double value(IFunction function) {
        double sup = Math.abs(function.value(points.get(0)) - funcPoints.get(0));
        for (int i = 1; i < points.size(); i++) {
            double curSup = Math.abs(function.value(points.get(i)) - funcPoints.get(i));
            if (curSup > sup) {
                sup = curSup;
            }
        }
        return sup;
    }
}
