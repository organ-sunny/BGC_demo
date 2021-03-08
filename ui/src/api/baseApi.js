import baseConfig from "../config/baseConfig.js";
import token from "../js/token.js";
import cookieConfig from "../config/cookieConfig.js";
import pageConfig from "../config/pageConfig.js";
import variableUtil from "../util/variableUtil.js";

export default {
    send(option) {
        return new Promise((successCallback, errorCallback) => {
            let type = option.type,
                url = baseConfig.host + "/api/" + option.url,
                headers = option.headers,
                data = option.data,
                dataType = option.dataType,
                sendData = null;

            if (variableUtil.isEmpty(headers)) {
                headers = [];
            }

            if (dataType === "file") {
                sendData = new FormData();
                for (let key in data) {
                    if (data.hasOwnProperty(key)) {
                        sendData.append(key, data[key]);
                    }
                }
            } else {
                if ((type === "post" || type === "POST")) {
                    headers.push({
                        key: "content-type",
                        value: "application/json"
                    });
                    sendData = JSON.stringify(data);
                } else {
                    if (!variableUtil.isEmpty(data)) {
                        if (data instanceof Object) {
                            let b = false;
                            for (let key in data) {
                                if (data.hasOwnProperty(key)) {
                                    let v = data[key];
                                    if (!variableUtil.isEmpty(v)) {
                                        let a = ``;
                                        if (!b) {
                                            a = "?";
                                            b = true;
                                        } else {
                                            a = "&";
                                        }
                                        url = `${url}${a}${key}=${v}`;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 设置token
            headers.push({
                key: cookieConfig.token,
                value: token.get()
            });

            let http = new XMLHttpRequest();
            console.log(url)
            http.open(type, url);
            for (let item of headers) {
                let key = item.key;
                let value = item.value;
                if (!variableUtil.isEmpty(key) && !variableUtil.isEmpty(value)) {
                    http.setRequestHeader(key, value);
                }
            }
            http.send(sendData);

            http.onreadystatechange = function () {
                if (http.status === 200 && http.readyState === 4) {
                    let response = JSON.parse(http.response);
                    console.log(response);

                    // token失效
                    if (response.code === 101) {
                        window.location.href = pageConfig.index;
                        return;
                    }

                    if (response.code === 200) {
                        successCallback(response.data);
                    } else {
                        errorCallback(response.message);
                    }
                }
            }
        });
    },
};