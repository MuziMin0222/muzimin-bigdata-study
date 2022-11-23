class Animal:
    def eat(self):
        print("吃")

    def drink(self):
        print("喝")

    def play(self):
        print("玩耍")

    def sleep(self):
        print("睡觉")


class Dog(Animal):
    def dark(self):
        print("狗叫")

    def sleep(self):
        print("不用睡觉")
        print("父类的方法：", end="\t")
        # 调用父类的方法需要用super().来进行引用
        super().sleep()


class XiaoTianQuan(Dog):
    def fly(self):
        print("飞")


xtq = XiaoTianQuan()

xtq.eat()
xtq.drink()
xtq.play()
xtq.sleep()
xtq.dark()
xtq.fly()
