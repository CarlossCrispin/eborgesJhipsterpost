// (function() {
//     'use strict';

//     angular
//         .module('eBorgesApp')
//         .controller('TesisDialogController', TesisDialogController);

//     TesisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tesis', 'Alumno', 'Grado', 'Departamento', 'Unidad', 'Investigador', 'TipoAsesor', 'Genero'];

//     function TesisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tesis, Alumno, Grado, Departamento, Unidad, Investigador, TipoAsesor, Genero) {
//         var vm = this;

//         vm.tesis = entity;
//         vm.clear = clear;
//         vm.datePickerOpenStatus = {};
//         vm.openCalendar = openCalendar;
//         vm.save = save;
//         vm.alumnos = Alumno.query();
//         vm.grados = Grado.query();
//         vm.departamentos = Departamento.query();
//         vm.unidads = Unidad.query();
//         vm.investigadors = Investigador.query();
//         vm.tipoasesors = TipoAsesor.query();

//         vm.investigador = entity;
//         vm.generos = Genero.query();
       


//         $timeout(function (){
//             angular.element('.form-group:eq(1)>input').focus();
//         });

//         function clear () {
//             $uibModalInstance.dismiss('cancel');
//         }

//         function save () {
//             vm.isSaving = true;
//             if (vm.tesis.id !== null) {
//                 Tesis.update(vm.tesis, onSaveSuccess, onSaveError);
//             } else {
//                 Tesis.save(vm.tesis, onSaveSuccess, onSaveError);
//             }
//             if (vm.investigador.id !== null) {
//                 Investigador.update(vm.investigador, onSaveSuccess, onSaveError);
//             } else {
//                 Investigador.save(vm.investigador, onSaveSuccess, onSaveError);
//             }
//         }

//         function onSaveSuccess (result) {
//             $scope.$emit('eBorgesApp:tesisUpdate', result);
//             $uibModalInstance.close(result);
//             vm.isSaving = false;
//             $scope.$emit('eBorgesApp:investigadorUpdate', result);
//             $uibModalInstance.close(result);
//             vm.isSaving = false;
//         }

//         function onSaveError () {
//             vm.isSaving = false;
//         }

//         vm.datePickerOpenStatus.fechadepublicacion = false;

//         function openCalendar (date) {
//             vm.datePickerOpenStatus[date] = true;
//         }
        
//     }
// })();
(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TesisDialogController', TesisDialogController);

    TesisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tesis', 'Alumno', 'Grado', 'Departamento', 'Unidad', 'Investigador', 'TipoAsesor','Principal', 'LoginService', '$state'];

    function TesisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tesis, Alumno, Grado, Departamento, Unidad, Investigador, TipoAsesor,Principal, LoginService, $state) {
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
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        
        
    //     $scope.alumno =  "First";
          
    //   vm.tesis.alumno=$scope.alumno;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        
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