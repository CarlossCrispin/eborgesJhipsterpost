(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('AlumnoDialogController', AlumnoDialogController);

    AlumnoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Alumno', 'Departamento', 'Grado', 'Genero'];

    function AlumnoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Alumno, Departamento, Grado, Genero) {
        var vm = this;

        vm.alumno = entity;
        vm.clear = clear;
        vm.save = save;
        vm.departamentos = Departamento.query();
        vm.grados = Grado.query();
        vm.generos = Genero.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.alumno.id !== null) {
                Alumno.update(vm.alumno, onSaveSuccess, onSaveError);
            } else {
                Alumno.save(vm.alumno, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eBorgesApp:alumnoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
