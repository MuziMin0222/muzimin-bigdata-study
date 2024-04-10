class Tool(object):
    count = 0

    def __init__(self, name):
        self.name = name
        Tool.count += 1


t1 = Tool("A")
t2 = Tool("B")
t3 = Tool("C")

# 结果是3
print(Tool.count)
