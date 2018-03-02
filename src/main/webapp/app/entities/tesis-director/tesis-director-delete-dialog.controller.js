(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TesisDirectorDeleteController',TesisDirectorDeleteController);

    TesisDirectorDeleteController.$inject = ['$uibModalInstance', 'entity', 'TesisDirector'];

    function TesisDirectorDeleteController($uibModalInstance, entity, TesisDirector) {
        var vm = this;

        vm.tesisDirector = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TesisDirector.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
