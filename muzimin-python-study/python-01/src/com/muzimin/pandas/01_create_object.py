import numpy as np
import pandas as pd

## 使用列表生成DataFrame
s = pd.Series([1, 3, 5, np.nan, 6, 8])

print(s)
print("----------------")

# 用含日期时间索引与标签的 NumPy 数组生成 DataFrame
dates = pd.date_range('20230222', periods=6)
print(dates)

print("----------------")

df = pd.DataFrame(np.random.randn(6, 4), index=dates, columns=list('ABCD'))
print(df)
print("----------------")

# 用 Series 字典对象生成 DataFrame:
df2 = pd.DataFrame({'A': 1.,
                    'B': pd.Timestamp('20130102'),
                    'C': pd.Series(1, index=list(range(4)), dtype='float32'),
                    'D': np.array([3] * 4, dtype='int32'),
                    'E': pd.Categorical(["test", "train", "test", "train"]),
                    'F': 'foo'})
print(df2)

# 查看数据
print(df.head(2))
print(df.tail(2))

# 显示索引与列名：
print(df.index)
print(df.columns)

# dataframe转NumPy,DataFrame.to_numpy() 的输出不包含行索引和列标签
print(df.to_numpy())

# describe() 可以快速查看数据的统计摘要：
print(df.describe())

# 转置数据：
print(df.T)

# 按轴排序：
print(df.sort_index(axis=1, ascending=False))

# 按值排序：
print(df.sort_values(by='B'))

# 选择单列，产生 Series，与 df.A 等效：
print(df['A'])
print(df.B)

# 用 [ ] 切片行
print(df[0:3])
print(df['20230224':'20230226'])

# 用标签提取一行数据：
print(df.loc[dates[0]])

# 用标签选择多列数据：
print(df.loc[:, ['A', 'B']])

# 用标签切片，包含行与列结束点：
print(df.loc['20230224':'20230226', ['A', 'B']])

# 返回对象降维：
print(df.loc['20230224', ['A', 'B']])

# 提取标量值：
print(df.loc[dates[0], 'A'])
print(df.at[dates[0], 'A'])

# 用整数位置选择, 比如获取第三行数据：
print(df.iloc[3])

# 类似 NumPy / Python，用整数列表按位置切片：
print(df.iloc[[1, 2, 4], [0, 2]])

# 显式整行切片
print(df.iloc[1:3, :])

# 显式整列切片：
print(df.iloc[:, 1:3])

# 显式提取值：
print(df.iloc[1, 1])
print(df.iat[1, 1])

# 用单列的值选择数据
print(df[df.A > 0])

# 选择 DataFrame 里满足条件的值 不满足条件的置空：
print(df[df > 0])

# 用 isin() 筛选：
df2 = df.copy()
df2['E'] = ['one', 'one', 'two', 'three', 'four', 'three']
print(df2[df2['E'].isin(['two', 'four'])])
