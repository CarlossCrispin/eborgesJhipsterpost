(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TipoAsesorDeleteController',TipoAsesorDeleteController);

    TipoAsesorDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoAsesor'];

    function TipoAsesorDeleteController($uibModalInstance, entity, TipoAsesor) {
        var vm = this;

        vm.tipoAsesor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoAsesor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
