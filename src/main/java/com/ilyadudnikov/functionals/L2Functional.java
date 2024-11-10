package com.ilyadudnikov.functionals;

import com.ilyadudnikov.functions.IDifferentiableFunction;
import com.ilyadudnikov.functions.IFunction;
import com.ilyadudnikov.math.IMatrix;
import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Matrix;
import com.ilyadudnikov.math.Vector;

import java.util.List;

public class L2Functional implements IDifferentiableFunctional, ILeastSquaresFunctional {
    private List<IVector> points;
    private List<Double> func_points;

    public L2Functional() {}
    public L2Functional(List<IVector> points, List<Double> func_points) {
        this.points = points;
        this.func_points = func_points;
    }

    @Override
    public IVector gradient(IFunction function) {
        IDifferentiableFunction differentiableFunction = (IDifferentiableFunction) function;

        IVector addition = differentiableFunction.gradient(points.get(0));
        if (addition.isEmpty()) {
            return new Vector();
        }

        IVector res = new Vector(addition.size());
        res.add(0.0);

        for (int i = 1; i < points.size(); i++) {
            addition = differentiableFunction.gradient(points.get(i));
            if (addition.isEmpty()) {
                return new Vector();
            }
            double sub = function.value(points.get(i)) - func_points.get(i);
            if (sub > 0) {
                res = res.add(addition.mul(2).mul(Math.abs(sub)));
            } else {
                res = res.sub(addition.mul(2).mul(Math.abs(sub)));
            }
        }
        return res;
    }

    @Override
    public IVector residual(IFunction function) {
        IVector res = new Vector();
        for (int i = 0; i < points.size(); i++) {
            res.add(function.value(points.get(i)) - func_points.get(i));
        }
        return res;
    }

    @Override
    public IMatrix jacobian(IFunction function) {
        IDifferentiableFunction differentiableFunction = (IDifferentiableFunction) function;
        IMatrix res = new Matrix();

        for (IVector point : points) {
            IVector a = differentiableFunction.gradient(point);
            if (!a.isEmpty()) {
                res.add(a);
            }
        }
        return res;
    }

    @Override
    public double value(IFunction function) {
        double res = 0.0;
        for (int i = 0; i < points.size(); i++) {
            res += (function.value(points.get(i)) - func_points.get(i)) * (function.value(points.get(i)) - func_points.get(i));
        }
        return Math.sqrt(res);
    }
}
