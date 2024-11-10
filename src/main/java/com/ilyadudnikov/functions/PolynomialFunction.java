package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.IVector;

public class PolynomialFunction implements IParametricFunction, IFunction {
    private IVector parameters;

    public PolynomialFunction() {}

    public PolynomialFunction(IVector parameters) {
        bind(parameters);
    }

    @Override
    public double value(IVector point) {
        double p = point.get(0);
        int n = parameters.size();
        double res = 0;
        for (int i = 0; i < n; i++) {
            res += parameters.get(n - 1 - i) * Math.pow(p, i);
        }
        return res;
    }

    @Override
    public IFunction bind(IVector parameters) {
        this.parameters = parameters;
        return this;
    }
}
