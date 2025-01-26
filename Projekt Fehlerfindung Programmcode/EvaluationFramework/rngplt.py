import matplotlib.pyplot as plt

with open("results/rng.txt", "r") as rngfile:
    lines = rngfile.readlines()

print(lines)

data = {
    "x": []
}

algos = ["asp", "hit", "pro", "tar", "och", "mcc"]
metrics = ["prec", "rec", "f1", "acc", "wexam", "bexam", "time"]

while len(lines) > 5:
    # Pop „Testing with n test cases“
    case_num = int(lines.pop(0).split(" ")[2])
    data["x"].append(case_num)

    for algo in algos:
        # Pop algorithm description
        lines.pop(0)

        # Pop metrics
        for metric in metrics:
            value = float(lines.pop(0).split(" ")[2])

            if algo not in data:
                data[algo] = {}

            if metric not in data[algo]:
                data[algo][metric] = []

            data[algo][metric].append(value)

    # Pop total time
    lines.pop(0)

for algo in algos:
    plt.plot(data["x"], data[algo]["f1"], label=algo)
plt.legend()
plt.show()
