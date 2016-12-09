

;(function($, undefined){

	var $form = $('.js_refund_form');

	var apply = {

		/**
		 * 表单提交
		 * @return {[type]} [description]
		 */
		_apply_submit: function (){

			var $number = $('#refund-number'),
				$refund_type = $form.find('input[name="refund_type"]'),
				$refund_price = $form.find('input[name="refund_price"]'),
				$refund_notice = $form.find('.js_refund_notice'),
				$images = $form.find('.js_img'),
				$account_type = $form.find('input[name="account_type"]'),
				$account = $form.find('input[name="account"]'),
				data = {};

			$form.submit(function(){
				var _src, oImg;

				data.number = $number.val();//数量
				data.type = $refund_type.val();//退款需求
				data.price = $.trim($refund_price.val());//退款金额
				data.notice = $.trim($refund_notice.text());//退款说明
				data.account_type = $account_type.val();
				data.account = $account.val();
				data.img = [];//上传图片
				oImg = data.img;

				$.each($images, function(i, obj){
					_src = $(obj).attr('src');
					_src && (oImg[oImg.length] = _src);
				});
				
				if(!$.isNumeric(data.number)){
					alert('请选择退款数量');
					return false;
				}
				if(!$.isNumeric(data.price)){
					alert('请输入退款金额');
					return false;
				}
				if(!data.account_type){
					alert('请选择退款账户');
					return false;
				}
				if(!data.account){
					alert('请选择退款账户');
					return false;
				}

			});
		},

		/**
		 * 退款金额格式化
		 * @return {[type]} [description]
		 */
		_format_price: function(){
			$form.find('input[name="refund_price"]').blur(function(){
				var $this = $(this),
					_price = parseFloat($.trim($this.val()), 10);

				if(!$.isNumeric(_price)){
					//不是数字，则清空
					alert('您输入的退款金额不是一个数字');
					$this.val('');
				}else{
					$this.val(_price.toFixed(2));
				}
			});
		},
		/**
		 * 退款需求切换
		 * @return {[type]} [description]
		 */
		_refund_type_switch: function(){
			var $refund_type = $('input[name="refund_type"]');
			$('.js_refund_type').on('click', 'li', function(){
				var $this = $(this),
					_index = $this.index();

				$this.addClass('cur').siblings().removeClass('cur');
				$refund_type.val(_index);
			});
		},

		_file_upload_dom: '<div class="img-box js_file_upload">'+
							'<div class="box">'+
								'<img class="thumb js_img" src="" alt="">'+
								'<input type="file" accept="image/*" capture="camera" />'+
								'<input type="hidden" name="images[1]" />'+
								'<p class="tips js_tips">上传照片</p>'+
							'</div>'+
							'<i class="remove js_remove"></i>'+
						'</div>',

		/**
		 * 图片压缩上传
		 * @return {[type]} [description]
		 */
		_file_upload: function(){
			var me = this;
			$js_file_upload = $form.find('.js_file_upload');

			$form.on('change', 'input[type="file"]', function(){
				var $this = $(this),
					$img = $this.siblings('.js_img'),
					$tips = $this.siblings('.js_tips'),
					$input = $this.siblings('input[type="hidden"]'),
					$img_box = $this.closest('.img-box');

				lrz($this[0].files[0], {width: 640}, function (res) {
		            $img.attr('src', res.base64);
		            $tips.text('重新上传');
		            $this.replaceWith($this.clone());
		            $img_box.addClass('uploaded');

		            //动态插入图片上传控件
		            var siblings_length = $img_box.siblings().length;
		            if(siblings_length < 2){
		            	var upload_dom = $(me._file_upload_dom).find('input[type="hidden"]').attr('name', 'images['+(siblings_length+1)+']').end();
		            	$img_box.parent().append(upload_dom);
		            }

		            // $.ajax({
		            // 	type: 'POST',
		            // 	url: 'http://www.gegejia.com/chunjie/20150214/upload',
		            // 	data: {'ysimg': res.base64},
		            // 	dataType: 'json',
		            // 	success: function(res){
		            // 		console.log(res);

		            // 		//todo 需要返回服务器图片地址，并赋值到input；
		            // 		//$input.val();
		            // 	}
		            // });
		            
		            console.log(res);
		            delete res.base64;
		            console.log(res);
		        });
			});
		},

		/**
		 * 删除图片
		 * @return {[type]} [description]
		 */
		_del_img: function(){
			$form.on('click', '.js_remove', function(){
				var $this = $(this),
					$wrap = $this.parent(),
					$img_box = $this.closest('.img-box'),
					$img = $wrap.find('.js_img'),
					$tips = $wrap.find('.js_tips'),
					$input = $wrap.find('input[type="hidden"]');

				$img.attr('src', '');
				$tips.text('上传照片');
				$input.val('');
				$img_box.removeClass('uploaded');
			});
		},

		/**
		 * 设置退款账户
		 */
		_set_account: function(){
			$form.find('.js_set_account').click(function(){
				$form.submit();

				//todo 需要提交表单，并跳转到退款账户设置页面
			})
		},

		init: function (){
			var me = this;
			//表单提交
			me._apply_submit();
			//退款需求切换
			me._refund_type_switch();
			//图片上传
			me._file_upload();
			//删除图片
			me._del_img();
			//设置退款账户
			me._set_account();
			//格式化退款金额
			me._format_price();
		}

	}

	window.apply = window.apply || {};
	window.apply.init = apply.init;
	apply.init();
	
})(jQuery, undefined);