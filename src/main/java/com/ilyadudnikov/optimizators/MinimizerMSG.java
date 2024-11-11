package com.ilyadudnikov.optimizators;

import com.ilyadudnikov.functionals.IDifferentiableFunctional;
import com.ilyadudnikov.functionals.IFunctional;
import com.ilyadudnikov.functions.IParametricFunction;
import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;

import java.util.Optional;

public class MinimizerMSG implements IOptimizator {
    private int maxIter = 100_000;
    private double eps = 1e-1;
    private double epsSmall = 1e-3, delta = 1e-9;

    @Override
    public IVector minimize(IFunctional _objective, IParametricFunction function, IVector initialParameters, Optional<IVector> minimumParameters, Optional<IVector> maximumParameters) {
        IDifferentiableFunctional objective = (IDifferentiableFunctional) _objective;
        IVector Sk, nextSk, gradk, nextGradk;
        double lyamk, wk, fk, nextFk;
        int iMin = 0;

        IVector param = new Vector();
        IVector nextParam = new Vector();
        IVector minParam = new Vector();
        param.addAll(initialParameters);
        minParam.addAll(initialParameters);

        Sk = objective.gradient(function.bind(param));
        gradk = Sk;
        nextFk = objective.value(function.bind(param));
        fk = 0;

        int iter = 0;
        for (int i = 0; i < maxIter && norm(Sk) >= eps && Math.abs(nextFk - fk) >= eps; i++) {
            fk = nextFk;
            System.out.print(iter + ": ");
            for (Double s : param) {
                System.out.print(" " + s);
            }
            System.out.print("\t f=" + objective.value(function.bind(param)) + "\n");

            IVector tmpParams1 = new Vector();
            IVector tmpParams2 = new Vector();

            double a = 1e-3, b = 10;

            while (Math.abs(b - a) > epsSmall) {
                double l1 = (a + b - delta) / 2;
                double l2 = (a + b + delta) / 2;
                tmpParams1 = param.add(Sk.mul(l1));
                tmpParams2 = param.add(Sk.mul(l2));
                double funcl1 = objective.value(function.bind(tmpParams1));
                double funcl2 = objective.value(function.bind(tmpParams2));
                if (funcl1 < funcl2) {
                    b = l2;
                } else {
                    a = l1;
                }
            }

            lyamk = a;
            param = param.add(Sk.mul(lyamk));
            nextGradk = objective.gradient(function.bind(param));
            nextFk = objective.value(function.bind(param));
            wk = -(norm(nextGradk) * norm(nextGradk)) / (norm(gradk) * norm(gradk));

            nextSk = nextGradk.negate().add(Sk.mul(wk));
            Sk = nextSk;
            gradk = nextGradk;
            iter++;
        }

        for (Double a : param) {
            System.out.print(" " + a);
        }
        System.out.print("\t f=" + objective.value(function.bind(param)));
        return param;
    }

    private double norm(IVector vector) {
        double res = 0;
        for (Double a : vector) {
            res += a * a;
        }
        return Math.sqrt(res);
    }
}
