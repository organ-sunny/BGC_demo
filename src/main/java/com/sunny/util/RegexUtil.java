package com.sunny.util;

/**
 * 手机号正则判断
 * 国家号码段分配如下：
 * 　　移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
 * 　　联通：130、131、132、152、155、156、185、186
 * 　　电信：133、153、180、189、（1349卫通）
 */
public class RegexUtil {

    public static boolean isMobileNum(String mobiles) {
        return mobiles.matches("^(13[0-9]|14[5|7]|15[0-9]|17[0-9]|18[0-9])\\d{8}$");
    }

    public static boolean isMailAddress(String mailAddress) {
        return mailAddress.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    public static boolean isPassword(String password, String username) {
        // 判断密码是否包含数字：包含返回1，不包含返回0
        int i = password.matches(".*\\d+.*") ? 1 : 0;

        // 判断密码是否包含字母：包含返回1，不包含返回0
        int j = password.matches(".*[a-zA-Z]+.*") ? 1 : 0;

        // 判断密码是否包含特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)：包含返回1，不包含返回0
        int k = password.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;

        // 判断密码长度是否在6-16位
        int l = password.length();

        // 判断密码中是否包含用户名
        boolean contains = password.contains(username);

        if (i + j + k < 2 || l < 6 || l > 16 || contains) {
            return false;
        }
        else {
            return true;
        }
    }
}
