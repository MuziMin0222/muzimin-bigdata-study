line = 1

while line <= 9:
    row = 1

    while row <= line:
        print("*", end=" ")
        row += 1

    print("")
    line += 1

print("=================")

line = 1
while line <= 9:
    row = 1

    while row <= line:
        print("%d * %d = %d" % (row, line, line * row), end="\t")
        row += 1

    line += 1
    print("")
