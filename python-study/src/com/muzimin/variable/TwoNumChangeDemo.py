# 方案1：使用临时变量
a = 10
b = 100

# temp = a
# a = b
# b = temp
#
# print(a)
# print(b)


# 方案2： 使用加总再相减算法
# a = a + b
# b = a - b
# a = a - b
# print(a)
# print(b)

# 方案3：使用元组进行替换
a, b = (b, a)
# 或者
a, b = b, a


def demo(list):
    list.remove(1)

    return list


gl_list = [1, 2, 3]
print(demo(gl_list))
print(gl_list)