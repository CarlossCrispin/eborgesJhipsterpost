$(document).ready( function() {
	$('#local').dynatable({
		table: {
		  defaultColumnIdStyle: 'trimDash'
		},
		features: {
		  paginate: true,
		  search: true,
		  recordCount: false,
		  perPageSelect: true
		},
		
	});    	
});
