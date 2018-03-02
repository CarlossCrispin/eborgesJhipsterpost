(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('ActaDialogController', ActaDialogController);

    ActaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Acta', 'Tesis', 'Alumno', 'Unidad'];

    function ActaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Acta, Tesis, Alumno, Unidad) {
        var vm = this;

        vm.acta = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.teses = Tesis.query();
        vm.alumnos = Alumno.query();
        vm.unidads = Unidad.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.acta.id !== null) {
                Acta.update(vm.acta, onSaveSuccess, onSaveError);
            } else {
                Acta.save(vm.acta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eBorgesApp:actaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechatomagrado = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
