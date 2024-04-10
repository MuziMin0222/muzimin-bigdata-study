# 定义一个字符串
str1 = "this is a 'string'"
str2 = 'this is a "string" too'

# 输出字符串
print(str1)
print(str2)

# 通过所以获取字符串中的字符
print(str1[1])
print(str2[0])

# 使用for循环遍历字符串
for char in str1:
    print("字符：%s" % char)

# 统计字符串的长度
print("字符串长度：%d" % len(str1))

# 统计子字符串在大字符串中出现的个数
print("子字符串is在大字符串中出现的个数：%d" % str1.count("is"))
print("子字符串abc在大字符串中出现的个数：%d" % str1.count("abc"))

# 某一个子字符串中出现的位置
print("子字符串is在大字符串中出现的第一个位置：%d" % str1.index("is"))
# 如果子字符串在大字符串中不存在，程序会报错
# print("子字符串abc在大字符串中出现的第一个位置：%d" % str1.index("abc"))

# 判断是否是空白字符，包括(\t,\n,\r,空格)，返回True
print("   ".isspace())  # true
print("aaa a sa".isspace())  # false

# 如果String至少有一个字符并且所有字符都是字母或数字则返回True
print("aaa".isalnum())  # True
print("aa a".isalnum())  # False
print("123".isalnum())  # True

# 如果String中至少有一个字符并且所有字符都是字符则返回True
print("aaa".isalpha())  # True
print("aa a".isalpha())  # False
print("123".isalpha())  # False

# 如果String只包含数字则返回True，全角数字
print("aaa".isdecimal())  # False
print("aa a".isdecimal())  # False
print("123".isdecimal())  # True
print("\u00b2".isdecimal())  # False

# 如果String只包含数字则返回True，全角数字, 转义数字
print("aaa".isdigit())  # False
print("aa a".isdigit())  # False
print("123".isdigit())  # True
print("\u00b2".isdigit())  # True

# 如果String只包含数字则返回True，全角数字，汉字数字
print("123".isnumeric())  # True
print("\u00b2".isnumeric())  # True
print("壹".isnumeric())  # True
print("asc".isnumeric())  # False

# 如果String是标题化(每个单词的首字母大写)则返回True
print("This Is A Title".istitle())  # True
print("This Is A title".istitle())  # False

# 如果String中包含至少一个区分大小写的字符，并且所有这些区分大小写字符都是小写，则为True
print("this is lower string".islower())  # True
print("This is Lower String".islower())  # False

# 如果String中包含至少一个区分大小写的字符，并且所有这些(分区大小写的)字符都是大写，则为True
print("THIS IS UPPSER STRING".isupper())  # True
print("This is Lower String".isupper())  # False

# 检查字符串是否以某一个字符串开头，是则返回True
print("String".startswith("Str"))  # true
print("String".startswith("str"))  # false

# 检查字符串是否以某一个字符串结尾，是则返回True
print("String".endswith("ing"))  # True
print("string".endswith("ING"))  # False

# 检查Str是否包含String中，如果Start和end有指定范围，则检查是否包含在指定范围内，如果是，返回开始的索引值，否则返回-1
# 类似的方法还有rflind：从右边开始查找；index: 与Find方法类似，只不过str不在String中会报错；rindex：与Index类似，不过从右边开始
print("String".find("tr"))  # 1
print("String String".find("tr", 7, 10))  # 8

# replace： 把string中的oldStr替换为newStr，如果num指定，则替换不超过num次
print("this is oldStr,i am want to oldStr replace newStr".replace("oldStr", "replaceStr",
                                                                  1))  # this is replaceStr,i am want to oldStr replace newStr
print("this is oldStr,i am want to oldStr replace newStr".replace("oldStr",
                                                                  "replaceStr"))  # this is replaceStr,i am want to replaceStr replace newStr

# 把字符串的第一个字符大写
print("muzimin Study Python".capitalize())  # Muzimin study python

# 把字符串的每一个单词首字母进行大写
print("muzimin Study Python".title())  # Muzimin Study Python

# String中所有的大写转为小写
print("Muzimin Study Python".lower())  # muzimin study python

# String中的小写转为大写
print("muzimin study python".upper())  # MUZIMIN STUDY PYTHON

# 翻转String中的大小写
print("Muzimin Study Python".swapcase())  # mUZIMIN sTUDY pYTHON

# 返回一个原字符串左/居中/右对齐，并使用空格填充至长度width的新字符串（\u3000是全角空格的含义,即中文的Acill值）
lineList = ["静夜思", "李白", "窗前明月光", "疑似地上霜", "举头望明月", "低头思故乡"]
for line in lineList:
    print("|%s|" % line)

for line in lineList:
    print("|%s|" % line.ljust(10, "\u3000"))

for line in lineList:
    print("|%s|" % line.center(10, "\u3000"))

for line in lineList:
    print("|%s|" % line.rjust(10, "\u3000"))

# 去掉String左边开始的空白字符
print("  aaa  ----".lstrip())

# 去掉String右边开始的空白字符
print("   ---  aaa  ----   ".rstrip())

# 去掉String两边的空白字符
print("    ---  aaa  ---   ".strip())

# 把字符串String分成一个3元素的元组
print("this is a string".partition("is"))  # ('th', 'is', ' is a string')

# 类似Partition方法，从右边开始查找
print("this is a string".rpartition("is"))  # ('this ', 'is', ' a string')

# 以某一个字符串进行切割，如果num有指定值，则仅分割num + 1个子字符串，str默认包含换行符，制表符，空格等
print("this is a string".split(" "))  # ['this', 'is', 'a', 'string']
print("this is a string".split(" ", 1))  # ['this', 'is a string']

# 按照行（\r,\n,\r\n）进行分割，返回一个包含各行作为元素的列表
print("aaa\nvvv\nccc\n".splitlines())  # ['aaa', 'vvv', 'ccc']

# 以String作为分隔符，将Seq中所有的元素的字符串合并为一个新的字符串
print("===>".join(["aaa", "bbb", "ccc", "ddd"]))   # aaa===>bbb===>ccc===>ddd
