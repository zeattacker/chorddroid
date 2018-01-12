package com.ramazeta.chordlib.interfaces;

import com.ramazeta.chordlib.models.Note;

import java.util.Set;

/**
 * Created by rama on 13/01/18.
 */

public interface Polynote<X extends Transposable<X>> extends Transposable<X> {

    public Set<Note> getNotes();

    public X transpose(int steps);

}
