# 定义一个字典列表，字典是一个无序的数据集合
dictionariesInfo = {
    "name": "muzimin",
    "age": 12,
    "high": 180
}

# 输出字典中的数据
print(dictionariesInfo)

# 取值,如果指定的Key不存在，程序会报错
print(dictionariesInfo["name"])

# 增加/修改,如果key不存在，就会新增键值对，如果存在，就会修改key对应的值
dictionariesInfo["add"] = "新增数据"
dictionariesInfo["name"] = "muzimin_new"

# 删除字典中的数据,如果key不存在，程序会报错
dictionariesInfo.pop("age")

# 输出字典中的数据
print(dictionariesInfo)

# 统计键值对的数量
print(len(dictionariesInfo))

# 合并字段,如果合并的字段中保存以及存在的键值对，就会覆盖原有的键值对
updateDic = {"name": "muzimin_update", "price": 20000}
dictionariesInfo.update(updateDic)

# 清空字典
# dictionariesInfo.clear()

# 输出字典中的数据
print(dictionariesInfo)

# 循环遍历字典
for k in dictionariesInfo:
    print("%s -> %s" % (k, dictionariesInfo[k]))
