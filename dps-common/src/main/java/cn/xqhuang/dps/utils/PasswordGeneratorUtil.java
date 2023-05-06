package cn.xqhuang.dps.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PasswordGeneratorUtil {
    private static final int letterRange = 26;
    private static final int numberRange = 10;
    private static final Random random = new Random();

    /**
     *
     * @param passwordLength  密码的长度
     * @param minVariousType  密码包含字符的最少种类
     */
    public static String generateRandomPassword(Integer passwordLength, Integer minVariousType) {
        char[] password = new char[passwordLength];
        List<Integer> pwCharsIndex = new ArrayList<>();
        for (int i = 0; i < password.length; i++) {
            pwCharsIndex.add(i);
        }
        List<CharacterType> takeTypes = new ArrayList<>(Arrays.asList(CharacterType.values()));
        List<CharacterType> fixedTypes = Arrays.asList(CharacterType.values());
        int typeCount = 0;
        while (pwCharsIndex.size() > 0) {
            int pwIndex = pwCharsIndex.remove(random.nextInt(pwCharsIndex.size()));//随机填充一位密码
            Character c;
            if (typeCount < minVariousType) {//生成不同种类字符
                c = generateCharacter(takeTypes.remove(random.nextInt(takeTypes.size())));
                typeCount++;
            } else {//随机生成所有种类密码
                c = generateCharacter(fixedTypes.get(random.nextInt(fixedTypes.size())));
            }
            password[pwIndex] = c;
        }
        return String.valueOf(password);
    }

    private static Character generateCharacter(CharacterType type) {
        Character c = null;
        int rand;
        switch (type) {
            case LOWERCASE:
                rand = random.nextInt(letterRange);
                rand += 97;
                c = (char) rand;
                break;
            case UPPERCASE:
                rand = random.nextInt(letterRange);
                rand += 65;
                c = (char) rand;
                break;
            case NUMBER:
                rand = random.nextInt(numberRange);
                rand += 48;
                c = (char) rand;
                break;
        }
        return c;
    }

    public enum CharacterType {
        LOWERCASE("lowercase", "随机小写字母"),
        NUMBER("lowercase", "随机大写字母"),
        UPPERCASE("lowercase", "随机数字");

        private final String code;
        private final String name;

        CharacterType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }


    public static void main(String[] args) {
        System.out.println(generateRandomPassword(6,2));
    }
}

