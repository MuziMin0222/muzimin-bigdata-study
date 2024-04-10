num = 10


def demo01():
    # 声明num是全局变量，后续对全局变量的操作，再使用赋值语句时，就不会创建局部变量
    global num

    num = 99
    print("demo01 %d" % num)


def demo02():
    print("demo02 %d" % num)

demo01()
demo02()