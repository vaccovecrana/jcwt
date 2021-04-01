package io.vacco.jcwt;

import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

import static io.vacco.jcwt.JSineSignal.*;
import static j8spec.J8Spec.*;

@RunWith(J8SpecRunner.class)
public class JCwtSpec {

  static {
    it("Can perform CWT analysis on a sine wave signal", () -> {
      double[] signalI = new double[signalR.length];
      JCwt.JCwtWorkBuffers buffers = JCwt.JCwtWorkBuffers.forSignal(signalR.length, 8, 4);
      File spectrum = new File("./build/cwt-scalogram.csv");
      PrintWriter p = new PrintWriter(new FileWriter(spectrum));

      JCwt.cwtMorlet(signalR, signalI, buffers, Math.PI * 2);
      for (double[] b : buffers.rOut) {
        p.println(Arrays.toString(b).replace("[", "").replace("]", ""));
      }
      p.close();
    });
  }

}
