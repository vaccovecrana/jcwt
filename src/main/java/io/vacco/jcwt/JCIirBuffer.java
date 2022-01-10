package io.vacco.jcwt;

import java.util.Arrays;

public class JCIirBuffer {

  public final double[] out;
  public final double smoothingFactor;

  public JCIirBuffer(int size, double smoothFactor) {
    this.out = new double[size];
    this.smoothingFactor = smoothFactor;
    Arrays.fill(out, 0);
  }

  public void update(double[] in) {
    for (int i = 0; i < out.length; i++) {
      out[i] = out[i] + (in[i] - out[i]) / smoothingFactor;
    }
  }

}
