def demo01():
    pwd = input("请输入密码：")

    if len(pwd) >= 8:
        return pwd
    else:
        e = Exception("请输入符合标准的密码!")
        raise e


try:
    print(demo01())
except Exception as result:
    print(result)