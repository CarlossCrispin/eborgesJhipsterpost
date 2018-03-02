(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('UnidadDeleteController',UnidadDeleteController);

    UnidadDeleteController.$inject = ['$uibModalInstance', 'entity', 'Unidad'];

    function UnidadDeleteController($uibModalInstance, entity, Unidad) {
        var vm = this;

        vm.unidad = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Unidad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
