package io.vacco.jcwt;

import com.esotericsoftware.jsonbeans.Json;
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

  public static int nextPow2(int x) {
    return x == 1 ? 1 : Integer.highestOneBit(x - 1) * 2;
  }

  public static double[] padPow2(double[] in) {
    double[] out = new double[nextPow2(in.length)];
    System.arraycopy(in, 0, out, 0, in.length);
    return out;
  }

  static {
    it("Can perform CWT analysis on a sine wave signal", () -> {
      Json j = new Json();
      double[] eas = j.fromJson(double[].class, new File("./src/test/resources/samples-eas.json"));

      // double[] signalI = new double[signalR.length];

      double[] sr, si;

      sr = padPow2(eas);
      si = new double[sr.length];

      JCwt.JCwtWorkBuffers buffers = JCwt.JCwtWorkBuffers.forSignal(sr.length, 8, 2);
      File spectrum = new File("./build/cwt-scalogram.csv");
      PrintWriter p = new PrintWriter(new FileWriter(spectrum));

      JCwt.cwtMorlet(sr, si, buffers, Math.PI * 2);
      for (double[] b : buffers.rOut) {
        p.println(Arrays.toString(b).replace("[", "").replace("]", ""));
      }
      p.close();
    });
  }

}
