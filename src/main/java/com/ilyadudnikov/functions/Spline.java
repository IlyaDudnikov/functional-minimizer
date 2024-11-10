package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spline implements IParametricFunction, IDifferentiableFunction {
    private IVector parameters;
    private List<Double> x = new ArrayList<>(Arrays.asList(0.0, 15.0));
    private List<Double> q;

    public Spline() {}

    public Spline(IVector parameters) {
        bind(parameters);
    }

    @Override
    public IVector gradient(IVector point) {
        int iCur = 0;
        double p = point.get(0);

        while (p > x.get(iCur + 1)) {
            iCur++;
        }

        IVector res = new Vector();
        List<Double> basis = basis(p, x.get(iCur), x.get(iCur + 1));
        res.addAll(basis);

        return res;
    }

    @Override
    public double value(IVector point) {
        int iCur = 0;
        double res = 0;
        double p = point.get(0);
        while (p > x.get(iCur + 1)) {
            iCur++;
        }
        List<Double> basis = basis(p, x.get(iCur), x.get(iCur + 1));
        for (int i = 2 * iCur, j = 0; j < 4; i++, j++) {
            res += q.get(i) * basis.get(j);
        }
        return res;
    }

    @Override
    public IFunction bind(IVector parameters) {
        this.parameters = parameters;
        q = new ArrayList<>();
        q.addAll(parameters);
        return this;
    }

    private List<Double> basis(double x, double x0, double x1) {
        List<Double> basis = new ArrayList<>();
        double hk = x1 - x0;
        double ksi = (x - x0) / hk;

        basis.add(1 - 3 * ksi * ksi + 2 * ksi * ksi * ksi); //x
        basis.add(hk*(ksi - 2 * ksi * ksi + ksi * ksi * ksi)); //x'

        basis.add(3 * ksi * ksi - 2 * ksi * ksi * ksi); //x+1
        basis.add(hk*(-ksi * ksi + ksi * ksi * ksi)); //x+1'
        return basis;
    }

    private List<Double> basis_diff(double x, double x0, double x1) {
        List<Double> basis = new ArrayList<>();
        double hk = x1 - x0;
        double ksi = (x - x0) / hk;

        basis.add(6 * ksi * (ksi - 1) / hk); //x
        basis.add(1 - 4 * ksi + 3 * ksi * ksi); //x'
        basis.add(6 * ksi * (1 - ksi) / hk); //x+1
        basis.add(ksi * (-2 + 3 * ksi)); //x+1'
        return basis;
    }
}
