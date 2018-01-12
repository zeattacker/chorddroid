package com.ramazeta.chordlib.interfaces;

/**
 * Created by rama on 13/01/18.
 */

public interface Transposable<E extends Transposable<E>> {
    public E transpose(int steps);
}
