(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('InvestigadorDialogController', InvestigadorDialogController);

    InvestigadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Investigador', 'Genero', 'Grado', 'Departamento'];

    function InvestigadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Investigador, Genero, Grado, Departamento) {
        var vm = this;

        vm.investigador = entity;
        vm.clear = clear;
        vm.save = save;
        vm.generos = Genero.query();
        vm.grados = Grado.query();
        vm.departamentos = Departamento.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.investigador.id !== null) {
                Investigador.update(vm.investigador, onSaveSuccess, onSaveError);
            } else {
                Investigador.save(vm.investigador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eBorgesApp:investigadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
