# GoodsNumControllerView
Android中一个用于控制商品数量增减的View  


####使用说明   
所有属性：   

| 属性 | 数据类型 | 意义 |                                               
| ------ | ------ | ------ |   
| textSize | px,dp,sp,dimens.xml中的值 |显示商品数量的数字 |                      
| textColor |颜色值,color.xml中的值 | 显示商品数量的数字颜色 |     
| leftCircleBackgroundColor | 颜色值,color.xml中的值 | 左边圆的背景色 |  
| rightCircleBackgroundColor | 颜色值,color.xml中的值 | 右边圆的背景色 |  
| leftCircleTextColor | 颜色值,color.xml中的值 | 左边圆中的符号颜色 |
| rightCircleTextColor | 颜色值,color.xml中的值 | 右边圆的符号颜色 |  
| leftCircleStrokeColor | 颜色值,color.xml中的值 | 左边圆的外边距颜色 |
| rightCircleStrokeColor | 颜色值,color.xml中的值 | 右边圆的外边距颜色 |
| subtractionIsLeft | boolean | true:左边圆内的符号是减号(false反之) |
| maxNum| int,interger.xml的值 |该商品数量的最大值|
| minNum| int,interger.xml的值 | 该商品数量的最小值 |
| initialValue | int,interger.xml的值 | 商品数量的初始值 |
##注意:layout_width和layout_height 设置为wrap_content时，宽和高分别为260px和80px,所以建议根据自己的情况设置宽高   
####demo图片

