class Wowen:
    def __init__(self, name):
        self.name = name
        # 私有属性，外界无法访问
        self.__age = 18

    # 私有方法，无法被访问
    def __getAge(self):
        print("this wowen name is %s, age is %d" % (self.name, self.__age))


lz = Wowen("lz")


# 如果要强制使用私有属性和方法，可以通过_类名__方法名（属性名）,这种方法在开发过程中不推荐使用
print(lz._Wowen__age)
lz._Wowen__getAge()