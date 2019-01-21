function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function getAppIdByPathParams() {
    var src = window.location.toString();
    var index = src.indexOf('/version/');
    if(index == -1) {
        return null;
    } else {
        appID = src.substr(index);
        return appID;
    }
}

function show_success(message, success_goto) {
    swal({
        title: "操作成功!",
        text: message,
        type: "success",
        confirmButtonText: "点击确认"
    }, function () {
        if (typeof success_goto == 'function') {
            success_goto();
        } else {
            window.location.href = success_goto;
        }
    });
}

function show_error(message, success_goto) {
    swal({
        title: "操作失败!",
        text: message,
        type: "error",
        confirmButtonText: "确认",
        confirmButtonColor: '#f27474'
    }, function () {
        if(null != success_goto) {
            if (typeof success_goto == 'function') {
                success_goto();
            } else {
                window.location.href = success_goto;
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
