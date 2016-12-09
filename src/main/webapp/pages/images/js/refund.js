$(function(){
	if (!/(iPhone|iPad|iPod|iOS|Android)/i.test(navigator.userAgent)) {
			$('body').addClass('pc');
	}
	$('.js_input').click(function(){
		$(this).find('input, select, textarea').focus();
	});
})
