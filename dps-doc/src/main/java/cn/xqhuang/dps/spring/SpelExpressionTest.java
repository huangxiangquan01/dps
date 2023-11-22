package cn.xqhuang.dps.spring;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Arrays;

/**
 * 拼写表达测验
 *
 * @author huangxiangquan
 * @date 2023/11/22 02:52
 */
public class SpelExpressionTest {

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String) exp.getValue();

        System.out.println(message);

        exp = parser.parseExpression("'Hello World'.concat('!')");
        message = (String) exp.getValue();

        System.out.println(message);

        // invokes 'getBytes()'
        exp = parser.parseExpression("'Hello World'.bytes");
        byte[] bytes = (byte[]) exp.getValue();

        System.out.println(Arrays.toString(bytes));
    }

}
