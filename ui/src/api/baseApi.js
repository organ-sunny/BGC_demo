import pageConfig from "../config/pageConfig.js";
import user from "../js/user.js";

let host = process.env.NODE_ENV === "development" ? "http://192.168.12.104:1001/api" : "/api";

export default {
    send(option) {
        return new Promise(function (successCallback, errorCallback) {
            _send(option).then((response) => {
                let responseJson = JSON.parse(response);
                console.log(responseJson);

                // token失效
                if (responseJson.code === 101) {
                    window.location.href = pageConfig.index;
                    return;
                }

                if (responseJson.code === 200) {
                    successCallback(responseJson.data);
                } else {
                    errorCallback(responseJson.message);
                }
            });
        });
    },

    getHeader() {
        let result = {};
        let userJson = user.get();

        result.token = userJson.token;
        return result;
    }
};

function _send(option) {
    return new Promise(function (s) {
        function isEmpty(data) {
            return data === undefined || data === null || data === "";
        }

        function isObject(data) {
            return typeof data === "object";
        }

        function isFile(data) {
            return data.__proto__ === File.prototype;
        }

        let url = option.url,
            type = option.type,
            header = option.header;
        if (type === "get") {
            type = "GET";
        }
        if (type === "post") {
            type = "POST";
        }
        if (isEmpty(header)) {
            header = {};
        }

        let urlData = option.urlData;
        let data = option.data;

        let check = option.check;
        if (isEmpty(check)) {
            check = {};
        }
        let urlCheck = check.urlData,
            dataCheck = check.data;

        let sendData = {
            url: null,
            data: null
        };

        // 参数校验
        if (!isEmpty(urlCheck) && isObject(urlCheck)) {
            if (!isEmpty(urlData) && isObject(urlData)) {
                sendData.url = {};
                for (let key in urlCheck) {
                    if (urlCheck.hasOwnProperty(key)) {
                        let v = urlData[key];
                        if (!isEmpty(v)) {
                            sendData.url[key] = v;
                        }
                    }
                }
            }
        } else {
            if (!isEmpty(urlData) && isObject(urlData)) {
                sendData.url = urlData;
            }
        }

        if (!isEmpty(dataCheck) && isObject(dataCheck)) {
            if (!isEmpty(data) && isObject(data)) {
                sendData.data = {};
                for (let key in dataCheck) {
                    if (dataCheck.hasOwnProperty(key)) {
                        let v = data[key];
                        if (!isEmpty(v)) {
                            sendData.data[key] = v;
                        }
                    }
                }
            }
        } else {
            if (!isEmpty(data) && isObject(data)) {
                sendData.data = data;
            }
        }

        // 是否存在文件
        let isExistFile = false;
        if (sendData.data !== null) {
            for (let key in sendData.data) {
                if (sendData.data.hasOwnProperty(key)) {
                    if (isFile(sendData.data[key])) {
                        isExistFile = true;
                    }
                }
            }
        }
        if (isExistFile) {
            let formData = new FormData();
            for (let key in sendData.data) {
                if (sendData.data.hasOwnProperty(key)) {
                    formData.append(key, sendData.data[key]);
                }
            }
            sendData.data = formData;
        } else {
            if (type !== "GET") {
                header["content-type"] = "application/json";
            }
            if (sendData.data !== null) {
                sendData.data = JSON.stringify(sendData.data);
            }
        }

        // 装配url参数
        if (sendData.url !== null) {
            let u = sendData.url.length === 0 ? "" : "?";
            for (let key in sendData.url) {
                if (sendData.url.hasOwnProperty(key)) {
                    let value = sendData.url[key];
                    if (!isEmpty(value)) {
                        u = u + key + "=" + sendData.url[key] + "&";
                    }
                }
            }
            if (u !== "") {
                u = u.substring(0, u.length - 1);
            }
            url = url + u;
        }


        // 发送
        let http = new XMLHttpRequest();
        http.open(type, host + url);
        for (let key in header) {
            if (header.hasOwnProperty(key)) {
                http.setRequestHeader(key, header[key]);
            }
        }
        if (sendData.data !== null) {
            http.send(sendData.data);
        } else {
            http.send();
        }

        http.onreadystatechange = function () {
            if (http.status === 200 && http.readyState === 4) {
                s(http.response);
            }
        }
    });
}