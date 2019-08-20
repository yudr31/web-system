/**
 * NNK全局对象
 */
var NNK = NNK || {};
NNK.NavTab = {
    reloadCurrentNavTab: function () {
        navTab._reload(navTab._getTabs().eq(navTab._currentIndex), true);
    }
};

NNK.initPageBefore = function ($page) {
    NNK.UI.Form.initSelectorCtrl($page);
};

NNK.initPageAfter = function ($page) {
    if ($.fn.nnkCombox) $("select.nnkCombox", $page).nnkCombox();
    if ($.fn.itemDetailRewrite) $("table.itemDetailRewrite", $page).itemDetailRewrite();
    // if ($.fn.nnkCombox) $("select.changeBtnLook", $page).changeBtnLook();
};

/**
 * 控件
 */
NNK.UI = {

};

/**
 * 表单控件
 */
NNK.UI.Form = {

    initSelectorCtrl: function ($page) {
        $("div.pageFormContent div.unit select.combox", $page).each(function () {
            if ($(this).attr("readonly") == "readonly") {
                var initValue = $(this).attr("initValue");
                var options = $(this).find("option");
                var selectValue = "";
                for (var i = 0; i < options.length; ++i) {
                    if (initValue == options[i].value) {
                        selectValue = options[i].innerText;
                    }
                }
                var html = '<input type="text" value="' + selectValue + '" readonly="readonly" />';
                //$(this).after(html);
                //$(this).remove();
            }
        });
    },

};