$.extend($.validator.messages, {
    required: "必填字段",
    phone: "请输入正确的手机号"//"数字、空格、括号"
});

var errorMsg = $("#errorMsg");
errorMsg.hide();

function showErrorMsg(msg) {
    errorMsg.html(msg);
    errorMsg.show();
}

function hideErrorMsg() {
    errorMsg.hide();
}

function validateCallback(form) {
    var $form = $(form);
    hideErrorMsg();
    if (!$form.valid()) return false;
    var $btnSubmit = $("#btnSubmit");
    $btnSubmit.prop("disabled",true);
    var _submitFn = function(){
        $.ajax({
            type: form.method || 'POST',
            url:'newLogin.do',
            data:$form.serializeArray(),
            dataType:"json",
            cache: false,
            success: function (json) {
                if (json.statusCode == 200){
                    location.href = ".";
                } else {
                    $btnSubmit.prop("disabled",false);
                    showErrorMsg(json.message);
                }
            },
            error: function () {
                $btnSubmit.prop("disabled",false);
                showErrorMsg("登录出错，请重新登录");
            }
        });
    }
    _submitFn();
    return false;
}