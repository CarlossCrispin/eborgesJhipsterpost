(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('EspecialidadDialogController', EspecialidadDialogController);

    EspecialidadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Especialidad'];

    function EspecialidadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Especialidad) {
        var vm = this;

        vm.especialidad = entity;
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
            if (vm.especialidad.id !== null) {
                Especialidad.update(vm.especialidad, onSaveSuccess, onSaveError);
            } else {
                Especialidad.save(vm.especialidad, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eBorgesApp:especialidadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
