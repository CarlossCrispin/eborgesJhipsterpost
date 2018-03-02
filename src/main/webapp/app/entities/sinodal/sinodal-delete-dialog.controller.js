(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('SinodalDeleteController',SinodalDeleteController);

    SinodalDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sinodal'];

    function SinodalDeleteController($uibModalInstance, entity, Sinodal) {
        var vm = this;

        vm.sinodal = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sinodal.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
