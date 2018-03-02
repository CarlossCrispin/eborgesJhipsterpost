(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TesisDeleteController',TesisDeleteController);

    TesisDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tesis'];

    function TesisDeleteController($uibModalInstance, entity, Tesis) {
        var vm = this;

        vm.tesis = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tesis.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
