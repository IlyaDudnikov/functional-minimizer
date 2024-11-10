package com.ilyadudnikov.functions;

import com.ilyadudnikov.math.IVector;

public interface IDifferentiableFunction extends IFunction {
    IVector gradient(IVector point);
}
