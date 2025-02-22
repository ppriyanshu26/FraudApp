import matplotlib.pyplot as plt
import numpy as np
import io
import base64

def generate_graph():
    x = np.linspace(0, 10, 100)
    y = np.sin(x)

    plt.figure()
    plt.plot(x, y, label="Sine Wave")
    plt.legend()
    plt.xlabel("X-axis")
    plt.ylabel("Y-axis")

    buf = io.BytesIO()
    plt.savefig(buf, format="png")
    buf.seek(0)
    encoded = base64.b64encode(buf.read()).decode('utf-8')
    buf.close()

    return encoded  # Return Base64 string
