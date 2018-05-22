var fth = false;
var $mainbodyheight = 500;
function menuHandler(item) {
    if ('logout' == item.id) {
        $.messager.confirm("警告", "确认退出系统?", function (v) {
            if (v) {
            	 window.location.reload(true);
            	 //暂时返回登录页
            	 window.location.href = ContextPath + "/";
//                window.onbeforeunload = function () { };
//                $.post(logoutUrl, function (d) {
//                    window.location.reload(true);
//                });
            }
        });
    }

    if ('updatepass' == item.id) {

        if (employee_id) {
            $("#dlg_pass").dialog("open");
            $("#epass").val("");
            $("#npass").val("");
            $("#znpass").val("");
        }
    }
};



$(function () {
    //	window.setTimeout(function(){
    //		$('#layout').layout('remove','south');
    //	},5000);
    $mainbodyheight = $("#mainbody").height() - 40;
    if (employee_id == 0) {
        var item = $('#mmp').menu('findItem', '修改密码');
        $('#mmp').menu('disableItem', item.target);
        //$("#mb").menubutton("disable");
    }
    //一二级标题加载
    $.ajax({
        type: "post",
        url: getModuleUrl,
        data:{ fatherId: 0 },
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        dataType: 'json',
        success: function (data) {
//            if (window.FirstLevelDataFun) {
//                window.FirstLevelDataFun(data);
//            }
            initHtml(data);
        },
        error: function (a, b, c) {
            //出现异常 再次请求
            $.ajax({
                type: "post",
                url: getModuleUrl,
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                data:{ fatherId: 0 },
                dataType: 'json',
                success: function (data) {
//                    if (window.FirstLevelDataFun) {
//                        window.FirstLevelDataFun(data);
//                    }
                    initHtml(data);
                },
                error: function (a, b, c) {
//                    if (window.FirstLevelDataFun) {
//                        window.FirstLevelDataFun(false);
//                    }
                }
            })
        }
    });

    function initHtml(data) {
        $.each(data, function (i, n) {

            if (n.level == 1) {
                $("#first_nav").append("<a class=\"first_menu\" data_id=\"" + n.id + "\" data-options=\"iconCls:'icon-" + n.useImg + "',group:'g1'\">" + n.name + "</a>");
            }
            else {
                if (!$("#_f" + n.fatherId).length) {
                    $("#second_nav").append("<div id=\"_f" + n.fatherId + "\" style=\"position:absolute;display:none;\"></div>");
                }
                $("#_f" + n.fatherId).append("<a class=\"easyui-linkbutton second_menu\" data_subid=\"" + n.id + "\" data-options=\"toggle:true,group:'g2',plain:true\">" + n.name + "</a>");
            }
        });

        $("#first_nav a").linkbutton({
            size: 'large',
            toggle: true,
            hasDownArrow: false,
            plain: true
        });

        $("#first_nav a").each(function () {

            $(this).mouseover(function () {

                $(this).linkbutton("select");
                $("#second_nav div").hide();
                $("#_f" + $(this).attr("data_id")).css("left", $(this).offset().left - 120);

                $("#_f" + $(this).attr("data_id")).show();
            });

        });

        $("#second_nav a").linkbutton({
            onClick: function () {
                $.messager.progress({
                    title: '请等待',
                    msg: '加载中...',
                    text: ' '
                });
                $("#first_nav span").removeClass("first-icon-hover");
                $("#first_nav").find(".l-btn-selected .l-btn-icon").addClass("first-icon-hover");
                get_third_forth($(this).attr("data_subid"));
            }
        });
        //			$(".secondBtn")[0].style.color = "#666";
        $("#first_nav a").eq(0).mouseover();
        $("#second_nav a").eq(0).click();

    }

    /**
	 * 根据2级id 等到3、4级标题
	 */
    function get_third_forth(fatherId) {
//        var _url_suffix = "&" + urlSuffix.substring(1);
        if (fth) {
            return;
        }
        fth = true;
        $("#third_nav ul").empty();
        $("#fourth_nav").empty();
        $(".tooltip-right").remove();

        $.post(getModuleUrl, { fatherId: fatherId }, function (data) {
            $.each(data, function (z, thi) {

                if (thi.level == 3) {
                	var img="";
                    if (thi.useImg && thi.useImg != "") {
                        img = "style='background-image:url(" + ContextPath + "/images/thirdmenu/" + thi.useImg + ".png);background-position:0px -42px;'";

                    }
                    var $seclist = '<li data_id="' + thi.id + '" class="clearfix"><p></p><a class=""><span class="thirdmenu" ' + img + '></span><br><em>' + thi.name + '</em></a><br><i></i></li>';

                    $($seclist).mouseover(function () {

                        $(this).addClass("third-icon-hover");
                    }).mouseout(function () {

                        $(this).removeClass("third-icon-hover");
                    }).appendTo("#third_nav_a");
                }
                else {
                    if (!$("#fourth_nav #_t" + thi.father_id).length) {
                        $("#fourth_nav").append("<div id='_t" + thi.fatherId + "'></div>");
                    }

                    $("#fourth_nav #_t" + thi.fatherId).append("<a class=\"easyui-linkbutton easyui-tooltip fourth_menu\" data_subid=\"" + thi.id + "\" data_href=\"" + thi.url + "\" data-options=\"toggle:true,group:'g4',plain:true\">" + thi.name + "</a><br/>");
                }

            });


            $("#fourth_nav a").linkbutton({
                onClick: function () {

                    var $module_name = $(this).text();
                    var $iframe_id = $(this).attr("data_subid");
                    var $hrefcc = $(this).attr("data_href");

//                    $.post(ContextPath + "/framework/systemModulesContraller/setFourLevelId?id=" + $iframe_id + _url_suffix,
//                        function (data) {
//                            //1 代表传值成功
//                        });

                    if ($("#tabsbody").tabs("exists", $module_name)) {
                        $("#tabsbody").tabs("select", $module_name);
                        var tab = $('#tabsbody').tabs('getSelected');
                        var tabIndex = $('#tabsbody').tabs('getTabIndex', tab);
                        var src = $(tab).find("iframe").attr("src");
                        //						$(tab).find("iframe").attr("src",src);
                        $("#tabsbody").tabs("close", tabIndex);
                        var height = $("#mainbody").height() - 40;

                        $("#tabsbody").tabs("add", {
                            title: $module_name,
                            selected: true,
                            closable: true,
                            content: '<div id="iframe' + $iframe_id + 'p" style="width:100%;position:fixed;"><center><div class="easyui-progressbar" data-options="value:100,text:\'\'" style="width:30%;margin-top:10%;"></div></center></div><iframe id="iframe' + $iframe_id + '" width="100%"  style="min-height:' + height + 'px" src="' + ContextPath + $hrefcc + ($hrefcc.indexOf("?") > -1 ? '&' : '?') + 'fourLevelId=' + $iframe_id + '" style="overflow:visible;" scrolling="yes" class="mainContent" frameborder="0"></iframe>'
                        });
                    }
                    else {
                        var height = $("#mainbody").height() - 40;

                        $("#tabsbody").tabs("add", {
                            title: $module_name,
                            selected: true,
                            closable: true,
                            content: '<div id="iframe' + $iframe_id + 'p" style="width:100%;position:fixed;"><center><div class="easyui-progressbar" data-options="value:100,text:\'\'" style="width:30%;margin-top:10%;"></div></center></div><iframe id="iframe' + $iframe_id + '" width="100%"  style="min-height:' + height + 'px" src="' + ContextPath + $hrefcc + ($hrefcc.indexOf("?") > -1 ? '&' : '?') + 'fourLevelId=' + $iframe_id + '" style="overflow:visible;" scrolling="yes" class="mainContent" frameborder="0"></iframe>'
                        });
                    }
                }
            });


            $("#third_nav_a li").tooltip({
                position: 'right',
                deltaX: -10,
                content: function () {
                    return $('#_t' + $(this).attr("data_id"));
                },
                onShow: function () {
                    var t = $(this);

                    t.tooltip('tip').unbind().bind('mouseenter', function () {
                        t.addClass("third-icon-hover");
                        t.tooltip('show');
                    }).bind('mouseleave', function () {
                        t.removeClass("third-icon-hover");
                        t.tooltip('hide');
                    });

                },
                onPosition: function () {

                    var wh = $(window).height();
                    var th = $(this).offset().top;
                    var tip = $(this).tooltip('tip');
                    var tiph = $(tip).height();

                    if (th + tiph + 20 < wh) {
                        tip.css('top', th);
                        $(this).tooltip('arrow').css('top', tiph > 40 ? 40 : 20);
                    }
                    else {
                        tip.css('top', wh - tiph - 20);
                        $(this).tooltip('arrow').css('top', tiph - 20);
                    }

                }
            });
            fth = false;
        }, "json").error(function () { fth = false; });

        $.messager.progress('close');
    };

    $("#xgmm").click(function () {
        //console.log("123");
        var epval = $("#epass").val();
        if (!epval) {
            $.messager.show({
                title: '提示',
                msg: "请输入原密码"
            });
            $("#epass").focus();
            return;
        }
        var val = $("#npass").val();
        if (!val) {
            $.messager.show({
                title: '提示',
                msg: "请输入新密码"
            });
            $("#npass").focus();
            return;
        }

        var zz = $("#znpass").val();

        if (!zz) {
            $.messager.show({
                title: '提示',
                msg: "请确认新密码"
            });
            $("#nzpass").focus();
            return;
        }

        if (val != zz) {
            $.messager.show({
                title: '提示',
                msg: "两次密码输入不一致"
            });
            return;
        }


        var newpass = hex_md5(val);

        var epass = hex_md5(epval);
        var data = {};
        data.employee_id = employee_id;
        data.user_name = user_name;
        data.password = epass;
        data.newpass = newpass;
        $.post(updatePassUrl, data, function (d) {
            if (d.success) {

                $.messager.alert('信息', '密码修改成功！', 'info', function () {

                    $('#dlg_pass').dialog('close');

                });
            }
            else {
                $.messager.show({
                    title: '错误',
                    msg: d.msg
                });
            }
        }, 'json');
    });


    $("#repass").keyup(function (e) {

        if (e.keyCode == 13) {
            $("#relogin").click();
        }
    });


    $("#relogin").linkbutton({
        onClick: function () {
            //console.log("123");
            var epval = $("#repass").val();
            if (!epval) {
                $.messager.show({
                    title: '提示',
                    msg: "请输入密码"
                });
                $("#repass").focus();
                return;
            }

            $("#relogin").linkbutton("disable");

            var newpass = hex_md5(epval);
            var data = {};
            data.tenentid = tenentid;
            data.loginUserName = user_name;
            data.password = newpass;
            data.themes = themes;

            $.post(reloginUrl, data, function (d) {

                $("#relogin").linkbutton("enable");

                if (d.success) {
                    fth = false;
                    $("#dlg_relogin").dialog("close");
                }
                else {
                    $.messager.show({
                        title: '错误',
                        msg: d.msg
                    });
                }
            }, 'json');
        }
    });




});

function openForth(id, name, href) {
    var $module_name = name;
//    var suffixUrl = getSuffixUrl(href);
    var suffixUrl = href;

    if ($("#tabsbody").tabs("exists", $module_name)) {
        $("#tabsbody").tabs("close", $module_name);
        var $iframe_id = id;
        var $hrefcc = suffixUrl;

        var height = $("#mainbody").height() - 40;

        $("#tabsbody").tabs("add", {
            title: $module_name,
            selected: true,
            closable: true,
            content: '<div id="iframe' + $iframe_id + 'p" style="width:100%;position:fixed;"><center><div class="easyui-progressbar" data-options="value:100,text:\'\'" style="width:30%;margin-top:10%;"></div></center></div><iframe id="iframe' + $iframe_id + '" width="100%" style="min-height:' + height + 'px" src="' + ContextPath + $hrefcc + '" style="overflow:visible;" scrolling="yes" class="mainContent" frameborder="0"></iframe>'
        });
    }
    else {
        var $iframe_id = id;
        var $hrefcc = suffixUrl;

        var height = $("#mainbody").height() - 40;

        $("#tabsbody").tabs("add", {
            title: $module_name,
            selected: true,
            closable: true,
            content: '<div id="iframe' + $iframe_id + 'p" style="width:100%;position:fixed;"><center><div class="easyui-progressbar" data-options="value:100,text:\'\'" style="width:30%;margin-top:10%;"></div></center></div><iframe id="iframe' + $iframe_id + '"  width="100%"  style="min-height:' + height + 'px" src="' + ContextPath + $hrefcc + '" style="overflow:visible;" scrolling="yes" class="mainContent" frameborder="0"></iframe>'
        });
    }

}

function relogin() {
    $("#username").val(user_name);
    $("#repass").val("");
    $("#dlg_relogin").dialog("open");

}


window.onbeforeunload = function () { return ' '; };
