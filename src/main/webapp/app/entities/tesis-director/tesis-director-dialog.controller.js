(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TesisDirectorDialogController', TesisDirectorDialogController);

    TesisDirectorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TesisDirector', 'Tesis', 'Investigador', 'TipoAsesor'];

    function TesisDirectorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TesisDirector, Tesis, Investigador, TipoAsesor) {
        var vm = this;

        vm.tesisDirector = entity;
        vm.clear = clear;
        vm.save = save;
        vm.teses = Tesis.query();
        vm.investigadors = Investigador.query();
        vm.tipoasesors = TipoAsesor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tesisDirector.id !== null) {
                TesisDirector.update(vm.tesisDirector, onSaveSuccess, onSaveError);
            } else {
                TesisDirector.save(vm.tesisDirector, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eBorgesApp:tesisDirectorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
