package io.vacco.jcwt;

public class JFft {

  public static double TWOPI = 6.28318530717959;

  public static void four1(double[] data, int nn, int isign) {
    int n, mmax, m, j, istep, i;
    double wtemp, wr, wpr, wpi, wi, theta;
    double tempr, tempi;

    n = nn << 1;
    j = 1;
    for (i = 1; i < n; i += 2) {
      if (j > i) {
        tempr = data[j];
        data[j] = data[i];
        data[i] = tempr;

        tempr = data[j + 1];
        data[j + 1] = data[i + 1];
        data[i + 1] = tempr;
      }

      m = n >> 1;
      while (m >= 2 && j > m) {
        j -= m;
        m >>= 1;
      }
      j += m;
    }

    mmax = 2;
    while (n > mmax) {
      istep = 2 * mmax;
      theta = TWOPI / (isign * mmax);
      wtemp = Math.sin(0.5 * theta);
      wpr = -2.0 * wtemp * wtemp;
      wpi = Math.sin(theta);
      wr = 1.0;
      wi = 0.0;

      for (m = 1; m < mmax; m += 2) {
        for (i = m; i <= n; i += istep) {
          j = i + mmax;
          tempr = wr * data[j] - wi * data[j + 1];
          tempi = wr * data[j + 1] + wi * data[j];
          data[j] = data[i] - tempr;
          data[j + 1] = data[i + 1] - tempi;
          data[i] += tempr;
          data[i + 1] += tempi;
        }
        wr = (wtemp = wr) * wpr - wi * wpi + wr;
        wi = wi * wpr + wtemp * wpi + wi;
      }
      mmax = istep;
    }
  }

  public static void doubleFft(double[] Or, double[] Oi,
                               double[] Ir, double[] Ii,
                               double[] tmp, int size, int sign) {
    int i, idx;
    double mag;

    for (i = 0; i < size; i++) {
      idx = 2 * i + 1;
      tmp[idx] = Ir[i];
      tmp[idx + 1] = Ii[i];
    }

    four1(tmp, size, sign);

    for (int k = 0; k < tmp.length; k++) {
      tmp[k] = tmp[(k + 1) % tmp.length];
    }

    mag = sign == -1 ? 1.0 / size : 1.0;

    for (i = 0; i < size; i++) {
      Or[i] = tmp[2 * i] * mag;
      Oi[i] = tmp[2 * i + 1] * mag;
    }
  }

  public static void multi(double[] Ri1, double[] Ii1, double[] Ri2,
                           double[] Or, double[] Oi, int size) {
    int i;
    for (i = 0; i < size; i++) {
      Or[i] = Ri1[i] * Ri2[i];
      Oi[i] = Ii1[i] * Ri2[i];
    }
  }

}
