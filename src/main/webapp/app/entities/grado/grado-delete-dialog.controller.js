(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('GradoDeleteController',GradoDeleteController);

    GradoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Grado'];

    function GradoDeleteController($uibModalInstance, entity, Grado) {
        var vm = this;

        vm.grado = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Grado.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
