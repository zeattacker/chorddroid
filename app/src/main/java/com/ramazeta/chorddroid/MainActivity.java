package com.ramazeta.chorddroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ramazeta.chordlib.components.ChordView;
import com.ramazeta.chordlib.components.Instrument;
import com.ramazeta.chordlib.components.Shape;
import com.ramazeta.chordlib.models.Chord;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Instrument instrumentGuitar,instrumentUkulele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChordView chordViewGuitar = (ChordView)findViewById(R.id.chordview_guitar);
        ChordView chordViewUkulele = (ChordView)findViewById(R.id.chordview_ukulele);
        Chord chord = Chord.lookup("A");
        try {
            //initialize instrument for guitar
            instrumentGuitar = new Instrument(getAssets(), Instrument.GUITAR);
            // initialize instrument for ukulele
            instrumentUkulele = new Instrument(getAssets(), Instrument.UKULELE);
        } catch (IOException e){
            e.printStackTrace();
        }
        //Transpose
//        instrumentGuitar.setTranspositionSteps(1);

        //Its have some chord variation, or you can use lookup() to get full of List<Shape>
        Shape shapeGuitar = instrumentGuitar.getShape(chord, 0);
        Shape shapeUkulele = instrumentUkulele.getShape(chord, 0);

        //set view
        chordViewGuitar.setShape(shapeGuitar);
        chordViewUkulele.setShape(shapeUkulele);
    }
}
