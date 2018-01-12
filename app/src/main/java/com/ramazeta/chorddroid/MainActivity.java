package com.ramazeta.chorddroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        Chord chord = Chord.lookup("C");
        try {
            instrumentGuitar = new Instrument(getAssets(), Instrument.GUITAR);
            instrumentUkulele = new Instrument(getAssets(), Instrument.UKULELE_BARITONE);
        } catch (IOException e){
            e.printStackTrace();
        }
//        instrumentGuitar.setTranspositionSteps(2);
        Shape shapeGuitar = instrumentGuitar.getShape(chord, 0);
        Shape shapeUkulele = instrumentUkulele.getShape(chord, 0);
        chordViewGuitar.setShape(shapeGuitar);
        chordViewUkulele.setShape(shapeUkulele);
    }
}
