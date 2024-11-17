package com.ilyadudnikov.math;

import java.util.List;

public interface IVector extends List<Double> {
    IVector negate();
    IVector add(IVector other);
    IVector sub(IVector other);
    IVector mul(double scalar);
//    IVector abs();
}
