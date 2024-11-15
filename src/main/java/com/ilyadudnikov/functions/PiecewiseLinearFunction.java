package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;

import java.util.ArrayList;
import java.util.List;

public class PiecewiseLinearFunction implements IParametricFunction, IDifferentiableFunction {
    List<Double> x;
    List<Double> q;

    public PiecewiseLinearFunction(IVector parameters) {
        bind(parameters);
    }

    @Override
    public IVector gradient(IVector point) {
        int intervalIndex = findIntervalIndex(point.get(0));
        if (intervalIndex == -1) {
            throw new IllegalArgumentException("Point is outside the defined intervals");
        }

        IVector gradientVector = initializeGradientVector();
        applyBasisFunctions(gradientVector, point.get(0), intervalIndex);

        return gradientVector;
    }

    private int findIntervalIndex(double p) {
        for (int i = 0; i < x.size() - 1; i++) {
            if (p >= x.get(i) && p <= x.get(i + 1)) {
                return i;
            }
        }
        return -1;
    }

    private IVector initializeGradientVector() {
        Vector gradientVector = new Vector();
        gradientVector.setSize(q.size());
        return gradientVector;
    }

    private void applyBasisFunctions(IVector gradientVector, double p, int intervalIndex) {
        double intervalLength = x.get(intervalIndex + 1) - x.get(intervalIndex);
        double normalizedPosition = (p - x.get(intervalIndex)) / intervalLength;

        double basisFunction1 = 1 - normalizedPosition;

        gradientVector.set(intervalIndex, basisFunction1);
        gradientVector.set(intervalIndex + 1, normalizedPosition);
    }

    @Override
    public double value(IVector point) {
        double px = point.get(0);

        int iCur = getIntervalIndexByPoint(px);

        double hx = (x.get(iCur + 1) - x.get(iCur));
        double ksi = (px - x.get(iCur)) / hx;
        double psi1 = 1 - ksi;
        return q.get(iCur) * psi1 + q.get(iCur + 1) * ksi;
    }

    private int getIntervalIndexByPoint(double p) {
        int iCur = 0;
        for (int i = 0; i < x.size() - 1; i++) {
            if (p >= x.get(i) && p <= x.get(i + 1)) {
                iCur = i;
                break;
            }
        }
        return iCur;
    }

    @Override
    public IFunction bind(IVector parameters) {
        q = new ArrayList<>();
        q.addAll(parameters);
        return this;
    }
}
