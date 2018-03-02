(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TipoAsesorDialogController', TipoAsesorDialogController);

    TipoAsesorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoAsesor'];

    function TipoAsesorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoAsesor) {
        var vm = this;

        vm.tipoAsesor = entity;
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
            if (vm.tipoAsesor.id !== null) {
                TipoAsesor.update(vm.tipoAsesor, onSaveSuccess, onSaveError);
            } else {
                TipoAsesor.save(vm.tipoAsesor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eBorgesApp:tipoAsesorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
