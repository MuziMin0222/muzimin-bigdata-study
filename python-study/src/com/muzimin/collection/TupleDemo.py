# 定义一个元组
tupleInfo = ("aaaa", 19, 1.134, "aaaa")

# 创建空元组
tupleEmpty = ()

# 定义一个元素的元组
tupleSingle = (5,)

# 获取元组数据
print(tupleInfo[0])

# 获取元素的索引
print(tupleInfo.index("aaaa"))

# 获取某个数据在tupe中的次数
print(tupleInfo.count("aaaa"))

# 返回tuple中元素的个数
print(len(tupleInfo))

# 使用迭代遍历元组, 在实际的开发过程中，除非能够确认元组的数据类型，否则针对元组的循环遍历的需求并不是很多
for myInfo in tupleInfo:
    print(myInfo)

# 元组和列表相互转化
listInfo = {1, 2, 3, 4}
# 列表转为元组
list2Tuple = tuple(listInfo)
print(type(list2Tuple))

# 元组转为列表
print(type(list(tupleInfo)))
