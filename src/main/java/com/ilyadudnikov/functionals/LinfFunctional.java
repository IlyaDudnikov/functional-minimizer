package com.ilyadudnikov.functionals;

import com.ilyadudnikov.functions.IFunction;
import com.ilyadudnikov.math.IVector;

import java.util.List;

public class LinfFunctional implements IFunctional {
    private List<IVector> points;
    private List<Double> func_points;

    public LinfFunctional() {}

    public LinfFunctional(List<IVector> points, List<Double> func_points) {
        this.points = points;
        this.func_points = func_points;
    }

    @Override
    public double value(IFunction function) {
        double sup = Math.abs(function.value(points.get(0)) - func_points.get(0));
        for (int i = 1; i < points.size(); i++) {
            double curSup = Math.abs(function.value(points.get(i)) - func_points.get(i));
            if (curSup > sup) {
                sup = curSup;
            }
        }
        return sup;
    }
}
