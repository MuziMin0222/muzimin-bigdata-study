# 多值参数，如果传入的是元组，一般用*args命名，如果传入的是字典，则一般用*kwargs
def demo(num, *args, **kwargs):
    print(num)
    print(args)
    print(kwargs)


demo(1)
demo(1, 2, 3, 4)
demo(1, 2, 3, 4, name="aaa", age=12)


# 字典和元组传入多值参数的方法时，需要进行拆包
def demo01(*args, **kwargs):
    print(args)
    print(kwargs)


gl_nums = (1, 2, 3)
gl_dict = {'a': 1, 'b': 3}
demo01(*gl_nums, **gl_dict)
