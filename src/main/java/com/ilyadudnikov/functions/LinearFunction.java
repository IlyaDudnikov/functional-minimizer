package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;

public class LinearFunction implements IParametricFunction, IDifferentiableFunction {
    private IVector parameters;

    public LinearFunction(IVector parameters) {
        bind(parameters);
    }

    @Override
    public IVector gradient(IVector point) {
        if (parameters == null || parameters.size() == 1) {
            return new Vector();
        }
        IVector gradient = new Vector();
        gradient.addAll(parameters);
        gradient.remove(gradient.size() - 1);
        return gradient;
    }

    @Override
    public double value(IVector point) {
        double res = 0;
        for (int i = 0; i < point.size(); i++) {
            res += parameters.get(i) * point.get(i);
        }
        res += parameters.get(point.size());
        return res;
    }

    @Override
    public IFunction bind(IVector parameters) {
        this.parameters = parameters;
        return this;
    }
}
