package io.vacco.jcwt;

import static io.vacco.jcwt.JFft.*;

public class JCwt {

  public static class JCwtWorkBuffers {

    public int octaves, voices, signalSize;
    public double[] Ri2, Ri1, Ii1, Ii, Ri, FFTBuf;
    public double[][] rOut, iOut;

    public static JCwtWorkBuffers forSignal(int signalSize, int octaves, int voices) {
      JCwtWorkBuffers buffers = new JCwtWorkBuffers();
      buffers.octaves = octaves;
      buffers.voices = voices;
      buffers.signalSize = signalSize;
      buffers.Ri2 = new double[signalSize];
      buffers.Ri1 = new double[signalSize];
      buffers.Ii1 = new double[signalSize];
      buffers.Ri = new double[signalSize];
      buffers.Ii = new double[signalSize];
      buffers.FFTBuf = new double[(signalSize * 2) + 1];
      buffers.rOut = new double[octaves * voices][signalSize];
      buffers.iOut = new double[octaves * voices][signalSize];
      return buffers;
    }
  }

  public static void morletFrequency(double cf, double scale, double[] w, int isize) {
    double tmp;
    int i;
    for (i = 0; i < isize; i++) {
      tmp = scale * i * TWOPI / isize - cf;
      tmp = -(tmp * tmp) / 2;
      w[i] = Math.exp(tmp);
    }
  }

  public static void cwtMorlet(double[] rIn, double[] iIn, JCwtWorkBuffers b, double centerFreq) {
    int i, j, k = 0;
    double a;

    for (i = 0; i < b.signalSize; i++) {
      b.Ri[i] = rIn[i];
      b.Ii[i] = iIn[i];
    }

    doubleFft(b.Ri1, b.Ii1, b.Ri, b.Ii, b.FFTBuf, b.signalSize, -1);

    for (i = 1; i <= b.octaves; i++) {
      for (j = 0; j < b.voices; j++) {
        a = Math.pow(2, (i + j / ((double) b.voices)));
        morletFrequency(centerFreq, a, b.Ri2, b.signalSize);
        multi(b.Ri1, b.Ii1, b.Ri2, b.rOut[k], b.iOut[k], b.signalSize);
        doubleFft(b.rOut[k], b.iOut[k], b.rOut[k], b.iOut[k], b.FFTBuf, b.signalSize, 1);
        k++;
      }
    }
  }

}
