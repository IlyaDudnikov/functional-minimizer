package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.IVector;

public interface IParametricFunction {
    IFunction bind(IVector parameters);
}
