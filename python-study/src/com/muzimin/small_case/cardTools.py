memberInfo = []


def showMenu():
    print("*" * 50)
    print("欢迎使用Python小案例")
    print("")
    print("操作1，表示增加数据")
    print("操作2，表示删除数据")
    print("操作3，表示修改数据")
    print("操作4，表示查询所有数据")
    print("操作5，表示通过姓名查询数据")
    print("*" * 50)


def createCard():
    print("-" * 50)
    print("增加数据")
    name = input("请输入姓名：")
    age = input("请输入年龄：")
    gender = input("请输入性别：")
    memberInfo.append({
        "name": name,
        "age": age,
        "gender": gender})

    print("新增用户 %s 增加成功" % name)


def updateByName():
    print("-" * 50)
    print("修改数据")
    name = input("请输入你要更新的姓名：")

    for item in memberInfo:
        if item["name"] == name:
            updateMsg = input("请问你要更新什么信息：")

            if updateMsg == "age":
                updateAge = input("请输入你要更新的年龄：")
                item["age"] = updateAge
                break
            elif updateMsg == "gender":
                updateGender = input("请输入你要更新的性别：")
                item["gender"] = updateGender
                break
            elif updateMsg == "name":
                updateName = input("请输入你要更新的姓名：")
                item["name"] = updateName
                break
            else:
                print("没有更新的信息，请重新修改")

    else:
        print("没有找到该用户")


def deleteByName():
    print("-" * 50)
    print("删除数据")
    name = input("请输入你要删除的姓名：")

    for item in memberInfo:
        if item["name"] == name:
            memberInfo.remove(item)
            break
    else:
        print("没有找到该用户")


def selectAll():
    print("-" * 50)
    print("查询全部数据")

    if len(memberInfo) == 0:
        print("当前没有用户，请添加新用户")

        # 可以返回一个函数的执行结果，下方代码就不再执行，如果return没有任何内容，就会回到调用函数的位置，并不返回任何的结果
        return

    print("%s\t\t%s\t\t%s" % ("姓名", "年龄", "性别"))
    print("=" * 50)
    for item in memberInfo:
        print("%s\t\t%s\t\t%s" % (item["name"], item["age"], item["gender"]))


def selectByCard():
    name = input("请输入你要查找的姓名: ")

    for item in memberInfo:
        if item["name"] == name:
            print("找到了 %s 的信息" % name)
            print("%s\t\t%s\t\t%s" % ("姓名", "年龄", "性别"))
            print("=" * 50)
            print("%s\t\t%s\t\t%s" % (item["name"], item["age"], item["gender"]))

            break
    else:
        print("没有找到 %s 的用户信息" % name)
