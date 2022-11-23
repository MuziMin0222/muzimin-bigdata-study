class MusicPlayer(object):
    # 记录对象是否被创建
    instance = None
    # 记录初始化方法中的动作是否被执行
    flag = True

    def __init__(self):
        if MusicPlayer.flag:
            MusicPlayer.flag = False
            print("初始化对象")

    def __new__(cls, *args, **kwargs):
        if cls.instance is None:
            cls.instance = super().__new__(cls)
        return cls.instance


mp = MusicPlayer()
mp1 = MusicPlayer()

print(mp)
print(mp1)
