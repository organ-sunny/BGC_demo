export default {

    // 超时时间：7天
    timeout: 7 * 24 * 60 * 60 * 1000,

    // 设置cookie
    set(key, value) {
        let timeout = new Date();
        timeout.setTime(timeout.getTime() + this.timeout);
        document.cookie = `${key}=${value};expires=${timeout.toUTCString()}`;
    },

    // 删除cookie
    delete(key) {
        let timeout = new Date();
        timeout.setTime(timeout.getTime() - 10000);
        document.cookie = `${key}=;expires=${timeout.toUTCString()}`;
    },

    // 获取cookie
    get(key) {
        let cookies = document.cookie.split(";");
        for (let cookie of cookies) {
            let keyValue = cookie.split("=");
            let keyValueLength = keyValue.length;
            let _key = keyValue[0];
            _key = _key.replace(" ", "");
            let _value = keyValue[1];
            if (keyValueLength !== 2) {
                _value = "";
                for (let i = 1; i < keyValueLength; i++) {
                    _value = _value + "=" + keyValue[i];
                }
                _value = _value.substring(1);
            }
            if (key === _key) {
                return _value;
            }
        }
        return null;
    }

};