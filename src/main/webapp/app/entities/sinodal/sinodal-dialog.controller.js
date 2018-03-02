(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('SinodalDialogController', SinodalDialogController);

    SinodalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sinodal', 'Acta', 'TipoAsesor', 'Investigador'];

    function SinodalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sinodal, Acta, TipoAsesor, Investigador) {
        var vm = this;

        vm.sinodal = entity;
        vm.clear = clear;
        vm.save = save;
        vm.actas = Acta.query();
        vm.tipoasesors = TipoAsesor.query();
        vm.investigadors = Investigador.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sinodal.id !== null) {
                Sinodal.update(vm.sinodal, onSaveSuccess, onSaveError);
            } else {
                Sinodal.save(vm.sinodal, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eBorgesApp:sinodalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
