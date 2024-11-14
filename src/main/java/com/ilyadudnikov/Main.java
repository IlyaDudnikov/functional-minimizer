package com.ilyadudnikov;

import com.ilyadudnikov.functionals.IFunctional;
import com.ilyadudnikov.functionals.L2Functional;
import com.ilyadudnikov.functions.*;
import com.ilyadudnikov.math.IVector;
import com.ilyadudnikov.math.Vector;
import com.ilyadudnikov.optimizators.IOptimizator;
import com.ilyadudnikov.optimizators.MinimizerMonteCarlo2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        IOptimizator minimizer = new MinimizerMonteCarlo2();
        IVector initial = new Vector();
        Optional<IVector> minimum = Optional.of(new Vector());
        Optional<IVector> maximum = Optional.of(new Vector());

        List<IVector> points = new ArrayList<>();
        List<Double> funcPoints = new ArrayList<>();
        Utils.readFilePoints("pointsW.txt", points, funcPoints);
        Utils.readFileParams("paramsW.txt", initial, minimum.get(), maximum.get());

        IFunctional functional = new L2Functional(points, funcPoints);

        IParametricFunction funL = new LinearFunction();
        IParametricFunction funP = new PolynomialFunction();
        IParametricFunction funS = new Spline();
        IParametricFunction funW = new PiecewiseLinearFunction();

        IVector res = minimizer.minimize(functional, funW, initial, minimum, maximum);

        System.out.println();
        for (Double a : res) {
            System.out.print(" " + a);
        }

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
//        System.out.printf("Hello and welcome!");

//
    }
}