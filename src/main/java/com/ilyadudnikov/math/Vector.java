package com.ilyadudnikov.math;

import java.util.ArrayList;
import java.util.Collections;

public class Vector extends ArrayList<Double> implements IVector{
    public Vector() {
        super();
    }

    public Vector(IVector vec) {
        super(vec);
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

    public void setSize(int size) {
        while (this.size() < size) {
            this.add(0.0);
        }
        while (this.size() > size) {
            this.remove(this.size() - 1);
        }
    }

    public void fill(double value) {
        Collections.fill(this, value);
    }
}
