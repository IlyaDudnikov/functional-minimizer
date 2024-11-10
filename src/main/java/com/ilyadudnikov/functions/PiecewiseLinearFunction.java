package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PiecewiseLinearFunction implements IParametricFunction, IDifferentiableFunction {
    private IVector parameters;
    List<Double> x =  new ArrayList<>(Arrays.asList(0.0, 1.0, 2.0, 3.0, 4.0, 5.0));
    List<Double> q;

    public PiecewiseLinearFunction() {}

    public PiecewiseLinearFunction(IVector parameters) {
        bind(parameters);
    }

    @Override
    public IVector gradient(IVector point) {
        IVector res = new Vector();

        int iCur = 0;
        double p = point.get(0);
        for (int i = 0; i < x.size() - 1; i++) {
            if (p == x.get(i) || p == x.get(i + 1)) {
                return res;
            }
            if (p > x.get(i) && p < x.get(i + 1)) {
                iCur = i;
                break;
            }
        }

        for (int i = 0; i < q.size(); i++) {
            res.add(0.0);
        }

        double hx = (x.get(iCur + 1) - x.get(iCur));
        double ksi = (p - x.get(iCur)) / hx;
        double psi1 = 1 - ksi;
        double psi2 = ksi;

        res.set(iCur, psi1);
        res.set(iCur + 1, psi2);

        return res;
    }

    @Override
    public double value(IVector point) {
        int iCur = 0;
        double p = point.get(0);
        for (int i = 0; i < x.size() - 1; i++) {
            if (p >= x.get(i) && p <= x.get(i + 1)) {
                iCur = i;
                break;
            }
        }
        double hx = (x.get(iCur + 1) - x.get(iCur));
        double ksi = (p - x.get(iCur)) / hx;
        double psi1 = 1 - ksi;
        double psi2 = ksi;

        double func = q.get(iCur) * psi1 + q.get(iCur + 1) * psi2;
        return func;
    }

    @Override
    public IFunction bind(IVector parameters) {
        this.parameters = parameters;
        q = new ArrayList<>();
        q.addAll(parameters);
        return this;
    }
}
