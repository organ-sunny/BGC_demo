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
        return mobiles.matches("^(13[0-9]|14[5|7]|15[0-9]|18[0-9])\\d{8}$");
    }

    public static boolean isMailAddress(String mailAddress) {
        return mailAddress.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }
}
