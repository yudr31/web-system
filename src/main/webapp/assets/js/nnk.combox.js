/**
 * @author Roger Wu
 */

(function($){
	var allSelectBox = [];
	var killAllBox = function(bid){
		$.each(allSelectBox, function(i){
			if (allSelectBox[i] != bid) {
				if (!$("#" + allSelectBox[i])[0]) {
					$("#op_" + allSelectBox[i]).remove();
					//allSelectBox.splice(i,1);
				} else {
					$("#op_" + allSelectBox[i]).css({ height: "", width: "" }).hide();
				}
				$(document).unbind("click", killAllBox);
			}
		});
	};

	$.extend($.fn, {
        nnkCombox:function(){
			/* 清理下拉层 */
			var _selectBox = [];
			$.each(allSelectBox, function(i){
				if ($("#" + allSelectBox[i])[0]) {
					_selectBox.push(allSelectBox[i]);
				} else {
					$("#op_" + allSelectBox[i]).remove();
				}
			});
			allSelectBox = _selectBox;

			return this.each(function(i){
				var $this = $(this).removeClass("combox");
				var name = $this.attr("name");
				var value= $this.val();

                // modify by Xiuwei begin
                if($this.attr("initValue") !== undefined && $this.attr("initValue") != "") {
                    value = $this.attr("initValue");
                    $this.val(value);
                }
                // modify by Xiuwei end

				var label = $("option[value=" + value + "]",$this).text();
				var ref = $this.attr("ref");
				var refUrl = $this.attr("refUrl") || "";

				var cid = $this.attr("id") || Math.round(Math.random()*10000000);
				var select = '<div class="combox"><div id="combox_'+ cid +'" class="select"' + (ref?' ref="' + ref + '"' : '') + '>';
				select += '<a href="javascript:" class="'+$this.attr("class")+'" name="' + name +'" value="' + value + '">' + label +'</a></div></div>';
				var options = '<ul class="comboxop" id="op_combox_'+ cid +'">';
				$("option", $this).each(function(){
					var option = $(this);
					options +="<li><a class=\""+ (value==option[0].value?"selected":"") +"\" href=\"#\" value=\"" + option[0].value + "\">" + option[0].text + "</a></li>";
				});
				options +="</ul>";

				$("body").append(options);
				$this.after(select);
				$("div.select", $this.next()).comboxSelect().append($this);
				if (ref && refUrl) {
					function _onchange(event){
						var $ref = $("#"+ref);
						if ($ref.size() == 0) return false;
						$.ajax({
							type:'POST', dataType:"text", url:refUrl.replace("{value}", encodeURIComponent($this.attr("value"))), cache: false,
							data:{},
							success: function(data){
								if (!data) return;
								var $refCombox = $ref.parents("div.combox:first");
								$ref.html(data).insertAfter($refCombox);
								$refCombox.remove();
								$ref.trigger("change").combox();
							},
							error: DWZ.ajaxError
						});
					}

					$this.unbind("change", _onchange).bind("change", _onchange);
				}

			});
		}
	});
})(jQuery);
