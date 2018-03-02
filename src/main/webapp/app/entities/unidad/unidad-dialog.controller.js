(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('UnidadDialogController', UnidadDialogController);

    UnidadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Unidad'];

    function UnidadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Unidad) {
        var vm = this;

        vm.unidad = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.unidad.id !== null) {
                Unidad.update(vm.unidad, onSaveSuccess, onSaveError);
            } else {
                Unidad.save(vm.unidad, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eBorgesApp:unidadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
