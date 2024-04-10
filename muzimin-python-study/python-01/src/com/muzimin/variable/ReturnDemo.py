def testReturn():
    temp = 10
    value = 100

    # 如果返回的是元组，则不需要括号包裹起来
    return temp, value


# 如果函数返回的类型是元组，同时希望单独处理元组中的元素，则可以使用多个变量，一次接受函数中的返回值
gl_temp, gl_value = testReturn()

print(gl_temp)
print(gl_value)