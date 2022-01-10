package io.vacco.jcwt;

import com.esotericsoftware.jsonbeans.Json;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import java.io.*;
import java.util.Arrays;

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
      Json js = new Json();
      double[] eas = js.fromJson(double[].class, new File("./src/test/resources/samples-eas.json"));
      // double[] signalI = new double[signalR.length];

      double[] sr, si;

      sr = padPow2(eas);
      si = new double[sr.length];

      JCwt.JCwtWorkBuffers buffers = JCwt.JCwtWorkBuffers.forSignal(sr.length, 32, 4);
      File spectrum = new File("./build/cwt-scalogram.csv");
      PrintWriter p = new PrintWriter(new FileWriter(spectrum));

      JCIirBuffer acc = new JCIirBuffer(buffers.rOut.length, 1);

      JCwt.cwtMorlet(sr, si, buffers, Math.PI * 2);

      double[] timeBuff = new double[buffers.rOut.length];

      for (int j = 0; j < buffers.rOut[0].length; j++) {
        for (int k = 0; k < timeBuff.length; k++) {
          timeBuff[k] = buffers.rOut[k][j];
        }
        acc.update(timeBuff);
        if (j % 8 == 0) { // timeBuff
          p.println(Arrays.toString(acc.out).replace("[", "").replace("]", ""));
        }
      }

      p.close();
    });
  }

}
