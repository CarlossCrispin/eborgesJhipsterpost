(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('AlumnoDeleteController',AlumnoDeleteController);

    AlumnoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Alumno'];

    function AlumnoDeleteController($uibModalInstance, entity, Alumno) {
        var vm = this;

        vm.alumno = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Alumno.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
