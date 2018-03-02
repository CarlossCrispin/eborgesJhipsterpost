(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('ActaDeleteController',ActaDeleteController);

    ActaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Acta'];

    function ActaDeleteController($uibModalInstance, entity, Acta) {
        var vm = this;

        vm.acta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Acta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
