(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('GradoDialogController', GradoDialogController);

    GradoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Grado'];

    function GradoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Grado) {
        var vm = this;

        vm.grado = entity;
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
            if (vm.grado.id !== null) {
                Grado.update(vm.grado, onSaveSuccess, onSaveError);
            } else {
                Grado.save(vm.grado, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eBorgesApp:gradoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
