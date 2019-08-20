(function ($) {
    $.extend($.fn, {
        itemDetailRewrite: function () {
            return this.each(function () {
                var $table = $(this).css("clear", "both"), $tbody = $table.find("tbody");
                var fields = [];
                $table.find("tr:first th[type]").each(function (i) {
                    var $th = $(this);
                    var field = {
                        type: $th.attr("type") || "text",
                        patternDate: $th.attr("dateFmt") || "yyyy-MM-dd",
                        name: $th.attr("name") || "",
                        defaultVal: $th.attr("defaultVal") || "",
                        size: $th.attr("size") || "12",
                        enumUrl: $th.attr("enumUrl") || "",
                        lookupGroup: $th.attr("lookupGroup") || "",
                        lookupUrl: $th.attr("lookupUrl") || "",
                        lookupPk: $th.attr("lookupPk") || "id",
                        lookupName: $th.attr("lookupName") || "name",
                        suggestUrl: $th.attr("suggestUrl"),
                        suggestFields: $th.attr("suggestFields"),
                        postField: $th.attr("postField") || "",
                        fieldClass: $th.attr("fieldClass") || "",
                        fieldId: $th.attr("fieldId") || "",
                        fieldAttrs: $th.attr("fieldAttrs") || "",
                        options: $th.attr("options") || ""
                    };
                    fields.push(field);
                });

                $tbody.find("a.btnDel").click(function () {
                    deleteTr($(this));
                    initSuffix($tbody);
                    return false;
                });

                $tbody.find("a.btnAdd").click(function () {
                    var $btnAdd = $(this);
                    var $td = $(this).parent();
                    var rowspan = $($td).attr("rowspan");
                    if (!rowspan) rowspan = 1;
                    rowspan = parseInt(rowspan) + 1;
                    $($td).attr("rowspan", rowspan);
                    var $tr = $($td).parent();
                    $("td:first", $($tr)).attr("rowspan", rowspan);
                    var trTm = "";
                    if (!trTm) trTm = trWithoutIndex(fields);
                    var $trTm = $(trTm);
                    $($tr).after($trTm);
                    initSuffix($tbody);
                    $($tbody).initUI().find("a.btnDel").die().live("click", function () {
                        deleteTr($(this));
                        initSuffix($tbody);
                        return false;
                    });
                    return false;
                });

                var addButTxt = $table.attr('addButton');
                if (addButTxt) {
                    var $addBut = $('<div class="button"><div class="buttonContent"><button type="button">' + addButTxt + '</button></div></div>').insertBefore($table).find("button");
                    var $rowNum = $('<input type="text" name="dwz_rowNum" class="textInput" style="margin:2px;" value="1" size="2"/>').insertBefore($table);

                    var trTm = "";
                    $addBut.click(function () {
                        if (!trTm) trTm = trHtml(fields);
                        var rowNum = 1;
                        try {
                            rowNum = parseInt($rowNum.val())
                        } catch (e) {
                        }
                        for (var i = 0; i < rowNum; i++) {
                            var $tr = $(trTm);
                            $tr.appendTo($tbody).initUI().find("a.btnDel").click(function () {
                                deleteTr($(this));
                                initSuffix($tbody);
                                return false;
                            });
                            $($tbody).find("a.btnAdd").die().live("click", function () {
                                var $td = $(this).parent();
                                var rowspan = $($td).attr("rowspan");
                                rowspan = parseInt(rowspan) + 1;
                                $($td).attr("rowspan", rowspan);
                                var $tr = $($td).parent();
                                $("td:eq(0)", $tr).attr("rowspan", rowspan);
                                var trTm = "";
                                if (!trTm) trTm = trWithoutIndex(fields);
                                var $trTm = $(trTm);
                                $($tr).after($trTm);
                                initSuffix($tbody);
                                $($tbody).initUI().find("a.btnDel").die().live("click", function () {
                                    deleteTr($(this));
                                    initSuffix($tbody);
                                    return false;
                                });
                                return false;
                            });
                        }
                        initSuffix($tbody);
                    });
                }

                /**
                 * 删除tr
                 * @param obj
                 */
                function deleteTr(obj) {
                    var $delTr = $(obj).parents("tr:first");
                    var attr = $($delTr).attr("positionValue");
                    var rowspan = 0;
                    $("tr[positionValue=" + attr + "] td", $($delTr).parent()).each(function () {
                        if (typeof($(this).attr("rowspan")) != "undefined") {
                            rowspan = $(this).attr("rowspan");
                            if (rowspan > 1)
                                $(this).attr("rowspan", parseInt(rowspan) - 1);
                        }
                    });
                    if ($($delTr).hasClass("first") && rowspan > 1) {
                        var $firstTd = $("td:first", $($delTr));
                        var $lastTd = $("td:last", $($delTr));
                        $($delTr).next().removeClass("second");
                        $($delTr).next().addClass("first");
                        $($delTr).next().append($lastTd);
                        $($delTr).next().prepend($firstTd);
                    }
                    $($delTr).remove();
                }

            });

            /**
             * 删除时重新初始化下标
             */
            function initSuffix($tbody) {

                var first_value = 0;
                var second_value = 0;
                $tbody.find('>tr').each(function (i) {
                    var positionValue = $(this).attr("positionValue");
                    $(':input, select, a.btnLook, a.btnAttach, index.index', this).each(function () {
                        var $this = $(this), val = $this.val(), html = $this.html();
                        var lookupGroup = $this.attr('lookupGroup');
                        if (lookupGroup) {
                            $this.attr('lookupGroup', lookupGroup.replaceSuffix(i));
                        }

                        var suffix = $this.attr("suffix");
                        if (suffix) {
                            $this.attr('suffix', suffix.replaceSuffix(i));
                        }
                        if (val && val.indexOf("#index#") >= 0) $this.val(val.replace('#index#', i + 1));
                        if (html) {
                            $this.html(replacePostionSubffix(first_value + 1, html));
                            if ($this.get(0).tagName == "SELECT")
                                $this.find("option[value='" + val + "']").attr("selected", "selected");
                        }
                    });

                    if ($(this).hasClass("first")) {
                        $(this).attr("positionValue", replacePostionSubffix(first_value, positionValue));
                        $('input, select, a.btnLook', $(this)).each(function () {
                            second_value = 0;
                            var $this = $(this);
                            var name = $this.attr('name');
                            if (name) $this.attr('name', name.replaceSuffix(first_value));
                            name = $this.attr('name');
                            if (name) $this.attr('name', replaceSecondSubffix(second_value, name));
                            var lookupback = $this.attr('lookupback');
                            if (lookupback) $this.attr('lookupback', lookupback.replaceSuffix(first_value));
                            lookupback = $this.attr('lookupback');
                            if (lookupback) $this.attr('lookupback', replaceSecondSubffix(second_value, lookupback));
                            var lookupgroup = $this.attr('lookupgroup');
                            if (lookupgroup) $this.attr('lookupgroup', lookupgroup.replaceSuffix(first_value));
                            lookupgroup = $this.attr('lookupgroup');
                            if (lookupgroup) $this.attr('lookupgroup', replaceSecondSubffix(second_value, lookupgroup));
                        });
                        first_value++;
                    } else if ($(this).hasClass("second")) {
                        $(this).attr("positionValue", replacePostionSubffix(first_value - 1, positionValue));
                        second_value++;
                        $('input, select, a.btnLook', $(this)).each(function () {
                            var $this = $(this);
                            var name = $this.attr('name');
                            if (name) $this.attr('name', name.replaceSuffix(first_value - 1));
                            name = $this.attr('name');
                            if (name) $this.attr('name', replaceSecondSubffix(second_value, name));
                            var lookupback = $this.attr('lookupback');
                            if (lookupback) $this.attr('lookupback', lookupback.replaceSuffix(first_value - 1));
                            lookupback = $this.attr('lookupback');
                            if (lookupback) $this.attr('lookupback', replaceSecondSubffix(second_value, lookupback));
                            var lookupgroup = $this.attr('lookupgroup');
                            if (lookupgroup) $this.attr('lookupgroup', lookupgroup.replaceSuffix(first_value - 1));
                            lookupgroup = $this.attr('lookupgroup');
                            if (lookupgroup) $this.attr('lookupgroup', replaceSecondSubffix(second_value, lookupgroup));
                        });
                    }
                });
            }


            function tdHtml(field) {
                var html = '', suffix = '';
                if (field.name.endsWith("[#index#]")) suffix = "[#index#]";
                else if (field.name.endsWith("[]")) suffix = "[]";
                var suffixFrag = suffix ? ' suffix="' + suffix + '" ' : '';
                var attrFrag = '';
                if (field.fieldAttrs) {
                    var attrs = DWZ.jsonEval(field.fieldAttrs);
                    for (var key in attrs) {
                        attrFrag += key + '="' + attrs[key] + '"';
                    }
                }
                switch (field.type) {
                    case 'index':
                        html = '第<index class="index">_#index#_</index>步';
                        html = '<td rowspan="1">' + html + '</td>';
                        break;
                    case 'add':
                        html = '<a href="javascript:void(0)" class="btnAdd ' + field.fieldClass + '">增加</a>';
                        html = '<td rowspan="1">' + html + '</td>';
                        break;
                    case 'del':
                        html = '<a href="javascript:void(0)" class="btnDel ' + field.fieldClass + '">删除</a>';
                        html = '<td>' + html + '</td>';
                        break;
                    case 'lookup':
                        html = '<input type="text" lookupback="' + field.lookupGroup + '.' + field.lookupName + '" style="width:' + field.size + ';" class="' + field.fieldClass + '" />'
                            + '<input type="hidden" name="' + field.name + '" lookupback="' + field.lookupGroup + '.' + field.lookupPk + '" />'
                            + '<a class="btnLook" href="' + field.lookupUrl + '" lookupgroup="' + field.lookupGroup + '">查找带回</a>';
                        html = '<td>' + html + '</td>';
                        break;
                    case 'attach':
                        html = '<input type="hidden" name="' + field.lookupGroup + '.' + field.lookupPk + suffix + '"/>'
                            + '<input type="text" name="' + field.name + '" style="width:' + field.size + ';" readonly="readonly" class="' + field.fieldClass + '"/>'
                            + '<a class="btnAttach" href="' + field.lookupUrl + '" lookupGroup="' + field.lookupGroup + '" ' + suggestFrag + ' lookupPk="' + field.lookupPk + '" width="560" height="300" title="查找带回">查找带回</a>';
                        html = '<td>' + html + '</td>';
                        break;
                    case 'enum':
                        $.ajax({
                            type: "POST", dataType: "html", async: false,
                            url: field.enumUrl,
                            data: {inputName: field.name},
                            success: function (response) {
                                html = response;
                            }
                        });
                        break;
                    case 'date':
                        html = '<input type="text" name="' + field.name + '" value="' + field.defaultVal + '" class="date ' + field.fieldClass + '" dateFmt="' + field.patternDate + '" style="width:' + field.size + ';"/>'
                            + '<a class="inputDateButton" href="javascript:void(0)">选择</a>';
                        html = '<td>' + html + '</td>';
                        break;
                    case 'select':
                        html = '<select name="' + field.name + '" value="' + field.defaultVal + '" style="width:' + field.size + ';" class="' + field.fieldClass + '" id="' + field.fieldId + '" ' + attrFrag + '>';
                        if (field.options) {
                            var options = DWZ.jsonEval(field.options);
                            for (var key in options) {
                                if (field.defaultVal == options[key])
                                    html += '<option selected="selected" value="' + options[key] + '">' + key + '</option>';
                                else
                                    html += '<option value="' + options[key] + '">' + key + '</option>';
                            }
                        }
                        html += '</select>';
                        html = '<td>' + html + '</td>';
                        break;
                    default:
                        html = '<input type="text" name="' + field.name + '" value="' + field.defaultVal + '" style="width:' + field.size + ';" class="' + field.fieldClass + '" ' + attrFrag + '/>';
                        html = '<td>' + html + '</td>';
                        break;
                }
                return html;
            }

            function tdHtmlWithoutIndex(field) {
                var html = '', suffix = '';

                if (field.name.endsWith("[#index#]")) suffix = "[#index#]";
                else if (field.name.endsWith("[]")) suffix = "[]";

                var suffixFrag = suffix ? ' suffix="' + suffix + '" ' : '';

                var attrFrag = '';
                if (field.fieldAttrs) {
                    var attrs = DWZ.jsonEval(field.fieldAttrs);
                    for (var key in attrs) {
                        attrFrag += key + '="' + attrs[key] + '"';
                    }
                }
                switch (field.type) {
                    case 'index':
                        html = '';
                        break;
                    case 'add':
                        html = '';
                        break;
                    case 'del':
                        html = '<a href="javascript:void(0)" class="btnDel ' + field.fieldClass + '">删除</a>';
                        html = '<td>' + html + '</td>';
                        break;
                    case 'lookup':
                        html = '<input type="text" lookupback="' + field.lookupGroup + '.' + field.lookupName + '" style="width:' + field.size + ';" class="' + field.fieldClass + '" />'
                            + '<input type="hidden" name="' + field.name + '" lookupback="' + field.lookupGroup + '.' + field.lookupPk + '" />'
                            + '<a class="btnLook" href="' + field.lookupUrl + '" lookupgroup="' + field.lookupGroup + '">查找带回</a>';
                        html = '<td>' + html + '</td>';
                        break;
                    case 'attach':
                        html = '<input type="hidden" name="' + field.lookupGroup + '.' + field.lookupPk + suffix + '"/>'
                            + '<input type="text" name="' + field.name + '" style="width:' + field.size + ';" readonly="readonly" class="' + field.fieldClass + '"/>'
                            + '<a class="btnAttach" href="' + field.lookupUrl + '" lookupGroup="' + field.lookupGroup + '" ' + suggestFrag + ' lookupPk="' + field.lookupPk + '" width="560" height="300" title="查找带回">查找带回</a>';
                        html = '<td>' + html + '</td>';
                        break;
                    case 'enum':
                        $.ajax({
                            type: "POST", dataType: "html", async: false,
                            url: field.enumUrl,
                            data: {inputName: field.name},
                            success: function (response) {
                                html = response;
                            }
                        });
                        break;
                    case 'date':
                        html = '<input type="text" name="' + field.name + '" value="' + field.defaultVal + '" class="date ' + field.fieldClass + '" dateFmt="' + field.patternDate + '" style="width:' + field.size + ';"/>'
                            + '<a class="inputDateButton" href="javascript:void(0)">选择</a>';
                        html = '<td>' + html + '</td>';
                        break;
                    case 'select':
                        html = '<select name="' + field.name + '" value="' + field.defaultVal + '" style="width:' + field.size + ';" class="' + field.fieldClass + '" id="' + field.fieldId + '" ' + attrFrag + '>';
                        if (field.options) {
                            var options = DWZ.jsonEval(field.options);
                            for (var key in options) {
                                if (field.defaultVal == options[key])
                                    html += '<option selected="selected" value="' + options[key] + '">' + key + '</option>';
                                else
                                    html += '<option value="' + options[key] + '">' + key + '</option>';
                            }
                        }
                        html += '</select>';
                        html = '<td>' + html + '</td>';
                        break;
                    default:
                        html = '<input type="text" name="' + field.name + '" value="' + field.defaultVal + '" style="width:' + field.size + ';" class="' + field.fieldClass + '" ' + attrFrag + '/>';
                        html = '<td>' + html + '</td>';
                        break;
                }
                return html;
            }

            function trWithoutIndex(fields) {
                var html = '';
                $(fields).each(function () {
                    html += tdHtmlWithoutIndex(this);
                });
                return '<tr class="unitBox second" positionValue="_#index#_">' + html + '</tr>';
            }

            function trHtml(fields) {
                var html = '';
                $(fields).each(function () {
                    html += tdHtml(this);
                });
                return '<tr class="unitBox first" positionValue="_#index#_">' + html + '</tr>';
            }

            function replaceSecondSubffix(index, name) {
                return name.replace(/\]\[[0-9]+\]/, '][' + index + ']').replace('#td_index#', index);
            }

            function replacePostionSubffix(index, name) {
                return name.replace(/_[0-9]_/, '_' + index + '_').replace('#index#', index);
            }
        }
    });
})(jQuery);