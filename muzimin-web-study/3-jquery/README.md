# jQuery介绍
1. jQuery就是JavaScript和查询(Query),辅助JavaScript开发的JS类库
2. 核心思想：写的更少，做的更多，实现了很多浏览器的兼容问题
3. 免费，开源
4. jQuery的SayHello[案例](./static_web/JqDemo_1.html)
5. 使用jQuery一定要引入jQuery库，jQuery中的$就是一个函数
6. 标签绑定点击事件
    1. 使用jQuery查询到标签对象
    2. 使用标签对象.clik(function(){})
# jQuery核心函数：$
$()括号里面传入不同的值会有不同的效果[案例](./static_web/jQDemo_2.html)
1. 传入参数为\[函数]时：表示页面加载完成之后，相当于window.onload = function(){}
2. 传入参数为\[HTML字符串]时：会对我们创建这个html标签对象
3. 传入参数为\[选择器字符串]时：
    1. $("#id属性值")：id选择器，根据id查询标签对象
    2. $("标签名")：标签名选择器，根据指定的标签名查询标签对象
    3. $(".class属性值")：类型选择器，可以根据class属性查询标签对象
4. 传入参数为\[DOM对象时]：会把这个dom对象转换为jQuery对象
# jQuery对象和dom对象区分
* dom对象
    * 通过getElementById(),getElementByName(),getElementByTagName(),createElement()方法创建的对象是Dom对象
* jQuery对象
    * 通过jQuery提供的API创建的对象
    * 通过jQuery包装的Dom对象
    * 通过jQuery提供的API查询的对象
* jQuery对象的本质是：dom对象的数组+jQuery提供的一系列功能函数
* jQuery对象不能使用dom的对象的属性和方法，反之亦然
* jQuery对象和dom对象的相互转换
    * jQuery对象转dom对象：jQuery对象\[下标] --> dom对象
    * dom对象转为jQuery对象：$(dom对象) -->jQuery对象
# 选择器&过滤器
[具体用法请参考菜鸟教程](https://www.runoob.com/jquery/jquery-ref-selectors.html)
* 基本选择器
    * id
    * element
    * .class
    * \* 
* 层级选择器
# html(),text(),val()
[案例演示](./static_web/jQDemo_3.html),这三个方法中，括号有参数，这是将参数内容设置到标签中，没有参数，就是获取数据
1. html()，设置和获取起始标签和结束标签中的内容，和dom属性innerHTML一样
2. text()，设置和获取起始标签和结束标签中的文本，和dom属性innerText一样
3. val()，设置和获取表单项的value属性值，也可以同时设置多个表单项的选中状态，和dom属性value一样
# attr(),prop()
[案例演示](./static_web/jQDemo_4.html),一个参数的时候是获取值，两个参数的时候是设置值
* attr()：可以设置和获取属性的值，不推荐的操作：checked，readOnly，selected，disabled等等  
attr方法还可以操作非标准的属性
* prop()：可以设置和获取属性的值，推荐的操作：checked，readOnly，selected，disabled等等
# dom的增删改
[案例演示](./static_web/jQDemo_5.html)
* 内部插入
    * appendTo()
    * prependTo()
* 外部插入
    * insertAfter()
    * insertBefore()
* 替换
    * replaceWith()
    * replaceAll()
* 删除
    * remove()
    * empty()