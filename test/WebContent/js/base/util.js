/**
 * 
 */
function readonly_all(domid,bool){
	if(bool)
	{
		$(domid).find(".textbox-text.validatebox-text").attr("disabled","true");
		$(domid).find(".Wdate").attr("disabled","true");
		$(domid).find("input[type='checkbox']").attr("disabled","true");
		$(domid).find("input[type='radio']").attr("disabled","true");
		$(domid).find("a.textbox-icon.combo-arrow").hide();
		
	}
	else
	{
		$(domid).find(".textbox-text.validatebox-text").removeAttr("disabled");
		$(domid).find(".Wdate").removeAttr("disabled");
		$(domid).find("input[type='checkbox']").removeAttr("disabled");
		$(domid).find("input[type='radio']").removeAttr("disabled");
		$(domid).find("a.textbox-icon.combo-arrow").show();	
	}
}