function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
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