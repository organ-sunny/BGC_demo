import baseConfig from "../config/baseConfig.js";
import token from "../js/token.js";
import cookieConfig from "../config/cookieConfig.js";
import pageConfig from "../config/pageConfig.js";

export default {
    send(option) {
        return new Promise((successCallback, errorCallback) => {
            let type = option.type,
                url = baseConfig.host + "/api/" + option.url,
                data = option.data,
                dataType = option.dataType,
                sendData = JSON.stringify(data);
            console.log(url);

            let http = new XMLHttpRequest();
            http.open(type, url);

            if (dataType === "file") {
                sendData = new FormData();
                for (let key in data) {
                    if (data.hasOwnProperty(key)) {
                        sendData.append(key, data[key]);
                    }
                }
            }

            if ((type === "post" || type === "POST") && dataType !== "file") {
                http.setRequestHeader("content-type", "application/json");
            }

            // 设置token
            http.setRequestHeader(cookieConfig.token, token.get());

            http.send(sendData);

            http.onreadystatechange = function () {
                if (http.status === 200 && http.readyState === 4) {
                    let response = JSON.parse(http.response);
                    console.log(response);

                    // token失效
                    if (response.code === 505) {
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