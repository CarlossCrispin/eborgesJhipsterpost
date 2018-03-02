(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('InvestigadorDeleteController',InvestigadorDeleteController);

    InvestigadorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Investigador'];

    function InvestigadorDeleteController($uibModalInstance, entity, Investigador) {
        var vm = this;

        vm.investigador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Investigador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
