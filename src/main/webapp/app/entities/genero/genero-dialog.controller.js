(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('GeneroDialogController', GeneroDialogController);

    GeneroDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Genero'];

    function GeneroDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Genero) {
        var vm = this;

        vm.genero = entity;
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
            if (vm.genero.id !== null) {
                Genero.update(vm.genero, onSaveSuccess, onSaveError);
            } else {
                Genero.save(vm.genero, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eBorgesApp:generoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
