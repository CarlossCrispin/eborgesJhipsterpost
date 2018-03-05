(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TesisDialogController', TesisDialogController);

    TesisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tesis', 'Alumno', 'Grado', 'Departamento', 'Unidad', 'Investigador', 'TipoAsesor',];

    function TesisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tesis, Alumno, Grado, Departamento, Unidad, Investigador, TipoAsesor) {
        var vm = this;

        vm.tesis = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.alumnos = Alumno.query();
        vm.grados = Grado.query();
        vm.departamentos = Departamento.query();
        vm.unidads = Unidad.query();
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
            if (vm.tesis.id !== null) {
                Tesis.update(vm.tesis, onSaveSuccess, onSaveError);
            } else {
                Tesis.save(vm.tesis, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eBorgesApp:tesisUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechadepublicacion = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
