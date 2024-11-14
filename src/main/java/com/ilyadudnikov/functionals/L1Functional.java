package com.ilyadudnikov.functionals;

import com.ilyadudnikov.functions.IDifferentiableFunction;
import com.ilyadudnikov.functions.IFunction;
import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;

import java.util.List;

public class L1Functional implements IDifferentiableFunctional {
    private List<IVector> points;
    private List<Double> funcPoints;

    public L1Functional() {}

    public L1Functional(List<IVector> points, List<Double> funcPoints) {
        this.points = points;
        this.funcPoints = funcPoints;
    }

    @Override
    public IVector gradient(IFunction function) {
        IDifferentiableFunction differentiableFunction = (IDifferentiableFunction) function;

        IVector addition = differentiableFunction.gradient(points.get(0));
        if (addition.isEmpty()) {
            return new Vector();
        }
        IVector res = new Vector(addition);
        if ((function.value(points.get(0)) - funcPoints.get(0)) < 0) {
            res = res.negate();
        }

        for (int i = 1; i < points.size(); i++) {
            addition = differentiableFunction.gradient(points.get(i));
            if (addition.isEmpty()) {
                return new Vector();
            }

//            double a = function.value(points.get(i));

            if ((function.value(points.get(i)) - funcPoints.get(i)) >= 0) {
                res = res.add(addition);
            } else {
                res = res.sub(addition);
            }
        }
        return res;
    }

    @Override
    public double value(IFunction function) {
        double res = 0.0;
        for (int i = 0; i < points.size(); i++) {
            res += Math.abs(function.value(points.get(i)) - funcPoints.get(i));
        }
        return res;
    }
}
