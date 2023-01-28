file = open("/Users/muzimin/code/IdeaProjects/RoadToBigData/python-study/README.md")

while True:
    content = file.readline()

    if not content:
        break

    print(content, end="")

file.close()
