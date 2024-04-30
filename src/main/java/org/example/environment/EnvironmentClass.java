package org.example.environment;

import org.example.distribution.Distribution;

import java.util.Iterator;

public class EnvironmentClass<T extends Distribution> implements Iterator<T> {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }

}
