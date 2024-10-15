import pyautogui

# 让所有的指令都停顿一秒
pyautogui.PAUSE = 0.3

# 获取显示屏的分辨率
print(pyautogui.size())

# 获取鼠标的位置
print(pyautogui.position())

# 点击浏览器
pyautogui.click(664, 877)
# 点击ODS设计
pyautogui.click(426, 817)
# 点击ODS表筛选
pyautogui.click(1034, 330)
# 点击筛选确认
pyautogui.click(1366, 801)
# 双击第一行的结果
pyautogui.doubleClick(717, 354)

# 复制第一格数据
# pyautogui.hotkey('command', 'c')
