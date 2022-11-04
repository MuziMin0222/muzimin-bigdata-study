from com.muzimin.small_case import cardTools

while True:
    cardTools.showMenu()
    actionStr = input("choose your operation please：")
    print("您选择的操作是【%s】" % actionStr)
    if actionStr == "1":
        cardTools.createCard()

    elif actionStr == "2":
        cardTools.deleteByName()

    elif actionStr == "3":
        cardTools.updateByName()

    elif actionStr == "4":
        cardTools.selectAll()

    elif actionStr == "5":
        cardTools.selectByCard()

    elif actionStr == "0":
        print("欢迎再次使用该用户案例")
        break
    else:
        print("您输入的不正确，请重新选择")
