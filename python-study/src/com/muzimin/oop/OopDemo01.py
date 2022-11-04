class Demo01:
    def method01(self, num):
        print(num + self.name)

    def __init__(self, nameStr):
        print(nameStr)
        self.name = nameStr


demo = Demo01("aaa")
demo.method01("1000")
