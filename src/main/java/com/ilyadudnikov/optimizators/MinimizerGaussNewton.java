package com.ilyadudnikov.optimizators;

import com.ilyadudnikov.functionals.IFunctional;
import com.ilyadudnikov.functionals.ILeastSquaresFunctional;
import com.ilyadudnikov.functions.IParametricFunction;
import com.ilyadudnikov.math.IMatrix;
import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Matrix;
import com.ilyadudnikov.math.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MinimizerGaussNewton implements IOptimizator {
    private IMatrix A = new Matrix();
    IVector b = new Vector();

    private void init(IMatrix jac) {
        for (List<Double> a : A) {
            a.clear();
        }
        A.clear();
        ///////////////////////////////////
        b.clear();
        ///////////////////////////////////

        int nParams = jac.get(0).size();
        for (int i = 0; i < nParams; i++) {
            List<Double> tmp = new ArrayList<>();
            for (int j = 0; j < nParams; j++) {
                tmp.add(0.0);
            }
            A.add(tmp);
            b.add(0.0);
        }
    }

    private void makeMatrixA(IMatrix jac) {
        int nParams = jac.get(0).size();
        for (int i = 0; i < nParams; i++) {
            for (int j = 0; j < nParams; j++) {
                A.get(i).set(j, 0.0);

                for (int k = 0; k < jac.size(); k++) {
                    double derI = jac.get(k).get(i);
                    double derJ = jac.get(k).get(j);
                    A.get(i).set(j, A.get(i).get(j) + derI * derJ);
                }
            }
        }
    }

    private void makeVectorB(IMatrix jac, IVector residual) {
        int nParams = jac.get(0).size();
        for (int i = 0; i < nParams; i++) {
            b.set(i, 0.0);
            for (int k = 0; k < jac.size(); k++) {
                double derI = jac.get(k).get(i);
                double dv = residual.get(k);
                b.set(i, b.get(i) - dv * derI);
            }
        }
    }

    private IVector gauss(IMatrix A, IVector b) {
        IVector solve = new Vector();
        int n = A.size();

        List<List<Double>> a = A;
        List<Double> y = b;
        double max;
        int k, index;
        final double eps = 1e-25;
        k = 0;
        while (k < n) {
            max = Math.abs(a.get(k).get(k));
            index = k;
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(a.get(i).get(k)) > max) {
                    max = Math.abs(a.get(i).get(k));
                    index = i;
                }
            }

            if (max <= eps) {
                System.out.println("Решение получить невозможно из-за нулевого столбца ");
                System.out.println(index + " матрицы A");
            }

            for (int j = 0; j < n; j++) {
                double tmp = a.get(k).get(j);
                a.get(k).set(j, a.get(index).get(j));
                a.get(index).set(j, tmp);
            }
            double temp = y.get(k);
            y.set(k, y.get(index));
            y.set(index, temp);

            for (int i = k; i < n; i++) {
                double tmp = a.get(i).get(k);
                if (Math.abs(tmp) > eps) {
                    for (int j = 0; j < n; j++) {
                        a.get(i).set(j, a.get(i).get(j) / temp);
                    }
                    y.set(i, y.get(i) / temp);
                    if (i != k) {
                        for (int j = 0; j < n; j++) {
                            a.get(i).set(j, a.get(i).get(j) - a.get(k).get(j));
                        }
                        y.set(i, y.get(i) - y.get(k));
                    }
                }
            }
            k++;
        }

        List<Double> tmpVec = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            tmpVec.add(0.0);
        }

        for (k = n - 1; k >= 0; k--) {
            tmpVec.set(k, y.get(k));
            for (int i = 0; i < k; i++) {
                y.set(i, y.get(i) - a.get(i).get(k) * tmpVec.get(k));
            }
            y.set(k, y.get(k) / a.get(k).get(k));
        }
        solve.addAll(y);
        return solve;
    }

    @Override
    public IVector minimize(IFunctional objective, IParametricFunction function, IVector initialParameters, Optional<IVector> minimumParameters, Optional<IVector> maximumParameters) {
        double epsBack = 1e-1, epsStag = 1e-3;
        int maxIter = 100_000, numIt = 0;
        IVector param = new Vector();
        param.addAll(initialParameters);

        double firstF = objective.value(function.bind(param));
        System.out.print("first: ");
        for (Double s : param) {
            System.out.print(" " + s);
        }
        System.out.print("\t f=" + firstF);

        double funcRel = 1, prevFuncRel = 2;

        while (firstF > 1e-10 && funcRel > epsBack && numIt < maxIter && Math.abs(funcRel - prevFuncRel) > epsStag) {
            prevFuncRel = funcRel;
            numIt++;

            IMatrix jacobian = ((ILeastSquaresFunctional)objective).jacobian(function.bind(param));
            IVector residual = ((ILeastSquaresFunctional)objective).residual(function.bind(param));
            init(jacobian);
            makeMatrixA(jacobian);
            makeVectorB(jacobian, residual);
            IVector solve = gauss(A, b);
            param = param.add(solve);
            funcRel = objective.value(function.bind(param)) / firstF;
            System.out.print(numIt + ": ");
            for (Double s : param) {
                System.out.print(" " + s);
            }
            System.out.println("\t f=" + funcRel);
        }
        return param;
    }
}
