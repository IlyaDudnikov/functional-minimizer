package com.ilyadudnikov.functionals;

import com.ilyadudnikov.functions.IFunction;
import com.ilyadudnikov.math.IVector;

public interface IDifferentiableFunctional extends IFunctional {
    IVector gradient(IFunction function);
}
