(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('EspecialidadDeleteController',EspecialidadDeleteController);

    EspecialidadDeleteController.$inject = ['$uibModalInstance', 'entity', 'Especialidad'];

    function EspecialidadDeleteController($uibModalInstance, entity, Especialidad) {
        var vm = this;

        vm.especialidad = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Especialidad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
