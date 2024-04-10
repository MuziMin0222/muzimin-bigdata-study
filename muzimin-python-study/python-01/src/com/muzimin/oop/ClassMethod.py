class Tool(object):
    count = 0

    @classmethod
    def classMethod(cls):
        print("工具个数%d" % cls.count)

    def __init__(self, name):
        self.name = name
        Tool.count += 1


t1 = Tool("A")
t2 = Tool("B")
t3 = Tool("C")

print(Tool.count)  # 结果是3
Tool.classMethod()  # 工具个数3
