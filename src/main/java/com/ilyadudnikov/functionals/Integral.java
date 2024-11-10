package com.ilyadudnikov.functionals;

import com.ilyadudnikov.functions.IFunction;
import com.ilyadudnikov.functions.PiecewiseLinearFunction;
import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;

import java.util.Arrays;
import java.util.List;

public class Integral implements IFunctional {
    private IFunction linFunc = new PiecewiseLinearFunction(new Vector((IVector) Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0)));
    private double hx;
    private double x0, x1;
    private int n;

    public Integral() {}

    public Integral(List<Double> linFuncParams, double x0, double x1, int n) {
        this.linFunc = new PiecewiseLinearFunction(new Vector((IVector) linFuncParams));
        this.hx = (x1 - x0) / n;
        this.x0 = x0;
        this.x1 = x1;
        this.n = n;
    }

    @Override
    public double value(IFunction function) {
        double res = 0;
        for (int i = 0; i < n; i++) {
            IVector tmp = new Vector();
            tmp.add((x0 + i * hx) * 0.5);
            res += (function.value(tmp) - linFunc.value(tmp)) * hx;
        }
        return res;
    }
}
