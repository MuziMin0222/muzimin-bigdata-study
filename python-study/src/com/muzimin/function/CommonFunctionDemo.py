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


