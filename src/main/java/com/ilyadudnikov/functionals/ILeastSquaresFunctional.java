package com.ilyadudnikov.functionals;

import com.ilyadudnikov.functions.IFunction;
import com.ilyadudnikov.math.IMatrix;
import com.ilyadudnikov.math.IVector;

public interface ILeastSquaresFunctional extends IFunctional {
    IVector residual(IFunction function);
    IMatrix jacobian(IFunction function);
}
