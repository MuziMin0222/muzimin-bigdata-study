from distutils.core import setup

setup(
    name="setup",   # 包名
    description="setup demo", # 描述信息
    long_description="long long long description",  # 详细的描述信息
    author="muzimin", # 作者
    author_email="muzimin@demo.com", # 作者邮箱
    url="www.muzimin.com",  # 主页
    py_modules=[
        "File1.send",
        "File2.receive"
    ]
)