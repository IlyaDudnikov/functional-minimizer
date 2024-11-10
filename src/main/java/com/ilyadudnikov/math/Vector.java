package com.ilyadudnikov.math;

import java.util.ArrayList;

public class Vector extends ArrayList<Double> implements IVector{
    public Vector() {
        super();
    }

    public Vector(IVector addition) {
        super(addition);
    }

    public Vector(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public IVector negate() {
        IVector result = new Vector();
        for (Double value : this) {
            result.add(-value);
        }
        return result;
    }

    @Override
    public IVector add(IVector other) {
        IVector result = new Vector();
        if (this.size() != other.size()) {
            throw new IllegalArgumentException("Vectors must be the same size");
        }
        for (int i = 0; i < this.size(); i++) {
            result.add(this.get(i) + other.get(i));
        }
        return result;
    }

    @Override
    public IVector sub(IVector other) {
        IVector result = new Vector();
        if (this.size() != other.size()) {
            throw new IllegalArgumentException("Vectors must be the same size");
        }
        for (int i = 0; i < this.size(); i++) {
            result.add(this.get(i) - other.get(i));
        }
        return result;
    }

    @Override
    public IVector mul(double scalar) {
        IVector result = new Vector();
        for (Double value : this) {
            result.add(value * scalar);
        }
        return result;
    }
}
