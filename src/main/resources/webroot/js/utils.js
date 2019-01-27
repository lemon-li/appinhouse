function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

/**
 * https://stackoverflow.com/questions/960866/how-can-i-convert-the-arguments-object-to-an-array-in-javascript
 * **/
function toRestAPI() {
    var args = new Array(arguments.length);
    for (var i = 0; i < args.length; ++i) {
        args[i] = arguments[i];
    }
    return args.join('/');
}

function getAppIdByPathParams() {
    var src = window.location.toString();
    var index = src.indexOf('/version/');
    if (index == -1) {
        return null;
    } else {
        appID = src.substr(index + 9);
        return appID;
    }
}

function showSuccess(message, goto) {
    swal({
        title: "操作成功!",
        text: message,
        type: "success",
        confirmButtonText: "点击确认"
    }, function () {
        if (typeof goto == 'function') {
            goto();
        } else {
            window.location.href = goto;
        }
    });
}

function showError(message, goto) {
    swal({
        title: "操作失败!",
        text: message,
        type: "error",
        confirmButtonText: "确认",
        confirmButtonColor: '#f27474'
    }, function () {
        if (null != goto) {
            if (typeof goto == 'function') {
                goto();
            } else {
                window.location.href = goto;
            }
        }
    });
}

function formData2Json(form) {
    var json_data = {};
    var form_data = $("#" + form).serializeArray();
    $.each(form_data, function () {
        json_data[this.name] = this.value;
    });
    return json_data;
}

function getMobileOperatingSystem() {
    var userAgent = navigator.userAgent || navigator.vendor || window.opera;

    // Windows Phone must come first because its UA also contains "Android"
    if (/windows phone/i.test(userAgent)) {
        return "Windows Phone";
    }

    if (/android/i.test(userAgent)) {
        return "Android";
    }

    // iOS detection from: http://stackoverflow.com/a/9039885/177710
    if (/iPad|iPhone|iPod/.test(userAgent) && !window.MSStream) {
        return "iOS";
    }

    return "unknown";
}

function isAndroid() {
    return getMobileOperatingSystem() === "Android";
}

function isIOS() {
    return getMobileOperatingSystem() === "iOS";
}

function isWeChat() {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        return true;
    } else {
        return false;
    }
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}
