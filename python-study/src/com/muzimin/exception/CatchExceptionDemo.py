try:
    num = int(input("请输入一个整数："))

    result = 10 / num

    print(result)
except ValueError:
    print("请输入正确的整数")
except ZeroDivisionError:
    print("0不能当除数")