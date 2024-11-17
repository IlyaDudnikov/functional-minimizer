package com.ilyadudnikov.optimizators;

import com.ilyadudnikov.Utils;
import com.ilyadudnikov.functionals.IFunctional;
import com.ilyadudnikov.functionals.L1Functional;
import com.ilyadudnikov.functionals.L2Functional;
import com.ilyadudnikov.functionals.LinfFunctional;
import com.ilyadudnikov.functions.IParametricFunction;
import com.ilyadudnikov.functions.LinearFunction;
import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MinimizerMonteCarloTests {
    private final IOptimizator optimizator = new MinimizerMonteCarlo();;
    private IParametricFunction function;
    private IFunctional functional;

    private final IVector initial = new Vector();
    private final Optional<IVector> minimum = Optional.of(new Vector());
    private final Optional<IVector> maximum = Optional.of(new Vector());

    private final List<IVector> points = new ArrayList<>();
    private final List<Double> funcPoints = new ArrayList<>();

    @Test
    void linearL1() {
        readLinearFunction();
        function = new LinearFunction();
        functional = new L1Functional(points, funcPoints);
        IVector res = optimizator.minimize(functional, function, initial, minimum, maximum);

        for (Double r : res) {
            System.out.print(" " + r);
        }
        System.out.println();

    }

    @Test
    void linearL2() {
        readLinearFunction();
        function = new LinearFunction();
        functional = new L2Functional(points, funcPoints);
        IVector res = optimizator.minimize(functional, function, initial, minimum, maximum);

        for (Double r : res) {
            System.out.print(" " + r);
        }
        System.out.println();

    }

    @Test
    void linearLinf() {
        readLinearFunction();
        function = new LinearFunction();
        functional = new LinfFunctional(points, funcPoints);
        IVector res = optimizator.minimize(functional, function, initial, minimum, maximum);

        for (Double r : res) {
            System.out.print(" " + r);
        }
        System.out.println();

    }

    void readLinearFunction() {
        try {
            Utils.readFileParams("linear_params.txt", initial, minimum.get(), maximum.get());
            Utils.readFilePoints("linear_points.txt", points, funcPoints);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
