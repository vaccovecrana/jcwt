import matplotlib.pyplot as plt
import numpy as np

f = './build/cwt-scalogram.csv'
matrix = np.loadtxt(f, delimiter=',')
matrix = np.rot90(matrix)

fig, ax = plt.subplots()
im = plt.imshow(
    matrix, cmap='viridis', interpolation='nearest',
    origin='upper', aspect='auto',
    vmin=-0.001, vmax=0.001
    # vmin=-0.5, vmax=0.5
)
plt.show()
