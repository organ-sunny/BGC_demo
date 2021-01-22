import baseConfig from "../config/baseConfig.js";

export default {
    send(option) {
        return new Promise((successCallback, errorCallback) => {
            let type = option.type,
                url = baseConfig.host + "/" + option.url,
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

            http.send(sendData);

            http.onreadystatechange = function () {
                if (http.status === 200 && http.readyState === 4) {
                    let response = JSON.parse(http.response);
                    console.log(response);
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