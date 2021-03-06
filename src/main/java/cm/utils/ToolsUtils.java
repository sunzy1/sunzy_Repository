package cm.utils;

import org.springframework.util.StringUtils;

import java.util.List;

public  class ToolsUtils {
    //删除尖括号内的数据，处理插件导出文本含有<div>问题
    public static String deleteJKH(String context) {
        if(StringUtils.isEmpty(context)){
            return context;
        }
        // 修改原来的逻辑，防止右括号出现在左括号前面的位置
        int head = context.indexOf('<'); // 标记第一个使用左括号的位置
        if (head == -1) {
            return context; // 如果context中不存在括号，什么也不做，直接跑到函数底端返回初值str
        } else {
            int next = head + 1; // 从head+1起检查每个字符
            int count = 1; // 记录括号情况
            do {
                if (context.charAt(next) == '<')
                    count++;
                else if (context.charAt(next) == '>')
                    count--;
                next++; // 更新即将读取的下一个字符的位置
                if (count == 0) // 已经找到匹配的括号
                {
                    String temp = context.substring(head, next); // 将两括号之间的内容及括号提取到temp中
                    context = context.replace(temp, ""); // 用空内容替换，复制给context
                    head = context.indexOf('<'); // 找寻下一个左括号
                    next = head + 1; // 标记下一个左括号后的字符位置
                    count = 1; // count的值还原成1
                }
            } while (head != -1); // 如果在该段落中找不到左括号了，就终止循环
        }
        return context; // 返回更新后的context
    }
    public static boolean notEmpity(List list){
        if(null!=list && list.size()>0){
            return true;
        }
        return false;
    }
}
