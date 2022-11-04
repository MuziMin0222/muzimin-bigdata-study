# 计算容器中的元素个数
print(len(["a", "b", "c"]))  # 3

# 删除变量
aList = ["a", "b", "c"]
del aList[1]
print(aList)  # ['a', 'c']

del (aList[1])
print(aList)  # ['a']

del aList
# print(aList)  # 报错

# 返回容器中元素最大的值，如果是字典，只针对Key比较
print(max(["a", "b", "c"]))  # c
print(max(("a", "b", "c")))  # c
print(max({"a": "z", "b": "y", "c": "x"}))  # c

# 返回容器中元素最小的值，如果是字典，只针对key比较
print(min(["a", "b", "c"]))  # c
print(min(("a", "b", "c")))  # c
print(min({"a": "z", "b": "y", "c": "x"}))  # c

# 切片,字典不允许切片
print(["a", "b", "c"][::-1])  # ['c', 'b', 'a']
print(("a", "b", "c")[::-1])  # ('c', 'b', 'a')

# 运算符
print(["a", "b"] + ["c"])  # ['a', 'b', 'c']
print((1, 2) + (5,))  # (1, 2, 5)
print("aa" + "c")  # aac

print(["aa"] * 3)  # ['aa', 'aa', 'aa']
print(("bb",) * 3)  # ('bb', 'bb', 'bb')
print("cc" * 3)  # cccccc

print(3 in [1, 2, 3])  # True
print(3 in (1, 2, 3))  # True
print("3" in "123")  # True
print("3" in {"3": 1, "2": 2, "1": 3})  # True

print(3 not in [1, 2, 3])  # False
print(3 not in (1, 2, 3))  # False
print("3" not in "123")  # False
print("3" not in {"3": 1, "2": 2, "1": 3})  # False

print([1, 2, 3] <= [1, 2, 3, 4])  # True
print((1, 2, 3) <= (1, 2, 3, 4))  # True
print("124" <= "125")  # True
