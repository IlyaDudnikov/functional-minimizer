package com.ilyadudnikov.optimizators;

import com.ilyadudnikov.functionals.IFunctional;
import com.ilyadudnikov.functions.IParametricFunction;
import com.ilyadudnikov.math.IVector;

import java.util.Optional;

public interface IOptimizator {
    IVector minimize(IFunctional objective, IParametricFunction function, IVector initialParameters, Optional<IVector> minimumParameters, Optional<IVector> maximumParameters);
}
