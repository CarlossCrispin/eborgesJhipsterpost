// $('#local').dynatable({
//     table: {
//       defaultColumnIdStyle: 'trimDash'
//     }
//   });

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

// $(document).ready(function() {
// 	$('#local').DataTable( {
// 			//dom: 'Bfrtip',
			
// 			/*"oTableTools": {
// 					"sSwfPath": "js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
// 				},*/
// 				"language": {
// 					"search": "Buscar:",
// 					"zeroRecords": "No se encontraron datos",
// 					"infoEmpty": "No hay datos para mostrar",
// 					"info": "Mostrando del START al END, de un total de TOTAL entradas",
// 					"paginate": {
// 							"first": "Primeros",
// 							"last": "Ultimos",
// 							"next": "Siguiente",
// 							"previous": "Anterior"
// 					},
// 			},
// 	} );
// } );
