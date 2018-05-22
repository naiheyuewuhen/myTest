/**
 * 
 */
$(document).ready(function() {
	$("#loginUserName").focus();
//	localStorage.setItem("username","");
	var localUsername=localStorage.getItem("username");
	var localPassword=localStorage.getItem("password");
	var localSaveuser=localStorage.getItem("saveuser");//记住密码选项
	if(localUsername){
		$("#loginUserName").val(localUsername);
	}
	if(localSaveuser=="saved"){
		$("#remember").addClass("cur");
	}else{
		$("#remember").removeClass("cur");
	}
	if(localPassword && localSaveuser=="saved"){
		$("#remember").addClass("cur");
		$("#password").val("******");
		$("#passwordHidden").val(localPassword);
	}
	$("#loginUserName").keyup(function(e) {
				if (e.which == 13) {
					$("#password").focus();
				}
			});

	$("#password").keyup(function(e) {
		if (e.keyCode == 13) {
			$("#login").focus();
			$("#login").click();
		}
	});
	$("#remember").on("click", function() {
				if ($(this).hasClass("cur")) {
					$(this).removeClass("cur");
					localStorage.removeItem("localSaveuser");
				} else {
					$(this).addClass("cur");
					localStorage.setItem("localSaveuser","saved");
				}
			});
});

function mysubmit() {
	if ($('#password').val() != '******') {
//		$("#passwordHidden").val(hex_md5($('#password').val()));
		$("#passwordHidden").val($('#password').val());
	}
	var username = $("#loginUserName").val();
	$.localStorage.set("tzxuser", username);
	$.localStorage.set("tzxtid", $("#tenentid").val());

	if ($("#remember").hasClass("cur")) {
		$.localStorage.set("tzxcheck", "cur");
	} else {
		$.localStorage.remove("tzxcheck");
	}

	var p;
	var b = false;
	if ($.localStorage.isSet("tzxpassword")) {
		p = $.localStorage.get("tzxpassword");
	} else {
		p = [];
	}

	for (var i = 0; i < p.length; i++) {
		if (p[i].u == username) {
			b = true;
			if ($("#remember").hasClass("cur") == true) {
				p[i].p = $("#passwordHidden").val();
			} else {
				p.splice(i, 1);
			}
			break;
		}
	}

	if (!b && $("#remember").hasClass("cur") == true) {
		p[p.length] = {
			u : username,
			p : $("#passwordHidden").val()
		};
	}

	// $.localStorage.set("tzxpassword",p);
	if (!$('#loginUserName').val()) {
		$("#loginUserName")
				.attr('style',
						'border-color:rgb(255,0,0);box-shadow:0 0 2px 1px rgb(200,223,244);');
		$("#tip").html("请输入用户名！<s></s>");
		$("#tip").show();
		$("#loginUserName").focus();
		return;
	}
	if (!$('#password').val()) {
		$('#password')
				.attr('style',
						'border-color:rgb(255,0,0);box-shadow:0 0 2px 1px rgb(200,223,244);');
		$("#tip").html("请输入密码！<s></s>");
		$("#tip").show();
		$('#password').focus();
		return;
	}
	var d = $('#myform').serialize();
//	var reqUrl = getLoginSuffixUrl(loginUrl, true);

	$.ajax({
		type : "post",
		url : loginUrl,
		data : d,
		dataType : "json",
		async : true,
		success : function(data) {
			if (data.success) {
				window.location.href = contextPath + data.url;
				$.localStorage.set("tzxpassword", p);
			} else {
				$("#tip").html(data.msg + '<s></s>');
				$("#tip").show();
				if (data.code == "passwordError") {
					$("#loginUserName")
							.attr('style',
									'border-color:rgb(255,0,0);box-shadow:0 0 2px 1px rgb(200,223,244);');
					$("#password")
							.attr('style',
									'border-color:rgb(255,0,0);box-shadow:0 0 2px 1px rgb(200,223,244);');
					$("#password").focus();
				}
			}

		},
		error : function(data) {
		}
	});

	/* $("#myform").submit(); */
}

// $("#myform input").bind('keyup', function(e){
// if (e.keyCode == 13){
// $("#login").trigger('click');
// }
// });

function resize(body) {
	var ww = $(window).width();
	if (ww && ww < 800) {
		body.width = ww + "px";
	} else {
		body.width = "100%";
	}
}