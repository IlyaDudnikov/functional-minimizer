package com.ilyadudnikov.linearFucntion;

import com.ilyadudnikov.Utils;
import com.ilyadudnikov.functionals.IFunctional;
import com.ilyadudnikov.functionals.L1Functional;
import com.ilyadudnikov.functionals.L2Functional;
import com.ilyadudnikov.functionals.LinfFunctional;
import com.ilyadudnikov.functions.IParametricFunction;
import com.ilyadudnikov.functions.LinearFunction;
import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;
import com.ilyadudnikov.optimizators.IOptimizator;
import com.ilyadudnikov.optimizators.MinimizerMonteCarlo;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinearMonteCarlo {
    private IOptimizator optimizator = new MinimizerMonteCarlo();
    private IParametricFunction function = new LinearFunction();

    private IVector initial = new Vector();
    private Optional<IVector> minimum = Optional.of(new Vector());
    private Optional<IVector> maximum = Optional.of(new Vector());

    private List<IVector> points = new ArrayList<>();
    private List<Double> funcPoints = new ArrayList<>();

    {
        try {
            Utils.readFileParams("linear_params.txt", initial, minimum.get(), maximum.get());
            Utils.readFilePoints("linear_points.txt", points, funcPoints);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void l1() {
        IFunctional functional = new L1Functional(points, funcPoints);
        IVector res = optimizator.minimize(functional, function, initial, minimum, maximum);

        System.out.println();
        for (Double a : res) {
            System.out.print(" " + a);
        }
    }

    @Test
    public void l2() {
        IFunctional functional = new L2Functional(points, funcPoints);
        IVector res = optimizator.minimize(functional, function, initial, minimum, maximum);

        System.out.println();
        for (Double a : res) {
            System.out.print(" " + a);
        }

    }

    @Test
    public void linf() {
        IFunctional functional = new LinfFunctional(points, funcPoints);
        IVector res = optimizator.minimize(functional, function, initial, minimum, maximum);

        System.out.println();
        for (Double a : res) {
            System.out.print(" " + a);
        }

    }
}
