$(document).ready(
	function(){
		$('#news').innerfade({
			animationtype: 'slide',
			speed: 750,
			timeout: 2000,
			type: 'random',
			containerheight: '1em'
		});

		$('#portfolio').innerfade({
			speed: 'slow',
			timeout: 4000,
			type: 'sequence',
			containerheight: '220px'
		});

		$('.fade').innerfade({
			speed: 'slow',
			timeout: 1000,
			type: 'sequence',
			containerheight: '1.5em'
		});
	}
);