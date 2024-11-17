package com.ilyadudnikov.optimizators;

import com.ilyadudnikov.functionals.IFunctional;
import com.ilyadudnikov.functions.IFunction;
import com.ilyadudnikov.functions.IParametricFunction;
import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;

import java.util.Optional;
import java.util.Random;

public class MinimizerMonteCarlo implements IOptimizator {
    private int maxIter = 100_000;
    double eps = 1e-1;

    @Override
    public IVector minimize(IFunctional objective, IParametricFunction function, IVector initialParameters,
                            Optional<IVector> minimumParameters, Optional<IVector> maximumParameters) {
        int iMin = 0;
        double t = 100_000;
        IVector param = new Vector();
        IVector minParam = new Vector();
        param.addAll(initialParameters);
        minParam.addAll(initialParameters);
        IFunction fun = function.bind(param);
        double currentMin = objective.value(fun);
        double f = currentMin;
        Random rand  = new Random();

//        for (Double a : param) {
//            System.out.print(" " + a);
//        }
//        System.out.print("\t f=" + currentMin);

        for (int i = 1; i <= maxIter && Math.abs(f) >= eps; i++) {
            t = t / i;
            for (int j = 0; j < param.size(); j++) {
                param.set(j, rand.nextDouble() * (maximumParameters.get().get(j) - minimumParameters.get().get(j)) + minimumParameters.get().get(j));
            }
            f = objective.value(function.bind(param));

            if (Math.abs(Math.exp((currentMin - f) / t)) >= rand.nextDouble()) {
                currentMin = f;
                for (int j = 0; j < param.size(); j++) {
                    minParam.set(j, param.get(j));
                }
                iMin = i;
            }
//            System.out.println();
//            System.out.print(i + ":");
//
//            for (Double a : param) {
//                System.out.print(" " + a);
//            }
//            System.out.print("\t f=" + f);
        }
//        System.out.println("\n" + iMin);

//        System.out.println("\n" + currentMin);
        System.out.println("f = " + currentMin);

        return minParam;
    }
}
