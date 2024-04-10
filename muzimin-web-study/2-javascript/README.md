# JavaScript的介绍
JavaScript语言诞生主要是完成页面的数据验证。因此允许在客户端，需要允许浏览器来解析JavaScript代码  
特点：  
1. 交互性（信息的动态交互）
2. 安全性（不允许直接访问本地硬盘）
3. 跨平台性（只要是可以解析JS的浏览器都可以执行，和平台无关）
# JavaScript和Html代码的结合使用
* 方式一  
只需要在head标签中，或者body标签中，使用script标签来书写JavaScript代码即可
[案例](static_web/HTML/jsDemo_1.html)
````html
<script type="text/javascript">
    //alert是JavaScript语言提供的一个警告框函数
    //它可以接受任意类型的参数，这个参数就是警告框的提示信息
    alert("hello javaScript");
</script>
````
* 方式2  
使用script标签引入单独的JavaScript代码文件[案例](static_web/HTML/jsDemo_2.html)
```html
<script type="text/javascript" src="../js/demo1.js"></script>
```
# JS的变量
* 变量类型
    * 数值类型
    * 字符串类型
    * 对象类型
    * 布尔类型
    * 函数类型
* JS里面特殊的值
    * undefind  未定义，所有JS变量未赋值的时候，默认值都是undefined
    * null  空值
    * NAN 非数字，非数值 NOT A NUMBER
* 定义变量的格式
```javascript
var 变量名;
var 变量名 = 值;
```
* 关系（比较）运算符  
和Java一致，特殊点在于等于  
等于：==，只比较字面上的结果  
全等于：===，比较全部，包括数据类型
```javascript
var c = "11"
var d = 11
alert(c == d); // true
alert(c === d); //false
```
* 逻辑运算[案例](./static_web/HTML/jsDemo_4.html)  
在JavaScript中，所有的变量都是可以作为一个Boolean值去使用的  
`0，null，undefined，""(空串)都认为是false`
    * 且运算：&&
    * 或运算：||
    * 取反运算：!  
    
1. &&且运算  
当表达式全为真的时候，返回最后一个表达式的值  
当表达式中，有一个为假的时候，返回第一个为假的值
2. || 或运算  
当表达式全为假时，返回最后一个表达式的值  
只有一个表达式为真。就会返回第一个为真的表达式的值
3. &&和||有短路现象：即&&或者||有了结果之后，后面的表达式不再执行
# 数组
[案例](./static_web/HTML/jsDemo_5.html)
* 定义方式  
格式：  
var 数组名 = [];   //空数组
var 数组名 = [1,"aaa",true]; //定义数组的同时赋值元素
```javascript
var arr = [];
alert(arr.length) //0
//在JavaScript中的数组，是可以根据最大下标值进行扩容操作的
arr[0] = "111"
alert(arr.length) //1
arr[4] = true
alert(arr.length) //5
//遍历
for (var i = 0; i < arr.length; i++) {
    alert(arr[i])
}
```
# 函数
[案例](./static_web/HTML/jsDemo_6.html)`在Java中函数允许重载，但是在JavaScript中函数不能重载，只会覆盖上一次的定义`
* 第一种，可以使用function关键字来定义函数，格式为  
```javascript
function 函数名(形参列表) {
  函数体
}
```
* 第二种  
```html
var 函数名 = function(形参列表){函数体}
```
* 函数的argument隐形参数（只在function函数内）,相当于Java的可变参数
# JS中的自定义对象
[案例](./static_web/HTML/jsDemo_7.html)
* 对象的定义
```javascript
//方式一
var 变量名 = new Object();      //定义一个实例对象
变量名.属性名 = 属性值;          //定义一个属性
变量名.函数名 = function() {}   // 定义一个函数

//方式二
var 变量名 = {
    属性名: 属性值,
    属性名1: 属性值,
    函数名: function() {
      函数体
    } 
}
```
* 对象的访问
```javascript
变量名.属性/函数名();
```
# JS中的事件
事件就是电脑输入设备与页面进行交互的响应。  
* **常用的事件**
    * onload 加载完成事件：页面加载完成之后，常用于做页面JS代码初始化操作
    * onclick 点击事件：常用于按钮的点击响应操作
    * onblur 失去焦点事件：常用于输入框失去焦点后验证其输入内容是否合法,所谓焦点就是光标所在位置
    * onchange 内容发生改变事件：常用于下拉列表和输入框内容发送改变后操作
    * onsubmit 表单提交事件：常用于表单提交前，验证所有表单项是否合法
* 事件的注册(绑定)：即告诉浏览器，当事件响应后要执行哪些操作的代码
    * 静态注册事件：通过HTML标签的事件属性直接赋予事件响应后的代码
    * 动态注册事件：通过JS代码得到标签的dom对象，然后再通过dom对象.事件名 = function(){}这种形式赋予事件响应后的代码
* 加载onload事件[演示](static_web/HTML/event/onloadDemo.html)
    * 静态加载
    ```html
     <body onload="onloadFunc()">
     <script type="text/javascript">
          function onloadFunc() {
              alert("静态注册onload事件")
          }
     </script>
    ``` 
  * 动态加载
  ```html
   <script type="text/javascript">
       window.onload = function () {
           alert("动态加载onload事件")
       }
   </script>
  ```
* 加载onclick事件[演示](static_web/HTML/event/onclickDemo.html)
```html
<head>
    <script type="text/javascript">
        function onclickFunc() {
            alert("这是静态点击事件")
        }

        window.onclick = function () {
            //document 即jsDemo_9的整个文件对象
            //getElementById 通过ID获取元素
            var elementById = document.getElementById("btn01");
            elementById.onclick = function () {
                alert("动态注册的onclick事件")
            }
        }
    </script>
</head>
<body>
    <button onclick="onclickFunc()">按钮1</button>
    <button id = "btn01">按钮2</button>
</body>
```
* 加载onblur事件[演示](./static_web/HTML/event/onblurDemo.html)
```html
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript">
        //静态注册
        function onblurFunc() {
            console.log("静态注册失去焦点事件")
        }

        //动态注册
        window.onblur = function () {
            var elementById = document.getElementById("password");
            elementById.onblur = function () {
                console.log("动态主持失去焦点事件")
            }
        }
    </script>
</head>
<body>
    账号：<input type="text" onblur="onblurFunc()"><br>
    密码：<input type="password" id="password">
</body>
```
* 记载onchange事件[演示](./static_web/HTML/event/onchangeDemo.html)
```html
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript">
        function onchangeFunc() {
            alert("爱好已经发送改变。。。。")
        }

        window.onchange = function () {
            var elementById = document.getElementById("select");
            elementById.onchange = function () {
                alert("擅长已经发送改变。。。。")
            }
        }

    </script>
</head>
<body>
    爱好：<br>
    <select onchange="onchangeFunc()">
        <option>---请选择---</option>
        <option>spark</option>
        <option>flink</option>
        <option>MapReduce</option>
    </select><br>

    擅长：<br>
    <select id="select">
        <option>---请选择---</option>
        <option>spark</option>
        <option>flink</option>
        <option>MapReduce</option>
    </select>
```
* 加载onsubmit事件[演示](./static_web/HTML/event/onsubmitDemo.html)
```html
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript">
        function onsubmitFunc() {
            alert("静态加载onsubmit，验证数据不合法，不提交表单信息")
            return false
        }

        window.onsubmit = function () {
            var elementById = document.getElementById("form");
            elementById.onsubmit = function () {
                alert("动态加载onsubmit，验证数据不合法，不提交表单信息")
                return false
            }
        }
    </script>
</head>
<body>
    <form action="http://localhost:8080" method="get" onsubmit="return onsubmitFunc()">
        <input type="submit" value="静态加载">
    </form><br>
    <form id="form" action="http://localhost:8080" method="get">
        <input type="submit" value="动态加载">
    </form>
</body>
```
# DOM模型
DOM全称是Document Object Model文档对象模型，将文档中的标签，属性，文本，转换成对象来管理。  
![如图所示](./image/js1.png)
* 对Document对象的理解
    * Document它管理了所有的HTML文档内容
    * Document它是一种树结构的文档，有层级关系
    * 让我们把所有的标签都对象化
    * 通过document访问所有的标签对象
* Document对象中的方法介绍
    * 通过标签的id属性查找标签dom对象，elementId是标签的ID属性值  
    ```document.getElementById(elementId)```  
    数据校验的案例[演示](./static_web/HTML/document/getElementByIdDemo.html)
    * 通过标签的name属性查找标签的dom对象，elementName标签的name属性值  
    ```document.getElementByName(elementName)```  
    全选和全不选的案例[演示](./static_web/HTML/document/getElementByNameDemo.html)
    * 通过标签名查找标签的dom对象，tagname是标签名  
    ```document.getElementByTagName(tagname)```
    * 通过给定的标签名，创建一个标签对象，tagName是要创建的标签名  
    ```document.createElement(tagName)```
* 节点的常用属性和方法
    * 属性
        * 获取当前节点的所有子节点  
        childNodes
        * 获取当前节点的第一个子节点  
        firstChild
        * 获取当前节点的最后一个子节点  
        lastChild
        * 获取当前节点的父节点  
        parentNode
        * 获取当前节点的下一个节点  
        nextSibling
        * 获取当前节点的上一个节点  
        previousSibling
        * 获取或设置标签的class属性值  
        className
        * 获取/设置起始标签和结束标签中的内容  
        innerHTML
        * 获取/设置起始标签和结束标签中的文本          
        innerText