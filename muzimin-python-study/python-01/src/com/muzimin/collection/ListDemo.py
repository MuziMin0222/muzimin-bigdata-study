# 定义一个列表
nameList = ["a", "b", "c", "d", "d", "d", "d", "d"]

# 获取元素
print(nameList[0])
print(nameList[1])
print(nameList[2])
print(nameList[3])

print("-------获取元素在列表中的索引位置----------")
# 获取元素在列表中的索引位置
print(nameList.index("a"))
print(nameList.index("b"))
print(nameList.index("c"))
print(nameList.index("d"))

print("------修改列表中的元素-------")
nameList[1] = "修改index = 1的新元素"

print("------增加新元素--------------------")
nameList.append("末尾新增元素")
nameList.insert(3, "在index = 3的位置插入新元素")
# extend会在列表末尾追加列表
newList = ["new_1", "new_2", "new_3"]
nameList.extend(newList)

print("-------删除元素---------------")
# 剔除列表中指定的元素
nameList.remove("a")
# 像栈一样将数据进行弹出
nameList.pop()
# 指定某个元素进行删除
nameList.pop(0)
# 使用del关键字对变量在内存中进行删除，也可以删除列表中的元素，删除后的变量不可以在后续代码中使用
del nameList[5]

# 清空列表所有元素
# nameList.clear()

# del nameList

print("--------统计列表中的元素个数------")
# len函数可以统计到列表元素的总个数
print("列表中包含 %d 个元素" % len(nameList))

# count函数可以统计列表中某一个数据出现的次数
print("列表中存在d的元素个数 %d" % nameList.count("d"))

# 升序
nameList.sort()

# 降序
nameList.sort(reverse=True)

# 列表翻转
nameList.reverse()

# 遍历列表
for myName in nameList:
    print(myName)

print("--------输出整个列表-----------")
# 输出整个列表
print(nameList)
