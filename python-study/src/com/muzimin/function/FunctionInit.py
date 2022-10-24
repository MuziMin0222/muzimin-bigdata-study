def test01():
    print("this is function")


def sumNumber(num: int, num2: int) -> int:
    """
    两数之和
    :param num: 数字1
    :param num2:  数字2
    :return: 数字的和
    """
    return num + num2


def testFunction():
    test01()
    print(sumNumber(10, 50))
